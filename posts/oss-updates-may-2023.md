Title: OSS updates May 2023
Date: 2023-05-30
Tags: clojure, oss updates
Description: My Clojure OSS updates for May 2023

In this post I'll give updates about open source I worked on during May 2023.

To see previous OSS updates, go [here](https://blog.michielborkent.nl/tags/oss-updates.html).

## [Babashka-conf](https://babashka.org/conf/)

Babashka-conf is happening June 10th in Berlin. Only a few tickets left!

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
</details>

<!--

sources: https://github.com/borkdude
local ~/dev and ~/dev/babashka dir (since github doesn't show all repos)

-->

## Updates

The following projects had updates in the last month. Note that only highlights
are mentioned and not a full overview of all changes. See the project's
changelogs for all changes.

- Preparations for [babashka conf](https://github.com/babashka/conf) are in full swing and I'm preparing a talk with the title 'Growing an ecosystem'.
- This month I've had the honor to visit the JUXT 10 year anniversary in London and met a lot of fellow Clojurians over there.
- Babashka and SCI will be featured at the last iteration of [Strange Loop](https://www.thestrangeloop.com/)!
- My OSS work is funded by Clojurists Together in [Q2](https://www.clojuriststogether.org/news/q2-2023-funding-announcement/)
- [clj-kondo](https://github.com/clj-kondo/clj-kondo): static analyzer and linter for Clojure code that sparks joy.
  - Version 2023.05.18 - 2023.05.26 were released. Full changelogs [here](https://github.com/clj-kondo/clj-kondo/blob/master/CHANGELOG.md). Highlights:
  - Linter `:uninitialized-var` moved from default `:level :off` to `:warning`
  - [#2065](https://github.com/clj-kondo/clj-kondo/issues/2065): new linter `:equals-true`: suggest using `(true? x)` over `(= true x)` (defaults to `:level :off`).
  - [#2066](https://github.com/clj-kondo/clj-kondo/issues/2066): new linters `:plus-one` and `:minus-one`: suggest using `(inc x)` over `(+ x 1)` (and similarly for `dec` and `-`, defaults to `:level :off`)
  - [#2058](https://github.com/clj-kondo/clj-kondo/issues/2058): warn about `#()` and `#""` in `.edn` files
- [babashka](https://github.com/babashka/babashka): native, fast starting Clojure interpreter for scripting.
  - Released 1.3.180, mostly a maintenance release
  - See the complete [CHANGELOG](https://github.com/babashka/babashka/blob/master/CHANGELOG.md)
- [scittle](https://github.com/babashka/scittle): Execute Clojure(Script) directly from browser script tags via SCI
  - [#58](https://github.com/babashka/scittle/issues/58): build system for creating scittle distribution with custom libraries. See [plugins/demo](https://github.com/babashka/scittle/tree/main/plugins/demo).
  - Use `window.location.hostname` for WebSocket connection instead of hardcoding `"localhost"` ([@pyrmont](https://github.com/pyrmont))
  - Upgrade `sci.configs` to `"33bd51e53700b224b4cb5bda59eb21b62f962745"`
  - Update nREPL implementation: implement `eldoc` (`info`, `lookup`) ([@benjamin-asdf](https://github.com/benjamin-asdf))
- [deps.clj](https://github.com/borkdude/deps.clj): A faithful port of the clojure CLI bash script to Clojure
  - Introduce all new programmatic [API](https://github.com/borkdude/deps.clj/blob/master/API.md)
  - Automatically use file when exceeding Windows argument length
- [deps.add-lib](https://github.com/borkdude/deps.add-lib): Clojure 1.12's add-lib feature for leiningen and/or other environments without a specific version of the clojure CLI
  - First clojars release
- [nbb](https://github.com/babashka/nbb): Scripting in Clojure on Node.js using SCI
  - Nbb is now compatible with [bun](https://bun.sh/). To run nbb in a bun project, use `bunx --bun nbb`.
- [squint](https://github.com/squint-cljs/squint): CLJS _syntax_ to JS compiler
  - Fix import with `$default`
- [cherry](https://github.com/squint-cljs/cherry) Experimental ClojureScript to ES6 module compiler
  - Support `with-out-str`
- [http-client](https://github.com/babashka/http-client): Babashka's http-client
  - Add `:authenticator` option
- [sci.configs](https://github.com/babashka/sci.configs): A collection of ready to be used SCI configs.
  - This project now has a configuration for datascript, for anyone who wants to use SCI together with datascript. See [this](https://github.com/babashka/sci.configs/commit/33bd51e53700b224b4cb5bda59eb21b62f962745) commit.
- [SCI](https://github.com/babashka/sci): Configurable Clojure/Script interpreter suitable for scripting and Clojure DSLs
  - Support `:require-macros`
- [process](https://github.com/babashka/process): Clojure library for shelling out / spawning sub-processes
  - Support `:pre-start-fn` in `exec`
  - Allow passing `:cmd` in map argument
  - Better testing for `exec` by [@lread](https://github.com/lread)
- [fs](https://github.com/babashka/fs) - File system utility library for Clojure
  - `:paths` argument for `fs/which` by [@lread](https://github.com/lread)
  - Support inputstream in `fs/copy`
  - Add `fs/owner` to return owner of file

<!-- ## Contributions to other projects -->

<!-- - [clojurescript](https://github.com/clojure/clojurescript): -->
<!--   - [PR 202](https://github.com/clojure/clojurescript/pull/202): a `macroexpand` fix -->
<!--   - [PR 203](https://github.com/clojure/clojurescript/pull/203): a symbol optimization fix -->
<!-- - [malli](https://github.com/metosin/malli/commit/cf918db28ff71a2f735f465f30f0bc1028ecd7d9): cherry integration -->
<!-- - [clerk](https://github.com/nextjournal/clerk/commit/cb079b14213185d27c5a2d1cc1e80943521a4fb5): cherry integration -->
<!-- - [clojure-lsp](https://github.com/clojure-lsp/clojure-lsp/commit/60d67cca59f0747e8b68802157afbe7f61440c7f): integrated a new clj-kondo feature: showing the languages in a CLJC context -->

## Other projects

These are (some of the) other projects I'm involved with but little to no activity
happened in the past month.

<details>
<summary>Click for more details</summary>
- [edamame](https://github.com/borkdude/edamame): Configurable EDN/Clojure parser with location metadata
  - Improvements for reading namespaced maps
- [babashka.book](https://github.com/babashka/book): Babashka manual
  - Several corrections
  - Dynamic `:exec-args`
  - Script-adjacent `bb.edn` docs
- [CLI](https://github.com/babashka/cli): Turn Clojure functions into CLIs!
  - Support `--no-option` and parse as `{:option false}`
  - Support grouped aliase like `-ome` as `{:o true, :m true, :e true}`
- [pod-babashka-go-sqlite3](https://github.com/babashka/pod-babashka-go-sqlite3): A babashka pod for interacting with sqlite3
  - Better error message when connection is not a string
- [instaparse-bb](https://github.com/babashka/instaparse-bb)
  - Add transform function
- [jet](https://github.com/borkdude/jet): CLI to transform between JSON, EDN, YAML and Transit using Clojure
  - Add option to elide commas
- [quickdoc](https://github.com/borkdude/quickdoc): Quick and minimal API doc generation for Clojure
- [rewrite-clj](https://github.com/clj-commons/rewrite-clj): Rewrite Clojure code and edn
- [sql pods](https://github.com/babashka/babashka-sql-pods): babashka pods for SQL databases
- [pod-babashka-buddy](https://github.com/babashka/pod-babashka-buddy): A pod around buddy core (Cryptographic Api for Clojure).
- [gh-release-artifact](https://github.com/borkdude/gh-release-artifact): Upload artifacts to Github releases idempotently
- [neil](https://github.com/babashka/neil): A CLI to add common aliases and features to deps.edn-based projects
- [cljs-showcase](https://github.com/borkdude/cljs-showcase): Showcase CLJS libs using SCI
- [carve](https://github.com/borkdude/carve) - Remove unused Clojure vars
- [grasp](https://github.com/borkdude/grasp): Grep Clojure code using clojure.spec regexes
- [quickblog](https://github.com/borkdude/quickblog): Light-weight static blog engine for Clojure and babashka
- [lein2deps](https://github.com/borkdude/lein2deps): leiningen to deps.edn converter
- [4ever-clojure](https://github.com/oxalorg/4ever-clojure) - Pure CLJS version of 4clojure, meant to run forever!
- [pod-babashka-lanterna](https://github.com/babashka/pod-babashka-lanterna): Interact with clojure-lanterna from babashka
- [pod-babashka-fswatcher](https://github.com/babashka/pod-babashka-fswatcher): babashka filewatcher pod
- [joyride](https://github.com/BetterThanTomorrow/joyride): VSCode CLJS scripting and REPL (via [SCI](https://github.com/babashka/sci))
- [clj2el](https://borkdude.github.io/clj2el/): transpile Clojure to elisp
- [deflet](https://github.com/borkdude/deflet): make let-expressions REPL-friendly!
- [babashka.json](https://github.com/babashka/json): babashka JSON library/adapter
</details>

<!-- - [tools-deps-native](https://github.com/babashka/tools-deps-native): Run tools.deps as a native binary-->
<!-- - [tools.bbuild](https://github.com/babashka/tools.bbuild): Library of functions for building Clojure projects-->
