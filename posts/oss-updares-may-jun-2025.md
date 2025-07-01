Title: OSS updates May and June 2025
Date: 2025-06-30
Tags: clojure, oss updates
Description: My Clojure OSS updates for May and June 2025
Preview: true

In this post I'll give updates about open source I worked on during May and June 2025.

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

sources: https://github.com/borkdude
local ~/dev and ~/dev/babashka dir (since github doesn't show all repos)


drwxr-xr-x   20 borkdude  staff    640 20 jun. 16:34 lein2deps
drwxr-xr-x@  25 borkdude  staff    800 19 jun. 22:38 sci.configs
drwxr-xr-x   10 borkdude  staff    320 19 jun. 18:00 graalvm-demos
drwxr-xr-x@  27 borkdude  staff    864 17 jun. 23:49 scittle
drwxr-xr-x@  51 borkdude  staff   1632 17 jun. 22:35 nbb
drwxr-xr-x   24 borkdude  staff    768 13 jun. 14:45 fusebox
drwxr-xr-x   28 borkdude  staff    896 12 jun. 14:43 promesa
drwxr-xr-x   32 borkdude  staff   1024 11 jun. 23:11 markdown
drwxr-xr-x   13 borkdude  staff    416  9 jun. 23:40 nrepl-client
drwxr-xr-x   25 borkdude  staff    800  9 jun. 12:16 martian
drwxr-xr-x   22 borkdude  staff    704  8 jun. 15:20 speculative
drwxr-xr-x   21 borkdude  staff    672  6 jun. 20:26 clojure-mcp
drwxr-xr-x   23 borkdude  staff    736  6 jun. 13:35 http-client
drwxr-xr-x   16 borkdude  staff    512  5 jun. 14:50 lein-clj-kondo
drwxr-xr-x   10 borkdude  staff    320  5 jun. 14:45 pod-registry
drwxr-xr-x   13 borkdude  staff    416  3 jun. 15:22 unused-deps
drwxr-xr-x   21 borkdude  staff    672 20 mei  22:50 markdown-clj
drwxr-xr-x   21 borkdude  staff    672 19 mei  17:36 hiccup
drwxr-xr-x    6 borkdude  staff    192 16 mei  13:43 bencode-js
drwxr-xr-x   58 borkdude  staff   1856 13 mei  12:11 ductile
drwxr-xr-x   36 borkdude  staff   1152 12 mei  20:55 malli
drwxr-xr-x  124 borkdude  wheel   3968 12 mei  20:53 cherry
drwxr-xr-x   21 borkdude  staff    672  5 mei  10:45 quickdoc
drwxr-xr-x   18 borkdude  staff    576  2 mei  12:15 instaparse.bb
drwxr-xr-x   26 borkdude  staff    832  2 mei  12:10 cli
drwxr-xr-x   32 borkdude  staff   1024  2 mei  11:09 rewrite-clj

babashka sub dir checken

drwxr-xr-x@  21 borkdude  staff       672 26 jun. 15:22 .git
drwxr-xr-x    7 borkdude  staff       224 25 jun. 21:08 target
drwxr-xr-x@  81 borkdude  staff      2592 25 jun. 21:07 .
-rw-r--r--    1 borkdude  staff      6995 25 jun. 21:07 project.clj
drwxr-xr-x@  50 borkdude  staff      1600 25 jun. 21:07 sci
-rw-r--r--    1 borkdude  staff     94524 24 jun. 15:30 CHANGELOG.md
-rw-r--r--    1 borkdude  staff     14267 24 jun. 14:42 deps.edn
-rwxr-xr-x    1 borkdude  staff      5554 24 jun. 10:48 install
-rwxr-xr-x    1 borkdude  staff  69894608 23 jun. 21:26 bb
drwxr-xr-x@ 923 borkdude  staff     29536 23 jun. 21:25 reports
-rw-r--r--    1 borkdude  staff     43452 23 jun. 21:25 metabom.jar
drwxr-xr-x  213 borkdude  staff      6816 20 jun. 16:53 ..
-rw-r--r--@   1 borkdude  staff         2 19 jun. 23:25 .lein-failures
-rw-r--r--    1 borkdude  staff       164 19 jun. 23:07 scratch.clj
drwxr-xr-x@   9 borkdude  staff       288 18 jun. 14:06 resources
-rw-r--r--    1 borkdude  staff       617 17 jun. 23:23 .gitignore
drwxr-xr-x@   8 borkdude  staff       256 17 jun. 23:17 .clj-kondo
-rw-r--r--@   1 borkdude  staff        20 17 jun. 23:17 .lein-env
drwxr-xr-x@  27 borkdude  staff       864 17 jun. 12:34 tmp
-rw-r--r--@   1 borkdude  staff     24944 13 jun. 23:13 .lein-repl-history
drwxr-xr-x  364 borkdude  staff     11648 13 jun. 17:47 .cpcache
drwxr-xr-x@  21 borkdude  staff       672 12 jun. 15:14 pods
drwxr-xr-x@  26 borkdude  staff       832  7 jun. 14:29 fs
drwxr-xr-x@  35 borkdude  staff      1120  3 jun. 21:40 deps.clj
-rw-r--r--    1 borkdude  staff     24080  3 jun. 17:16 README.md
-rwxr-xr-x    1 borkdude  staff  70043136  2 jun. 16:32 bb-markdown
-->

## Updates

Here are updates about the projects/libraries I've worked on in the last two months.

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
  - Support Selmer template for new posts in api/new; see [Templates > New
    posts](README.md#new-posts) in README. ([@jmglov](https://github.com/jmglov))
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
  - Add an [ajv](examples/ajv) example

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

- [SCI](https://github.com/babashka/sci): Configurable Clojure/Script interpreter suitable for scripting
  - Fix [#957](https://github.com/babashka/sci/issues/957): `sci.async/eval-string+` should return promise with `:val nil` for ns form rather than `:val <Promise>`
  - Fix [#959](https://github.com/babashka/sci/issues/959): Java interop improvement: instance method invocation now leverages type hints
  - Fix [#942](https://github.com/babashka/sci/issues/942): improve error location of invalid destructuring
  - Add `volatile?` to core vars
  - Fix [#950](https://github.com/babashka/sci/issues/950): interop on local in CLJS
  - Bump edamame to `1.4.28`

- [quickdoc](https://github.com/borkdude/quickdoc): Quick and minimal API doc generation for Clojure
  - Fix [#32](https://github.com/borkdude/quickdoc/issues/32): fix anchor links to take into account var names that differ only by case
  - Revert source link in var title and move back to `<sub>`
  - Specify clojure 1.11 as the minimal Clojure version in `deps.edn`
  - Fix macro information
  - Fix [#39](https://github.com/borkdude/quickdoc/issues/39): fix link when var is named multiple times in docstring
  - Upgrade clj-kondo to `2025.04.07`
  - Add explicit `org.babashka/cli` dependency

- [CLI](https://github.com/babashka/cli): Turn Clojure functions into CLIs!
  - [#119](https://github.com/babashka/cli/issues/119): `format-table` now formats multiline cells appropriately ([@lread](https://github.com/lread))
  - Remove `pom.xml` and `project.clj` for cljdoc
  - [#116](https://github.com/babashka/cli/issues/116): Un-deprecate `:collect` option to support custom transformation of arguments to collections ([@lread](https://github.com/lread))
  - Support `:collect` in `:spec`

- [process](https://github.com/babashka/process): Clojure library for shelling out / spawning sub-processes
  - [#163](https://github.com/babashka/process/issues/163), [#164](https://github.com/babashka/process/issues/164): Program resolution strategy for `exec` and Windows now matches macOS/Linux/PowerShell ([@lread](https://github.com/lread))
  - Fix memory leak by executing shutdown hook when process finishes earlier than VM exit ([@maxweber](https://github.com/maxweber))

- [html](https://github.com/borkdude/html): Html generation library inspired by squint's html tag
  - Fix [#3](https://github.com/borkdude/html/issues/3): allow dynamic attribute value: `(html [:a {:a (+ 1 2 3)}])`
  - Fix [#9](https://github.com/borkdude/html/issues/9): shortcuts for id and classes

- [cherry](https://github.com/squint-cljs/cherry): Experimental ClojureScript to ES6 module compiler
  - Add `cljs.pprint/pprint`
  - Add `add-tap`
  - Bump squint compiler common which brings in new `#html` id and class shortcuts + additional features and optimizations, such as an optimization for `aset`

- [nbb](https://github.com/babashka/nbb): Scripting in Clojure on Node.js using SCI
  - Add better Deno + `jsr:` dependency support, stay tuned.

- [instaparse-bb](https://github.com/babashka/instaparse-bb): Use instaparse from babashka
  - Several improvements which makes babashka compatible with [test.chuck](https://github.com/gfredericks/test.chuck). See [this screenshot](https://files.mastodon.social/media_attachments/files/114/437/768/756/996/338/original/b8ebcb333f287e5c.png)!

- [edamame](https://github.com/borkdude/edamame): Configurable EDN/Clojure parser with location metadata
  - [#117](https://github.com/borkdude/edamame/issues/117): throw on triple colon keyword

- [fs](https://github.com/babashka/fs) - File system utility library for Clojure
  - [#141](https://github.com/babashka/fs/issues/141): `fs/match` doesn't match when root dir contains glob or regex characters in path
  - [#138](https://github.com/babashka/fs/issues/138): Fix `fs/update-file` to support paths ([@rfhayashi](https://github.com/rfhayashi))

- [sql pods](https://github.com/babashka/babashka-sql-pods): babashka pods for SQL databases
  - Upgrade to GraalVM 23, fixes encoding issue with Korean characters

## Other projects

These are (some of the) other projects I'm involved with but little to no activity
happened in the past month.

<details>
<summary>Click for more details</summary>
- [rewrite-edn](https://github.com/borkdude/rewrite-edn): Utility lib on top of
- [deps.clj](https://github.com/borkdude/deps.clj): A faithful port of the clojure CLI bash script to Clojure
- [scittle](https://github.com/babashka/scittle): Execute Clojure(Script) directly from browser script tags via SCI
- [rewrite-clj](https://github.com/clj-commons/rewrite-clj): Rewrite Clojure code and edn
- [pod-babashka-go-sqlite3](https://github.com/babashka/pod-babashka-go-sqlite3): A babashka pod for interacting with sqlite3
- [tools-deps-native](https://github.com/babashka/tools-deps-native) and [tools.bbuild](https://github.com/babashka/tools.bbuild): use tools.deps directly from babashka
- [http-client](https://github.com/babashka/http-client): babashka's http-client<br>
- [http-server](https://github.com/babashka/http-server): serve static assets
- [bbin](https://github.com/babashka/bbin): Install any Babashka script or project with one comman
- [sci.configs](https://github.com/babashka/sci.configs): A collection of ready to be used SCI configs.
  - Added a configuration for `cljs.spec.alpha` and related namespaces
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

