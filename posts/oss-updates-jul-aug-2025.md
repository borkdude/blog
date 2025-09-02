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

## Updates

Here are updates about the projects/libraries I've worked on in the last two months, 19 in total!

- [babashka](https://github.com/babashka/babashka): native, fast starting Clojure interpreter for scripting.
  - Bump edamame (support old-style `#^` metadata)
  - Bump SCI: fix `satisfies?` for protocol extended to `nil`
  - Bump rewrite-clj to `1.2.50`
  - **1.12.204 (2025-06-24)**
  - Compatibility with [clerk](https://github.com/nextjournal/clerk)'s main branch
  - [#1834](https://github.com/babashka/babashka/issues/1834): make `taoensso/trove` work in bb by exposing another `timbre` var
  - Bump `timbre` to `6.7.1`
  - Protocol method should have `:protocol` meta
  - Add `print-simple`
  - Make bash install script work on Windows for GHA
  - Upgrade Jsoup to `1.21.1`
  - **1.12.203 (2025-06-18)**
  - Support `with-redefs` + `intern` (see SCI issue [#973](https://github.com/babashka/sci/issues/973)
  - [#1832](https://github.com/babashka/babashka/issues/1832): support `clojure.lang.Var/intern`
  - Re-allow `init` as task name
  - **1.12.202 (2025-06-15)**
  - Support `clojure.lang.Var/{get,clone,reset}ThreadBindingFrame` for JVM Clojure compatibility
  - [#1741](https://github.com/babashka/babashka/issues/1741): fix `taoensso.timbre/spy` and include test
  - Add `taoensso.timbre/set-ns-min-level!` and `taoensso.timbre/set-ns-min-level`
  - **1.12.201 (2025-06-12)**
  - [#1825](https://github.com/babashka/babashka/issues/1825): Add [Nextjournal Markdown](https://github.com/nextjournal/markdown) as built-in Markdown library
  - Promesa compatibility (pending PR [here](https://github.com/funcool/promesa/pull/160))
  - Upgrade clojure to `1.12.1`
  - [#1818](https://github.com/babashka/babashka/issues/1818): wrong argument order in `clojure.java.io/resource` implementation
  - Add `java.text.BreakIterator`
  - Add classes for compatibility with [promesa](https://github.com/funcool/promesa):
    - `java.lang.Thread$Builder$OfPlatform`
    - `java.util.concurrent.ForkJoinPool`
    - `java.util.concurrent.ForkJoinPool$ForkJoinWorkerThreadFactory`
    - `java.util.concurrent.ForkJoinWorkerThread`
    - `java.util.concurrent.SynchronousQueue`
  - Add `taoensso.timbre/set-min-level!`
  - Add `taoensso.timbre/set-config!`
  - Bump `fs` to `0.5.26`
  - Bump `jsoup` to `1.20.1`
  - Bump `edamame` to `1.4.30`
  - Bump `taoensso.timbre` to `6.7.0`
  - Bump `pods`: more graceful error handling when pod quits unexpectedly
  - [#1815](https://github.com/babashka/babashka/issues/1815): Make install-script wget-compatible ([@eval](https://github.com/eval))
  - [#1822](https://github.com/babashka/babashka/issues/1822): `type` should prioritize `:type` metadata
  - `ns-name` should work on symbols
  - `:clojure.core/eval-file` should affect `*file*` during eval
  - [#1179](https://github.com/babashka/babashka/issues/1179): run `:init` in tasks only once
  - [#1823](https://github.com/babashka/babashka/issues/1823): run `:init` in tasks before task specific requires
  - Fix `resolve` when `*ns*` is bound to symbol
  - Bump `deps.clj` to `1.12.1.1550`
  - Bump `http-client` to `0.4.23`

- [SCI](https://github.com/babashka/sci): Configurable Clojure/Script interpreter suitable for scripting
  - **0.10.47 (2025-06-27)**
  - Security issue: function recursion can be forced by returning internal keyword as return value
  - Fix [#975](https://github.com/babashka/sci/issues/975): Protocol method should have :protocol var on metadata
  - Fix [#971](https://github.com/babashka/sci/issues/971): fix `satisfies?` for protocol that is extended to `nil`
  - Fix [#977](https://github.com/babashka/sci/issues/977): Can't analyze sci.impl.analyzer with splint
  - **0.10.46 (2025-06-18)**
  - Fix [#957](https://github.com/babashka/sci/issues/957): `sci.async/eval-string+` should return promise with `:val nil` for ns form rather than `:val <Promise>`
  - Fix [#959](https://github.com/babashka/sci/issues/959): Java interop improvement: instance method invocation now leverages type hints
  - Bump edamame to `1.4.30`
  - Give metadata `:type` key priority in `type` implementation
  - Fix [#967](https://github.com/babashka/sci/issues/967): `ns-name` should work on symbols
  - Fix [#969](https://github.com/babashka/sci/issues/969): `^:clojure.core/eval-file` metadata should affect binding of `*file*` during evaluation
  - Sync `sci.impl.Reflector` with changes in `clojure.lang.Reflector` in clojure 1.12.1
  - Fix `:static-methods` option for class with different name in host
  - Fix [#973](https://github.com/babashka/sci/issues/973): support `with-redefs` on core vars, e.g. `intern`. The fix for this
    issue entailed quite a big refactor of internals which removes "magic"
    injection of ctx in core vars that need it.
  - Add `unchecked-set` and `unchecked-get` for CLJS compatibility

- [clerk](https://github.com/nextjournal/clerk): Moldable Live Programming for Clojure
  - Make clerk compatible with babashka

- [quickblog](https://github.com/borkdude/quickblog): light-weight static blog engine for Clojure and babashka
  - **0.4.7 (2025-06-12)**
  - Switch to [Nextjournal Markdown](https://github.com/nextjournal/markdown) for markdown rendering
    The minimum babashka version to be used with quickblog is now v1.12.201 since it comes with Nextjournal Markdown built-in.
  - Link to previous and next posts; see "Linking to previous and next posts" in
    the README ([@jmglov](https://github.com/jmglov))
  - Fix flaky caching tests ([@jmglov](https://github.com/jmglov))
  - Fix argument passing in test runner ([@jmglov](https://github.com/jmglov))
  - Add `--date` to api/new. ([@jmglov](https://github.com/jmglov))
  - Support Selmer template for new posts in api/new
  - Add 'language-xxx' to pre/code blocks
  - Fix README.md with working version in quickstart example
  - Fix [#104](https://github.com/borkdude/quickblog/issues/104): fix caching with respect to previews
  - Fix [#104](https://github.com/borkdude/quickblog/issues/104): document `:preview` option

- [edamame](https://github.com/borkdude/edamame): configurable EDN and Clojure parser with location metadata and more
  - **1.4.31 (2025-06-25)**
  - Fix [#124](https://github.com/borkdude/edamame/issues/124): add `:imports` to `parse-ns-form`
  - Fix [#125](https://github.com/borkdude/edamame/issues/125): Support `#^:foo` deprecated metadata reader macro ([@NoahTheDuke](https://github.com/NoahTheDuke))
  - Fix [#127](https://github.com/borkdude/edamame/issues/127): expose `continue` value that indicates continue-ing parsing ([@NoahTheDuke](https://github.com/NoahTheDuke))
  - Fix [#122](https://github.com/borkdude/edamame/issues/122): let `:auto-resolve-ns` affect syntax-quote
  - **1.4.30**
  - [#120](https://github.com/borkdude/edamame/issues/120): fix `:auto-resolve-ns` failing case

- [squint](https://github.com/squint-cljs/squint): CLJS _syntax_ to JS compiler
  - [#678](https://github.com/squint-cljs/squint/issues/678): Implement `random-uuid` ([@rafaeldelboni](https://github.com/rafaeldelboni))
  - **v0.8.149 (2025-06-19)**
  - [#671](https://github.com/squint-cljs/squint/issues/671): Implement `trampoline` ([@rafaeldelboni](https://github.com/rafaeldelboni))
  - Fix [#673](https://github.com/squint-cljs/squint/issues/673): remove experimental atom as promise option since it causes unexpected behavior
  - Fix [#672](https://github.com/squint-cljs/squint/issues/672): alias may contain dots
  - **v0.8.148 (2025-05-25)**
  - Fix [#669](https://github.com/squint-cljs/squint/issues/669): munge refer-ed + renamed var
  - **v0.8.147 (2025-05-09)**
  - Fix [#661](https://github.com/squint-cljs/squint/issues/661): support `throw` in expression position
  - Fix [#662](https://github.com/squint-cljs/squint/issues/662): Fix extending protocol from other namespace to `nil`
  - Better solution for multiple expressions in return context in combination with pragmas

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
  - **v0.7.23 (2025-06-18)**
  - [#107](https://github.com/babashka/scittle/issues/107): add `replicant` plugin ([@jeroenvandijk](https://github.com/jeroenvandijk))
  - [#102](https://github.com/babashka/scittle/issues/102): add `applied-science/js-interop` plugin ([@chr15m](https://github.com/chr15m))
  - [#105](https://github.com/babashka/scittle/issues/105): add `goog.string/htmlEscape` ([@ikappaki](https://github.com/ikappaki) )
  - [#113](https://github.com/babashka/scittle/issues/113): add `unchecked-set` and `unchecked-get`

- [nbb](https://github.com/babashka/nbb): Scripting in Clojure on Node.js using SCI
  - **1.3.204 (2025-05-15)**
  - [#389](https://github.com/babashka/nbb/issues/389): fix regression caused by [#387](https://github.com/babashka/nbb/issues/387)
  - **1.3.203 (2025-05-13)**
  - [#387](https://github.com/babashka/nbb/issues/387): bump `import-meta-resolve` to fix deprecation warnings on Node 22+
  - **1.3.202 (2025-05-12)**
  - Fix nbb nrepl server for Deno
  - **1.3.201 (2025-05-08)**
  - Deno improvements for loading `jsr:` and `npm:` deps, including react in combination with reagent
  - [#382](https://github.com/babashka/nbb/issues/382): prefix all node imports with `node:`

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
  - **0.7.186**
  - Make library more GraalVM `native-image` friendly
  - **0.7.184**
  - Consolidate utils in `nextjournal.markdown.utils`
  - **0.7.181**
  - Hiccup JVM compatibility for fragments (see [#34](https://github.com/nextjournal/markdown/issues/34))
  - Support HTML blocks (`:html-block`) and inline HTML (`:html-inline`) (see [#7](https://github.com/nextjournal/markdown/issues/7))
  - Bump commonmark to 0.24.0
  - Bump markdown-it to 14.1.0
  - Render `:code` according to spec into `<pre>` and `<code>` block with language class (see [#39](https://github.com/nextjournal/markdown/issues/39))
  - No longer depend on `applied-science/js-interop`
  - Accept parsed result in `->hiccup` function
  - Expose `nextjournal.markdown.transform` through main `nextjournal.markdown` namespace
  - Stabilize API and no longer mark library alpha

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
  - [#147](https://github.com/babashka/fs/issues/147): `fs/unzip` should allow selective extraction of files ([@sogaiu](https://github.com/sogaiu))
  - [#145](https://github.com/babashka/fs/issues/145): `fs/modified-since` works only with ms precision but should support the precision of the filesystem

- [cherry](https://github.com/squint-cljs/cherry): Experimental ClojureScript to ES6 module compiler
  - Fix `cherry.embed` which is used by malli

- [deps.clj](https://github.com/borkdude/deps.clj): A faithful port of the clojure CLI bash script to Clojure
  - Released several versions catching up with the clojure CLI

## Other projects

These are (some of the) other projects I'm involved with but little to no activity
happened in the past month.

<details>
<summary>Click for more details</summary>
- [CLI](https://github.com/babashka/cli): Turn Clojure functions into CLIs!
- [process](https://github.com/babashka/process): Clojure library for shelling out / spawning sub-processes
- [html](https://github.com/borkdude/html): Html generation library inspired by squint's html tag
- [instaparse-bb](https://github.com/babashka/instaparse-bb): Use instaparse from babashka
- [sql pods](https://github.com/babashka/babashka-sql-pods): babashka pods for SQL databases
- [rewrite-edn](https://github.com/borkdude/rewrite-edn): Utility lib on top of
- [rewrite-clj](https://github.com/clj-commons/rewrite-clj): Rewrite Clojure code and edn
- [pod-babashka-go-sqlite3](https://github.com/babashka/pod-babashka-go-sqlite3): A babashka pod for interacting with sqlite3
- [tools-deps-native](https://github.com/babashka/tools-deps-native) and [tools.bbuild](https://github.com/babashka/tools.bbuild): use tools.deps directly from babashka
- [http-server](https://github.com/babashka/http-server): serve static assets
- [bbin](https://github.com/babashka/bbin): Install any Babashka script or project with one comman
- [qualify-methods](https://github.com/borkdude/qualify-methods)
  - Initial release of experimental tool to rewrite instance calls to use fully
    qualified methods (Clojure 1.12 only0
- [neil](https://github.com/babashka/neil): A CLI to add common aliases and features to deps.edn-based projects.<br>
- [tools](https://github.com/borkdude/tools): a set of [bbin](https://github.com/babashka/bbin/) installable scripts
- [sci.nrepl](https://github.com/babashka/sci.nrepl): nREPL server for SCI projects that run in the browser
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

