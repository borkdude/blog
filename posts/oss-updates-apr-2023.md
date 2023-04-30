Title: OSS updates April 2023
Date: 2023-05-01
Tags: clojure, oss updates
Description: My Clojure OSS updates for April 2023

In this post I'll give updates about open source I worked on during April 2023.

## [Babashka-conf](https://babashka.org/conf/)

Babashka-conf is happening June 10th in Berlin. Save the date and/or submit your
babashka/clojure-related talk or workshop in the CfP!

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

On to the projects that I've been working on!

<!--

sources: https://github.com/borkdude
local ~/dev and ~/dev/babashka dir (since github doesn't show all repos)

-->

## Projects

The following projects had updates in the last month. Note that only highlights
are mentioned and not a full overview of all changes. See the project's
changelogs for all changes.

- [clj-kondo](https://github.com/clj-kondo/clj-kondo): static analyzer and linter for Clojure code that sparks joy.
  - [#1196](https://github.com/clj-kondo/clj-kondo/issues/1196): show language context in `.cljc` files with `:output {:langs true}`. See [docs](https://github.com/clj-kondo/clj-kondo/blob/master/doc/config.md#show-language-context-in-cljc-files).
  - [#2030](https://github.com/clj-kondo/clj-kondo/issues/2030): Add a new `:discouraged-tag` linter for discouraged tag literals. See the [docs](https://github.com/clj-kondo/clj-kondo/blob/master/doc/linters.md#discouraged-tag).
- [babashka](https://github.com/babashka/babashka): native, fast starting Clojure interpreter for scripting
  - Released 1.3.177 - 1.3.179
  - [#1541](https://github.com/babashka/babashka/issues/1541): respect `bb.edn`
  adjacent to invoked file. This eases writing system-global scripts from
  projects without using bbin. See [docs](https://book.babashka.org/#_script_adjacent_bb_edn).
  - See the complete [CHANGELOG](https://github.com/babashka/babashka/blob/master/CHANGELOG.md).
- [cherry](https://github.com/squint-cljs/cherry) Experimental ClojureScript to ES6 module compiler
  - Improve `cherry.embed`
- [SCI](https://github.com/babashka/sci): Configurable Clojure/Script
  interpreter suitable for scripting and Clojure DSLs
  - Better error message when trying to `recur` across `try`
  - Improvement for error locations in multiple threads
- [edamame](https://github.com/borkdude/edamame): Configurable EDN/Clojure parser with location metadata
  - Improvements for reading namespaced maps
- [babashka.book](https://github.com/babashka/book): Babashka manual
  - Several corrections
  - Dynamic `:exec-args`
  - Script-adjacent `bb.edn` docs
- [CLI](https://github.com/babashka/cli): Turn Clojure functions into CLIs!
  - Support `--no-option` and parse as `{:option false}`
  - Support grouped aliase like `-ome` as `{:o true, :m true, :e true}`
- [http-client](https://github.com/babashka/http-client): Babashka's http-client
  - Support `java.net.URI` directly in `:uri` option
  - Better `:ssl-config` option support
  - Better `:proxy` option support
- [pod-babashka-go-sqlite3](https://github.com/babashka/pod-babashka-go-sqlite3): A babashka pod for interacting with sqlite3
  - Better error message when connection is not a string
- [nbb](https://github.com/babashka/nbb): Scripting in Clojure on Node.js using SCI
  - Fix `:local/root` deps in `nbb.edn` when not invoking from current working directory
  - Fix regression, `cljs.core/PersistentQueue.EMPTY` no longer working
- [instaparse-bb](https://github.com/babashka/instaparse-bb)
  - Add transform function
- [jet](https://github.com/borkdude/jet): CLI to transform between JSON, EDN, YAML and Transit using Clojure
  - Add option to elide commas
- [deps.clj](https://github.com/borkdude/deps.clj): A faithful port of the clojure CLI bash script to Clojure
  - Catch up with clojure CLI changes

## Contributions to other projects

- [clojurescript](https://github.com/clojure/clojurescript):
  - [PR 202](https://github.com/clojure/clojurescript/pull/202): a `macroexpand` fix
  - [PR 203](https://github.com/clojure/clojurescript/pull/203): a symbol optimization fix
- [malli](https://github.com/metosin/malli/commit/cf918db28ff71a2f735f465f30f0bc1028ecd7d9): cherry integration
- [clerk](https://github.com/nextjournal/clerk/commit/cb079b14213185d27c5a2d1cc1e80943521a4fb5): cherry integration
- [clojure-lsp](https://github.com/clojure-lsp/clojure-lsp/commit/60d67cca59f0747e8b68802157afbe7f61440c7f): integrated a new clj-kondo feature: showing the languages in a CLJC context

## Other projects

These are (some of the) other projects I'm involved with but little to no activity
happened in the past month.

- [process](https://github.com/babashka/process): Clojure library for shelling out / spawning sub-processes
- [quickdoc](https://github.com/borkdude/quickdoc): Quick and minimal API doc generation for Clojure
- [rewrite-clj](https://github.com/clj-commons/rewrite-clj): Rewrite Clojure code and edn
- [sql pods](https://github.com/babashka/babashka-sql-pods): babashka pods for SQL databases
- [squint](https://github.com/squint-cljs/squint): CLJS _syntax_ to JS compiler
- [pod-babashka-buddy](https://github.com/babashka/pod-babashka-buddy): A pod around buddy core (Cryptographic Api for Clojure).
- [gh-release-artifact](https://github.com/borkdude/gh-release-artifact): Upload artifacts to Github releases idempotently
- [neil](https://github.com/babashka/neil): A CLI to add common aliases and features to deps.edn-based projects
- [cljs-showcase](https://github.com/borkdude/cljs-showcase): Showcase CLJS libs using SCI
- [carve](https://github.com/borkdude/carve) - Remove unused Clojure vars
- [grasp](https://github.com/borkdude/grasp): Grep Clojure code using clojure.spec regexes
- [scittle](https://github.com/babashka/scittle): Execute Clojure(Script) directly from browser script tags via SCI
- [quickblog](https://github.com/borkdude/quickblog): Light-weight static blog engine for Clojure and babashka
- [sci.configs](https://github.com/babashka/sci.configs): A collection of ready to be used SCI configs
- [lein2deps](https://github.com/borkdude/lein2deps): leiningen to deps.edn converter
- [4ever-clojure](https://github.com/oxalorg/4ever-clojure) - Pure CLJS version of 4clojure, meant to run forever!
- [fs](https://github.com/babashka/fs) - File system utility library for Clojure
- [pod-babashka-lanterna](https://github.com/babashka/pod-babashka-lanterna): Interact with clojure-lanterna from babashka
- [pod-babashka-fswatcher](https://github.com/babashka/pod-babashka-fswatcher): babashka filewatcher pod
- [joyride](https://github.com/BetterThanTomorrow/joyride): VSCode CLJS scripting and REPL (via [SCI](https://github.com/babashka/sci))
- [clj2el](https://borkdude.github.io/clj2el/): transpile Clojure to elisp
- [deflet](https://github.com/borkdude/deflet): make let-expressions REPL-friendly!
- [babashka.json](https://github.com/babashka/json): babashka JSON library/adapter

<!-- - [tools-deps-native](https://github.com/babashka/tools-deps-native): Run tools.deps as a native binary-->
<!-- - [tools.bbuild](https://github.com/babashka/tools.bbuild): Library of functions for building Clojure projects-->
