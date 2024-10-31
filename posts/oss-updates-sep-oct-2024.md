Title: OSS updates September and October 2024
Date: 2024-11-01
Tags: clojure, oss updates
Description: My Clojure OSS updates for September and October 2024

In this post I'll give updates about open source I worked on during September and October 2024.

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

If you're used to sponsoring through some other means which isn't listed above, please get in touch.

On to the projects that I've been working on!
</details>

<!--

sources: https://github.com/borkdude
local ~/dev and ~/dev/babashka dir (since github doesn't show all repos)

drwxr-xr-x@  79 borkdude  staff   2528 Apr 28 16:32 babashka
-->

## Updates

Here are updates about the projects/libraries I've worked on last month.

- [clj-kondo](https://github.com/clj-kondo/clj-kondo): static analyzer and linter for Clojure code that sparks joy.<br>
  - Unreleased:
  - [#2386](https://github.com/clj-kondo/clj-kondo/issues/2386): fix regression introduced in [#2364](https://github.com/clj-kondo/clj-kondo/issues/2364) in `letfn` (unreleased)
  - v2024.08.29:
  - [#2303](https://github.com/clj-kondo/clj-kondo/issues/2303): Support array class notation of Clojure 1.12 (`byte/1`)
  - [#916](https://github.com/clj-kondo/clj-kondo/issues/916): New linter: `:destructured-or-binding-of-same-map` which warns about
    `:or` defaults referring to bindings of same map, which is undefined and may result in broken
    behavior
  - [#2362](https://github.com/clj-kondo/clj-kondo/issues/2362): turn min-version warning into lint warning
  - [#1603](https://github.com/clj-kondo/clj-kondo/issues/1603): Support Java classes in `:analyze-call` hook
  - [#2369](https://github.com/clj-kondo/clj-kondo/issues/2369): false positive unused value in quoted list
  - [#2374](https://github.com/clj-kondo/clj-kondo/issues/2374): Detect misplaced return Schemas ([@frenchy64](https://github.com/frenchy64))
  - [#2364](https://github.com/clj-kondo/clj-kondo/issues/2364): performance: code that analyzed fn arity is ran twice
  - [#2355](https://github.com/clj-kondo/clj-kondo/issues/2355): support `:as-alias` with current namespace without warning about self-requiring namespace
  - v2024.08.01:
  - [#2359](https://github.com/clj-kondo/clj-kondo/issues/2359): `@x` should warn with type error about `x` not being an IDeref, e.g. with `@inc`
  - [#2345](https://github.com/clj-kondo/clj-kondo/issues/2345): Fix SARIF output and some enhancements ([@nxvipin](https://github.com/nxvipin))
  - [#2335](https://github.com/clj-kondo/clj-kondo/issues/2335): read causes side effect, thus not an unused value
  - [#2336](https://github.com/clj-kondo/clj-kondo/issues/2336): `do` and `doto` type checking ([@yuhan0](https://github.com/yuhan0))
  - [#2322](https://github.com/clj-kondo/clj-kondo/issues/2322): report locations for more reader errors ([@yuhan0](https://github.com/yuhan0))
  - [#2342](https://github.com/clj-kondo/clj-kondo/issues/2342): report unused maps, vectors, sets, regexes, functions as `:unused-value`
  - [#2352](https://github.com/clj-kondo/clj-kondo/issues/2352): type mismatch error for `or` without arguments
  - [#2344](https://github.com/clj-kondo/clj-kondo/issues/2344): copying configs and linting dependencies can now be done in one go with `--dependencies --copy-configs`
  - [#2357](https://github.com/clj-kondo/clj-kondo/issues/2357): `:discouraged-namespace` can have `:level` per namespace

- [babashka](https://github.com/babashka/babashka): native, fast starting Clojure interpreter for scripting.
  - Mostly bumped library dependencies and improvements in SCI

- [edamame](https://github.com/borkdude/edamame): Configurable EDN/Clojure parser with location metadata
  - Support new Clojure 1.12 array notation

- [SCI](https://github.com/babashka/sci): Configurable Clojure/Script interpreter suitable for scripting and Clojure DSLs
  - Fix [#923](https://github.com/babashka/sci/issues/923): check for duplicate keys in dynamic set or map literals
  - Fix [#926](https://github.com/babashka/sci/issues/926): Support `add-watch` on vars in CLJS

- [cherry](https://github.com/squint-cljs/cherry): Experimental ClojureScript to ES6 module compiler
  - [#135](https://github.com/squint-cljs/cherry/issues/135): Fix UMD build

- [nbb](https://github.com/babashka/nbb): Scripting in Clojure on Node.js using SCI
  - Bump org.babashka/cli
  - Bump SCI

- [quickdoc](https://github.com/borkdude/quickdoc): Quick and minimal API doc generation for Clojure
  - Fix [#39](https://github.com/borkdude/quickdoc/issues/39): fix link when var is named multiple times in docstring

- [http-server](https://github.com/babashka/http-server): serve static assets
  - [#13](https://github.com/babashka/http-server/issues/13): add an ending slash to the dir link, and don't encode the slashes ([@KDr2](https://github.com/KDr2))
  - [#16](https://github.com/babashka/http-server/issues/16): support range requests

- [CLI](https://github.com/babashka/cli): Turn Clojure functions into CLIs!<br>
  - Fix [#102](https://github.com/babashka/cli/issues/102): `format-table` correctly pads cells containing ANSI escape codes

- [deps.clj](https://github.com/borkdude/deps.clj): A faithful port of the clojure CLI bash script to Clojure
  - Upgrade/sync with clojure CLI v1.11.4.1474
  - deps.clj is now available as `clojure.exe` via the [clj-msi](https://github.com/casselc/clj-msi) installer and the official installation method on plain Windows!

- [fs](https://github.com/babashka/fs) - File system utility library for Clojure
  - [#132](https://github.com/babashka/fs/issues/132): add `read-link` to resolve symbolic link, without target of link needing to exist

- [tools](https://github.com/borkdude/tools): a set of [bbin](https://github.com/babashka/bbin/) installable scripts
  - Updated antq
  - Added `--minimize` option to the ddiff script

- [http-client](https://github.com/babashka/http-client): babashka's http-client<br>
  - Ensure that http-client works with Clojure 1.10 as the minimum supported Clojure version

- [sci.nrepl](https://github.com/babashka/sci.nrepl): nREPL server for SCI projects that run in the browser
  - Mostly changes to accomodate running sci.nrepl with [clerk](https://github.com/nextjournal/clerk) viewer functions

- [clerk](https://github.com/nextjournal/clerk): Moldable Live Programming for Clojure
  - Mostly worked on making viewer functions available from `.cljs` files and allow working on them via a nREPL session

- [squint](https://github.com/squint-cljs/squint): CLJS _syntax_ to JS compiler
  - Nikita Prokopov made the [squint logo](https://github.com/squint-cljs/squint/blob/main/logo/logo.svg)!
  - [#542](https://github.com/squint-cljs/squint/issues/542): fix `run` on Windows

## Other projects

These are (some of the) other projects I'm involved with but little to no activity
happened in the past month.

<details>
<summary>Click for more details</summary>
- [html](https://github.com/borkdude/html): Html generation library inspired by squint's html tag
- [neil](https://github.com/babashka/neil): A CLI to add common aliases and features to deps.edn-based projects.<br>
- [rewrite-edn](https://github.com/borkdude/rewrite-edn): Utility lib on top of
  rewrite-clj with common operations to update EDN while preserving whitespace
  and comments
- [instaparse-bb](https://github.com/babashka/instaparse-bb): Use instaparse from babashka
- [scittle](https://github.com/babashka/scittle): Execute Clojure(Script) directly from browser script tags via SCI
- [bbin](https://github.com/babashka/bbin): Install any Babashka script or project with one command<br>
- [process](https://github.com/babashka/process): Clojure library for shelling out / spawning sub-processes
- [babashka.json](https://github.com/babashka/json): babashka JSON library/adapter
- [tools-deps-native](https://github.com/babashka/tools-deps-native) and [tools.bbuild](https://github.com/babashka/tools.bbuild): use tools.deps directly from babashka
- [squint-macros](https://github.com/squint-cljs/squint-macros): a couple of
  macros that stand-in for
  [applied-science/js-interop](https://github.com/applied-science/js-interop)
  and [promesa](https://github.com/funcool/promesa) to make CLJS projects
  compatible with squint and/or cherry.
- [sci.configs](https://github.com/babashka/sci.configs): A collection of ready to be used SCI configs.
- [grasp](https://github.com/borkdude/grasp): Grep Clojure code using clojure.spec regexes
- [lein-clj-kondo](https://github.com/clj-kondo/lein-clj-kondo): a leiningen plugin for clj-kondo
- [http-kit](https://github.com/http-kit/http-kit): Simple, high-performance event-driven HTTP client+server for Clojure.
- [babashka.nrepl](https://github.com/babashka/babashka.nrepl): The nREPL server from babashka as a library, so it can be used from other SCI-based CLIs
- [jet](https://github.com/borkdude/jet): CLI to transform between JSON, EDN, YAML and Transit using Clojure
- [pod-babashka-go-sqlite3](https://github.com/babashka/pod-babashka-go-sqlite3): A babashka pod for interacting with sqlite3
- [pod-babashka-fswatcher](https://github.com/babashka/pod-babashka-fswatcher): babashka filewatcher pod
- [lein2deps](https://github.com/borkdude/lein2deps): leiningen to deps.edn converter
- [sql pods](https://github.com/babashka/babashka-sql-pods): babashka pods for SQL databases
- [cljs-showcase](https://github.com/borkdude/cljs-showcase): Showcase CLJS libs using SCI
- [babashka.book](https://github.com/babashka/book): Babashka manual
- [rewrite-clj](https://github.com/clj-commons/rewrite-clj): Rewrite Clojure code and edn
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

