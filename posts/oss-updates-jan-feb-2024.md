Title: OSS updates January and February 2024
Date: 2024-02-29
Tags: clojure, oss updates
Description: My Clojure OSS updates for January and February 2024

In this post I'll give updates about open source I worked on during January and February 2024.

To see previous OSS updates, go [here](https://blog.michielborkent.nl/tags/oss-updates.html).

## Sponsors

I'd like to thank all the sponsors and contributors that make this work
possible. Like you can read on [Bozhidar
Batsov](https://metaredux.com/posts/2024/02/15/cider-community-impact.html)'s
blog these aren't the easiest times for Open Source sponsored software. I have
no reason to complain, but I did see a similar drop in sponsoring in the last
year. I'm thankful for those who sponsored my projects in the past and even more
for those who keep doing so!

Without _you_, the below projects would not be as mature or wouldn't
exist or be maintained at all.

Current top tier sponsors:

- [Clojurists Together](https://clojuriststogether.org/)
- [Roam Research](https://roamresearch.com/)
- [Nextjournal](https://nextjournal.com/)
- [Cognitect](https://www.cognitect.com/)

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

-->

## Updates

Here are updates about the projects/libraries I've worked on last month.

- [clj-kondo](https://github.com/clj-kondo/clj-kondo): static analyzer and linter for Clojure code that sparks joy.
  Released 2024.02.12
  - [#2276](https://github.com/clj-kondo/clj-kondo/issues/2276): New Clojure 1.12 array notation (`String*`) may occur outside of metadata
  - [#2278](https://github.com/clj-kondo/clj-kondo/issues/2278): `bigint` in CLJS is a known symbol in `extend-type`
  - [#2288](https://github.com/clj-kondo/clj-kondo/issues/2288): fix static method analysis and suppressing `:java-static-field-call` locally
  - [#2293](https://github.com/clj-kondo/clj-kondo/issues/2293): fix false positive static field call for `(Thread/interrupted)`
  - [#2903](https://github.com/clj-kondo/clj-kondo/issues/2903): publish multi-arch Docker images (including linux aarch64)
  - [#2274](https://github.com/clj-kondo/clj-kondo/issues/2274): Support clojure 1.12 new type hint notations
  - [#2260](https://github.com/clj-kondo/clj-kondo/issues/2260): calling static _field_ as function should warn, e.g. `(System/err)`
  - [#1917](https://github.com/clj-kondo/clj-kondo/issues/1917): detect string being called as function
  - [#1923](https://github.com/clj-kondo/clj-kondo/issues/1923): Lint invalid fn name
  - [#2256](https://github.com/clj-kondo/clj-kondo/issues/2256): enable `assert` in hooks
  - [#2253](https://github.com/clj-kondo/clj-kondo/issues/2253): add support for `datomic-type-extensions` to datalog syntax checking
  - [#2255](https://github.com/clj-kondo/clj-kondo/issues/2255): support `:exclude-files` in combination with linting from stdin + provided `--filename` argument
  - [#2246](https://github.com/clj-kondo/clj-kondo/issues/2246): preserve metadata on symbol when going through `:macroexpand` hook
  - [#2254](https://github.com/clj-kondo/clj-kondo/issues/2254): lint files in absence of config dir
  - [#2251](https://github.com/clj-kondo/clj-kondo/issues/2251): support suppressing `:unused-value` using `:config-in-call`
  - [#2266](https://github.com/clj-kondo/clj-kondo/issues/2266): suppress `:not-a-function` linter in reader tag
  - [#2259](https://github.com/clj-kondo/clj-kondo/issues/2259): `ns-map` unmaps var defined prior in namespace
  - [#2272](https://github.com/clj-kondo/clj-kondo/issues/2272): Report var usage in `if`/`when` condition as always truthy, e.g. `(when #'some-var 1)`

- [squint](https://github.com/squint-cljs/squint): CLJS _syntax_ to JS compiler
  - [#472](https://github.com/squint-cljs/squint/issues/472): Use consistent alias
  - [#474](https://github.com/squint-cljs/squint/issues/474): fix JSX fragment
  - [#475](https://github.com/squint-cljs/squint/issues/475): don't crash watcher on deleting file
  - Add `simple-benchmark`
  - [#468](https://github.com/squint-cljs/squint/issues/468): Keywords in JSX should render with hyphens
  - [#466](https://github.com/squint-cljs/squint/issues/466): Fix `doseq` expression with `set!` in function return position
  - [#462](https://github.com/squint-cljs/squint/issues/462): Add `"exports"` field to `package.json`
  - [#460](https://github.com/squint-cljs/squint/issues/460): escape `<` and `>` in JSX strings
  - [#458](https://github.com/squint-cljs/squint/issues/458): don't emit `null` in statement position
  - [#455](https://github.com/squint-cljs/squint/issues/455): don't export non-public vars
  - Fix infix operator in return position
  - Allow playground to use JSX in non-REPL mode
  - Add transducer arity to all existing core functions

- [babashka](https://github.com/babashka/babashka): native, fast starting Clojure interpreter for scripting.
  Two releases in the past two months with the following changes:
  - [#1660](https://github.com/babashka/babashka/issues/1660): add `:deps-root` as part of hash to avoid caching issue with `deps.clj`
  - [#1632](https://github.com/babashka/babashka/issues/1632): fix `(.readPassword (System/console))` by upgrading GraalVM to `21.0.2`
  - [#1661](https://github.com/babashka/babashka/issues/1661): follow symlink when reading adjacent bb.edn
  - [#1665](https://github.com/babashka/babashka/issues/1665): `read-string` should use non-indexing reader for compatibilty with Clojure
  - Bump edamame to 1.4.24
  - Bump http-client to 0.4.16
  - Bump babashka.cli to 0.8.57
  - Uberjar task: support reader conditional in .cljc file
  - Support reader conditional in .cljc file when creating uberjar
  - Add more `javax.net.ssl` classes
  - [#1675](https://github.com/babashka/babashka/issues/1675): add `hash-unordered-coll`
  - [#1658](https://github.com/babashka/babashka/issues/1658): fix command line parsing for scripts that parse `--version` or `version` etc
  - Add `clojure.reflect/reflect`
  - Add `java.util.ScheduledFuture`, `java.time.temporal.WeekFields`
  - Support `Runnable` to be used without import
  - Allow `catch` to be used as var name
  - [#1646](https://github.com/babashka/babashka/issues/1646): command-line-args are dropped when file exists with same name
  - [#1645](https://github.com/babashka/babashka/issues/1645): Support for `clojure.lang.LongRange`
  - [#1652](https://github.com/babashka/babashka/issues/1652): allow `bb.edn` to be empty
  - [#1586](https://github.com/babashka/babashka/issues/1586): warn when config file doesn't exist and `--debug` is enabled
  - [#1410](https://github.com/babashka/babashka/issues/1410): better error message when exec fn doesn't exist
  - Bump `babashka.cli` to `0.8.55` which contains subcommand improvements
  - Bump `deps.clj` to `1.11.1.1435`
  - Bump `babashka.fs` to `0.5.20`
  - Compatibility with `plumbing.core`
  - Compatibility with `shadow.css` by improving `tools.reader` compatibility
  - [#1647](https://github.com/babashka/babashka/issues/1647): Allow capturing env vars at build time (only relevant for building bb)

- [process](https://github.com/babashka/process): Clojure library for shelling out / spawning sub-processes
  - [#123](https://github.com/babashka/process/issues/123): `exec` now converts `:env` and `:extra-env` keywords ([@lread](https://github.com/lread))
  - [#140](https://github.com/babashka/process/issues/140): accept `java.nio.file.Path` as `:dir` argument
  - [#148](https://github.com/babashka/process/issues/148): accept `Path` in `:out`, `:err` and `:in`
  - Support `:out :bytes` ([@hansbugge](https://github.com/hansbugge))

- [babashka.json](https://github.com/babashka/json): babashka JSON library/adapter
  - Released version 0.1.6 which fixes `:key-fn` + `read` behavior for cheshire

- [tools-deps-native](https://github.com/babashka/tools-deps-native) and [tools.bbuild](https://github.com/babashka/tools.bbuild): use tools.deps directly from babashka
  - Upgraded the underlying tools.build version to the latest version used in tools.build (the very latest wasn't compatible with tools.build!)

- [edamame](https://github.com/borkdude/edamame): Configurable EDN/Clojure parser with location metadata
  - Support new `^[String]` metadata notation which desugars into `^{:param-tags [String]}`
  - Add `:map` and `:set` options to coerce map/set literals into customizable data structures, for example, an ordered collections to preserve key order.

- [nbb](https://github.com/babashka/nbb): Scripting in Clojure on Node.js using SCI
  - Add `cljs.test/run-test` macro
  - Add cljs.core/Atom
  - Add promesa `promesify`

- [http-client](https://github.com/babashka/http-client): babashka's http-client
  - [#45](https://github.com/babashka/http-client/issues/45): query param values are double encoded

- [CLI](https://github.com/babashka/cli): Turn Clojure functions into CLIs!
  - Fix [#82](https://github.com/babashka/cli/issues/82): prefer alias over composite option
  - Add `:opts` to `:error-fn` input
  - Fix command line args for test runner `--dirs`, `--only`, etc
  - Fix `--no-option` (`--no` prefix) in combination with subcommands
  - Prioritize `:exec-args` over spec `:default`s
  - `dispatch` improvements ([@Sohalt](https://github.com/Sohalt), [@borkdude](https://github.com/borkdude)):
    - The `:cmds` order of entries in the table doesn't matter
    - Support parsing intermediate options: `foo --opt1=never bar --opt2=always`

- [SCI](https://github.com/babashka/sci): Configurable Clojure/Script interpreter suitable for scripting and Clojure DSLs
  - Bump edamame
  - Add `hash-ordered-coll`
  - `read-string` should use non-indexing reader

## Other projects

These are (some of the) other projects I'm involved with but little to no activity
happened in the past month.

<details>
<summary>Click for more details</summary>
- [http-server](https://github.com/babashka/http-server): serve static assets

- [cherry](https://github.com/squint-cljs/cherry): Experimental ClojureScript to ES6 module compiler

- [fs](https://github.com/babashka/fs) - File system utility library for Clojure

- [neil](https://github.com/babashka/neil): A CLI to add common aliases and features to deps.edn-based projects

- [squint-macros](https://github.com/squint-cljs/squint-macros): a couple of
  macros that stand-in for
  [applied-science/js-interop](https://github.com/applied-science/js-interop)
  and [promesa](https://github.com/funcool/promesa) to make CLJS projects
  compatible with squint and/or cherry.

- [sci.configs](https://github.com/babashka/sci.configs): A collection of ready to be used SCI configs.

- [grasp](https://github.com/borkdude/grasp): Grep Clojure code using clojure.spec regexes

- [lein-clj-kondo](https://github.com/clj-kondo/lein-clj-kondo): a leiningen plugin for clj-kondo

- [http-kit](https://github.com/http-kit/http-kit): Simple, high-performance event-driven HTTP client+server for Clojure.

- [deps.clj](https://github.com/borkdude/deps.clj): A faithful port of the clojure CLI bash script to Clojure

- [babashka.nrepl](https://github.com/babashka/babashka.nrepl): The nREPL server from babashka as a library, so it can be used from other SCI-based CLIs

- [rewrite-edn](https://github.com/borkdude/rewrite-edn): Utility lib on top of
  rewrite-clj with common operations to update EDN while preserving whitespace
  and comments
- [jet](https://github.com/borkdude/jet): CLI to transform between JSON, EDN, YAML and Transit using Clojure
- [quickdoc](https://github.com/borkdude/quickdoc): Quick and minimal API doc generation for Clojure
- [pod-babashka-go-sqlite3](https://github.com/babashka/pod-babashka-go-sqlite3): A babashka pod for interacting with sqlite3
- [pod-babashka-fswatcher](https://github.com/babashka/pod-babashka-fswatcher): babashka filewatcher pod
- [lein2deps](https://github.com/borkdude/lein2deps): leiningen to deps.edn converter
- [scittle](https://github.com/babashka/scittle): Execute Clojure(Script) directly from browser script tags via SCI
- [sql pods](https://github.com/babashka/babashka-sql-pods): babashka pods for SQL databases
- [cljs-showcase](https://github.com/borkdude/cljs-showcase): Showcase CLJS libs using SCI
- [babashka.book](https://github.com/babashka/book): Babashka manual
- [instaparse-bb](https://github.com/babashka/instaparse-bb)
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

