Title: Tree shaking squint on the server with esbuild
Date: 2026-09-25
Tags: clojure, squint, babashka, hyper
Description: How hyper uses esbuild on the JVM to serve only the squint core functions a page uses.

[Hyper](https://github.com/dynamic-alpha/hyper) is a server-rendered web
framework for Clojure. You can write pages using hiccup, and hyper renders them
on the server to HTML. Incremental updates to the HTML reach the browser through
server-sent events (SSE), using [Datastar](https://data-star.dev/).

Here is a basic counter example:

```clojure
(defn home-page [req]
  (let [count* (h/tab-cursor :count 0)]
    [:div
     [:h1 "Count: " @count*]
     [:button {:data-on:click (h/action (swap! count* inc))}
      "Increment"]]))
```

Some interactions can be handled directly in the browser. A hint below a search
field, for example, can update as you type without a round trip to the server.
Datastar stores client state in so called signals and evaluates JavaScript expressions in
`data-*` attributes. With hyper's `h/expr` macro, you can write those expressions
in Clojure syntax:

```clojure
(let [query* (h/signal :query "")]
  [:div
   [:input {:data-bind query*}]
   [:span {:data-text (h/expr (if (zero? (.-length @query*))
                                "Type to search"
                                (str "Searching for " (subs @query* 0 20))))}]])
```

The `:data-bind` attribute binds the input to the `query` signal. Inside
`h/expr`, `@query*` compiles to Datastar's `$query` expression. During macro expansion,
[Squint](https://github.com/squint-cljs/squint) compiles the expression to
JavaScript:

```js
((($query.length === 0)) ? ("Type to search") : (`${"Searching for "}${hyper_sc.subs($query, 0, 20)}`))
```

## Tree-shaking on the server

The compiled expression calls `subs` from squint's core library, which hyper
loads as `window.hyper_sc`. The full library is about 128 KB, or 33 KB gzipped.
With esbuild, hyper can remove unused core functions through tree shaking
and serve a smaller bundle. Although esbuild is written in Go, it is usually
invoked through its npm package. For hyper, it would be convenient to call it
directly from Clojure.

Enter [babashka.esbuild](https://github.com/babashka/babashka.esbuild)!
This library calls esbuild as a shared library through [babashka.ffi](https://github.com/babashka/ffi).
It works on the JVM and in babashka:

```clojure
{:deps {io.github.squint-cljs/squint {:mvn/version "0.14.210"}
        org.babashka/esbuild {:mvn/version "0.1.1"}}}
```

Here is how to compile a squint function to a JavaScript module and bundle it
with esbuild:

```clojure
(require '[squint.compiler :as squint]
         '[babashka.esbuild :as esbuild]
         '[clojure.java.io :as io])

(spit "core.js" (slurp (io/resource "squint/core.js")))

(spit "main.js"
      (squint/compile-string
       "(defn search-hint [query]
          (if (zero? (.-length query))
            \"Type to search\"
            (str \"Searching for \" (subs query 0 20))))"
       {:import-maps {"squint-cljs/core.js" "./core.js"}}))

(-> (esbuild/build {:entry-points ["main.js"]
                    :bundle true
                    :format :esm
                    :minify true})
    :outputs first :contents)
```

The compiled `main.js` looks like this:

```js
import * as squint_core from './core.js';
var search_hint = function (query) {
if ((query.length === 0)) {
return "Type to search"} else {
return `${"Searching for "}${squint_core.subs(query, 0, 20)}`};

};

export { search_hint }
```

After bundling and minification, esbuild returns:

```js
function e(t,n,r){return t.substring(n,r)}var u=function(t){return t.length===0?"Type to search":`Searching for ${e(t,0,20)}`};export{u as search_hint};
```

The bundle contains just `subs` and the `search-hint` function: 152 bytes in
total (145 gzipped). The full core library alone was 128,117 bytes (32,650 gzipped).

## In hyper

In hyper, the compiled `h/expr` expressions end up in HTML attributes. To find out which core functions
these expressions need, hyper uses information returned by the squint compiler.
Since version 0.14.210, squint includes the names of the core functions used
during compilation:

```clojure
(:used-core-vars (squint/compile* "(str \"Searching for \" (subs query 0 20))"))
;;=> #{"subs"}
```

Hyper collects these names from every `h/expr` and every `defc` component in the
application. At startup (when using the `:tree-shake?` option), it passes
esbuild an entry module that re-exports those functions:

```js
export { subs } from './core.js';
```

Hyper serves the resulting bundle at `/hyper/squint-core.js` and exposes it as
`window.hyper_sc`. The `defc` components use the same object:

```js
const $sc = window.hyper_sc;
```

The URL's `v` parameter is a hash of `core.js` and the names of the core
functions used by the application. The bundle stays the same for a given hash,
so hyper serves it with a one-year cache lifetime:

```
/hyper/squint-core.js?v=3f9a1c2b7d4e
Cache-Control: public, max-age=31536000, immutable
```

When a deployment changes the set of core functions in use, the hash changes.
The page records its current version in a data attribute:

```html
<div id="hyper-app" data-hyper-squint-version="1a2b3c4d">
```

If a server update contains a different version, the page reloads to fetch the
new bundle.

## Using it

To enable tree shaking in hyper, add the esbuild dependency and enable native
access for the JVM:

```clojure
{:deps {org.babashka/esbuild {:mvn/version "0.1.1"}}
 :aliases {:run {:jvm-opts ["--enable-native-access=ALL-UNNAMED"]}}}
```

Then pass `:tree-shake? true` when creating the handler:

```clojure
(h/create-handler #'routes :tree-shake? true)
```

Since the dependency is optional, hyper serves squint's full `core.js` by default.

Note that hyper has `h/defc` to define webcomponents using Squint too, which also
work with tree-shaking, but I left that out of this blog to keep it short and
readable.

## Wrapping up

As you can see, we can write server-side rendered HTML applications and have
some fun using ClojureScript in the shape of Squint too, while getting very
reasonable JS compilation sizes. And we don't have to think about it at all,
just deploy to production with `:tree-shake? true` and done! No build process.
