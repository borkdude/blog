Title: Embedding cherry for runtime CLJS compilation
Date: 2023-03-30
Tags: clojure, cherry

[Cherry](https://github.com/squint-cljs/cherry) is a new, experimental
ClojureScript compiler.  One of the main ideas behind cherry is to reduce
friction between ClojureScript and JS tooling. It can be used as a standalone
compiler directly as an NPM package without a JVM.

Since v0.0.1, cherry can be embedded into an existing vanilla CLJS or shadow-cljs project.
To facilitate this, cherry exposes the `cherry.embed` namespace. It contains the following:

- `preserve-ns`: a macro to define a CLJS namespace as globals, so cherry output
  can access functions after advanced compilation.
- `eval-form`, `eval-string`, `compile-form` and `compile-string` functions to evaluate or compile an s-expr or string

An example shadow-cljs project:

`deps.edn`:

``` clojure
{:aliases {:cherry {:extra-deps {io.github.squint-cljs/cherry
                                 {:git/sha "4e948708cb20ab0a1a892c30fe87842a2efcc380"}}}
           :shadow {:extra-deps {thheller/shadow-cljs {:mvn/version "2.22.9"}}
                    :main-opts ["-m" "shadow.cljs.devtools.cli"]}}}
```

`package.json`:

``` json
{"type": "module"}
```

`shadow-cljs.edn`
``` clojure
{:deps true
 :builds
 {:cli
  {:target :esm
   :runtime :node
   :output-dir "out"
   :modules {:eval {:init-fn my.cherry/init}}
   :build-hooks [(shadow.cljs.build-report/hook
                  {:output-to "report.html"})]}}}

```

`src/my/cherry.cljs`:
``` clojure
(ns my.cherry
  (:require [cherry.embed :as cherry]))

(cherry/preserve-ns 'cljs.core)
(cherry/preserve-ns 'clojure.string)

(defn init []
  (let [[_ expr] (.slice js/process.argv 2)]
    (cherry/eval-string expr)))
```

When compiling this with `clojure -M:cherry:shadow release cli`, we get a single
`out/eval.js` file of about 550kb of which roughly 330kb comes from preserving
the `cljs.core` namespace.

We can invoke it with:

``` shell
$ node out/eval.js -e '(do (prn :hello) (js/console.log (clojure.string/join , [1 2 3])))'
:hello
123
```

Note that you can enable `eval` in your CLJS/shadow app with:

``` clojure
(set! *eval* cherry/eval-form)
```

Full example:

``` clojure
(ns my-shadow.app
  (:require [cherry.embed :as cherry]))

(cherry/preserve-ns 'cljs.core)

(set! *eval* cherry/eval-form)

(def x (eval '(+ 1 2 3)))
```

Cherry is still experimental and looking for feedback. Please try it out and join the Clojurians Slack channel for discussion.
