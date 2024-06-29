Title: OSS updates May and June 2024
Date: 2024-06-30
Tags: clojure, oss updates
Description: My Clojure OSS updates for May and June 2024

In this post I'll give updates about open source I worked on during May and June 2024.

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

- [babashka](https://github.com/babashka/babashka): native, fast starting Clojure interpreter for scripting.
  Released v1.3.191 with the following changes:<br>
  - Fix [#1688](https://github.com/babashka/babashka/issues/1688): `use-fixtures` should add metadata to `*ns*`
  - Fix [#1692](https://github.com/babashka/babashka/issues/1692): Add support for `ITransientSet` and `org.flatland/ordered-set`
  - Bump org.flatland/ordered to `1.15.12`.
  - Partially Fix [#1695](https://github.com/babashka/babashka/issues/1695): `--repl` arg handling should consume only one arg (itself) ([@bobisageek](https://github.com/bobisageek))
  - Partially Fix [#1695](https://github.com/babashka/babashka/issues/1695): make `*command-line-args*` value available in the REPL ([@bobisageek](https://github.com/bobisageek))
  - Fix [#1686](https://github.com/babashka/babashka/issues/1686): do not fetch dependencies/invoke java for `version`, `help`, and `describe` options ([@bobisageek](https://github.com/bobisageek))
  - [#1696](https://github.com/babashka/babashka/issues/1696): add `clojure.lang.DynamicClassLoader` constructors ([@bobisageek](https://github.com/bobisageek))
  - [#1696](https://github.com/babashka/babashka/issues/1696): add `clojure.core/*source-path*` (points to the same sci var as `*file*`) ([@bobisageek](https://github.com/bobisageek))
  - [#1696](https://github.com/babashka/babashka/issues/1696): add `clojure.main/with-read-known` ([@bobisageek](https://github.com/bobisageek))
  - [#1696](https://github.com/babashka/babashka/issues/1696): add `clojure.core.server/repl-read` ([@bobisageek](https://github.com/bobisageek))
  - [#1696](https://github.com/babashka/babashka/issues/1696): make the `cognitect-labs/transcriptor` library work ([@bobisageek](https://github.com/bobisageek))
  - [#1700](https://github.com/babashka/babashka/issues/1700): catch exceptions from resolving symbolic links during `bb.edn` lookup ([@bobisageek](https://github.com/bobisageek))
  - Support `java.nio.channels.ByteChannel` + several other related interop
  - Bump `nrepl/bencode` to `1.2.0`
  - Bump `babashka/fs`
  - Bump `org.babashka/http-client` to `0.4.18`

- [squint](https://github.com/squint-cljs/squint): CLJS _syntax_ to JS compiler
  - [#536](https://github.com/squint-cljs/squint/issues/536): HTML is not escaped in dynamic expression
  - [#537](https://github.com/squint-cljs/squint/issues/537): Fix `not`: wrap argument in parens
  - Return interop expression in function body
  - Prefer value from props map over explicit value
  - `#html` improvements, support `:&` for spreading props
  - [#492](https://github.com/squint-cljs/squint/issues/492): defclass static methods and fields
  - [#526](https://github.com/squint-cljs/squint/issues/526): Fix export of class name with dashes
  - [#517](https://github.com/squint-cljs/squint/issues/517): Preserve state over REPL evals
  - [#513](https://github.com/squint-cljs/squint/issues/513): Fix `shuffle` core function random distribution and performances
  - [#517](https://github.com/squint-cljs/squint/issues/517): Fix re-definition of class with `defclass` in REPL
  - [#522](https://github.com/squint-cljs/squint/issues/522): fix `nil` `#html` rendering issue

- [html](https://github.com/borkdude/html): Html generation library inspired by squint's html tag
  - A NEW library for html generation that is both safe, performant, generates easy to understand code and works the same across CLJ and CLJS.

- [cherry](https://github.com/squint-cljs/cherry): Experimental ClojureScript to ES6 module compiler
  - Fix [#130](https://github.com/squint-cljs/cherry/issues/130): fix predefined `:aliases` for cherry.embed
  - Support `IDeref`, `ISwap`, `IReset` in `deftype`

- [clojure-mode](https://github.com/nextjournal/clojure-mode): Clojure/Script mode for CodeMirror 6.
  - Fix [#54](https://github.com/nextjournal/clojure-mode/issues/54): support slurping from within string literal

- [pottery](https://github.com/brightin/pottery): A clojure library to interact with gettext and PO/POT files
  - Contributed a few improvements to dealing with reader conditionals

- [nbb](https://github.com/babashka/nbb): Scripting in Clojure on Node.js using SCI
  - Fix `cljs.pprint/print-table` + `with-out-str`
  - Fixed `cljs.test/testing` macro to display strings correctly on test failure ([@jaidetree](https://github.com/jaidetree))

- [CLI](https://github.com/babashka/cli): Turn Clojure functions into CLIs!<br>
  - Fix [#98](https://github.com/babashka/cli/issues/98): internal options should not interfere with :restrict

- [deps.clj](https://github.com/borkdude/deps.clj): A faithful port of the clojure CLI bash script to Clojure
  - Upgrade/sync with clojure CLI v1.11.3.1463

- [neil](https://github.com/babashka/neil): A CLI to add common aliases and features to deps.edn-based projects.<br>
  Released version 0.3.65 with the following changes:
  - [#209](https://github.com/babashka/neil/issues/209): add newlines between dependencies
  - [#185](https://github.com/babashka/neil/issues/185): throw on non-existing library
  - Bump `babashka.cli`
  - Fetch latest stable `slipset/deps-deploy`, instead of hard-coding ([@vedang](https://github.com/vedang))
  - Several emacs package improvements ([@agzam](https://github.com/agzam))

- [clj-kondo](https://github.com/clj-kondo/clj-kondo): static analyzer and linter for Clojure code that sparks joy.<br>
  - Fix [#2335](https://github.com/clj-kondo/clj-kondo/issues/2335): read causes side effect, thus not an unused value
  - Fix [#2336](https://github.com/clj-kondo/clj-kondo/issues/2336): `do` and `doto` type checking ([@yuhan0](https://github.com/yuhan0))
  - Fix [#2322](https://github.com/clj-kondo/clj-kondo/issues/2322): report locations for more reader errors ([@yuhan0](https://github.com/yuhan0))
  - Imports were copied to `.clj-kondo/imports` but weren't pick up correctly. Thanks [@frenchy64](https://github.com/frenchy64) for reporting the bug.
  - [#2333](https://github.com/clj-kondo/clj-kondo/issues/2333): Add location to invalid literal syntax errors
  - [#2323](https://github.com/clj-kondo/clj-kondo/issues/2323): New linter `:redundant-str-call` which detects unnecessary `str` calls. Off by default.
  - [#2302](https://github.com/clj-kondo/clj-kondo/issues/2302): New linter: `:equals-expected-position` to enforce expected value to be in first (or last) position. See [docs](https://github.com/clj-kondo/clj-kondo/blob/master/doc/linters.md)
  - [#1035](https://github.com/clj-kondo/clj-kondo/issues/1035): Support SARIF output with `--config {:output {:format :sarif}}`
  - [#2307](https://github.com/clj-kondo/clj-kondo/issues/2307): import configs to intermediate dir
  - [#2309](https://github.com/clj-kondo/clj-kondo/issues/2309): Report unused `for` expression
  - [#2315](https://github.com/clj-kondo/clj-kondo/issues/2315): Fix regression with unused JavaScript namespace
  - [#2304](https://github.com/clj-kondo/clj-kondo/issues/2304): Report unused value in `defn` body
  - [#2227](https://github.com/clj-kondo/clj-kondo/issues/2227): Allow `:flds` to be used in keys destructuring for ClojureDart
  - [#2316](https://github.com/clj-kondo/clj-kondo/issues/2316): Handle ignore hint on protocol method
  - [#2322](https://github.com/clj-kondo/clj-kondo/issues/2322): Add location to warning about invalid unicode character
  - [#2319](https://github.com/clj-kondo/clj-kondo/issues/2319): Support `:discouraged-var` on global JS values, like `js/fetch`

## Other projects

These are (some of the) other projects I'm involved with but little to no activity
happened in the past month.

<details>
<summary>Click for more details</summary>
- [rewrite-edn](https://github.com/borkdude/rewrite-edn): Utility lib on top of
  rewrite-clj with common operations to update EDN while preserving whitespace
  and comments
- [instaparse-bb](https://github.com/babashka/instaparse-bb): Use instaparse from babashka
- [scittle](https://github.com/babashka/scittle): Execute Clojure(Script) directly from browser script tags via SCI
- [http-client](https://github.com/babashka/http-client): babashka's http-client<br>
- [bbin](https://github.com/babashka/bbin): Install any Babashka script or project with one command<br>
- [SCI](https://github.com/babashka/sci): Configurable Clojure/Script interpreter suitable for scripting and Clojure DSLs
- [fs](https://github.com/babashka/fs) - File system utility library for Clojure
- [process](https://github.com/babashka/process): Clojure library for shelling out / spawning sub-processes
- [babashka.json](https://github.com/babashka/json): babashka JSON library/adapter
- [tools-deps-native](https://github.com/babashka/tools-deps-native) and [tools.bbuild](https://github.com/babashka/tools.bbuild): use tools.deps directly from babashka
- [edamame](https://github.com/borkdude/edamame): Configurable EDN/Clojure parser with location metadata
- [http-server](https://github.com/babashka/http-server): serve static assets
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
- [quickdoc](https://github.com/borkdude/quickdoc): Quick and minimal API doc generation for Clojure
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

