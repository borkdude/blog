Title: OSS updates February 2023
Date: 2023-03-01
Tags: clojure, oss updates
Description: My Clojure OSS updates for February 2023

In this post I'll give updates about open source I worked on during February 2023.

## Sponsors

But first off, I'd like to thank all the sponsors and contributors that make
this work possible! Top sponsors:

- [Clojurists Together](https://clojuriststogether.org/)
- [Roam Research](https://roamresearch.com/)
- [Nextjournal](https://nextjournal.com/)
- [Toyokumo](https://toyokumo.co.jp/)
- [Cognitect](https://www.cognitect.com/)
- [Kepler16](https://kepler16.com/)
- [Adgoji](https://www.adgoji.com/)

If you want to ensure that the projects I work on are sustainably maintained,
you can sponsor this work in the following ways. Thank you!

- [Github Sponsors](https://github.com/sponsors/borkdude)
- The [Babaska](https://opencollective.com/babashka) or [Clj-kondo](https://opencollective.com/clj-kondo) OpenCollective
- [Ko-fi](https://ko-fi.com/borkdude)
- [Patreon](https://www.patreon.com/borkdude)
- [Clojurists Together](https://www.clojuriststogether.org/)

If you're used to sponsoring through some other means which isn't listed above, please get in touch.

> **Attention**
<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/1/17/Warning.svg/260px-Warning.svg.png" width="20px">

> If you are using Github Sponsors and are making payments via Paypal, please
update to a creditcard since Github Sponsors won't support Paypal from February
23rd 2023. Read their statement
[here](https://github.blog/changelog/2023-01-23-github-sponsors-will-stop-supporting-paypal/). If
you are not able to pay via a creditcard, you can still sponsor me via one of
the ways mentioned above.

## Projects

<!--

sources: https://github.com/borkdude
local ~/dev and ~/dev/babashka dir (since github doesn't show all repos)

-->

## [babashka](https://github.com/babashka/babashka): native, fast starting Clojure interpreter for scripting

New release: 1.2.174

Highlights:

- Use GraalVM 22.3.1 on JDK 19.0.2. This adds virtual thread support. See [demo](https://twitter.com/borkdude/status/1572222344684531717).
- Add more `java.time` and related classes with the goal of supporting [juxt.tick](https://github.com/juxt/tick) ([issue](https://github.com/juxt/tick/issues/86))

See the complete [CHANGELOG](https://github.com/babashka/babashka/blob/master/CHANGELOG.md).

#### Babashka compatibility in external libs

I worked together with the maintainers of the following libraries to make them compatible with babashka:

- [kaocha](https://github.com/lambdaisland/kaocha): test runner
- [multiformats](https://github.com/greglook/clj-multiformats): Clojure(Script) implementations of the self-describing multiformat specs

### [http-client](https://github.com/babashka/http-client): Babashka's http-client

The `babashka.http-client` namespace mostly replaces
[babashka.curl](https://github.com/babashka/babashka.curl).

This month support for `:multipart` uploads was added, mostly based on and
inspired by [hato](https://github.com/gnarroway/hato)'s implementation.

### [clj-kondo](https://github.com/clj-kondo/clj-kondo): Static analyzer and linter for Clojure code that sparks joy

New release: 2023.02.17

Some highlights:

- [#1976](https://github.com/clj-kondo/clj-kondo/issues/1976): warn about using multiple bindings after varargs (`&`) symbol in fn syntax
- Add arity checks for core `def`
- [#1954](https://github.com/clj-kondo/clj-kondo/issues/1954): new `:uninitialized-var` linter. See [docs](https://github.com/clj-kondo/clj-kondo/blob/master/doc/linters.md#uninitialized-var).
- [#1996](https://github.com/clj-kondo/clj-kondo/issues/1996): expose `hooks-api/resolve`. See [docs](https://github.com/clj-kondo/clj-kondo/blob/master/doc/hooks.md#api).

[Check the
changelog](https://github.com/clj-kondo/clj-kondo/blob/master/CHANGELOG.md) for
details.

### [SCI](https://github.com/babashka/sci): Configurable Clojure/Script interpreter suitable for scripting and Clojure DSLs

This month:

- Adding JS libraries to a SCI context. See [docs](https://github.com/babashka/sci#javascript-libraries)
- Keyword arguments as map support for CLJS
- Making loading of libraries thread-safe in JVM
- Several fixes with respect to `deftype` and `toString` + `equals`

### [fs](https://github.com/babashka/fs): File system utility library for Clojure

Highlights:

- several `xdg-*-home` helper functions, contributed by [@eval](https://github.com/eval)
- `babashka.fs/zip`  now takes a `:root` option to elide a parent folder or folders.
E.g. `(fs/zip "src" {:root "src"})` will zip `src/foo.clj` into the zip file under `foo.clj`.

See [changelog](https://github.com/babashka/fs/blob/master/CHANGELOG.md#changelog) for more details.

### [process](https://github.com/babashka/process): Clojure library for shelling out / spawning sub-processes

This month I looked into wrapping output of processes with a prefix so when ran in parallel, you can easily distuingish them. A preliminary solution is in [this thread](https://github.com/babashka/process/discussions/102#discussioncomment-4903758).

### [pod-babashka-lanterna](https://github.com/babashka/pod-babashka-lanterna): Interact with clojure-lanterna from babashka

A very experimental 0.0.1 release was published.

You can try it out by playing tetris in the console with babashka:

``` clojure
bb -Sdeps '{:deps {io.github.borkdude/console-tetris {:git/sha "2d3bee34ea93c84608c7cc5994ae70480b2df54c"}}}' -m tetris.core
```

### [nbb](https://github.com/babashka/nbb): Scripting in Clojure on Node.js using SCI

Finally nbb has gotten support for passing maps to keyword argument functions:

``` clojure
(defn foo [& {:keys [a b c]}])
(foo :a 1 :b 2 :c 3)
(foo {:a 1 :b 2 :c 3})
```

Several other improvements have been made in the area of macros and resolving JS
library references and resolving dependencies in an `nbb.edn` file, relative to
an invoked script which is not in the current directory.

See changelogs [here](https://github.com/babashka/nbb/blob/main/CHANGELOG.md).

### [joyride](https://github.com/BetterThanTomorrow/joyride): VSCode CLJS scripting and REPL (via [SCI](https://github.com/babashka/sci))

This month I contributed a built-in version of
[rewrite-clj](https://github.com/clj-commons/rewrite-clj) to joyride, so
joyriders can rewrite their code from within VSCode.


### [cljs-showcase](https://github.com/borkdude/cljs-showcase): Showcase CLJS libs using SCI

A little project to show how you can use SCI to showcare your CLJS library in an interactive way.

### Brief mentions

The following projects also got updates, mostly in the form of maintenance and
performance improvements. This post would get too long if I had to go into
detail about them, so I'll briefly mention them in random order:

- [CLI](https://github.com/babashka/cli): Turn Clojure functions into CLIs!
- [quickdoc](https://github.com/borkdude/quickdoc): Quick and minimal API doc generation for Clojure
- [process](https://github.com/babashka/process): Clojure library for shelling out / spawning sub-processes
- [rewrite-clj](https://github.com/clj-commons/rewrite-clj): Rewrite Clojure code and edn
- [sql pods](https://github.com/babashka/babashka-sql-pods): babashka pods for SQL databases
- [squint](https://github.com/squint-cljs/squint): CLJS _syntax_ to JS compiler

### Other projects

These are some of the other projects I'm involved with but little to no activity
happened in the past month.

- [carve](https://github.com/borkdude/carve) - Remove unused Clojure vars
- [deps.clj](https://github.com/borkdude/deps.clj): A faithful port of the clojure CLI bash script to Clojure
- [edamame](https://github.com/borkdude/edamame): Configurable EDN/Clojure parser with location metadata
- [cherry](https://github.com/squint-cljs/cherry): Experimental ClojureScript to ES6 module compiler
- [grasp](https://github.com/borkdude/grasp): Grep Clojure code using clojure.spec regexes
- [jet](https://github.com/borkdude/jet): CLI to transform between JSON, EDN, YAML and Transit using Clojure
- [scittle](https://github.com/babashka/scittle): Execute Clojure(Script) directly from browser script tags via SCI
- [neil](https://github.com/babashka/neil): A CLI to add common aliases and features to deps.edn-based projects
- [quickblog](https://github.com/borkdude/quickblog): Light-weight static blog engine for Clojure and babashka
- [sci.configs](https://github.com/babashka/sci.configs): A collection of ready to be used SCI configs
- [lein2deps](https://github.com/borkdude/lein2deps): leiningen to deps.edn converter
- [4ever-clojure](https://github.com/oxalorg/4ever-clojure) - Pure CLJS version of 4clojure, meant to run forever!
- [instaparse-bb](https://github.com/babashka/instaparse-bb)
- [pod-babashka-buddy](https://github.com/babashka/pod-babashka-buddy): A pod around buddy core (Cryptographic Api for Clojure).

<!-- - [tools-deps-native](https://github.com/babashka/tools-deps-native): Run tools.deps as a native binary-->
<!-- - [tools.bbuild](https://github.com/babashka/tools.bbuild): Library of functions for building Clojure projects-->
