Title: OSS updates September and October 2026
Date: 2026-10-30
Tags: clojure, oss updates
Description: My Clojure OSS updates for September and October 2026

In this post I'll give updates about open source I worked on during September and October 2026.

To see previous OSS updates, go [here](https://blog.michielborkent.nl/tags/oss-updates.html).

## Sponsors

I'd like to thank all the sponsors and contributors who make this work
possible. Without you, the projects below would not be as mature or would not
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
you can sponsor this work in the following ways. If you work for a company that
uses my OSS, please ask your employer, that would be even better. Thank you!

- [GitHub Sponsors](https://github.com/sponsors/borkdude)
- The [Babashka](https://opencollective.com/babashka) or [Clj-kondo](https://opencollective.com/clj-kondo) OpenCollective
- [Ko-fi](https://ko-fi.com/borkdude)
- [Patreon](https://www.patreon.com/borkdude)
- [Clojurists Together](https://www.clojuriststogether.org/)

</details>


## Intro

A lot happened again in the last two months! One major highlight was my visit to
the [Clojure Conj](https://2026.clojure-conj.org) together with Rahul Dé, where we
organized a Babashka workshop. It was great seeing lots of
familiar and new faces over there. To give people at the workshop the best
experience possible, lots of improvements went into babashka prior to the
Conj... workshop driven development I guess. The major milestones I focused on during the last 6-8 months were:

- Automatic command line help and completions in babashka tasks via [babashka.cli](https://github.com/babashka/cli)
- Resolving dependencies without a JVM (by vendoring tools.deps and rewriting the maven part)
- [Babashka FFI](https://github.com/babashka/ffi): a foreign function library to call C-ABI functions from libraries
- Better nREPL support: support popular nREPL middleware like CIDER's test middleware, the CIDER inspector, etc
- TUI support via JLine

All of this combined made for a great workshop, where we developed a stateless password manager that used cryptography either by shelling out or by FFI. This project combined the most-used libraries in babashka, like [babashka.fs](https://github.com/babashka/fs), [babashka.process](https://github.com/babashka/process), [babashka.cli](https://github.com/babashka/cli), [babashka.ffi](https://github.com/babashka/ffi), etc. so even if you didn't end up using this password manager (most people probably didn't) you certainly were exposed to what you would typically use in a bb script or project.
Rahul and I will give this workshop at [EuroClojure 2027](https://2027.euroclojure.org/) as well, so in case you missed it, you'll get one more chance!

<img src="assets/conj-2026-workshop.jpg" width="70%" align="center" alt="Babashka workshop at Clojure/conj 2026, seen from the back of the room">

<img src="assets/conj-2026-rich-rahul.jpg" width="70%" align="center" alt="Rich Hickey and Rahul Dé getting his hamster hammock signed at Clojure/conj 2026">

## Highlights

Here are some personal favorite highlights from the last two months.

- [Squint](https://github.com/squint-cljs/squint)
  - now has [source-map](https://github.com/squint-cljs/squint#source-maps) support
  - was adopted by [Hyper](https://github.com/dynamic-alpha/hyper), a Datastar-centric web framework, to produce JS server side. Also see this [blog post](https://blog.michielborkent.nl/squint-tree-shaking-hyper-esbuild.html)
  - has a new [playground design](https://squint-cljs.github.io/squint/) thanks to [@paintparty](https://github.com/paintparty) ([#1045](https://github.com/squint-cljs/squint/pull/1045))

- [Antq](https://github.com/clj-commons/antq), a tool to update outdated deps, now runs with bb!

- You can now run `build.clj` in babashka with `tools.build` (which comes bundled with bb) and get `--help` and shell completions for your build tasks. [Sean Corfield](https://github.com/seancorfield) has been adopting this in projects he maintains. See for example: [next.jdbc](https://github.com/seancorfield/next-jdbc/blob/develop/bb.edn), [HoneySQL](https://github.com/seancorfield/honeysql/blob/develop/bb.edn), [logging4j2](https://github.com/seancorfield/logging4j2/blob/develop/bb.edn) and [rephrase](https://github.com/seancorfield/rephrase/blob/main/bb.edn)

- [Babashka.ffi](https://github.com/babashka/ffi)
  - First Clojars release on 2026-09-18, for the JVM, native image, CLJS and nbb. Node.js 26.1+ through `node:ffi`
  - JVM performance PRs by [@Ramblurr](https://github.com/Ramblurr) ([#36](https://github.com/babashka/ffi/pull/36), [#37](https://github.com/babashka/ffi/pull/37))
  - Adopted by [sqlite4clj](https://github.com/andersmurphy/sqlite4clj), which moved from coffi to `babashka.ffi` ([#26](https://github.com/andersmurphy/sqlite4clj/pull/26), [@Ramblurr](https://github.com/Ramblurr))

- [Clj-kondo](https://github.com/clj-kondo/clj-kondo)
  - Type system improvements: `(when (and (string? x) (pos? n)) (inc x))` will now warn since `(string? x)` implies `x` is a string and `(inc x)` will crash on that input
  - `:unknown-map-key` linter that warns when requesting keys from maps that provably aren't there: given `(defn opts [] {:host "localhost"})`, `(:hots (opts))` will warn.

## Blog posts

Blog posts that I published in this period:
- Babashka 1.13.222: the conj release (2026-09-14)
  https://blog.michielborkent.nl/babashka-1.13.222.html
- Tree shaking squint on the server with esbuild (2026-09-25)
  https://blog.michielborkent.nl/squint-tree-shaking-hyper-esbuild.html

## Updates

Here are some highlights per project. See each project's `CHANGELOG.md` for the full list.

- [Babashka](https://github.com/babashka/babashka): native, fast-starting Clojure interpreter for scripting.
  - Resolve dependencies without a JVM by default. Set `:deps-resolver :jvm` in `bb.edn` or the `add-deps` map, or `BABASHKA_DEPS_RESOLVER=jvm`, to use Java as before. See [the blog post](https://blog.michielborkent.nl/babashka-1.13.222.html)
  - Make `clojure.tools.deps` and its script namespaces, including `clojure.tools.deps.script.make-classpath2`, available to scripts
  - Bundle `clojure.tools.build.api`, with patched `install` and `javac` for babashka. A tools.build dependency on the classpath takes precedence and runs from source
  - Add `bb repl --connect [addr]` to connect to an nREPL server with completion, eldoc, documentation lookup and Ctrl-C interruption. Accepts `host:port`, a port or `unix://path`. Reads `.nrepl-port` by default.
  - Bind the nREPL server to `127.0.0.1` by default instead of `0.0.0.0`. Use `bb --nrepl-server 0.0.0.0:1667` in Docker
  - Bundle nREPL ops for the CIDER inspector, test runner (`C-c C-t`) and `cider-version`
  - Add the nREPL `interrupt` op. Interruption is cooperative and does not stop tight loops
  - Smaller binary: the bundled Clojure sources are stored gzipped, and jline's class files are no longer embedded a second time as resources.
  - Add the CIDER debugger to the nREPL server: `#dbg`, `#break`, `C-u C-M-x` and the step commands
  - Add CIDER's track-state to the nREPL server: font-lock of loaded vars and the box around instrumented functions
  - Add `babashka.deps.maven` with `find-versions`, `with-repository-credentials`, `active-proxy` and `model-repos`
  - Block the certificates of the eMudhra intermediate CA compromise ([@stathissideris](https://github.com/stathissideris))
  - The macOS amd64 binary requires macOS 11.0 or later
  - [Full changelog](https://github.com/babashka/babashka/blob/master/CHANGELOG.md)

- [babashka.ffi](https://github.com/babashka/ffi): call C functions in shared libraries from Clojure.
  - 0.1.1: First standalone release of `babashka.ffi`, available on Clojars for JVM Clojure, ClojureScript and nbb
  - Call C libraries from JVM Clojure on JDK 25 or newer, or from applications compiled with GraalVM 25 or newer. ClojureScript, nbb and shadow-cljs can call C libraries on Node.js 26.1 or newer through `node:ffi`
  - [#63](https://github.com/babashka/ffi/issues/63): `callback` accepts struct layouts as argument and return types
  - JVM call performance by [@Ramblurr](https://github.com/Ramblurr) ([#36](https://github.com/babashka/ffi/pull/36), [#37](https://github.com/babashka/ffi/pull/37))
  - New [examples](https://github.com/babashka/ffi/tree/main/examples): JavaScriptCore, pinball, SDL3, libusb and QuickJS

- [babashka.esbuild](https://github.com/babashka/babashka.esbuild): use esbuild from babashka and the JVM
  - New project. Bundle and transform JavaScript, TypeScript, JSX and CSS through `babashka.ffi`, without Node.js
  - Expose esbuild's metafile and metafile analysis ([#3](https://github.com/babashka/babashka.esbuild/pull/3), [@lread](https://github.com/lread))
  - Used by [cljdoc](https://cljdoc.org) and [hyper](https://github.com/dynamic-alpha/hyper)

- [deps-deploy](https://github.com/babashka/deps-deploy): deploy jars to Clojars and Maven Central from babashka
  - New project. Takes the options of [slipset/deps-deploy](https://github.com/slipset/deps-deploy), with transfers through `babashka.http-client`
  - GPG signing, local install, snapshots and several jars in one deploy
  - Maven Central through the Publisher Portal

- [SCI](https://github.com/babashka/sci): Configurable Clojure/Script interpreter suitable for scripting
  - Clojure 1.13 map destructuring as of clojure/clojure 33fb064d (1.13.0-alpha11): `:req!`, `:excess` and `:missing` directives, `:defaults` removed. Adds the `selector` macro to `clojure.core`.
  - Speed up `binding` (about 4x on the JVM, 2x in native bb) and `try` without a catch (about 4x)
  - `ns-map` is 6x faster and `ns-refers` 60x
  - Fix [#1094](https://github.com/babashka/sci/issues/1094): a call to a multi-arity fn created a new fn for the called arity on every call, 25x slower than a single-arity call in CLJS
  - CLJS: creating a closure inside a jitted fn is 4x to 9x faster, and creating a multi-arity fn compiles instead of escaping to the interpreter
  - Fix `instance?` on a protocol returning `true` for a type the protocol is extended to, such as `nil` or `Object`, on the JVM
  - [Full changelog](https://github.com/babashka/sci/blob/master/CHANGELOG.md)

- [clj-kondo](https://github.com/clj-kondo/clj-kondo): static analyzer and linter for Clojure code that sparks joy.
  - NEW linter `:unknown-map-key`: warn on a keyword lookup of a key not present in a map. E.g. `(:hots (opts))` will warn when `opts` returns `{:host "localhost"}`.
  - Catch up with Clojure 1.13.0-alpha9 destructuring
  - Type checker: a type predicate narrows the type of a local in `if-not`, `cond` and `and`. E.g. `(when (and (string? x) (pos? n)) (inc x))` will warn.
  - Type checker: `merge` and `merge-with` require a map or `nil` as first map argument. E.g. `(merge [1 2] [3 4])` will warn.
  - [#2774](https://github.com/clj-kondo/clj-kondo/issues/2774): `:warn-on-reflection` also warns on Java constructor calls. E.g. `(Date. x)` will warn.
  - [#3006](https://github.com/clj-kondo/clj-kondo/issues/3006), [#3014](https://github.com/clj-kondo/clj-kondo/issues/3014), [#3015](https://github.com/clj-kondo/clj-kondo/issues/3015), [#3016](https://github.com/clj-kondo/clj-kondo/issues/3016): fixes for hooks generated with `:clj-kondo/macroexpand-hook`: referred macros, helper namespaces, requires without alias and nested hooks ([@imrekoszo](https://github.com/imrekoszo))
  - [#2945](https://github.com/clj-kondo/clj-kondo/issues/2945): support `:order-by`, `:having` and `:timeout` in Datalevin and Datahike queries
  - [#2998](https://github.com/clj-kondo/clj-kondo/issues/2998): analyze Java 25 class files ([@skylightis666](https://github.com/skylightis666))
  - [Full changelog](https://github.com/clj-kondo/clj-kondo/blob/master/CHANGELOG.md)

- [Squint](https://github.com/squint-cljs/squint): CLJS _syntax_ to JS compiler
  - [#453](https://github.com/squint-cljs/squint/issues/453): Add source maps for compiled files
  - [#594](https://github.com/squint-cljs/squint/issues/594): support `for await` in `doseq`, with `^:await` on a binding, `(doseq [^:await x xs] ...)`, or on the binding vector for every binding ([#1053](https://github.com/squint-cljs/squint/pull/1053), [@shaunlebron](https://github.com/shaunlebron))
  - Add `eduction`, `implements?` and `keyword-identical?`
  - Optimize compiled output for `or`, `and` and `reify`
  - Optimize compiled output for core predicates such as `even?` and `string?` in `if`, `and`, `or` and `not`
  - [#1032](https://github.com/squint-cljs/squint/issues/1032): `#html`: escape attribute values from `{:& m}` ([@mskyan](https://github.com/mskyan))
  - Playground: Implement draggable resizing of editor and compile panes ([#1045](https://github.com/squint-cljs/squint/pull/1045), [@paintparty](https://github.com/paintparty))
  - Playground: Support codemirror dark theme across editor, repl output, and compiled code instances ([#1045](https://github.com/squint-cljs/squint/pull/1045), [@paintparty](https://github.com/paintparty))
  - [Full changelog](https://github.com/squint-cljs/squint/blob/main/CHANGELOG.md)

- [neil](https://github.com/babashka/neil): A CLI to add common aliases and features to deps.edn-based projects
  - 0.3.71: neil requires babashka 1.13.229 or later
  - [#254](https://github.com/babashka/neil/issues/254): auto-generated CLI help and [shell completions](https://github.com/babashka/neil#shell-completions) via babashka.cli. `neil dep --help` no longer throws
  - [#272](https://github.com/babashka/neil/issues/272): make `babashka.neil/-main` easier to call from code ([@lread](https://github.com/lread))
  - [#268](https://github.com/babashka/neil/issues/268): `neil dep upgrade LIB` upgrades only `LIB` instead of all deps
  - [#267](https://github.com/babashka/neil/issues/267): `neil dep add`, `dep upgrade` and `dep versions` find versions in the `:mvn/repos` of `deps.edn`. `dep versions` no longer lists snapshots

- [babashka CLI](https://github.com/babashka/cli): Turn Clojure functions into CLIs!
  - [#226](https://github.com/babashka/cli/pull/226): use metadata and docstrings from `:fn` and `:exec-fn` vars in babashka and ClojureDart
  - `dispatch`: add `:supplied` to the opts map's `:org.babashka/cli` metadata with the set of option keys given on the command line
  - [#233](https://github.com/babashka/cli/issues/233): Generated help no longer prints extra spaces when `:prog` is not specified ([@lread](https://github.com/lread))
  - [Full changelog](https://github.com/babashka/cli/blob/master/CHANGELOG.md)

- [nbb](https://github.com/babashka/nbb): Scripting in Clojure on Node.js using SCI
  - Add `babashka.ffi` built-in library for calling C functions. Requires Node.js 26.1 or newer
  - Bump SCI: faster multi-arity function dispatch, bumps edamame to `1.6.44`

- [Edamame](https://github.com/borkdude/edamame): configurable EDN and Clojure parser with location metadata and more
  - Performance: speed up parsing plain decimal numbers on the JVM [#155](https://github.com/borkdude/edamame/pull/155)
  - Performance: build map literals with transients [#159](https://github.com/borkdude/edamame/pull/159)
  - Require Clojure 1.10 or newer [#158](https://github.com/borkdude/edamame/pull/158)

- [deps.clj](https://github.com/borkdude/deps.clj): a faithful port of the Clojure CLI Bash script to Clojure
  - Allow `--version`, `--help`, `-Sdescribe` and a cached `-Spath` to run without Java.
  - Add `*make-classpath-fn*` to allow in-process classpath resolution.
  - Catch up with Clojure CLI 1.12.6.1673

- [rewrite-edn](https://github.com/borkdude/rewrite-edn): Utility lib on top of rewrite-clj
  - [#39](https://github.com/borkdude/rewrite-edn/issues/39): Support namespaced maps like `#:a{:x 1}`
  - [neil#239](https://github.com/babashka/neil/issues/239): Read and update maps with metadata like `^{:x 1} {:a 1}`
  - Fix `get`, `assoc`, `update` and `dissoc` when a map value equals the key

- [http-client](https://github.com/babashka/http-client): HTTP client for Clojure and babashka
  - [#86](https://github.com/babashka/http-client/issues/86): `uri-with-query` no longer decodes the existing query, fragment and user info when `:query-params` are used ([@dylanpulver](https://github.com/dylanpulver))
  - [#91](https://github.com/babashka/http-client/issues/91): `decode-body` passes a nil body through, e.g. of a 407 to `CONNECT`

- [fs](https://github.com/babashka/fs): file system utility library for Clojure
  - [#284](https://github.com/babashka/fs/issues/284): non-recursive `match` and `glob` now return immediate child directories, not only files
  - [#269](https://github.com/babashka/fs/issues/269): `match` and `glob` no longer drop pattern text after a 2nd colon on Unix ([@marksto](https://github.com/marksto))
  - [#279](https://github.com/babashka/fs/issues/279): minimum supported version of Clojure is now 1.11 ([@lread](https://github.com/lread))

- [html](https://github.com/borkdude/html): HTML generation library inspired by squint's HTML tag
  - Omit attributes whose value is `nil`, or `false` in html mode: `[:input {:disabled false}]` rendered `disabled="false"`, which disables the input. Matches hiccup ([#19](https://github.com/borkdude/html/pull/19), [@telekid](https://github.com/telekid))
  - Render `true` and `false` as `"true"` and `"false"` for `aria-*`, `data-*`, `contenteditable`, `draggable`, `focusable` and `spellcheck`. Names match in any case, so `:spellCheck` works as well

- [quickblog](https://github.com/borkdude/quickblog): lightweight static blog engine for Clojure and babashka
  - Add `:pre-process-markdown-fn` and `:post-process-html-fn` options to transform each post's Markdown and HTML ([README](https://github.com/borkdude/quickblog#pre--and-post-processing-markdown)) ([@jmglov](https://github.com/jmglov))

- [gh-release-artifact](https://github.com/borkdude/gh-release-artifact): Upload artifacts to GitHub releases idempotently
  - Throw when Github does not accept an upload. A failed upload no longer leaves a green build
  - Upload the new bytes before touching the existing asset when replacing one

- [Buzz](https://github.com/borkdude/buzz): write a web application with the JVM or babashka only
  - New `observe`, `atom-source` and `buzz.source/Source` API. `:watch` and `invalidate!` are gone ([#20](https://github.com/borkdude/buzz/pull/20))

- [raylib-bb-ffi](https://github.com/borkdude/raylib-bb-ffi): raylib bindings for babashka via `babashka.ffi`
  - New experiment, ported with an LLM. Tests whether the examples of [raylib-jlt](https://github.com/jlt-commons/raylib-jlt) and [raylib-clj](https://github.com/b12n-oss/raylib-clj) run in babashka and on the JVM through `babashka.ffi`

- [cljbang](https://github.com/borkdude/cljbang.el): a Clojure-like language that runs as Emacs Lisp
  - Add `for` ([#12](https://github.com/borkdude/cljbang.el/pull/12)) and `juxt` ([#9](https://github.com/borkdude/cljbang.el/pull/9), [@kpassapk](https://github.com/kpassapk))
  - Fix [#10](https://github.com/borkdude/cljbang.el/issues/10): `str/join` with a separator and an empty coll returns `""`

- [Cherry](https://github.com/squint-cljs/cherry): Experimental ClojureScript to ES6 module compiler
  - Bump squint: don't crash the nREPL server on a SyntaxError from eval ([#208](https://github.com/squint-cljs/cherry/pull/208))

- [Cream](https://github.com/borkdude/cream): Clojure + GraalVM [Crema](https://github.com/oracle/graal/issues/11327) native binary
  - Compare load times, tooling and GraalVM build with babashka ([#7](https://github.com/borkdude/cream/pull/7))

- [lein-clj-kondo](https://github.com/clj-kondo/lein-clj-kondo) and [clj-kondo-bb](https://github.com/clj-kondo/clj-kondo-bb): released alongside each clj-kondo release

Contributions to third party projects:

- [antq](https://github.com/clj-commons/antq): antq runs on babashka ([#316](https://github.com/clj-commons/antq/pull/316), [#380](https://github.com/clj-commons/antq/pull/380)) and is installable through bbin ([#386](https://github.com/clj-commons/antq/pull/386))
- [hyper](https://github.com/dynamic-alpha/hyper): compile `h/expr` against a served squint core, tree-shaken with esbuild. See [the blog post](https://blog.michielborkent.nl/squint-tree-shaking-hyper-esbuild.html). Serve source maps for `defc` components behind `:source-map?`
- [nextjournal/markdown](https://github.com/nextjournal/markdown): [#71](https://github.com/nextjournal/markdown/issues/71): render the link title in the default `:link` renderer
- [clojure-mode](https://github.com/nextjournal/clojure-mode): bind Option+S to unwrap on macOS ([#62](https://github.com/nextjournal/clojure-mode/pull/62))

## Other projects

These are some other projects I'm involved with, but little to no activity
happened in the past two months.

<details>
<summary>Click for more details</summary>

- [quickdoc](https://github.com/borkdude/quickdoc): Quick and minimal API doc generation for Clojure
- [pod-babashka-go-sqlite3](https://github.com/babashka/pod-babashka-go-sqlite3): A babashka pod for interacting with sqlite3
- [pod-babashka-gozxing](https://github.com/babashka/pod-babashka-gozxing): a babashka pod for QR code and barcode decoding/encoding
- [unused-deps](https://github.com/borkdude/unused-deps): Find unused deps in a clojure project
- [pod-babashka-fswatcher](https://github.com/babashka/pod-babashka-fswatcher): babashka filewatcher pod
- [sci.nrepl](https://github.com/babashka/sci.nrepl): nREPL server for SCI projects that run in the browser
- [babashka.nrepl-client](https://github.com/babashka/nrepl-client)
- [sci.configs](https://github.com/babashka/sci.configs): A collection of ready-to-use SCI configs.
- [rewrite-clj](https://github.com/clj-commons/rewrite-clj): Rewrite Clojure code and edn
- [tools-deps-native](https://github.com/babashka/tools-deps-native) and [tools.bbuild](https://github.com/babashka/tools.bbuild): use tools.deps directly from babashka
- [bbin](https://github.com/babashka/bbin): Install any Babashka script or project with one command
- [graal-build-time](https://github.com/clj-easy/graal-build-time): initialize Clojure classes at build time for GraalVM native-image
- [qualify-methods](https://github.com/borkdude/qualify-methods): experimental tool to rewrite instance calls to use fully qualified methods (Clojure 1.12 only)
- [tools](https://github.com/borkdude/tools): a set of [bbin](https://github.com/babashka/bbin/) installable scripts
- [babashka.json](https://github.com/babashka/json): babashka JSON library/adapter
- [speculative](https://github.com/borkdude/speculative)
- [squint-macros](https://github.com/squint-cljs/squint-macros): a couple of
  macros that stand-in for
  [applied-science/js-interop](https://github.com/applied-science/js-interop)
  and [promesa](https://github.com/funcool/promesa) to make CLJS projects
  compatible with squint and/or cherry.
- [http-kit](https://github.com/http-kit/http-kit): Simple, high-performance event-driven HTTP client+server for Clojure.
- [jet](https://github.com/borkdude/jet): CLI to transform between JSON, EDN, YAML and Transit using Clojure
- [lein2deps](https://github.com/borkdude/lein2deps): leiningen to deps.edn converter
- [cljs-showcase](https://github.com/borkdude/cljs-showcase): Showcase CLJS libs using SCI
- [babashka.book](https://github.com/babashka/book): Babashka manual
- [pod-babashka-buddy](https://github.com/babashka/pod-babashka-buddy): A pod around buddy core (Cryptographic API for Clojure).
- [carve](https://github.com/borkdude/carve) - Remove unused Clojure vars
- [4ever-clojure](https://github.com/oxalorg/4ever-clojure) - Pure CLJS version of 4clojure, meant to run forever!
- [pod-babashka-lanterna](https://github.com/babashka/pod-babashka-lanterna): Interact with clojure-lanterna from babashka
- [clj2el](https://borkdude.github.io/clj2el/): transpile Clojure to elisp
- [deflet](https://github.com/borkdude/deflet): make let-expressions REPL-friendly!
- [deps.add-lib](https://github.com/borkdude/deps.add-lib): Clojure 1.12's add-lib feature for leiningen and/or other environments without a specific version of the clojure CLI
- [process](https://github.com/babashka/process): Clojure library for shelling out / spawning subprocesses
- [parmezan](https://github.com/borkdude/parmezan): fixes unbalanced or unexpected parens or other delimiters in Clojure files
- [Reagami](https://github.com/borkdude/reagami): A minimal zero-deps Reagent-like for Squint and CLJS
- [Scittle](https://github.com/babashka/scittle): Execute Clojure(Script) directly from browser script tags via SCI
- [Choq](https://github.com/squint-cljs/choq): a ~5 MB binary running the cherry compiler on embedded QuickJS
- [squint-inline](https://github.com/squint-cljs/squint-inline): write squint functions and inline expressions in a ClojureScript project
- [graaljs-cherry](https://github.com/borkdude/graaljs-cherry): a native-image cherry REPL on GraalJS
- [clj-kondo-browser](https://github.com/borkdude/clj-kondo-browser): a static Clojure source browser built from clj-kondo analysis
- [grasp](https://github.com/borkdude/grasp): Grep Clojure code using clojure.spec regexes

</details>
