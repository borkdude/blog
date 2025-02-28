Title: OSS updates January and February 2025
Date: 2025-02-28
Tags: clojure, oss updates
Description: My Clojure OSS updates for January and February 2025

In this post I'll give updates about open source I worked on during January and February 2025.

To see previous OSS updates, go [here](https://blog.michielborkent.nl/tags/oss-updates.html).

## Sponsors

I'd like to thank all the sponsors and contributors that make this work
possible. Without _you_, the below projects would not be as mature or wouldn't
exist or be maintained at all.

Current top tier sponsors:

- [Clojurists Together](https://clojuriststogether.org/)
- [Roam Research](https://roamresearch.com/)
- [Nextjournal](https://nextjournal.com/)
- [Nubank](https://nubank.com.br)

Open the details section for more info about sponsoring.

<details>
<summary>Sponsor info</summary>

If you want to ensure that the projects I work on are sustainably maintained,
you can sponsor this work in the following ways. Thank you!

- [Github Sponsors](https://github.com/sponsors/borkdude)
- The [Babaska](https://opencollective.com/babashka) or [Clj-kondo](https://opencollective.com/clj-kondo) OpenCollective
- [Ko-fi](https://ko-fi.com/borkdude)
- [Patreon](https://www.patreon.com/borkdude)
- [Clojurists Together](https://www.clojuriststogether.org/)

On to the projects that I've been working on!
</details>

<!--

sources: https://github.com/borkdude
local ~/dev and ~/dev/babashka dir (since github doesn't show all repos)

drwxr-xr-x  489 borkdude  staff  15648 28 feb 13:47 squint
drwxr-xr-x@ 315 borkdude  staff  10080 28 feb 12:21 homebrew-brew
drwxr-xr-x@  79 borkdude  staff   2528 28 feb 11:02 babashka

-->

## Updates

As I'm writing this I'm still recovering from a flu that has kept me bedridden
for a good few days, but I'm starting to feel better now.

Here are updates about the projects/libraries I've worked on in the last two months.

- [clj-kondo](https://github.com/clj-kondo/clj-kondo): static analyzer and linter for Clojure code that sparks joy.<br>
  - Unreleased:
  - [#2493](https://github.com/clj-kondo/clj-kondo/issues/2493): reduce image size of native image
  - 2025.02.20:
  - [#2473](https://github.com/clj-kondo/clj-kondo/issues/2473): New linter: `:unknown-ns-options` will warn on malformed `(ns)` calls. The linter is `{:level :warning}` by default. ([@Noahtheduke](https://github.com/Noahtheduke))
  - [#2475](https://github.com/clj-kondo/clj-kondo/issues/2475): add `:do-template` linter to check args & values counts ([@imrekoszo](https://github.com/imrekoszo))
  - [#2465](https://github.com/clj-kondo/clj-kondo/issues/2465): fix `:discouraged-var` linter for fixed arities
  - [#2277](https://github.com/clj-kondo/clj-kondo/issues/2277): prefer an array class symbol over `(Class/forName ...)` in `defprotocol` and `extend-type`
  - [#2466](https://github.com/clj-kondo/clj-kondo/issues/2466): fix false positive with tagged literal in macroexpand hook
  - [#2463](https://github.com/clj-kondo/clj-kondo/issues/2463): using `:min-clj-kondo-version` results in incorrect warning ([@imrekoszo](https://github.com/imrekoszo))
  - [#2464](https://github.com/clj-kondo/clj-kondo/issues/2464): `:min-clj-kondo-version` warning/error should have a location in `config.edn` ([@imrekoszo](https://github.com/imrekoszo))
  - [#2472](https://github.com/clj-kondo/clj-kondo/issues/2472) hooks `api/resolve` should return `nil` for unresolved symbols and locals
  - [#2472](https://github.com/clj-kondo/clj-kondo/issues/2472): add `api/env` to determine if symbol is local
  - [#2482](https://github.com/clj-kondo/clj-kondo/issues/2482): Upgrade to Oracle GraalVM 23
  - [#2483](https://github.com/clj-kondo/clj-kondo/issues/2483): add `api/quote-node` and `api/quote-node?` to hooks API ([@camsaul](https://github.com/camsaul))
  - [#2490](https://github.com/clj-kondo/clj-kondo/issues/2490): restore unofficial support for ignore hints via metadata

- [babashka](https://github.com/babashka/babashka): native, fast starting Clojure interpreter for scripting.
  - Experimenting upgrading to new beta core.async, work in is a branch ready to be merged
  - [#1785](https://github.com/babashka/babashka/issues/1785): Allow subclasses of `Throwable` to have instance methods invoked ([@bobisageek](https://github.com/bobisageek))
  - [#1791](https://github.com/babashka/babashka/issues/1791): interop problem on Jsoup form element
  - [#1793](https://github.com/babashka/babashka/issues/1793): Bump `rewrite-clj` to `1.1.49` (fixes parsing of `foo//` among other things)
  - Bump `deps.clj`
  - Bump `fs`

- [fs](https://github.com/babashka/fs) - File system utility library for Clojure
  - v0.5.24 (2025-01-09)
  - [#135](https://github.com/babashka/fs/issues/135): additional fix for preserving protocol when calling `fs/path` on multiple arguments ([@Sohalt](https://github.com/Sohalt))

- [SCI](https://github.com/babashka/sci): Configurable Clojure/Script interpreter suitable for scripting
  - Records should have keys present and set to `nil`

- [deps.clj](https://github.com/borkdude/deps.clj): A faithful port of the clojure CLI bash script to Clojure
  - Catch up with several new versions of clojure CLI
  - Fix [#132](https://github.com/borkdude/deps.clj/issues/132): copy install tools.edn to config dir when install version is newer, similar to clojure CLI bash script
  - Adds support for `XDG_DATA_HOME` environment variable according to [XDG Base Directory Specification](https://specifications.freedesktop.org/basedir-spec/latest/)

- [sql pods](https://github.com/babashka/babashka-sql-pods): babashka pods for SQL databases
  - Add support for DuckDB ([@avelino](https://github.com/paintparty))

- [edamame](https://github.com/borkdude/edamame): Configurable EDN/Clojure parser with location metadata
  - Fix #115: add location to exception for invalid keyword

- [rewrite-edn](https://github.com/borkdude/rewrite-edn): Utility lib on top of
  rewrite-clj with common operations to update EDN while preserving whitespace
  and comments
  - [#40](https://github.com/borkdude/rewrite-edn/issues/40): `assoc`/`update` now handles map keys that have no indent at all ([@lread](https://github.com/lread))
  - [#43](https://github.com/borkdude/rewrite-edn/issues/43): bump rewrite-clj to 1.1.49 ([@lread](https://github.com/lread))
  - [#40](https://github.com/borkdude/rewrite-edn/issues/40): `assoc`/`update` now handles map keys that have no indent at all 
  ([@lread](https://github.com/lread))
  - [#40](https://github.com/borkdude/rewrite-edn/issues/40): `assoc`/`update` now aligns indent to comment if that's all that is in the map
  ([@lread](https://github.com/lread))
  - [#40](https://github.com/borkdude/rewrite-edn/issues/40): `update` now indents new entries in same way as `assoc`
  ([@lread](https://github.com/lread))

- [squint](https://github.com/squint-cljs/squint): CLJS _syntax_ to JS compiler
  - Fix [#609](https://github.com/squint-cljs/squint/issues/609): make `remove` return a transducer when no collection is provided
  - Fix [#611](https://github.com/squint-cljs/squint/issues/611): Implement the `set?` function ([@jonasseglare](https://github.com/jonasseglare))
  - Fix [#613](https://github.com/squint-cljs/squint/issues/613): Optimize `aset`
  - Fix [#626](https://github.com/squint-cljs/squint/issues/XXX): Implement `take-last`
  - Fix [#615](https://github.com/squint-cljs/squint/issues/615): `(zero? "0")` should return `false`
  - Fix [#617](https://github.com/squint-cljs/squint/issues/617): `deftype` field name munging problem
  - Fix [#618](https://github.com/squint-cljs/squint/issues/618): Named multi-arity `fn` args don't get munged ([@grayrest](https://github.com/grayrest))
  - Fix [#622](https://github.com/squint-cljs/squint/issues/622): operator precendence issue with `|` and `if`
  - Add `clojure.string` functions `lower-case`, `upper-case`, `capitalize` ([@plexus](https://github.com/plexus))
  - Fix [#605](https://github.com/squint-cljs/squint/issues/605): merge command line `--paths` with `squint.edn` config properly
  - Fix [#607](https://github.com/squint-cljs/squint/issues/607): make `mapcat` return a transducer if no collections are provided ([@jonasseglare](https://github.com/jonasseglare))

## Other projects

These are (some of the) other projects I'm involved with but little to no activity
happened in the past month.

<details>
<summary>Click for more details</summary>
- [CLI](https://github.com/babashka/cli): Turn Clojure functions into CLIs!
- [scittle](https://github.com/babashka/scittle): Execute Clojure(Script) directly from browser script tags via SCI
- [nbb](https://github.com/babashka/nbb): Scripting in Clojure on Node.js using SCI
- [rewrite-clj](https://github.com/clj-commons/rewrite-clj): Rewrite Clojure code and edn
- [pod-babashka-go-sqlite3](https://github.com/babashka/pod-babashka-go-sqlite3): A babashka pod for interacting with sqlite3
- [tools-deps-native](https://github.com/babashka/tools-deps-native) and [tools.bbuild](https://github.com/babashka/tools.bbuild): use tools.deps directly from babashka
- [http-client](https://github.com/babashka/http-client): babashka's http-client<br>
- [cherry](https://github.com/squint-cljs/cherry): Experimental ClojureScript to ES6 module compiler
- [http-server](https://github.com/babashka/http-server): serve static assets
- [bbin](https://github.com/babashka/bbin): Install any Babashka script or project with one comman
- [sci.configs](https://github.com/babashka/sci.configs): A collection of ready to be used SCI configs.
  - Added a configuration for `cljs.spec.alpha` and related namespaces
- [qualify-methods](https://github.com/borkdude/qualify-methods)
  - Initial release of experimental tool to rewrite instance calls to use fully
    qualified methods (Clojure 1.12 only0
- [clerk](https://github.com/nextjournal/clerk): Moldable Live Programming for Clojure
  - Add support for `:require-cljs` which allows you to use `.cljs` files for render functions
  - Add support for nREPL for developing render functions
- [process](https://github.com/babashka/process): Clojure library for shelling out / spawning sub-processes
  - Work has started to support prepending output (in support for babashka parallel tasks). Stay tuned.
- [neil](https://github.com/babashka/neil): A CLI to add common aliases and features to deps.edn-based projects.<br>
- [quickdoc](https://github.com/borkdude/quickdoc): Quick and minimal API doc generation for Clojure
- [tools](https://github.com/borkdude/tools): a set of [bbin](https://github.com/babashka/bbin/) installable scripts
- [sci.nrepl](https://github.com/babashka/sci.nrepl): nREPL server for SCI projects that run in the browser
- [html](https://github.com/borkdude/html): Html generation library inspired by squint's html tag
- [instaparse-bb](https://github.com/babashka/instaparse-bb): Use instaparse from babashka
- [babashka.json](https://github.com/babashka/json): babashka JSON library/adapter
- [squint-macros](https://github.com/squint-cljs/squint-macros): a couple of
  macros that stand-in for
  [applied-science/js-interop](https://github.com/applied-science/js-interop)
  and [promesa](https://github.com/funcool/promesa) to make CLJS projects
  compatible with squint and/or cherry.
- [grasp](https://github.com/borkdude/grasp): Grep Clojure code using clojure.spec regexes
- [lein-clj-kondo](https://github.com/clj-kondo/lein-clj-kondo): a leiningen plugin for clj-kondo
- [http-kit](https://github.com/http-kit/http-kit): Simple, high-performance event-driven HTTP client+server for Clojure.
- [babashka.nrepl](https://github.com/babashka/babashka.nrepl): The nREPL server from babashka as a library, so it can be used from other SCI-based CLIs
- [jet](https://github.com/borkdude/jet): CLI to transform between JSON, EDN, YAML and Transit using Clojure
- [pod-babashka-fswatcher](https://github.com/babashka/pod-babashka-fswatcher): babashka filewatcher pod
- [lein2deps](https://github.com/borkdude/lein2deps): leiningen to deps.edn converter
- [cljs-showcase](https://github.com/borkdude/cljs-showcase): Showcase CLJS libs using SCI
- [babashka.book](https://github.com/babashka/book): Babashka manual
- [pod-babashka-buddy](https://github.com/babashka/pod-babashka-buddy): A pod around buddy core (Cryptographic Api for Clojure).
- [gh-release-artifact](https://github.com/borkdude/gh-release-artifact): Upload artifacts to Github releases idempotently
- [carve](https://github.com/borkdude/carve) - Remove unused Clojure vars
- [4ever-clojure](https://github.com/oxalorg/4ever-clojure) - Pure CLJS version of 4clojure, meant to run forever!
- [pod-babashka-lanterna](https://github.com/babashka/pod-babashka-lanterna): Interact with clojure-lanterna from babashka
- [joyride](https://github.com/BetterThanTomorrow/joyride): VSCode CLJS scripting and REPL (via [SCI](https://github.com/babashka/sci))
- [clj2el](https://borkdude.github.io/clj2el/): transpile Clojure to elisp
- [deflet](https://github.com/borkdude/deflet): make let-expressions REPL-friendly!
- [deps.add-lib](https://github.com/borkdude/deps.add-lib): Clojure 1.12's add-lib feature for leiningen and/or other environments without a specific version of the clojure CLI

</details>

