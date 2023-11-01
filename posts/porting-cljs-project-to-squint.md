Title: Porting a ClojureScript project to Squint
Date: 2023-11-01
Tags: clojure, squint
Description: Porting a ClojureScript project to Squint


## Squint

[Squint](https://github.com/squint-cljs/squint) is a ClojureScript syntax to JS
compiler. I deliberately don't call it a CLJS compiler, since it has some
differences with CLJS: all data structures in squint are just JS objects, arrays
and iterators/generators. The standard library mimics ClojureScript in that it
never mutates if the standard library doesn't mutate, e.g. `assoc` returns a new
object and doesn't mutate the object argument.  This approach has a few benefits
and of course a few drawbacks. Some of the benefits are that JS interop becomes
considerably easier, the bundle size becomes way smaller, in some cases
performance is better (but as always, this is very contextual) and sharing a
library to NPM to be consumed by other JavaScript projects becomes easier as
well. A few drawbacks of this approach are that we lose structural sharing of
CLJS data structures (the squint lib mostly uses copy-on-write like how CLJS
once started) and you lose the fast equality checks you can get from structural
sharing.  Note that there is also squint's sister project,
[cherry](https://github.com/squint-cljs/cherry) which, does use CLJS's immutable
data structures, but you get a minimum of 350kb project size due to CLJS's
standard library being bundled with it. This may or may not be worth it and may
offset the drawbacks of squint for you. Let's just see how far we can get with
just squint in porting a ClojureScript project.

## Clojure-mode

As a case study in this blog, we're going to look at
[clojure-mode](https://github.com/nextjournal/clojure-mode), a project by
Nextjournal which offers a Clojure mode for
[CodeMirror](https://codemirror.net/) 6. A request was made some time ago to
make this library usable directly from JavaScript as an NPM library. Since most
of the code in this project is Java interop (using the excellent
[js-interop](https://github.com/applied-science/js-interop) library it seemed
like a good candidate for a squint port. Martin Kavalar of Nextjournal asked me
if I could do this in a way such that the original ClojureScript library would
still be usable from ClojureScript as well. One of the ways to accomplish this
is to use `.cljc` files.

## Reader conditionals

When porting the project, I added features and fixed bugs in squint to
accomodate targeting both CLJS and squint. So almost every `.cljs` file was renamed to a `.cljc` file and CLJS-specific code like `goog-define` was re-implemented using a squint override:

``` clojure
#?(:squint (def node-js? (some? js/globalThis.process))
   :cljs (goog-define node-js? false))
```

The above expression checks whether we are inside NodeJS, which arguably could have been done without `goog-define` but my goal was to leave the original code as is.

## Watcher

To ease developing, I added a watcher to the squint compiler, which reads the `squint.edn` file in your project and then automatically compiles all `.cljs` and `.cljc` files in your project to a corresponding `.mjs` file in an `:output-dir` (the extension is configurable):

`squint.edn`:
``` clojure
{:paths ["src-shared" "src-squint" "test"]
 :output-dir "dist"}
```

The watcher can be started with `npx squint watch` (or `yarn squint watch` or
`bun squint watch`).  In `src-shared` I put the files which are ported from the
`src` directory, while `src-squint` has extra files that are specific for the
squint port. The `src` directory contains CLJS-specific files that are not in
scope for test.

When running `npx squint compile` all the files in `:paths` are re-compiled,
which is handy whe you want to distribute the project to NPM.

## Stub macros

In a lot of places the `js-interop` library was used to create literals, functions with JS object destructuring, etc, most of which squint already does out of the box.
To accomodate this, I wrote a bunch of macros which basically did nothing and replaces the `j` alias with a namespace which mocks the `js-interop` library:

``` clojure
(ns nextjournal.clojure-mode
  (:require ...
            #?@(:squint []
                :cljs [[applied-science.js-interop :as j]])
            ...)
  #?(:squint (:require-macros [applied-science.js-interop :as j])))
```

In squint, macros are (currently) loaded via the `:require-macros` section in a
`ns` form. The squint compiler looks for a `.cljc` file within `:paths`, the
loads this file using [SCI](https://github.com/babashka/sci), makes the
transformation and the resumes compilation of the transformed form. The reason
SCI is used is that squint doesn't know Clojure's data structures at runtime,
but you can still use them at compile time. This model is pretty similar how
CLJS executes macros on the JVM.

The stub macros for `js-interop` look like this:

``` clojure
(ns applied-science.js-interop
  (:refer-clojure :exclude [defn get get-in fn let select-keys assoc!]))

(defmacro lit [x] x)

(defmacro defn [& body]
  `(clojure.core/defn ~@body))

(defmacro get-in [& body]
  `(clojure.core/get-in ~@body))

(defmacro fn [& body]
  `(clojure.core/fn ~@body))

(defmacro let [& body]
  `(clojure.core/let ~@body))

(defmacro call-in [obj path & fs]
  `(.. ~obj ~@(map #(symbol (str "-" %)) path) ~@(map list fs)))

...
```

As you can see, most of the macros just defer to squint's default behavior. But
a macro like `call-in`, which isn't part of squint, transforms to just raw JS
interop.
