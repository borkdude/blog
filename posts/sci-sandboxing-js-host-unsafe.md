Title: Sandboxing SCI on a JavaScript host
Date: 2026-11-17
Tags: clojure, clojurescript, SCI, security
Description: SCI sandbox on JavaScript considered unsafe

## SCI for sandboxing

[SCI](https://github.com/babashka/sci) is a Clojure interpreter that is mostly known for its use in [babashka](https://github.com/babashka/babashka), a Clojure environment for scripting. SCI gives access to Clojure scripting in environments where it would otherwise be difficult. Babashka runs as a GraalVM native-image, which until recently did not allow dynamic class loading. SCI, being an interpreter rather than a compiler that targets bytecode, still offers you Clojure evaluation. SCI can also be used in the browser, where ClojureScript `eval` normally does not reach, unless you're using its bootstrapped version and are willing to accept the bundle size that comes with that. [Scittle](https://github.com/babashka/scittle) is an example of a SCI-based environment for the browser. It allows you to run Clojure code directly in script tags.

Another use that SCI is designed for is sandboxing. A sandbox allows you to run code in a restraining environment. SCI achieves this by limiting the set of host functions (via the `:namespaces` option) and classes (via the `:classes` option) you can use. SCI has a fairly safe set of defaults. For example, they do not allow you to read or write files.

Most people who use SCI for sandboxing use it in a JVM-based environment. This article is not about that. Using the sandbox on a JS host like Node.js should be considered unsafe. This came to light through a report about [bytefield-svg](https://github.com/Deep-Symmetry/bytefield-svg), which uses SCI and is served by [Kroki](https://kroki.io).

## SCI sandboxing on JS should be considered unsafe

This is why SCI sandboxing on JS should be considered unsafe today.
In SCI, a handful of `clojure.core` functions that are enabled by default can read or write any property of a JS value.
Take `aget`, a function that is essential in ClojureScript, for reading from an array. When you evaluate:

``` clojure
(def str-ctor (aget "x" "constructor"))
```

you'll get the constructor for a string, `String`, which is itself a function in JS. When you in turn get the constructor from that using `(aget str-ctor "constructor")` you'll get back the `Function` constructor. Using that you can evaluate anything you want.

```clojure
(let [F (aget (aget "x" "constructor") "constructor")
      f (F "return 'ESCAPE_OK_' + (6*7)")]
  (f))
;;=> "ESCAPE_OK_42"
```

A simpler variant of this is:

```clojure
(def f ((type (type "x")) "return 1 + 1"))
(f) ;;=> 2
```

On Node.js such a function has access to `process`. You can already guess where this is going right?

``` clojure
(let [F (aget (aget "x" "constructor") "constructor")]
  ((F "return process.version")))
;;=> "v22.11.0"
```

Using `process` you can spawn any process on your system to do harmful stuff.


In SCI `js/...` globals and dot interop like `(.-constructor x)` are blocked in a
default context. But SCI doesn't protect you from the above "jailbreak".


Since functions like `aget` and `type` are essential for CLJS to operate (note that `aget` is not only used for arrays, but also, against its design, to get fields from objects), I have decided against changing this since that would break existing programs.

## Security on the host level

The best way to sandbox an environment is on the host level. In `Node.js` you can limit evaluation of arbitrary code with this option:

```bash
NODE_OPTIONS=--disallow-code-generation-from-strings node your-app.js
```

This makes `new Function(...)` and `eval(...)` throw.

SCI keeps working with this flag, but you will no longer have JIT (which was introduced a couple of versions ago).

In the browser, a Content Security Policy without `'unsafe-eval'` has the same effect.

On top of that, you can remove the functions that allow the unsafe evaluation from the context:

```clojure
(sci/init {:namespaces {'clojure.core {'aget nil
                                       'aset nil
                                       'unchecked-get nil
                                       'unchecked-set nil
                                       'type nil}}})
```

or change them to alternative behaviors. E.g. you could change `aget`, `aset`, `unchecked-get` and `unchecked-set` to functions that only accept a numeric index, and `type` to a function that throws when it would return `Function`. Checking that the first argument is an array is not enough: `(aget arr "constructor")` still reaches `Array`.

Note that any macros that rely on these functions will break if you override the functions with `nil`. E.g `amap` breaks when you remove `aget`.

## Closing thoughts

In Clojurians I did a poll on how many people were using sandboxing with SCI on JS. All people who used sandboxing were doing so on a JVM-based host. Therefore, I declare SCI sandboxing bankruptcy on JS hosts by default.
The onus of making the behavior safe is on the user. I documented how to do so in [SCI's README](https://github.com/babashka/sci#sandboxing-on-a-javascript-host-is-unsafe). Contributions to that documentation are welcome.
