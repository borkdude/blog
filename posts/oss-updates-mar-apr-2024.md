Title: OSS updates January and February 2024
Date: 2024-04-30
Tags: clojure, oss updates
Description: My Clojure OSS updates for March and April 2024

In this post I'll give updates about open source I worked on during January and February 2024.

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
drwxr-xr-x   22 borkdude  staff    704 Apr 26 16:19 bbin
drwxr-xr-x   14 borkdude  staff    448 Apr 25 18:38 vite-plugin-squint
drwxr-xr-x  432 borkdude  staff  13824 Apr 25 16:16 squint
-->

## Updates

Here are updates about the projects/libraries I've worked on last month.

- [neil](https://github.com/babashka/neil): A CLI to add common aliases and features to deps.edn-based projects.<br>
  Released version 0.3.65 with the following changes:
  - [#209](https://github.com/babashka/neil/issues/209): add newlines between dependencies
  - [#185](https://github.com/babashka/neil/issues/185): throw on non-existing library
  - Bump `babashka.cli`
  - Fetch latest stable `slipset/deps-deploy`, instead of hard-coding ([@vedang](https://github.com/vedang))
  - Several emacs package improvements ([@agzam](https://github.com/agzam))

- [CLI](https://github.com/babashka/cli): Turn Clojure functions into CLIs!<br>
  Released version 0.8.58-59
  - Fix [#96](https://github.com/babashka/cli/issues/96): prevent false defaults from being removed/ignored
  - Fix [#91](https://github.com/babashka/cli/issues/91): keyword options and hyphen options should not mix
  - Fix [#89](https://github.com/babashka/cli/issues/89): long option never represents alias

- [rewrite-edn](https://github.com/borkdude/rewrite-edn): Utility lib on top of
  rewrite-clj with common operations to update EDN while preserving whitespace
  and comments<br>
  Released 0.4.8 with the following update:
  - Add newline after adding new element to top level map with `assoc-in`

- [nbb](https://github.com/babashka/nbb): Scripting in Clojure on Node.js using SCI
  - nbb bundle JS output will ignore `nbb.edn`
  - [#351](https://github.com/babashka/nbb/issues/351): Update bun docs/example.
  - Add `cljs.core/exists?`

- [clojure-mode](https://github.com/nextjournal/clojure-mode): Clojure/Script mode for CodeMirror 6.
  - Fix [#49](https://github.com/nextjournal/clojure-mode/issues/49): bug with hitting backspace after line comment
    Test it in the [squint playground](https://squint-cljs.github.io/squint/?repl=true&src=I18oKyAxIDIgMyk%3D).

- [instaparse-bb](https://github.com/babashka/instaparse-bb): Use instaparse from babashka
  - Serialize regexes in parse results

- [scittle](https://github.com/babashka/scittle): Execute Clojure(Script) directly from browser script tags via SCI<br>
  Released v0.6.17
  - [#77](https://github.com/babashka/babashka/issues/77): make dependency on browser (`js/document`) optional so scittle can run in webworkers, Node.js, etc.
  - [#69](https://github.com/babashka/babashka/issues/69): executing script tag with src + whitespace doesn't work
  - [#72](https://github.com/babashka/babashka/issues/72): add clojure 1.11 functions like `update-vals`
  - [#75](https://github.com/babashka/babashka/issues/75): Support reader conditionals in source code


- [cherry](https://github.com/squint-cljs/cherry): Experimental ClojureScript to ES6 module compiler
  - [#127](https://github.com/squint-cljs/cherry/issues/127): fix duplicate `cherry-cljs` property in `package.json` which caused issues with some bundlers
  - Bump squint common compiler code

- [clj-kondo](https://github.com/clj-kondo/clj-kondo): static analyzer and linter for Clojure code that sparks joy.<br>
  Released 2024.03.13
  - Fix memory usage regression introduced in 2024.03.05
  - [#2299](https://github.com/clj-kondo/clj-kondo/issues/2299): Add documentation for `:java-static-field-call`.
  - [#1732](https://github.com/clj-kondo/clj-kondo/issues/1732): new linter: `:shadowed-fn-param` which warns on using the same parameter name twice, as in `(fn [x x])`
  - [#2276](https://github.com/clj-kondo/clj-kondo/issues/2276): New Clojure 1.12 array notation (`String*`) may occur outside of metadata
  - [#2278](https://github.com/clj-kondo/clj-kondo/issues/2278): `bigint` in CLJS is a known symbol in `extend-type`
  - [#2288](https://github.com/clj-kondo/clj-kondo/issues/2288): fix static method analysis and suppressing `:java-static-field-call` locally
  - [#2293](https://github.com/clj-kondo/clj-kondo/issues/2293): fix false positive static field call for `(Thread/interrupted)`
  - [#2903](https://github.com/clj-kondo/clj-kondo/issues/2903): publish multi-arch Docker images (including linux aarch64)
  - [#2295](https://github.com/clj-kondo/clj-kondo/issues/2295): lint case test symbols in list
  <br>Unreleased changed:
  - [#1035](https://github.com/clj-kondo/clj-kondo/issues/1035): Support SARIF output with `--config {:output {:format :sarif}}`
  - [#2309](https://github.com/clj-kondo/clj-kondo/issues/2309): report unused for expression
  - [#2135](https://github.com/clj-kondo/clj-kondo/issues/2135): fix regression with unused JavaScript namespace
  - [#2302](https://github.com/clj-kondo/clj-kondo/issues/2302): New linter: `:equals-expected-position` to enforce expected value to be in first (or last) position. See [docs](https://github.com/clj-kondo/clj-kondo/blob/master/doc/linters.md)
  - [#2304](https://github.com/clj-kondo/clj-kondo/issues/2304): Report unused value in `defn` body

- [clerk](https://github.com/nextjournal/clerk)
  - [#646](https://github.com/nextjournal/clerk/issues/646) Fix parsing + location issue which fixes compatibility with honey.sql

- [http-client](https://github.com/babashka/http-client): babashka's http-client<br>
  Released 0.4.17-19
  - [#55](https://github.com/babashka/http-client/issues/55): allow `:body` be `java.net.http.HttpRequest$BodyPublisher`
  - Support a Clojure function as `:client` option, mostly useful for testing
  - [#49](https://github.com/babashka/http-client/issues/49): add `::oauth-token` interceptor
  - [#52](https://github.com/babashka/http-client/issues/52): document `:throw` option


- [squint](https://github.com/squint-cljs/squint): CLJS _syntax_ to JS compiler
  - [#509](https://github.com/squint-cljs/squint/issues/509): Optimization: use arrow fn for implicit IIFE when possible
  - Optimization: emit `const` in let expressions, which esbuild optimizes better
  - Don't wrap arrow function in parens, see [this issue](https://github.com/squint-cljs/vite-plugin-squint/pull/12)
  - Fix [#499](https://github.com/squint-cljs/squint/issues/499): add support for emitting arrow functions with `^:=>` metadata
  - Fix [#505](https://github.com/squint-cljs/squint/issues/505): Support `:rename` in `:require`
  - Fix [#490](https://github.com/squint-cljs/squint/issues/490): render css maps in html mode
  - Fix [#502](https://github.com/squint-cljs/squint/issues/502): allow method names in `defclass` to override squint built-ins
  - Fix [#496](https://github.com/squint-cljs/squint/issues/496): don't wrap strings in another set of quotes
  - Fix rendering of attribute expressions in HTML (should be wrapped in quotes)
  - Compile destructured function args to JS destructuring when annotated with `^:js`. This benefits working with vitest and playwright.
  - [#481](https://github.com/squint-cljs/squint/issues/481): BREAKING, squint no longer automatically copies all non-compiled files to the `:output-dir`. This behavior is now explicit with `:copy-resources`, see docs.
  - Add new `#html` reader for producing HTML literals using hiccup. See [docs](https://github.com/squint-cljs/squint?tab=readme-ov-file#html) and [playground example](https://squint-cljs.github.io/squint/?src=KG5zIG15ZWxlbWVudAogICg6cmVxdWlyZSBbc3F1aW50LmNvcmUgOnJlZmVyIFtkZWZjbGFzcyBqcy10ZW1wbGF0ZV1dCiAgIFsiaHR0cHM6Ly9lc20uc2gvbGl0IiA6YXMgbGl0XSkpCgooZGVmY2xhc3MgTXlFbGVtZW50CiAgKGV4dGVuZHMgbGl0L0xpdEVsZW1lbnQpCiAgKGZpZWxkIG5hbWUgIldvcmxkIikKICAoZmllbGQgY291bnQgMCkKCiAgKGNvbnN0cnVjdG9yIFtfXQogICAgKHN1cGVyKSkKCiAgT2JqZWN0CiAgKHJlbmRlciBbdGhpc10KICAgICNodG1sIF5saXQvaHRtbAogICAgWzpkaXYKICAgICBbOmgxIG5hbWVdCiAgICAgWzpidXR0b24geyJAY2xpY2siICguLW9uQ2xpY2sgdGhpcykKICAgICAgICAgICAgICAgOnBhcnQgImJ1dHRvbiJ9CiAgICAgICJDbGljayBjb3VudCAiIGNvdW50XV0pCgogIChvbkNsaWNrIFt0aGlzXQogICAgKHNldCEgY291bnQgKGluYyBjb3VudCkpCiAgICAoLmRpc3BhdGNoRXZlbnQgdGhpcyAobmV3IGpzL0N1c3RvbUV2ZW50ICJjb3VudC1jaGFuZ2VkIikpKSkKCihzZXQhICguLXByb3BlcnRpZXMgTXlFbGVtZW50KSAjanMgeyJjb3VudCIgI2pzIHt9fSkKCihqcy93aW5kb3cuY3VzdG9tRWxlbWVudHMuZGVmaW5lICJteS1lbGVtZW50IiBNeUVsZW1lbnQpCgooZGVmIGFwcCAob3IgKGpzL2RvY3VtZW50LnF1ZXJ5U2VsZWN0b3IgIiNhcHAiKQogICAgICAgICAgIChkb3RvIChqcy9kb2N1bWVudC5jcmVhdGVFbGVtZW50ICJkaXYiKQogICAgICAgICAgICAgKHNldCEgLWlkICJhcHAiKQogICAgICAgICAgICAgKGpzL2RvY3VtZW50LmJvZHkucHJlcGVuZCkpKSkKCihzZXQhICguLWlubmVySFRNTCBhcHApICNodG1sIFs6bXktZWxlbWVudF0p).
  - [#483](https://github.com/squint-cljs/squint/issues/483): Fix operator precedence problem

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

- [fs](https://github.com/babashka/fs) - File system utility library for Clojure

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

