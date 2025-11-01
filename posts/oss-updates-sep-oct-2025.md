Title: OSS updates September and October 2025
Date: 2025-08-05
Tags: clojure, oss updates
Description: My Clojure OSS updates for September and October 2025

In this post I'll give updates about open source I worked on during September and October 2025.

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

- TODO: mention talk at last Dutch Clojure Meetup
- TODO: mention upcoming talk at Clojure Conj 2025!

- ls -lat ~/dev
- babashka sub dir checken

-->

## Updates

Autumn is upon us.



<img src="assets/dutch-clojure-meetup-october-2025.jpg" width="50%" align="center">

One of the big things I’m looking forward to is speaking at [Clojure Conj 2025](https://www.2025.clojure-conj.org/schedule).

- eucalypt
- reagami
- edamame clojureCLR support
- SCI clojureCLR support

- [Cljdoc](https://github.com/cljdoc/cljdoc/blob/488fe6282737c1237c5394a66a7e8392a000c6bb/doc/cljdoc-developer-technical-guide.adoc#front-end-code) chose squint for its small bundle sizes and easy migration off of TypeScript towards CLJS



...


Here are updates about the projects/libraries I've worked on in the last two months in detail.

- [babashka](https://github.com/babashka/babashka): native, fast starting Clojure interpreter for scripting.
  - Bump to clojure 1.12.3
  - [#1870](https://github.com/babashka/babashka/issues/1870): add `.addMethod` to clojure.lang.MultiFn
  - [#1869](https://github.com/babashka/babashka/issues/1869): add `clojure.lang.ITransientCollection` for `instance?` checks
  - [#1865](https://github.com/babashka/babashka/issues/1865): support `reify` + `equals` + `hashCode` on `Object`
  - Add `java.nio.charset.CharsetDecoder`, `java.nio.charset.CodingErrorAction`, `java.nio.charset.CharacterCodingException` in support of the [sfv](https://github.com/outskirtslabs/sfv) library
  - Fix `nrepl-server` completions and lookup op to be compatible with rebel-readline
  - Add `clojure.lang.Ref` for `instance?` checks
  - Bump SCI: align unresolved symbol error message with Clojure
  - Use GraalVM 25
  - Bump deps.clj to 1.12.3.1557
  - Change unknown or REPL file path to `NO_SOURCE_PATH` instead of `<expr>` since this can cause issues on Windows when checking for absolute file paths
  - [#1001](https://github.com/babashka/babashka/issues/1001): fix encoding issues on Windows in Powershell. Also see this [GraalVM](https://github.com/oracle/graal/issues/12249) issue
  - Fixes around `java.security` and allowing setting deprecated Cipher suites at runtime. See this [commit](https://github.com/babashka/babashka/commit/ace237832a5844330f5f9c342e1498eb0ca5f7ac).
  - Support Windows Git Bash in bash install script

- [SCI](https://github.com/babashka/sci): Configurable Clojure/Script interpreter suitable for scripting
  - ClojureCLR support in progress

- [clj-kondo](https://github.com/clj-kondo/clj-kondo): static analyzer and linter for Clojure code that sparks joy.<br>
  - Unreleased
  - [#2651](https://github.com/clj-kondo/clj-kondo/issues/2651): resume linting after paren mismatches
  - 2025.10.23
  - [#2590](https://github.com/clj-kondo/clj-kondo/issues/2590): NEW linter: `duplicate-key-in-assoc`, defaults to `:warning`
  - [#2639](https://github.com/clj-kondo/clj-kondo/issues/2639): NEW `:equals-nil` linter to detect `(= nil x)` or `(= x nil)` patterns and suggest `(nil? x)` instead ([@conao3](https://github.com/conao3))
  - [#2633](https://github.com/clj-kondo/clj-kondo/issues/2633): support new `defparkingop` macro in core.async alpha
  - [#2635](https://github.com/clj-kondo/clj-kondo/pull/2635): Add `:interface` flag to `:flags` set in `:java-class-definitions` analysis output to distinguish Java interfaces from classes ([@hugoduncan](https://github.com/hugoduncan))
  - [#2636](https://github.com/clj-kondo/clj-kondo/issues/2636): set global SCI context so hooks can use `requiring-resolve` etc.
  - [#2641](https://github.com/clj-kondo/clj-kondo/issues/2641): fix linting of `def` body, no results due to laziness bug
  - [#1743](https://github.com/clj-kondo/clj-kondo/issues/1743): change `:not-empty?` to only warn on objects that are already seqs
  - Performance optimization for `:ns-groups` (thanks [@severeoverfl0w](https://github.com/severeoverfl0w))
  - Flip `:self-requiring-namespace` level from `:off` to `:warning`
  - 2025.09.22
  - Remove `dbg` from `data_readers.clj` since this breaks when using together with CIDER
  - 2025.09.19
  - [#1894](https://github.com/clj-kondo/clj-kondo/issues/1894): support `destruct` syntax
  - [#2624](https://github.com/clj-kondo/clj-kondo/issues/2624): lint argument types passed to `get` and `get-in` (especially to catch swapped arguments to get in threading macros) ([@borkdude](https://github.com/borkdude), [@Uthar](https://github.com/Uthar))
  - [#2564](https://github.com/clj-kondo/clj-kondo/issues/2564): detect calling set with wrong number of arguments
  - [#2603](https://github.com/clj-kondo/clj-kondo/issues/2603): warn on `:inline-def` with nested `deftest`
  - ...

- [squint](https://github.com/squint-cljs/squint): CLJS _syntax_ to JS compiler
  - Support passing keyword to `mapv`
  - v0.9.177
  - Inline `identical?` calls
  - Clean up emissiong of paren wrapping
  - Add `nat-int?`, `neg-int?`, `pos-int?` ([@eNotchy](https://github.com/eNotchy))
  - Add `rand`
  - v0.9.176
  - Fix rendering of `null` and `undefined` in `#html`
  - v0.9.175
  - [#747](https://github.com/squint-cljs/squint/issues/747): `#html` escape fix
  - v0.9.174
  - Optimize nested `assoc` calls, e.g. produced with `->`
  - Avoid object spread when object isn't shared (`auto-transient`)
  - v0.9.173 (2025-10-19)
  - Optimize `str` even more
  - v0.9.172 (2025-10-19)
  - Remove debug output from compilation
  - v0.9.171 (2025-10-19)
  - Optimize `=`, `and`, and `not=` even more
  - v0.9.170 (2025-10-19)
  - `not=` on undefined and false should return `true`
  - v0.9.169 (2025-10-18)
  - Optimize code produced for `assoc`, `assoc!` and `get` when object argument can be inferred or is type hinted with `^object`
  - Optimize `str` using macro that compiles into template strings + `?? ''` for null/undefined
  - Fix [#732](https://github.com/squint-cljs/squint/issues/732): `take-last` should return `nil` or empty seq for negative numbers
  - v0.8.159 (2025-10-13)
  - [#725](https://github.com/squint-cljs/squint/issues/725): `keys` and `vals` should work on `js/Map`
  - v0.8.158 (2025-10-10)
  - Make `map-indexed` and `keep-indexed` lazy
  - v0.8.157 (2025-10-08)
  - Compile time optimization for `=` when using it on numbers, strings or keyword literals
  - v0.8.156 (2025-10-07)
  - Switch `=` to a deep-equals implementation that works on primitives, objects, `Arrays`, `Maps` and `Sets`
  - v0.8.155 (2025-10-02)
  - Fix [#710](https://github.com/squint-cljs/squint/issues/710): add `parse-double`
  - Fix [#714](https://github.com/squint-cljs/squint/issues/714): `assoc-in` on `nil` or `undefined`
  - Fix [#714](https://github.com/squint-cljs/squint/issues/714): `dissoc` on `nil` or `undefined`
  - v0.8.154 (2025-09-19)
  - Basic `:import-maps` support in `squint.edn` (just literal replacements, prefixes not supported yet)

- [clerk](https://github.com/nextjournal/clerk): Moldable Live Programming for Clojure
  - Support evaluation of quoted regex
  - Support macros defined in notebooks
  - Bump cherry

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

- [edamame](https://github.com/borkdude/edamame): configurable EDN and Clojure parser with location metadata and more
  - Fix [#132](https://github.com/borkdude/edamame/issues/132): Add counterpart to Clojure's `*suppress-read*`: `:suppress-read`

- [sci.configs](https://github.com/babashka/sci.configs): A collection of ready to be used SCI configs.
  - Add config for dataspex

- [nbb](https://github.com/babashka/nbb): Scripting in Clojure on Node.js using SCI
  - nREPL improvement for vim-fireplace

- [Nextjournal Markdown](https://github.com/nextjournal/markdown)
  - Drop KaTeX dependency by inlining TeXMath lib

- [babashka.nrepl-client](https://github.com/babashka/nrepl-client)
  - Add `:responses` key with raw responses

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

- [http-server](https://github.com/babashka/http-server): serve static assets
  - 0.1.15
  - [#22](https://github.com/babashka/http-server/issues/22): fix off-by-one error in range requests ([@jyn514](https://github.com/jyn514))
  - 0.1.14
  - [#21](https://github.com/babashka/http-server/issues/21): Add `:not-found` option for handling unfound files. The option is a function of the request and should return a map with `:status` and `:body`.
  - [#19](https://github.com/babashka/http-server/issues/19): Add text/html MIME types for asp and aspx file extensions ([@respatialized](https://github.com/respatialized))
  - 0.1.13
  - [#16](https://github.com/babashka/http-server/issues/16): support range requests ([jmglov](https://github.com/jmglov))
  - [#13](https://github.com/babashka/http-server/issues/13): add an ending slash to the dir link, and don't encode the slashes ([@KDr2](https://github.com/KDr2))
  - [#12](https://github.com/babashka/http-server/issues/12): Add headers to index page (rather than just file responses)

Contributions to third party projects:

- [specter](https://github.com/redplanetlabs/specter): Clojure(Script)'s missing piece
  - Fix babashka support by removing optimizations that only worked due to SCI bug

- [clojure-test-suite](https://github.com/jank-lang/clojure-test-suite): Dialect-independent tests for clojure.core, and others, focused on characterizing how Clojure JVM behaves so that other dialects to reach parity.
  - Added babashka to the test suite

## Other projects

These are (some of the) other projects I'm involved with but little to no activity
happened in the past month.

<details>
<summary>Click for more details</summary>

- [quickdoc](https://github.com/borkdude/quickdoc): Quick and minimal API doc generation for Clojure
- [unused-deps](https://github.com/borkdude/unused-deps): Find unused deps in a clojure project
- [http-client](https://github.com/babashka/http-client): babashka's http-client
- [quickblog](https://github.com/borkdude/quickblog): light-weight static blog engine for Clojure and babashka
- [process](https://github.com/babashka/process): Clojure library for shelling out / spawning sub-processes
- [html](https://github.com/borkdude/html): Html generation library inspired by squint's html tag
- [instaparse-bb](https://github.com/babashka/instaparse-bb): Use instaparse from babashka
- [sql pods](https://github.com/babashka/babashka-sql-pods): babashka pods for SQL databases
- [rewrite-edn](https://github.com/borkdude/rewrite-edn): Utility lib on top of
- [rewrite-clj](https://github.com/clj-commons/rewrite-clj): Rewrite Clojure code and edn
- [tools-deps-native](https://github.com/babashka/tools-deps-native) and [tools.bbuild](https://github.com/babashka/tools.bbuild): use tools.deps directly from babashka
- [bbin](https://github.com/babashka/bbin): Install any Babashka script or project with one comman
- [qualify-methods](https://github.com/borkdude/qualify-methods)
  - Initial release of experimental tool to rewrite instance calls to use fully
    qualified methods (Clojure 1.12 only0
- [neil](https://github.com/babashka/neil): A CLI to add common aliases and features to deps.edn-based projects.<br>
- [tools](https://github.com/borkdude/tools): a set of [bbin](https://github.com/babashka/bbin/) installable scripts
- [babashka.json](https://github.com/babashka/json): babashka JSON library/adapter
- [speculative](https://github.com/borkdude/speculative)
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

