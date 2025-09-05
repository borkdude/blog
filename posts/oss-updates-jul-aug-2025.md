Title: OSS updates July and August 2025
Date: 2025-08-04
Tags: clojure, oss updates
Description: My Clojure OSS updates for July and August 2025

In this post I'll give updates about open source I worked on during July and August 2025.

To see previous OSS updates, go [here](https://blog.michielborkent.nl/tags/oss-updates.html).

## Sponsors

I'd like to thank all the sponsors and contributors that make this work
possible. Without you the below projects would not be as mature or wouldn't
exist or be maintained at all! So a sincere thank you to everyone who
contributes to the sustainability of these projects.

<img alt="gratitude" src="https://emoji.slack-edge.com/T03RZGPFR/gratitude/f8716bb6fb7e5249.png" width="50px" text-align="center">

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

</details>

<!--

- TODO: mention upcoming talk at Clojure Conj 2025!

- ls -lat ~/dev
- babashka sub dir checken

-->

## News

- I'll be doing a talk related to babashka at the [conj 2025](https://www.2025.clojure-conj.org/schedule)!

## Updates

Here are updates about the projects/libraries I've worked on in the last two months, 19 in total!

- [babashka](https://github.com/babashka/babashka): native, fast starting Clojure interpreter for scripting.
  - Bump clojure to `1.12.2`
  - [#1843](https://github.com/babashka/babashka/issues/1843): BREAKING (potententially): non-daemon thread handling change. Similar
    to JVM clojure, babashka now waits for non-daemon threads to finish. This
    means you don't have to append `@(promise)` anymore when you spawn an
    httpkit server, for example. For futures and agents, bb uses a thread pool
    that spawns daemon threads, so that pool isn't preventing an exit. This
    behavior is similar to `clojure -X`. You can get back the old behavior where
    bb always forced an exit and ignored running non-daemon threads with
    `--force-exit`.
  - [#1690](https://github.com/babashka/babashka/issues/1690): bind `clojure.test/*test-out*` to same print-writer as `*out*` in nREPL server
  - Add `Compiler/demunge`
  - Add `clojure.lang.TaggedLiteral/create`
  - Add `java.util.TimeZone/setDefault`
  - Add `println-str`
  - SCI: Var literal or special form gets confused with local of same name
  - [#1852](https://github.com/babashka/babashka/issues/1852): `(.getContextClassLoader (Thread/currentThread))` should be able to return results from babashka classpath
  - Bump `deps.clj` to `1.12.2.1565`
  - Bind more vars like `*warn-on-reflection*` during `load{string,reader}` (same as JVM Clojure) so can load code in other than than the main thread
  - [#1845](https://github.com/babashka/babashka/issues/1845): expose `cheshire.generate/{add-encoder,encode-str}`
  - Bump timbre to `6.8.0`
  - Bump clojure.tools.logging to `1.3.0`
  - Improve interop using type hints on qualified instance methods
  - Bump Jsoup to `1.21.2`
  - Bump `fs` to `0.5.7`
  - Bump `cheshire` to `6.1.0`
  - Pods: no exception on destroy when there's still calls in progress

- [SCI](https://github.com/babashka/sci): Configurable Clojure/Script interpreter suitable for scripting
  - Add `println-str`
  - Fix [#997](https://github.com/babashka/sci/issues/997): Var is mistaken for local when used under the same name in a `let` body
  - Fix regression introduced in [#987](https://github.com/babashka/sci/issues/987)
  - Fix [#963](https://github.com/babashka/sci/issues/963): respect `:param-tags` on qualified instance method
  - Add `*suppress-read*`
  - Add `load-reader`
  - Fix [#872](https://github.com/babashka/sci/issues/872): `*loaded-libs*` is now the single source of truth about loaded libs
  - Fix [#981](https://github.com/babashka/sci/issues/981): respect type hint on instance method callee
  - Add core dynamic vars like `*warn-on-reflection*` and bind them during
    `load-string` etc. such that `set!`-ing then in a `future` works.
  - Fix [#984](https://github.com/babashka/sci/issues/984): support alternative `set!` syntax in CLJS
  - Fix [#987](https://github.com/babashka/sci/issues/987): method or property name in interop should be munged
  - Fix [#986](https://github.com/babashka/sci/issues/986): preserve error location for js static method
  - Fix [#990](https://github.com/babashka/sci/issues/990): fix `merge-opts` with `:bindings` + deprecate `:bindings` (replaced by `:namespaces {'user ...}`)

- [clerk](https://github.com/nextjournal/clerk): Moldable Live Programming for Clojure
  - Upgrade to Reagent and fix unsafe HTML rendering
  - Add viewers for HTML markdown nodes
  - Support file watching in babashka
  - Support server side rendering of formulas using KaTeX

- [edamame](https://github.com/borkdude/edamame): configurable EDN and Clojure parser with location metadata and more
  - **1.4.31 (2025-06-25)**
  - Fix [#124](https://github.com/borkdude/edamame/issues/124): add `:imports` to `parse-ns-form`
  - Fix [#125](https://github.com/borkdude/edamame/issues/125): Support `#^:foo` deprecated metadata reader macro ([@NoahTheDuke](https://github.com/NoahTheDuke))
  - Fix [#127](https://github.com/borkdude/edamame/issues/127): expose `continue` value that indicates continue-ing parsing ([@NoahTheDuke](https://github.com/NoahTheDuke))
  - Fix [#122](https://github.com/borkdude/edamame/issues/122): let `:auto-resolve-ns` affect syntax-quote
  - **1.4.30**
  - [#120](https://github.com/borkdude/edamame/issues/120): fix `:auto-resolve-ns` failing case

- [squint](https://github.com/squint-cljs/squint): CLJS _syntax_ to JS compiler
  - v0.8.153 (2025-08-31)
  - Fix [#704](https://github.com/squint-cljs/squint/issues/704): `while` didn't compile correctly
  - Add `clojure.string/includes?`
  - Emit less code for varargs functions
  - Fix solidJS example
  - Documentation improvements ([@lread](https://github.com/lread))
  - Fix [#697](https://github.com/squint-cljs/squint/issues/697): `ClassCastException` in statement function when passed Code records
  - v0.8.152 (2025-07-18)
  - Fix [#680](https://github.com/squint-cljs/squint/issues/680): support import attributes using `:with` option in require, e.g. `:with {:type :json}`
  - v0.8.151 (2025-07-15)
  - Implement `not=` as function
  - Fix [#684](https://github.com/squint-cljs/squint/issues/684): JSX output
  - v0.8.150 (2025-07-09)
  - [#678](https://github.com/squint-cljs/squint/issues/678): Implement `random-uuid` ([@rafaeldelboni](https://github.com/rafaeldelboni))
  - Fix [#681](https://github.com/squint-cljs/squint/issues/681): support unsafe HTML via `[:$ ...]` tag

- [clj-kondo](https://github.com/clj-kondo/clj-kondo): static analyzer and linter for Clojure code that sparks joy.<br>
  - [#2560](https://github.com/clj-kondo/clj-kondo/issues/2560): NEW linter: `:locking-suspicious-lock`: report when locking is used on a single arg, interned value or local object
  - [#2555](https://github.com/clj-kondo/clj-kondo/issues/2555): false positive with `clojure.string/replace` and `partial` as replacement fn
  - **2025.06.05**
  - [#2541](https://github.com/clj-kondo/clj-kondo/issues/2541): NEW linter: `:discouraged-java-method`. See [docs](https://github.com/clj-kondo/clj-kondo/blob/master/doc/linters.md)
  - [#2522](https://github.com/clj-kondo/clj-kondo/issues/2522): support `:config-in-ns` on `:missing-protocol-method`
  - [#2524](https://github.com/clj-kondo/clj-kondo/issues/2524): support `:redundant-ignore` on `:missing-protocol-method`
  - [#2536](https://github.com/clj-kondo/clj-kondo/issues/2536): false positive with `format` and whitespace flag after percent
  - [#2535](https://github.com/clj-kondo/clj-kondo/issues/2535): false positive `:missing-protocol-method` when using alias in method
  - [#2534](https://github.com/clj-kondo/clj-kondo/issues/2534): make `:redundant-ignore` aware of `.cljc`
  - [#2527](https://github.com/clj-kondo/clj-kondo/issues/2527): add test for using ns-group + config-in-ns for `:missing-protocol-method` linter
  - [#2218](https://github.com/clj-kondo/clj-kondo/issues/2218): use `ReentrantLock` to coordinate writes to cache directory within same process
  - [#2533](https://github.com/clj-kondo/clj-kondo/issues/2533): report inline def under fn and defmethod
  - [#2521](https://github.com/clj-kondo/clj-kondo/issues/2521): support `:langs` option in `:discouraged-var` to narrow to specific language
  - [#2529](https://github.com/clj-kondo/clj-kondo/issues/2529): add `:ns` to `&env` in `:macroexpand-hook` macros when executing in CLJS
  - [#2547](https://github.com/clj-kondo/clj-kondo/issues/2547): make redundant-fn-wrapper report only for all cljc branches
  - [#2531](https://github.com/clj-kondo/clj-kondo/issues/2531): add `:name` data to `:unresolved-namespace` finding for clojure-lsp

- [sci.configs](https://github.com/babashka/sci.configs): A collection of ready to be used SCI configs.
  - A configuration for [replicant](https://github.com/cjohansen/replicant/) was added

- [scittle](https://github.com/babashka/scittle): Execute Clojure(Script) directly from browser script tags via SCI
  - v0.7.27 (2025-08-21)
  - [#95](https://github.com/babashka/scittle/issues/121): support string requires
    of `globalThis` js deps ([@chr15m](https://github.com/chr15m)). See
    [docs](https://github.com/babashka/scittle/blob/main/doc/js-libraries.md).
  - Potentially breaking: `(.-foo-bar {})` now behaves as `{}.foo_bar`, i.e. the property or method name is munged.
  - v0.7.26 (2025-08-20)
  - [#121](https://github.com/babashka/scittle/issues/121): add `cjohansen/dataspex` plugin ([@jeroenvandijk](https://github.com/jeroenvandijk))
  - [#118](https://github.com/babashka/scittle/issues/118): add `goog.string/format` ([@jeroenvandijk](https://github.com/jeroenvandijk))
  - Support alternative `(set! #js {} -a 1)` CLJS syntax (by bumping SCI)
  - Add source maps to distribution
  - Add dev versions of all modules in the `dev` folder of the distribution + a `dev/scitte.cljs-devtools.js` module

- [nbb](https://github.com/babashka/nbb): Scripting in Clojure on Node.js using SCI
  - nREPL improvement for vim-fireplace

- [quickdoc](https://github.com/borkdude/quickdoc): Quick and minimal API doc generation for Clojure
  - **v0.2.5 (2025-05-01)**
  - Fix [#32](https://github.com/borkdude/quickdoc/issues/32): fix anchor links to take into account var names that differ only by case
  - **v0.2.4 (2025-05-01)**
  - Revert source link in var title and move back to `<sub>`
  - Specify clojure 1.11 as the minimal Clojure version in `deps.edn`
  - Fix macro information
  - Fix [#39](https://github.com/borkdude/quickdoc/issues/39): fix link when var is named multiple times in docstring
  - Upgrade clj-kondo to `2025.04.07`
  - Add explicit `org.babashka/cli` dependency

- [Nextjournal Markdown](https://github.com/nextjournal/markdown)
  - Drop KaTeX dependency by inlining TeXMath lib

- [babashka.nrepl-client](https://github.com/babashka/nrepl-client)
  - Add `:responses` key with raw responses

- [speculative](https://github.com/borkdude/speculative)
  - Add spec for `even?`

- [http-client](https://github.com/babashka/http-client): babashka's http-client
  -  **0.4.23 (2025-06-06)**
  - [#75](https://github.com/babashka/http-client/issues/75): override existing content type header in multipart request
  - Accept `:request-method` in addition to `:request` to align more with other clients
  - Accept `:url` in addition to `:uri` to align more with other clients

- [unused-deps](https://github.com/borkdude/unused-deps): Find unused deps in a clojure project
  - This is a brand new project!

- [fs](https://github.com/babashka/fs) - File system utility library for Clojure
  - Documentation improvements
  - Fix wrong typehint

- [cherry](https://github.com/squint-cljs/cherry): Experimental ClojureScript to ES6 module compiler
  - `not=` is now a function

- [CLI](https://github.com/babashka/cli): Turn Clojure functions into CLIs!
  - [#122](https://github.com/babashka/cli/issues/122): introduce new
    `:repeated-opts` option to enforce repeating the option for accepting multiple
    values (e.g. `--foo 1 --foo 2` rather than `--foo 1 2`)

- [deps.clj](https://github.com/borkdude/deps.clj): A faithful port of the clojure CLI bash script to Clojure
  - Fixed Java download program that respects `CLJ_JVM_OPTS` for downloading tools jar.
  - Released several versions catching up with the clojure CLI

- [pod-babashka-fswatcher](https://github.com/babashka/pod-babashka-fswatcher): babashka filewatcher pod
  - Pod protocol fix: don't send done with async messages
  - Robustness improvements
  - Bump fsnotify

- [sci.nrepl](https://github.com/babashka/sci.nrepl): nREPL server for SCI projects that run in the browser
  - Send current working directory in describe message (for tools like clojure-mcp)
  - Add `"session-closed"` to close op reply

- [pod-babashka-go-sqlite3](https://github.com/babashka/pod-babashka-go-sqlite3): A babashka pod for interacting with sqlite3
  - JSON1 support

- [specter](https://github.com/redplanetlabs/specter): Clojure(Script)'s missing piece
  - Fix babashka support by removing optimizations that only worked due to SCI bug

## Other projects

These are (some of the) other projects I'm involved with but little to no activity
happened in the past month.

<details>
<summary>Click for more details</summary>

- [quickblog](https://github.com/borkdude/quickblog): light-weight static blog engine for Clojure and babashka
- [process](https://github.com/babashka/process): Clojure library for shelling out / spawning sub-processes
- [html](https://github.com/borkdude/html): Html generation library inspired by squint's html tag
- [instaparse-bb](https://github.com/babashka/instaparse-bb): Use instaparse from babashka
- [sql pods](https://github.com/babashka/babashka-sql-pods): babashka pods for SQL databases
- [rewrite-edn](https://github.com/borkdude/rewrite-edn): Utility lib on top of
- [rewrite-clj](https://github.com/clj-commons/rewrite-clj): Rewrite Clojure code and edn
- [tools-deps-native](https://github.com/babashka/tools-deps-native) and [tools.bbuild](https://github.com/babashka/tools.bbuild): use tools.deps directly from babashka
- [http-server](https://github.com/babashka/http-server): serve static assets
- [bbin](https://github.com/babashka/bbin): Install any Babashka script or project with one comman
- [qualify-methods](https://github.com/borkdude/qualify-methods)
  - Initial release of experimental tool to rewrite instance calls to use fully
    qualified methods (Clojure 1.12 only0
- [neil](https://github.com/babashka/neil): A CLI to add common aliases and features to deps.edn-based projects.<br>
- [tools](https://github.com/borkdude/tools): a set of [bbin](https://github.com/babashka/bbin/) installable scripts
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

