Title: OSS updates March and April 2026
Date: 2026-05-01
Tags: clojure, oss updates
Description: My Clojure OSS updates for March and April 2026

In this post I'll give updates about open source I worked on during March and April 2026.

To see previous OSS updates, go [here](https://blog.michielborkent.nl/tags/oss-updates.html).

## Sponsors

I'd like to thank all the sponsors and contributors that make this work
possible. Without you, the below projects would not be as mature or wouldn't
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
- The [Babashka](https://opencollective.com/babashka) or [Clj-kondo](https://opencollective.com/clj-kondo) OpenCollective
- [Ko-fi](https://ko-fi.com/borkdude)
- [Patreon](https://www.patreon.com/borkdude)
- [Clojurists Together](https://www.clojuriststogether.org/)

</details>

## Updates

### Babashka conf and Dutch Clojure Days 2026

[Babashka Conf 2026](https://babashka.org/conf/) is happening on May 8th in the OBA Oosterdok library in Amsterdam! David Nolen, primary maintainer of ClojureScript, will be our keynote speaker. We're excited to have [Nubank](https://international.nubank.com.br/careers/), [Exoscale](https://www.exoscale.com/jobs/), [Bob](https://github.com/bobisageek), [Flexiana](https://flexiana.com) and [Itonomi](https://itonomi.com) as sponsors. Nubank and Exoscale are hiring. Wendy Randolph will be our event host. For the schedule and other info, see [babashka.org/conf](https://babashka.org/conf/). Join the babashka-conf Slack channel on Clojurians Slack for last minute communication.
The day after babashka conf, [Dutch Clojure Days 2026](https://clojuredays.org/) will be happening, so you can enjoy a whole weekend of Clojure in Amsterdam.
Hope to see many of you there!

### Projects

In the last two months I spent significant time organizing babashka conf, but made progress in several projects as well.

My upstream work to enable `async/await` in ClojureScript was merged in the beginning of March. The implementation mirrors squint. Thanks David for reviewing and merging. Also `deftest` now supports an `^:async` annotation so you can use `async/await` and don't need to mess around with the `cljs.test/async` macro anymore:

- [CLJS-3470](https://clojure.atlassian.net/browse/CLJS-3470) `async/await`
- [CLJS-3476](https://clojure.atlassian.net/browse/CLJS-3476) `async deftest`

I'll be presenting this work at the Dutch Clojure Days.

[Rebel-readline](https://github.com/bhauman/rebel-readline/tree/master/rebel-readline) is now bb compatible. The work involved mainly exposing more JLine stuff and making sure rebel-readline didn't hit any internal JLine APIs.
One step to drive this to completion was to make a dependency, [compliment](https://github.com/alexander-yakushev/compliment/), bb compatible. Thanks both to Bruce and Alexander for the cooperation.

[Squint](https://github.com/squint-cljs/squint) now supports `cljs.test` and multimethods!

On the [cream](https://github.com/borkdude/cream) front, I put in effort to make the binary smaller and have been keeping up with the new GraalVM EA releases. I've been posting bug reports to the crema maintainer. Currently there's still an unfixed bug around core.async that I have trouble reproducing in pure Java. I also added lots of library tests to CI so I can ensure stability in the long run. For now it remains experimental, but the direction is promising.

A performance PR to [weavejester/dependency](https://github.com/weavejester/dependency) speeds up `depend`, `depends?` and `topo-sort` significantly, so [clerk](https://github.com/nextjournal/clerk) notebooks render faster.

The [cljfmt](https://github.com/weavejester/cljfmt) library, also by [@weavejester](https://github.com/weavejester), now fully runs from source in babashka. The Java diff library that wasn't bb-compatible was replaced with [text-diff](https://github.com/borkdude/text-diff), but only for the babashka path. The JVM build of cljfmt still uses the original Java diff library, with a possible switch later once text-diff has matured.

Several SCI fixes were made to improve Clojure compatibility between babashka and Clojure. E.g. records can now support extending to `IFn` which was a blocker for some Clojure libs that tried to run in `bb` so far.

Clj-kondo `2026.04.15` got a few new linters thanks to [@jramosg](https://github.com/jramosg) for stewarding most of these. It also has better out of the box [potemkin](https://github.com/clj-commons/potemkin) support, and [@alexander-yakushev](https://github.com/alexander-yakushev) contributed a wave of performance improvements.

Here are updates about the projects/libraries I've worked on in the last two months in more detail.

- NEW: [text-diff](https://github.com/borkdude/text-diff): colorized text diff utility for Clojure

- [babashka](https://github.com/babashka/babashka): native, fast starting Clojure interpreter for scripting.
  - Released 1.12.216, 1.12.217 and 1.12.218
  - Support rebel-readline as external REPL provider:
    - Add proxy support for `Completer`, `Highlighter`, `ParsedLine`, `Writer`, `Reader`
    - Add `clojure.repl/special-doc` and `clojure.repl/set-break-handler!`
    - Add `clojure.main/repl-read`
    - Add `org.jline.reader.Buffer` to class allowlist
  - Add `clojure.java.javadoc` namespace with `javadoc` available in REPL [#1933](https://github.com/babashka/babashka/issues/1933)
  - Fix `(doc var)`, `(doc set!)` and other special forms [#1932](https://github.com/babashka/babashka/issues/1932)
  - Support `(source inc)` and `(source babashka.fs/exists?)` for built-in vars [#1935](https://github.com/babashka/babashka/issues/1935)
  - Support `BABASHKA_REPL_HISTORY` env var for configurable REPL history location [#1930](https://github.com/babashka/babashka/issues/1930)
  - Fix `deftype` and `defrecord` inside non-top-level forms (e.g. `let`, `testing`) [#1936](https://github.com/babashka/babashka/issues/1936)
  - [#1948](https://github.com/babashka/babashka/issues/1948): add `java.util.HexFormat` interop support
  - [#1403](https://github.com/babashka/babashka/issues/1403): fix uberscript warnings with `:as-alias`
  - [#1955](https://github.com/babashka/babashka/issues/1955): support `-version` as an alias for `--version`
  - [#1954](https://github.com/babashka/babashka/issues/1954): add `clojure.lang.EdnReader$ReaderException`
  - [#1951](https://github.com/babashka/babashka/issues/1951): fix `--prepare` flag skipping next token
  - [#1967](https://github.com/babashka/babashka/issues/1967): expose `clojure.data.xml.tree/{flatten-elements,event-tree}`, `clojure.data.xml.event` record constructors, and `clojure.data.xml.jvm.parse/string-source`
  - [#1969](https://github.com/babashka/babashka/issues/1969): include `java.net.Proxy` and `java.net.Proxy$Type` Java classes ([@jeeger](https://github.com/jeeger))
  - [#1939](https://github.com/babashka/babashka/issues/1939): disable JLine backslash escaping/shell history commands ([@bobisageek](https://github.com/bobisageek))
  - Performance improvements for math operations and for calling functions on locals
  - Add cp437 (IBM437) charset support and many new classes to reflection config (`java.lang.reflect.Constructor`, `java.lang.reflect.Executable`, `java.util.stream.Collectors`, `java.util.Comparator` for `reify`, etc.)
  - Bump JLine to 4.0.12, cheshire to 6.2.0, `nextjournal.markdown` to 0.7.255, edamame to 1.5.39, `data.xml` to 0.2.0-alpha11, `jsoup` to 1.22.2, rewrite-clj to 1.2.54, tools.cli to 1.4.256, transit-clj to 1.1.357, fs to 0.5.32
  - [Full changelog](https://github.com/babashka/babashka/blob/master/CHANGELOG.md)

- [SCI](https://github.com/babashka/sci): Configurable Clojure/Script interpreter suitable for scripting
  - Fix `recur` with 20+ args in `loop` ([#1035](https://github.com/babashka/sci/issues/1035))
  - Check `recur` arity, throw when it doesn't match ([#1034](https://github.com/babashka/sci/issues/1034))
  - Support `IFn` on `defrecord`, `deftype` and `reify` ([#808](https://github.com/babashka/sci/issues/808), [#1036](https://github.com/babashka/sci/pull/1036))
  - Validate single binding pair in let-like conditional macros ([#1037](https://github.com/babashka/sci/pull/1037))
  - Normalize SCI types in hierarchy lookups ([#1033](https://github.com/babashka/sci/pull/1033))
  - Expose `IPrintWithWriter` as protocol ([#1032](https://github.com/babashka/sci/pull/1032))
  - Optimize tight loops: fused binding nodes + specialized inlined calls ([#1031](https://github.com/babashka/sci/pull/1031))
  - Support special form documentation in `doc` macro
  - Include SCI types in `ns-map`
  - [Full changelog](https://github.com/babashka/sci/blob/master/CHANGELOG.md)

- [clj-kondo](https://github.com/clj-kondo/clj-kondo): static analyzer and linter for Clojure code that sparks joy.<br>
  Special thanks to [@jramosg](https://github.com/jramosg) and [@alexander-yakushev](https://github.com/alexander-yakushev) for their significant contributions in this release.
  - Released 2026.04.15
  - [#2788](https://github.com/clj-kondo/clj-kondo/issues/2788): NEW linter: `:not-nil?` which suggests `(some? x)` instead of `(not (nil? x))`, and similar patterns with `when-not` and `if-not` (default level: `:off`)
  - [#2520](https://github.com/clj-kondo/clj-kondo/issues/2520): NEW linter: `:protocol-method-arity-mismatch` which warns when a protocol method is implemented with an arity that doesn't match any arity declared in the protocol ([@jramosg](https://github.com/jramosg))
  - [#2520](https://github.com/clj-kondo/clj-kondo/issues/2520): NEW linter: `:missing-protocol-method-arity` (off by default) which warns when a protocol method is implemented but not all declared arities are covered
  - [#2768](https://github.com/clj-kondo/clj-kondo/issues/2768): NEW linter: `:redundant-declare` which warns when `declare` is used after a var is already defined ([@jramosg](https://github.com/jramosg))
  - [#1878](https://github.com/clj-kondo/clj-kondo/issues/1878): support potemkin's `import-fn`, `import-macro`, and `import-def`
  - [#2498](https://github.com/clj-kondo/clj-kondo/issues/2498): support new potemkin `import-vars` `:refer` and `:rename` syntax
  - Performance optimizations across many linting paths ([@alexander-yakushev](https://github.com/alexander-yakushev)) and hook-fn lookup caching to avoid repeated SCI evaluation
  - Add type support for `pmap` and future-related functions (`future`, `future-call`, `future-done?`, `future-cancel`, `future-cancelled?`) ([@jramosg](https://github.com/jramosg))
  - [#2762](https://github.com/clj-kondo/clj-kondo/issues/2762): fix false positive: `throw` with string in CLJS no longer warns about type mismatch ([@jramosg](https://github.com/jramosg))
  - [#2770](https://github.com/clj-kondo/clj-kondo/issues/2770): linter-specific ignores now correctly respect the specified linters
  - [#2773](https://github.com/clj-kondo/clj-kondo/issues/2773): align executable path for images to be `/bin/clj-kondo` ([@harryzcy](https://github.com/harryzcy))
  - [#2621](https://github.com/clj-kondo/clj-kondo/issues/2621): load imports from symlinked config dir ([@walterl](https://github.com/walterl))
  - [#2798](https://github.com/clj-kondo/clj-kondo/issues/2798): report correct filename and error details when `StackOverflowError` occurs during analysis
  - [Full changelog](https://github.com/clj-kondo/clj-kondo/blob/master/CHANGELOG.md)

- [cream](https://github.com/borkdude/cream): Clojure + GraalVM [Crema](https://github.com/oracle/graal/issues/11327) native binary
  - Followed each GraalVM EA release: EA21 shrunk the binary to ~175MB, EA22 brought a virtual-thread fix, EA23 fixed the forkjoin segfault, EA24 finally allowed re-enabling `clojure.core.async-test`
  - Added smoke tests for `httpkit`, `nextjournal/markdown`, `clj-yaml`, core.async ioc-macros
  - Updated 10M loop benchmark numbers for EA21
  - Added Windows test status notes (still some failures on EA22)

- [squint](https://github.com/squint-cljs/squint): CLJS _syntax_ to JS compiler
  - Released 0.10.186, 0.11.187, 0.11.188 and 0.11.189
  - Add multimethod support: `defmulti`, `defmethod`, `get-method`, `methods`, `remove-method`, `remove-all-methods`, `prefer-method`, `prefers`, plus hierarchy ops `isa?`, `derive`, `underive`, `make-hierarchy`, `parents`, `ancestors`, `descendants` ([#806](https://github.com/squint-cljs/squint/issues/806))
  - `cljs.test/report` is now a multimethod, extensible via `defmethod`. `test-var` now fires `:begin-test-var` / `:end-test-var` events.
  - Accept plain `await` in async functions, in anticipation of CLJS next. The legacy `js-await` and `js/await` forms continue to work as aliases for now.
  - Add built-in `cljs.test` / `clojure.test` support: `deftest`, `is`, `testing`, `are`, `use-fixtures`, `async`, `run-tests`
  - Fix `with-meta` now preserves callability when applied to a function
  - [#783](https://github.com/squint-cljs/squint/issues/783): auto-load macros from `.cljc` files via `:require` (no need for `:require-macros`); resolve qualified symbols from macro expansions
  - [#784](https://github.com/squint-cljs/squint/issues/784): resolve transitive macro deps and auto-import runtime deps from macro expansion
  - [#809](https://github.com/squint-cljs/squint/issues/809): add `squint.compiler/compile*` and `squint.compiler/transpile*` which accept either a string or a sequence of pre-parsed forms, skipping the `forms -> string -> forms` roundtrip for SSR use cases
  - [#810](https://github.com/squint-cljs/squint/issues/810): shorthand classes in `#html` / `#jsx` were erased when an attrs map was present without a `:class` key
  - [Full changelog](https://github.com/squint-cljs/squint/blob/main/CHANGELOG.md)

- [cherry](https://github.com/squint-cljs/cherry): Experimental ClojureScript to ES6 module compiler
  - Accept plain `await` as a special form, in anticipation of CLJS next
  - Multiple `:require-macros` clauses with `:refer` now properly accumulate instead of overwriting each other
  - Add `cherry.test` with `clojure.test`-compatible testing API: `deftest`, `is`, `testing`, `are`, `use-fixtures`, `async`, `run-tests`. Macros are compiler built-ins (shared with squint), so no `:require-macros` plumbing is needed in user code.

- [nbb](https://github.com/babashka/nbb): Scripting in Clojure on Node.js using SCI
  - Released 1.4.207
  - [#408](https://github.com/babashka/nbb/issues/408): support `IFn` on `defrecord` and `reify`
  - Fix REPL and nREPL not awaiting promesa thenables (e.g. `p/then` results)

- [fs](https://github.com/babashka/fs): file system utility library for Clojure
  - Released 0.5.32 and 0.5.33
  - [#232](https://github.com/babashka/fs/issues/232): add `touch` fn ([@lread](https://github.com/lread) & [@borkdude](https://github.com/borkdude))
  - [#197](https://github.com/babashka/fs/issues/197): docstring review - step 1: consistent arg naming, improved docstrings, added `Coercions and Returns` / `Argument Naming Conventions` sections to README ([@lread](https://github.com/lread))
  - [#231](https://github.com/babashka/fs/issues/231): get/set attribute functions were never following links. They now respect the `:nofollow-links` option ([@lread](https://github.com/lread))
  - [#254](https://github.com/babashka/fs/issues/254): fix `split-ext` and `extension` on dotfiles with parent dirs (e.g. `foo/.gitignore`)
  - [#202](https://github.com/babashka/fs/issues/202): `gzip` & `gunzip` now honor dest dir when specified ([@lread](https://github.com/lread))
  - [#215](https://github.com/babashka/fs/issues/215): document effect of `umask` on created files and directories ([@lread](https://github.com/lread))
  - [#182](https://github.com/babashka/fs/issues/182): enable soft & hard link tests on Windows ([@lread](https://github.com/lread))
  - [#242](https://github.com/babashka/fs/issues/242): test: add JDK 26 to CI test matrix ([@lread](https://github.com/lread))

- [clerk](https://github.com/nextjournal/clerk): Moldable Live Programming for Clojure
  - Improve analysis performance by bumping `weavejester/dependency` ([#808](https://github.com/nextjournal/clerk/pull/808))
  - Bump SCI to `v0.12.51` ([#793](https://github.com/nextjournal/clerk/pull/793)), enables `async`/`await` in viewer functions
  - Improve presentation performance ([#803](https://github.com/nextjournal/clerk/pull/803))
  - Remove bb-specific code for array-map data structure ([#805](https://github.com/nextjournal/clerk/pull/805))
  - Preserve TOC opts ([#806](https://github.com/nextjournal/clerk/pull/806))
  - Remove redundant declare of `present+reset!` ([#809](https://github.com/nextjournal/clerk/pull/809))
  - Fix `build-graph` crash on non-Clojure source files ([#810](https://github.com/nextjournal/clerk/pull/810))

- [edamame](https://github.com/borkdude/edamame): configurable EDN and Clojure parser with location metadata and more
  - Released 1.5.38 and 1.5.39
  - `parse-ns-form`: include `:use` and `:require-macros` in `:requires`
  - Check if object is iobj before attaching metadata [#141](https://github.com/borkdude/edamame/issues/141) [#142](https://github.com/borkdude/edamame/pull/142)

- [Nextjournal Markdown](https://github.com/nextjournal/markdown): A cross-platform Clojure/Script parser for Markdown
  - Released 0.7.225
  - Add option `:disable-footnotes true` to disable parsing footnotes [#67](https://github.com/nextjournal/markdown/issues/67)

- [quickdoc](https://github.com/borkdude/quickdoc): Quick and minimal API doc generation for Clojure
  - Released 0.2.6
  - [#42](https://github.com/borkdude/quickdoc/issues/42): fix var name not recognized in docstring when preceded by multiline backtick expression
  - [#52](https://github.com/borkdude/quickdoc/issues/52): fix formatting of function signature when `:or` destructuring uses namespaced keyword fallback value
  - Dedent indented docstrings before rendering [#53](https://github.com/borkdude/quickdoc/issues/53)

- [grasp](https://github.com/borkdude/grasp): Grep Clojure code using clojure.spec regexes
  - Released 0.2.5
  - Bump SCI to 0.12.51, Clojure to 1.12.4
  - Upgrade CI to GraalVM 25; move Windows CI from Appveyor to GitHub Actions
  - Fix bug in native which dropped all match results ([@bsless](https://github.com/bsless))
  - Fix circular reference in `grasp.impl`

- [babashka.nrepl](https://github.com/babashka/babashka.nrepl): The nREPL server from babashka as a library
  - Lock output stream in `send` to prevent interleaved bencode frames from concurrent writes
  - `info` and `lookup` op refinements: `lookup` carries nested `info` map whereas `info` is a flatmap

- [pod-babashka-instaparse](https://github.com/babashka/pod-babashka-instaparse): instaparse from babashka
  - Expose `add-line-and-column-info-to-metadata`
  - Drop macOS Intel builds, now building for macOS aarch64 only
  - Migrate Windows CI from Appveyor to GitHub Actions
  - Upgrade CI to GraalVM 25
  - Add `--features=clj_easy.graal_build_time.InitClojureClasses` to native-image

- [instaparse-bb](https://github.com/babashka/instaparse-bb): Use instaparse from babashka
  - Released 0.0.7
  - Bump pod to 0.0.7
  - Add `add-line-and-column-info-to-metadata` and `get-failure`
  - Fix opts passing in `parser` (e.g. `:output-format :enlive`)
  - Support `java.net.URL` for grammars

- [babashka-sql-pods](https://github.com/babashka/babashka-sql-pods): babashka pods for SQL databases
  - Released 0.1.5 and 0.1.6
  - [#74](https://github.com/babashka/babashka-sql-pods/issues/74): add DB2 support ([@janezj](https://github.com/janezj))
  - [#72](https://github.com/babashka/babashka-sql-pods/issues/72): handle concurrent requests ([@katangafor](https://github.com/katangafor))
  - Upgrade to Oracle GraalVM 25.0.2; bump `next.jdbc`, `cheshire` (Jackson 2.12 -> 2.20), PostgreSQL, MSSQL, HSQLDB, MySQL Connector/J drivers
  - Remove DuckDB support
  - [#51](https://github.com/babashka/babashka-sql-pods/issues/51): macOS binaries are now aarch64 only

- [http-client](https://github.com/babashka/http-client): HTTP client built on java.net.http
  - Replace defunct `httpstat.us` examples with `httpbin.org` in tests

- [neil](https://github.com/babashka/neil): A CLI to add common aliases and features to deps.edn-based projects
  - Fix README instructions for dev installation ([@teodorlu](https://github.com/teodorlu))

- [deps.clj](https://github.com/borkdude/deps.clj): a faithful port of the clojure CLI bash script to Clojure
  - Released 1.12.4.1618
  - [#145](https://github.com/borkdude/deps.clj/pull/145): support for installing in FreeBSD and Windows bash environments including MINGW64, MSYS_NT and Cygwin ([@ikappaki](https://github.com/ikappaki))
  - Catch up with Clojure CLI 1.12.4.1618

Contributions to third party projects:

- [ClojureScript](https://github.com/clojure/clojurescript):
  - [CLJS-3470](https://clojure.atlassian.net/browse/CLJS-3470): added `async/await` support (merged!)
  - [CLJS-3476](https://clojure.atlassian.net/browse/CLJS-3476): added `async deftest` support (merged!)
- [weavejester/dependency](https://github.com/weavejester/dependency): improve performance of `depend`, `depends?`, and `topo-sort`
- [Engelberg/instaparse](https://github.com/Engelberg/instaparse): submitted [#242](https://github.com/Engelberg/instaparse/pull/242) for babashka compatibility. Required `:bb` reader conditionals to replace `AutoFlattenSeq` deftype with plain vectors + metadata markers, wrap `Parser` records as callable fns, and add a CI test runner. Open, awaiting review.

## Other projects

These are (some of the) other projects I'm involved with but little to no activity
happened in the past two months.

<details>
<summary>Click for more details</summary>
- [scittle](https://github.com/babashka/scittle): Execute Clojure(Script) directly from browser script tags via SCI
- [pod-babashka-go-sqlite3](https://github.com/babashka/pod-babashka-go-sqlite3): A babashka pod for interacting with sqlite3
- [unused-deps](https://github.com/borkdude/unused-deps): Find unused deps in a clojure project
- [pod-babashka-fswatcher](https://github.com/babashka/pod-babashka-fswatcher): babashka filewatcher pod
- [sci.nrepl](https://github.com/babashka/sci.nrepl): nREPL server for SCI projects that run in the browser
- [babashka.nrepl-client](https://github.com/babashka/nrepl-client)
- [http-server](https://github.com/babashka/http-server): serve static assets
- [sci.configs](https://github.com/babashka/sci.configs): A collection of ready to be used SCI configs.
- [html](https://github.com/borkdude/html): Html generation library inspired by squint's html tag
- [rewrite-edn](https://github.com/borkdude/rewrite-edn): Utility lib on top of rewrite-clj
- [rewrite-clj](https://github.com/clj-commons/rewrite-clj): Rewrite Clojure code and edn
- [tools-deps-native](https://github.com/babashka/tools-deps-native) and [tools.bbuild](https://github.com/babashka/tools.bbuild): use tools.deps directly from babashka
- [bbin](https://github.com/babashka/bbin): Install any Babashka script or project with one command
- [qualify-methods](https://github.com/borkdude/qualify-methods)
  - Initial release of experimental tool to rewrite instance calls to use fully
    qualified methods (Clojure 1.12 only)
- [tools](https://github.com/borkdude/tools): a set of [bbin](https://github.com/babashka/bbin/) installable scripts
- [babashka.json](https://github.com/babashka/json): babashka JSON library/adapter
- [speculative](https://github.com/borkdude/speculative)
- [squint-macros](https://github.com/squint-cljs/squint-macros): a couple of
  macros that stand-in for
  [applied-science/js-interop](https://github.com/applied-science/js-interop)
  and [promesa](https://github.com/funcool/promesa) to make CLJS projects
  compatible with squint and/or cherry.
- [lein-clj-kondo](https://github.com/clj-kondo/lein-clj-kondo): a leiningen plugin for clj-kondo
- [http-kit](https://github.com/http-kit/http-kit): Simple, high-performance event-driven HTTP client+server for Clojure.
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
- [CLI](https://github.com/babashka/cli): Turn Clojure functions into CLIs!
- [quickblog](https://github.com/borkdude/quickblog): light-weight static blog engine for Clojure and babashka
- [process](https://github.com/babashka/process): Clojure library for shelling out / spawning sub-processes
- [parmezan](https://github.com/borkdude/parmezan): fixes unbalanced or unexpected parens or other delimiters in Clojure files
- [reagami](https://github.com/borkdude/reagami): A minimal zero-deps Reagent-like for Squint and CLJS

</details>
