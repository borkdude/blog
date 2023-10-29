Title: OSS updates September 2023
Date: 2023-10-31
Tags: clojure, oss updates
Description: My Clojure OSS updates for October 2023

In this post I'll give updates about open source I worked on during October 2023.

To see previous OSS updates, go [here](https://blog.michielborkent.nl/tags/oss-updates.html).

## Sponsors

I'd like to thank all the sponsors and contributors that make
this work possible! Open the details section for more info.

<details>
<summary>Sponsor info</summary>
Top sponsors:

- [Clojurists Together](https://clojuriststogether.org/)
- [Roam Research](https://roamresearch.com/)
- [Nextjournal](https://nextjournal.com/)
- [Toyokumo](https://toyokumo.co.jp/)
- [Cognitect](https://www.cognitect.com/)
- [Kepler16](https://kepler16.com/)
- [Pitch](https://github.com/pitch-io)

If you want to ensure that the projects I work on are sustainably maintained,
you can sponsor this work in the following ways. Thank you!

- [Github Sponsors](https://github.com/sponsors/borkdude)
- The [Babaska](https://opencollective.com/babashka) or [Clj-kondo](https://opencollective.com/clj-kondo) OpenCollective
- [Ko-fi](https://ko-fi.com/borkdude)
- [Patreon](https://www.patreon.com/borkdude)
- [Clojurists Together](https://www.clojuriststogether.org/)

If you're used to sponsoring through some other means which isn't listed above, please get in touch.

On to the projects that I've been working on!
</details>

<!--

sources: https://github.com/borkdude
local ~/dev and ~/dev/babashka dir (since github doesn't show all repos)

-->

## Updates

drwxr-xr-x@  55 borkdude  staff   1760 Oct 27 17:36 clj-kondo
drwxr-xr-x   16 borkdude  staff    512 Oct 27 15:55 hoplon-javelin
drwxr-xr-x  172 borkdude  staff   5504 Oct 27 15:50 .
drwxr-xr-x@  27 borkdude  staff    864 Oct 27 15:06 scittle
drwxr-xr-x@  47 borkdude  staff   1504 Oct 27 15:05 nbb
drwxr-xr-x@  25 borkdude  staff    800 Oct 27 15:00 sci.configs
drwxr-xr-x@  78 borkdude  staff   2496 Oct 27 14:59 babashka
drwxr-xr-x   58 borkdude  staff   1856 Oct 26 16:44 squint

Here are updates about the projects/libraries I've worked on last month.

- [squint](https://github.com/squint-cljs/squint): CLJS _syntax_ to JS compiler
  ## 0.3.35 (2023-10-25)

  - [#347](https://github.com/squint-cljs/squint/issues/347): Add `:pre` and `:post` support in `fn`
  - Add `number?`
  - Support `docstring` in `def`

  ## 0.3.34 (2023-10-24)

  - Handle multipe source `:paths` in a more robust fashion

  ## 0.3.33 (2023-10-24)

  - [#344](https://github.com/squint-cljs/squint/issues/344): macros can't be used via aliases

  ## 0.3.32 (2023-10-17)

  - Add `squint.edn` support, see [docs](README.md#squintedn)
  - Add `watch` subcommand to watch `:paths` from `squint.edn`
  - Make generated `let` variable names in JS more deterministic, which helps hot reloading in React
  - Added a [vite + react example project](examples/vite-react).
  - Resolve symbolic namespaces `(:require [foo.bar])` from `:paths`

  ## 0.2.31 (2023-10-09)

  - Add `bit-and` and `bit-or`

  ## 0.2.30 (2023-10-04)

  - Include `lib/squint.core.umd.js` which defines a global `squint.core` object (easy to use in browsers, see [docs](README.md#compile-on-a-server-use-in-a-browser))

  ## 0.2.29 (2023-10-03)

  - Add `subs`, `fn?`, `re-seq`
  - Add `squint.edn` with `:paths` to resolve macros from (via `:require-macros`)

- [neil](https://github.com/babashka/neil): A CLI to add common aliases and features to deps.edn-based projects
  Version 0.2.62 released
  - Fix NPE during `neil dep upgrade`
- [cherry](https://github.com/squint-cljs/cherry): Experimental ClojureScript to ES6 module compiler
  - Released version 0.1.9
- [grasp](https://github.com/borkdude/grasp): Grep Clojure code using clojure.spec regexes
  - Fix self-requiring namespace (which clj-kondo now also catches!)
- [lein-clj-kondo](https://github.com/clj-kondo/lein-clj-kondo): a leiningen plugin for clj-kondo
  - Bumped clj-kondo version
- [http-kit](https://github.com/http-kit/http-kit): Simple, high-performance event-driven HTTP client+server for Clojure.
  - [#543](https://github.com/http-kit/http-kit/issues/543) Migrate away from `SimpleDateFormat` to `java.time`, fixes native-image issue and virtual threads
- [clojure-mode](https://github.com/nextjournal/clojure-mode)
  - Porting this CLJS project such that it can run with [squint](https://github.com/squint-cljs/squint) also. Demo [here](https://snapshots.nextjournal.com/clojure-mode/build/8cfb2a20e7ea203056c47ebd80b7f4dd50a27e40/squint/dist/index.html). This work is funded by [Nextjournal](https://nextjournal.com/).
- [clj-kondo](https://github.com/clj-kondo/clj-kondo): static analyzer and linter for Clojure code that sparks joy.
  Published a new version (2023.09.07) with these changes:
  - [#1332](https://github.com/clj-kondo/clj-kondo/issues/1332): New linter `:unused-alias`. See [docs](doc/linters.md).
  - [#2143](https://github.com/clj-kondo/clj-kondo/issues/2143): false positive type warning for `clojure.set/project`
  - [#2145](https://github.com/clj-kondo/clj-kondo/issues/2145): support ignore hint on multi-arity branch of function definition
  - [#2147](https://github.com/clj-kondo/clj-kondo/issues/2147): use alternative solution as workaround for https://github.com/cognitect/transit-clj/issues/43
  - [#2152](https://github.com/clj-kondo/clj-kondo/issues/2152): Fix false positive with used-underscored-binding with core.match
  - [#2150](https://github.com/clj-kondo/clj-kondo/issues/2150): allow command line options = as in --fail-level=error
  - [#2149](https://github.com/clj-kondo/clj-kondo/issues/2149): `:lint-as clojure.core/defmacro` should suppress `&env` as unresolved symbol
  - [#2161](https://github.com/clj-kondo/clj-kondo/issues/2161): Fix type annotation for `clojure.core/zero?` to number -> boolean
  - [#2165](https://github.com/clj-kondo/clj-kondo/issues/2165): Fix error when serializing type data to cache
  - [#2167](https://github.com/clj-kondo/clj-kondo/issues/2167): Don't crash when `:unresolved-symbol` linter config contains unqualified symbol
  - [#2170](https://github.com/clj-kondo/clj-kondo/issues/2170): `:keyword-binding` linter should ignore auto-resolved keywords
  - [#2172](https://github.com/clj-kondo/clj-kondo/issues/2172): detect invalid amount of args and invalid argument type for `throw`
  - [#2164](https://github.com/clj-kondo/clj-kondo/issues/2164): deftest inside let triggers :unused-value
  - [#2154](https://github.com/clj-kondo/clj-kondo/issues/2154): add `:exclude` option to `:deprecated-namespace` linter
  - [#2134](https://github.com/clj-kondo/clj-kondo/issues/2134): don't warn on usage of private var in `data_readers.clj(c)`
  - [#2148](https://github.com/clj-kondo/clj-kondo/issues/2148): warn on configuration error in `:unused-refeferred-var` linter
  - Expose more vars in `clj-kondo.hooks-api` interpreter namespace
- [babashka](https://github.com/babashka/babashka): native, fast starting Clojure interpreter for scripting.
  Version 1.3.185 released!
  - [#1624](https://github.com/babashka/babashka/pull/1624): Use Oracle GraalVM 21 ([@lispyclouds](https://github.com/lispyclouds))
  - Use PGO to speed up loops (now 2-3x faster for `(time (loop [val 0 cnt 10000000] (if (pos? cnt) (recur (inc val) (dec cnt)) val)))`!)
  - Bump babashka.http-client to v0.4.15
  - Bump rewrite-clj to v0.1.1.47
  - [#1619](https://github.com/babashka/babashka/issues/1619): Fix reflection issue with `Thread/sleep` in `core.async/timeout`
  - Support interop on `java.util.stream.IntStream`
  - [#1513](https://github.com/babashka/babashka/issues/1513): Fix interop on `Thread/sleep` with numbers that aren't already longs
  - Bump babashka.cli to 0.7.53
  - Fix [#babashka.nrepl/66](https://github.com/babashka/babashka.nrepl/issues/66)
  - Various nREPL server improvements (classpath op, file lookup information for `cider-find-var`)
  - Bump cheshire to 5.12.0
- [http-client](https://github.com/babashka/http-client): babashka's http-client
  - A number of small bugfixes and additions
- A number of experiments around [squint](https://github.com/squint-cljs/squint):
  - [bun-squint-loader](https://github.com/borkdude/bun-squint-loader): a demo
  of how to implement a loader for [bun](https://github.com/oven-sh/bun) which
  lets you directly load `.cljs` files which are then compiled using
  - squint [cloudflare worker](https://github.com/borkdude/squint-bun-cloudflare)
- [rewrite-edn](https://github.com/borkdude/rewrite-edn): Utility lib on top of
  rewrite-clj with common operations to update EDN while preserving whitespace
  and comments
  - Fixed a round-tripping issue by bumping rewrite-clj
- [tools-deps-native](https://github.com/babashka/tools-deps-native) and [tools.bbuild](https://github.com/babashka/tools.bbuild): use tools.deps directly from babashka
  - aarch64 binary (thanks @TimoKramer for contributing)
  - update upstream projects
- [deps.clj](https://github.com/borkdude/deps.clj): A faithful port of the clojure CLI bash script to Clojure
  - Bumped tools jar and fixed a bug concerning SHA comparison
- [SCI](https://github.com/babashka/sci): Configurable Clojure/Script interpreter suitable for scripting and Clojure DSLs
  - Add experimental `:static-methods` override to control how a static method is
    invoked. This allowed a fix in babashka for `Thread/sleep` on non-longs and
    for `Class/forName` which works arond a bug in Oracle GraalVM 21.
- [sci.configs](https://github.com/babashka/sci.configs): A collection of ready to be used SCI configs.
  - [@holyjak](https://github.com/holyjak) made a configuration for [Fulcro](https://github.com/fulcrologic/fulcro) which can be seen live in action [here](https://blog.jakubholy.net/2023/interactive-code-snippets-fulcro/)

## Other projects

These are (some of the) other projects I'm involved with but little to no activity
happened in the past month.

<details>
<summary>Click for more details</summary>
- [CLI](https://github.com/babashka/cli): Turn Clojure functions into CLIs!
- [jet](https://github.com/borkdude/jet): CLI to transform between JSON, EDN, YAML and Transit using Clojure
- [quickdoc](https://github.com/borkdude/quickdoc): Quick and minimal API doc generation for Clojure
- [nbb](https://github.com/babashka/nbb): Scripting in Clojure on Node.js using SCI
- [pod-babashka-go-sqlite3](https://github.com/babashka/pod-babashka-go-sqlite3): A babashka pod for interacting with sqlite3
- [pod-babashka-fswatcher](https://github.com/babashka/pod-babashka-fswatcher): babashka filewatcher pod
- [edamame](https://github.com/borkdude/edamame): Configurable EDN/Clojure parser with location metadata
- [lein2deps](https://github.com/borkdude/lein2deps): leiningen to deps.edn converter
- [scittle](https://github.com/babashka/scittle): Execute Clojure(Script) directly from browser script tags via SCI
- [sql pods](https://github.com/babashka/babashka-sql-pods): babashka pods for SQL databases
- [cljs-showcase](https://github.com/borkdude/cljs-showcase): Showcase CLJS libs using SCI
- [fs](https://github.com/babashka/fs) - File system utility library for Clojure
- [process](https://github.com/babashka/process): Clojure library for shelling out / spawning sub-processes
- [babashka.book](https://github.com/babashka/book): Babashka manual
- [instaparse-bb](https://github.com/babashka/instaparse-bb)
- [rewrite-clj](https://github.com/clj-commons/rewrite-clj): Rewrite Clojure code and edn
- [pod-babashka-buddy](https://github.com/babashka/pod-babashka-buddy): A pod around buddy core (Cryptographic Api for Clojure).
- [gh-release-artifact](https://github.com/borkdude/gh-release-artifact): Upload artifacts to Github releases idempotently
- [carve](https://github.com/borkdude/carve) - Remove unused Clojure vars
- [quickblog](https://github.com/borkdude/quickblog): Light-weight static blog engine for Clojure and babashka
- [4ever-clojure](https://github.com/oxalorg/4ever-clojure) - Pure CLJS version of 4clojure, meant to run forever!
- [pod-babashka-lanterna](https://github.com/babashka/pod-babashka-lanterna): Interact with clojure-lanterna from babashka
- [joyride](https://github.com/BetterThanTomorrow/joyride): VSCode CLJS scripting and REPL (via [SCI](https://github.com/babashka/sci))
- [clj2el](https://borkdude.github.io/clj2el/): transpile Clojure to elisp
- [deflet](https://github.com/borkdude/deflet): make let-expressions REPL-friendly!
- [babashka.json](https://github.com/babashka/json): babashka JSON library/adapter
- [deps.add-lib](https://github.com/borkdude/deps.add-lib): Clojure 1.12's add-lib feature for leiningen and/or other environments without a specific version of the clojure CLI

</details>

