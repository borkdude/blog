Title: OSS updates January and February 2026
Date: 2026-03-05
Tags: clojure, oss updates
Description: My Clojure OSS updates for January and February 2026

In this post I'll give updates about open source I worked on during January and February 2026.

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

Babashka Conf 2026 is happening on May 8th in the OBA Oosterdok library in Amsterdam! David Nolen, primary maintainer of ClojureScript, will be our keynote speaker! We're excited to have Nubank, Exoscale, Bob and Itonomi as sponsors. Wendy Randolph will be our event host / MC / speaker liaison :-). The CfP is now closed. More information [here](https://babashka.org/conf/). Get your ticket via [Meetup.com](https://www.meetup.com/the-dutch-clojure-meetup/events/312079164/) (there is a waiting list, but more places may become available).
The day after babashka conf, [Dutch Clojure Days 2026](https://clojuredays.org/) will be happening, so you can enjoy a whole weekend of Clojure in Amsterdam.
Hope to see many of you there!

### Projects

I spent a lot of time making SCI's `deftype`, `case`, and `macroexpand-1` match JVM Clojure more closely. As a result, libraries like riddley, cloverage, specter, editscript, and compliment now work in babashka.

After seeing [charm.clj](https://codeberg.org/timokramer/charm.clj), a terminal UI library, I decided to incorporate JLine3 into babashka so people can build terminal UIs. The goal is to be able to run rebel-readline + nREPL from source in babashka, but this is still work in progress (e.g. the compliment PR is still pending). Since I had JLine anyway, babashka also got a major REPL upgrade with multi-line editing, tab completion, ghost text, and persistent history.

On the CLJS side, SCI, scittle, and nbb all gained `async/await` support.

Last but not least, I started [cream](https://github.com/borkdude/cream), an experimental native binary that runs full JVM Clojure with fast startup using GraalVM's Crema. Unlike babashka, it supports runtime bytecode generation (`definterface`, `deftype`, `gen-class`). It currently depends on a fork of Clojure and GraalVM EA, so it's not production-ready yet.

Here are updates about the projects/libraries I've worked on in the last two months in detail.

- NEW: [cream](https://github.com/borkdude/cream): Clojure + GraalVM [Crema](https://github.com/oracle/graal/issues/11327) native binary
  - A native binary that runs full JVM Clojure with fast startup, using GraalVM's Crema (RuntimeClassLoading) to enable runtime `eval`, `require`, and library loading
  - Unlike babashka, supports `definterface`, `deftype`, `gen-class`, and other constructs that generate JVM bytecode at runtime
  - Can run `.java` source files directly, as a fast alternative to JBang
  - Cross-platform: Linux, macOS, Windows

- [babashka](https://github.com/babashka/babashka): native, fast starting Clojure interpreter for scripting.
  - Released 1.12.214 and 1.12.215
  - [#1909](https://github.com/babashka/babashka/issues/1909): add JLine3 for TUI support
  - Console REPL (`bb repl`) improvements: multi-line editing, tab completion, ghost text, eldoc, doc-at-point (`C-x` `C-d`), persistent history
  - Support `deftype` with map interfaces (e.g. `IPersistentMap`, `ILookup`, `Associative`). Libraries like core.cache and linked now work in babashka.
  - Compatibility with riddley, cloverage, editscript, charm.clj
  - [#1299](https://github.com/babashka/babashka/issues/1299): add new `babashka.terminal` namespace that exposes `tty?`
  - Add keyword completions to nREPL and console REPL
  - `deftype` supports `Object` + `hashCode`
  - [#1923](https://github.com/babashka/babashka/issues/1923): support `reify` with `java.time.temporal.TemporalQuery`
  - Fix `reify` with methods returning `int`/`short`/`byte`/`float`
  - [Full changelog](https://github.com/babashka/babashka/blob/master/CHANGELOG.md)

- [SCI](https://github.com/babashka/sci): Configurable Clojure/Script interpreter suitable for scripting
  - Released 0.12.51
  - `deftype` now macroexpands to `deftype*`, matching JVM Clojure, enabling code walkers like riddley
  - `case` now macroexpands to JVM-compatible `case*` format, enabling tools like riddley and cloverage
  - Support `async/await` in ClojureScript. See [docs](https://github.com/babashka/sci/blob/master/doc/async-await.md).
  - Support functional interface adaptation for instance targets
  - Infer type tags from let binding values to binding names
  - `defrecord` now expands to `deftype*` (like Clojure), with factory fns emitted directly in the macro expansion
  - `macroexpand-1` now accepts an optional env map as first argument
  - Add `proxy-super`, `proxy-call-with-super`, `update-proxy` and `proxy-mappings`
  - Support [#564](https://github.com/babashka/sci/issues/564): `this-as` in ClojureScript
  - Store current analysis context during macro invocation, enabling tools like riddley to access outer locals
  - [Full changelog](https://github.com/babashka/sci/blob/master/CHANGELOG.md)

- [clj-kondo](https://github.com/clj-kondo/clj-kondo): static analyzer and linter for Clojure code that sparks joy.<br>
  [@jramosg](https://github.com/jramosg), [@tomdl89](https://github.com/tomdl89) and [@hugod](https://github.com/hugod) have been on fire with contributions this period. Six new linters!
  - Released 2026.01.12 and 2026.01.19
  - [#2735](https://github.com/clj-kondo/clj-kondo/issues/2735): NEW linter: `:duplicate-refer` which warns on duplicate entries in `:refer` of `:require` ([@jramosg](https://github.com/jramosg))
  - [#2734](https://github.com/clj-kondo/clj-kondo/issues/2734): NEW linter: `:aliased-referred-var`, which warns when a var is both referred and accessed via an alias in the same namespace ([@jramosg](https://github.com/jramosg))
  - [#2745](https://github.com/clj-kondo/clj-kondo/issues/2745): NEW linter: `:is-message-not-string` which warns when `clojure.test/is` receives a non-string message argument ([@jramosg](https://github.com/jramosg))
  - [#2712](https://github.com/clj-kondo/clj-kondo/issues/2712): NEW linter: `:redundant-format` to warn when format strings contain no format specifiers ([@jramosg](https://github.com/jramosg))
  - [#2709](https://github.com/clj-kondo/clj-kondo/issues/2709): NEW linter: `:redundant-primitive-coercion` to warn when primitive coercion functions are applied to expressions already of that type ([@hugod](https://github.com/hugod))
  - Add new types `array`, `class`, `inst` and type checking support for related functions ([@jramosg](https://github.com/jramosg))
  - Add type checking support for `clojure.test` functions and macros ([@jramosg](https://github.com/jramosg))
  - [#2340](https://github.com/clj-kondo/clj-kondo/issues/2340): Extend `:condition-always-true` linter to check first argument of `clojure.test/is` ([@jramosg](https://github.com/jramosg))
  - [#2729](https://github.com/clj-kondo/clj-kondo/issues/2729): Check for arity mismatch for bound vectors, sets & maps, not just literals ([@tomdl89](https://github.com/tomdl89))
  - [#2768](https://github.com/clj-kondo/clj-kondo/issues/2768): NEW linter: `:redundant-declare` which warns when `declare` is used after a var is already defined ([@jramosg](https://github.com/jramosg))
  - Add type support for `pmap` and future-related functions ([@jramosg](https://github.com/jramosg))
  - Upgrade to GraalVM 25
  - [Full changelog](https://github.com/clj-kondo/clj-kondo/blob/master/CHANGELOG.md)

- [squint](https://github.com/squint-cljs/squint): CLJS _syntax_ to JS compiler
  [@tonsky](https://github.com/tonsky) and [@willcohen](https://github.com/willcohen) contributed several improvements this period.
  - Add `squint.math`, also available as `clojure.math` namespace
  - [#779](https://github.com/squint-cljs/squint/issues/779): Added `compare-and-swap!`, `swap-vals!` and `reset-vals!` ([@tonsky](https://github.com/tonsky))
  - [#788](https://github.com/squint-cljs/squint/issues/788): Fixed compilation of `dotimes` with `_` binding ([@tonsky](https://github.com/tonsky))
  - [#790](https://github.com/squint-cljs/squint/issues/790): Fixed `shuffle` not working on lazy sequences ([@tonsky](https://github.com/tonsky))
  - Multiple `:require-macros` with `:refer` now accumulate instead of overwriting ([@willcohen](https://github.com/willcohen))
  - Fix emitting negative zero value (`-0.0`)
  - Fix [#792](https://github.com/squint-cljs/squint/issues/792): `prn` `js/undefined` as `nil`
  - Fix [#793](https://github.com/squint-cljs/squint/issues/793): fix `yield*` IIFE
  - [Full changelog](https://github.com/squint-cljs/squint/blob/main/CHANGELOG.md)

- [scittle](https://github.com/babashka/scittle): Execute Clojure(Script) directly from browser script tags via SCI
  - Support `async/await`. See docs.
  - Implement `js/import` not using `eval`
  - Support `this-as`
  - nREPL: print `#<Promise value>` when a promise is evaluated

- [nbb](https://github.com/babashka/nbb): Scripting in Clojure on Node.js using SCI
  - Support async/await. See docs for syntax.
  - Print promise result value in REPL/nREPL: `(js/Promise.resolve 1) ;;=> #<Promise 1>`

- [fs](https://github.com/babashka/fs) - File system utility library for Clojure
  - Released 0.5.31
  - [#212](https://github.com/babashka/fs/issues/212): Introduce `:keep true` option in `with-temp-dir`
  - [#188](https://github.com/babashka/fs/issues/188) `copy-tree` now throws if `src` or `dest` is a symbolic link when not following links ([@lread](https://github.com/lread))
  - [#201](https://github.com/babashka/fs/issues/201) `gzip` now accepts `source-file` `Path` ([@lread](https://github.com/lread))
  - [#207](https://github.com/babashka/fs/issues/207) review and update `glob` and `match` docstrings ([@lread](https://github.com/lread))

- [clerk](https://github.com/nextjournal/clerk): Moldable Live Programming for Clojure
  - Fix browse when using random port by passing 0, fixes #801
  - bb now supports editscript

- [neil](https://github.com/babashka/neil): A CLI to add common aliases and features to deps.edn-based projects.<br>
  - [#258](https://github.com/babashka/neil/issues/258): `neil test` now exits with non-zero exit code when tests fail

- [cherry](https://github.com/squint-cljs/cherry): Experimental ClojureScript to ES6 module compiler
  - Multiple `:require-macros` clauses with `:refer` now properly accumulate instead of overwriting each other

Contributions to third party projects:

- [editscript](https://github.com/juji-io/editscript): Added babashka support, deps.edn for git dep usage, fixed CLJS tests
- [riddley](https://github.com/ztellman/riddley): Added babashka compatibility, clj-kondo config
- [cloverage](https://github.com/cloverage/cloverage): Added babashka compatibility, migrated tools.cli from deprecated `cli/cli` to `cli/parse-opts`, bumped riddley
- [specter](https://github.com/redplanetlabs/specter): Added babashka compatibility
- [compliment](https://github.com/alexander-yakushev/compliment): Added babashka compatibility ([PR #131](https://github.com/alexander-yakushev/compliment/pull/131))
- [rebel-readline](https://github.com/bhauman/rebel-readline): Removed JLine impl class dependencies for babashka compatibility, released 0.1.7
- [Selmer](https://github.com/yogthos/Selmer): Namespaced script tag context keys to avoid collisions, removed runtime require of clojure.tools.logging
- [charm.clj](https://codeberg.org/timokramer/charm.clj): Contributed JLine integration, FFM native terminal interface, babashka and native-image compatibility

## Other projects

These are (some of the) other projects I'm involved with but little to no activity
happened in the past month.

<details>
<summary>Click for more details</summary>
- [pod-babashka-go-sqlite3](https://github.com/babashka/pod-babashka-go-sqlite3): A babashka pod for interacting with sqlite3
- [unused-deps](https://github.com/borkdude/unused-deps): Find unused deps in a clojure project
- [pod-babashka-fswatcher](https://github.com/babashka/pod-babashka-fswatcher): babashka filewatcher pod
- [sci.nrepl](https://github.com/babashka/sci.nrepl): nREPL server for SCI projects that run in the browser
- [babashka.nrepl-client](https://github.com/babashka/nrepl-client)
- [http-server](https://github.com/babashka/http-server): serve static assets
- [sci.configs](https://github.com/babashka/sci.configs): A collection of ready to be used SCI configs.
- [http-client](https://github.com/babashka/http-client): babashka's http-client
- [html](https://github.com/borkdude/html): Html generation library inspired by squint's html tag
- [instaparse-bb](https://github.com/babashka/instaparse-bb): Use instaparse from babashka
- [sql pods](https://github.com/babashka/babashka-sql-pods): babashka pods for SQL databases
- [rewrite-edn](https://github.com/borkdude/rewrite-edn): Utility lib on top of
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
- [edamame](https://github.com/borkdude/edamame): configurable EDN and Clojure parser with location metadata and more
- [CLI](https://github.com/babashka/cli): Turn Clojure functions into CLIs!
- [quickblog](https://github.com/borkdude/quickblog): light-weight static blog engine for Clojure and babashka
- [process](https://github.com/babashka/process): Clojure library for shelling out / spawning sub-processes
- [deps.clj](https://github.com/borkdude/deps.clj): A faithful port of the clojure CLI bash script to Clojure
- [reagami](https://github.com/borkdude/reagami): A minimal zero-deps Reagent-like for Squint and CLJS
- [parmezan](https://github.com/borkdude/parmezan): fixes unbalanced or unexpected parens or other delimiters in Clojure files
- [quickdoc](https://github.com/borkdude/quickdoc): Quick and minimal API doc generation for Clojure
- [Nextjournal Markdown](https://github.com/nextjournal/markdown)

</details>
