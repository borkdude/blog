Title: OSS updates November and December 2025
Date: 2026-01-01
Tags: clojure, oss updates
Description: My Clojure OSS updates for November and December 2025

In this post I'll give updates about open source I worked on during November and December 2025.

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

<!--

- ls -lat ~/dev
- babashka sub dir checken

-->

## Updates

### Clojure Conj 2025

Last November I had the honor and pleasure to visit the Clojure Conj 2025. I met
a host of wonderful and interesting long-time and new Clojurians, many that I've
known online for a long time and now met for the first time. It was especially exciting to finally meet Rich Hickey and talk to him during a meeting about Clojure dialects and Clojure tooling. The talk that I gave there: "Making
tools developers actually use" will come online soon.

<img src="https://photos.clojure-conj.org/uploads/medium2x/21/bb/eee18492f42518506f78b0f48587.jpg" width="100%" align="center" alt="presentation at Dutch Clojure meetup">

### Babashka conf and Dutch Clojure Days 2026

In 2026 I'm organizing Babashka Conf 2026. It will be an afternoon event (13:00-17:00) hosted in the Forum hall of the beautiful public library of Amsterdam.
More information [here](https://babashka.org/conf/). Get your ticket via [Meetup.com](https://www.meetup.com/the-dutch-clojure-meetup/events/312079164/) (currently there's a waiting list, but more places will come available once speakers are confirmed). CfP will open mid January.
The day after babashka conf, Dutch Clojure Days 2026 will be happening. It's not too late to get your talk proposal in. More info [here](https://clojuredays.org/).

### Projects

Here are updates about the projects/libraries I've worked on in the last two months in detail.

- [babashka](https://github.com/babashka/babashka): native, fast starting Clojure interpreter for scripting.
  - Bump `process` to `0.6.25`
  - Bump `deps.clj`
  - Fix #1901: add `java.security.DigestOutputStream`
  - Redefining namespace with `ns` should override metadata
  - Bump `nextjournal.markdown` to `0.7.222`
  - Bump `edamame` to `1.5.37`
  - Fix [#1899](https://github.com/babashka/babashka/issues/1899): `with-meta` followed by `dissoc` on records no longer works
  - Bump `fs` to `0.5.30`
  - Bump `nextjournal.markdown` to `0.7.213`
  - Fix [#1882](https://github.com/babashka/babashka/issues/1882): support for reifying `java.time.temporal.TemporalField` ([@EvenMoreIrrelevance](https://github.com/EvenMoreIrrelevance))
  - Bump Selmer to `1.12.65`
  - SCI: `sci.impl.Reflector` was rewritten into Clojure
  - `dissoc` on record with non-record field should return map instead of record
  - Bump edamame to `1.5.35`
  - Bump `core.rrb-vector` to `0.2.0`
  - Migrate detecting of executable name for self-executing uberjar executable from `ProcessHandle` to to native image `ProcessInfo` to avoid sandbox errors
  - Bump `cli` to `0.8.67`
  - Bump `fs` to `0.5.29`
  - Bump `nextjournal.markdown` to `0.7.201`

- [SCI](https://github.com/babashka/sci): Configurable Clojure/Script interpreter suitable for scripting
  - Add support for `:refer-global` and `:require-global`
  - Add `println-str`
  - Fix [#997](https://github.com/babashka/sci/issues/997): Var is mistaken for local when used under the same name in a `let` body
  - Fix [#1001](https://github.com/babashka/sci/issues/1001): JS interop with reserved js keyword fails (regression of [#987](https://github.com/babashka/sci/issues/987))
  - `sci.impl.Reflector` was rewritten into Clojure
  - Fix [babashka/babashka#1886](https://github.com/babashka/babashka/issues/1886): Return a map when dissociating a
    record basis field.
  - Fix [#1011](https://github.com/babashka/sci/issues/1011): reset ns metadata when evaluating ns form multiple times
  - Fix for https://github.com/babashka/babashka/issues/1899
  - Fix [#1010](https://github.com/babashka/sci/issues/1010): add `js-in` in CLJS
  - Add `array-seq`

- [clj-kondo](https://github.com/clj-kondo/clj-kondo): static analyzer and linter for Clojure code that sparks joy.<br>
  - [#2600](https://github.com/clj-kondo/clj-kondo/issues/2600): NEW linter: `unused-excluded-var` to warn on unused vars in `:refer-clojure :exclude` ([@jramosg](https://github.com/jramosg))
  - [#2459](https://github.com/clj-kondo/clj-kondo/issues/2459): NEW linter: `:destructured-or-always-evaluates` to warn on s-expressions in `:or` defaults in map destructuring ([@jramosg](https://github.com/jramosg))
  - Add type checking support for `sorted-map-by`, `sorted-set`, and `sorted-set-by` functions
  ([@jramosg](https://github.com/jramosg))
  - Add new type `array` and type checking support for the next functions: `to-array`, `alength`,
  `aget`, `aset` and `aclone` ([@jramosg](https://github.com/jramosg))
  - Fix [#2695](https://github.com/clj-kondo/clj-kondo/issues/2696): false positive `:unquote-not-syntax-quoted` in leiningen's `defproject`
  - Leiningen's `defproject` behavior can now be configured using `leiningen.core.project/defproject`
  - Fix [#2699](https://github.com/clj-kondo/clj-kondo/issues/2699): fix false positive unresolved string var with extend-type on CLJS
  - Rename `:refer-clojure-exclude-unresolved-var` linter to `unresolved-excluded-var` for consistency
  - v2025.12.23
  - [#2654](https://github.com/clj-kondo/clj-kondo/issues/2654): NEW linter: `redundant-let-binding`, defaults to `:off` ([@tomdl89](https://github.com/tomdl89))
  - [#2653](https://github.com/clj-kondo/clj-kondo/issues/2653): NEW linter: `:unquote-not-syntax-quoted` to warn on `~` and `~@` usage outside syntax-quote (`` ` ``) ([@jramosg](https://github.com/jramosg))
  - [#2613](https://github.com/clj-kondo/clj-kondo/issues/2613): NEW linter: `:refer-clojure-exclude-unresolved-var` to warn on non-existing vars in `:refer-clojure :exclude` ([@jramosg](https://github.com/jramosg))
  - [#2668](https://github.com/clj-kondo/clj-kondo/issues/2668): Lint `&` syntax errors in let bindings and lint for trailing `&` ([@tomdl89](https://github.com/tomdl89))
  - [#2590](https://github.com/clj-kondo/clj-kondo/issues/2590): `duplicate-key-in-assoc` changed to `duplicate-key-args`, and now lints `dissoc`, `assoc!` and `dissoc!` too ([@tomdl89](https://github.com/tomdl89))
  - [#2651](https://github.com/clj-kondo/clj-kondo/issues/2651): resume linting after paren mismatches
  - [clojure-lsp#2651](https://github.com/clojure-lsp/clojure-lsp/issues/2157): Fix inner class name for java-class-definitions.
  - [clojure-lsp#2651](https://github.com/clojure-lsp/clojure-lsp/issues/2157): Include inner class java-class-definition analysis.
  - Bump `babashka/fs`
  - [#2532](https://github.com/clj-kondo/clj-kondo/issues/2532): Disable `:duplicate-require` in `require` + `:reload` / `:reload-all`
  - [#2432](https://github.com/clj-kondo/clj-kondo/issues/2432): Don't warn for `:redundant-fn-wrapper` in case of inlined function
  - [#2599](https://github.com/clj-kondo/clj-kondo/issues/2599): detect invalid arity for invoking collection as higher order function
  - [#2661](https://github.com/clj-kondo/clj-kondo/issues/2661): Fix false positive `:unexpected-recur` when `recur` is used inside `clojure.core.match/match` ([@jramosg](https://github.com/jramosg))
  - [#2617](https://github.com/clj-kondo/clj-kondo/issues/2617): Add types for `repeatedly` ([@jramosg](https://github.com/jramosg))
  - Add `:ratio` type support for `numerator` and `denominator` functions ([@jramosg](https://github.com/jramosg))
  - [#2676](https://github.com/clj-kondo/clj-kondo/issues/2676): Report unresolved namespace for namespaced maps with unknown aliases ([@jramosg](https://github.com/jramosg))
  - [#2683](https://github.com/clj-kondo/clj-kondo/issues/2683): data argument of `ex-info` may be nil since clojure 1.12
  - Bump built-in ClojureScript analysis info
  - Fix [#2687](https://github.com/clj-kondo/clj-kondo/issues/2687): support new `:refer-global` and `:require-global` ns options in CLJS
  - Fix [#2554](https://github.com/clj-kondo/clj-kondo/issues/2544): support inline configs in `.cljc` files

- [edamame](https://github.com/borkdude/edamame): configurable EDN and Clojure parser with location metadata and more
Edamame: configurable EDN and Clojure parser with location metadata and more
  - Minor: leave out `:edamame/read-cond-splicing` when not splicing
  - Allow `:read-cond` function to override `:edamame/read-cond-splicing` value
  - The result from `:read-cond` with a function should be spliced. This behavior differs from `:read-cond` + `:preserve` which always returns a reader conditional object which cannot be spliced.
  - Support function for `:features` option to just select the first feature that occurs

- [squint](https://github.com/squint-cljs/squint): CLJS _syntax_ to JS compiler
  - Allow macro namespaces to load `"node:fs", etc.` to read config files for conditional compilation
  - Don't emit IIFE for top-level let so you can write `let` over `defn` to capture values.
  - Fix `js-yield` and `js-yield*` in expression position
  - Implement `some?` as macro
  - Fix [#758](https://github.com/squint-cljs/squint/issues/758): `volatile!`, `vswap!`, `vreset!`
  - `pr-str`, `prn` etc now print EDN (with the idea that you can paste it back into your program)
  - new `#js/Map` reader that reads a JavaScript `Map` from a Clojure map (maps are printed like this with `pr-str` too)
  - Support passing keyword to `mapv`
  - [#759](https://github.com/squint-cljs/squint/issues/759): `doseq` can't be used in expression context
  - Fix [#753](https://github.com/squint-cljs/squint/issues/753): optimize output of dotimes
  - `alength` as macro

- [reagami](https://github.com/borkdude/reagami): A minimal zero-deps Reagent-like for Squint and CLJS
  - Performance enhancements
  - treat `innerHTML` as a property rather than an attribute
  - Drop support for camelCased properties / (css) attributes
  - Fix `:default-value` in input range
  - Support data param in `:on-render`
  - Support default values for uncontrolled components
  - Fix child count mismatch
  - Fix re-rendering/patching of subroots
  - Add `:on-render` hook for mounting/updating/unmounting third part JS components

- NEW: [parmezan](https://github.com/borkdude/parmezan): fixes unbalanced or unexpected parens or other delimiters in Clojure files

- [CLI](https://github.com/babashka/cli): Turn Clojure functions into CLIs!
  - [#126](https://github.com/babashka/cli/issues/126): `-` value accidentally parsed as option, e.g. `--file -`
  - [#124](https://github.com/babashka/cli/issues/124): Specifying exec fn that starts with hyphen is treated as option
  - Drop Clojure 1.9 support. Minimum Clojure version is now 1.10.3.

- [clerk](https://github.com/nextjournal/clerk): Moldable Live Programming for Clojure
  - always analyze doc (but not deps) when no-cache is set (#786)
  - add option to disable inline formulas in markdown (#780)

- [scittle](https://github.com/babashka/scittle): Execute Clojure(Script) directly from browser script tags via SCI
  - [#114](https://github.com/babashka/scittle/issues/114): Enable source maps ([@jeroenvandijk](https://github.com/jeroenvandijk))
  - [#140](https://github.com/babashka/scittle/issues/140): Enable customizing the nrepl websocket port ([@PEZ](https://github.com/PEZ))
  - Bump shadow-cljs and SCI

- [Nextjournal Markdown](https://github.com/nextjournal/markdown)
  - Add config option to avoid TeX formulas
  - API improvements for passing options

- [cherry](https://github.com/squint-cljs/cherry): Experimental ClojureScript to ES6 module compiler
  - Fix `cherry compile` CLI command not receiving file arguments
  - Bump shadow-cljs to `3.3.4`
  - Fix [#163](https://github.com/squint-cljs/cherry/issues/163): Add assert to macros ([@willcohen](https://github.com/willcohen))
  - Fix [#165](https://github.com/squint-cljs/cherry/issues/165): Fix ClojureScript protocol dispatch functions ([@willcohen](https://github.com/willcohen))
  - Fix [#167](https://github.com/squint-cljs/cherry/issues/167): Protocol dispatch functions inside IIFEs; bump squint [accordingly](https://github.com/squint-cljs/squint/commit/db0e3c7b831568a3766e9ade0e2a05490446bfe5)
  - Fix [#169](https://github.com/squint-cljs/cherry/issues/169): fix `extend-type` on `Object`
  - Fix [#171](https://github.com/squint-cljs/cherry/issues/171): Add `satisfies?` macro ([@willcohen](https://github.com/willcohen))

- [deps.clj](https://github.com/borkdude/deps.clj): A faithful port of the clojure CLI bash script to Clojure
  - Released several versions catching up with the clojure CLI

- [quickdoc](https://github.com/borkdude/quickdoc): Quick and minimal API doc generation for Clojure
  - Fix extra newline in codeblock

- [quickblog](https://github.com/borkdude/quickblog): light-weight static blog engine for Clojure and babashka
  - Add support for a blog contained within another website; see [Serving an alternate content root](https://github.com/borkdude/quickblog/README.md#serving-an-alternate-content-root) in README.  ([@jmglov](https://github.com/jmglov))
  - Upgrade babashka/http-server to 0.1.14
  - Fix `:blog-image-alt` option being ignored when using CLI (`bb quickblog render`)

- [nbb](https://github.com/babashka/nbb): Scripting in Clojure on Node.js using SCI
  - [#395](https://github.com/babashka/nbb/issues/395): fix `vim-fireplace` infinite loop on nREPL session close.
  - Add `ILookup` and `Cons`
  - Add `abs`
  - nREPL: support `"completions"` op

- [neil](https://github.com/babashka/neil): A CLI to add common aliases and features to deps.edn-based projects.<br>
  - neil.el - a hook that runs after finding a package ([@agzam](https://github.com/agzam))
  - neil.el - adds a function for injecting a found package into current CIDER session ([@agzam](https://github.com/agzam))
  - [#245](https://github.com/babashka/neil/issues/245): neil.el - neil-executable-path now can be set to `clj -M:neil`
  - [#251](https://github.com/babashka/neil/issues/251): Upgrade library deps-new to 0.10.3
  - [#255](https://github.com/babashka/neil/issues/255): update maven search URL

- [fs](https://github.com/babashka/fs) - File system utility library for Clojure
  - [#154](https://github.com/babashka/fs/issues/154) reflect in directory check and docs that `move` never follows symbolic links ([@lread](https://github.com/lread))
  - [#181](https://github.com/babashka/fs/issues/181) `delete-tree` now deletes broken symbolic link `root` ([@lread](https://github.com/lread))
  - [#193](https://github.com/babashka/fs/issues/193) `create-dirs` now recognizes sym-linked dirs on JDK 11 ([@lread](https://github.com/lread)) 
  - [#184](https://github.com/babashka/fs/issues/184): new check in `copy-tree` for copying to self too rigid
  - [#165](https://github.com/babashka/fs/issues/165): `zip` now excludes `zip-file` from `zip-file` ([@lread](https://github.com/lread))
  - [#167](https://github.com/babashka/fs/issues/167): add `root` fn which exposes `Path` `getRoot` ([@lread](https://github.com/lread))
  - [#166](https://github.com/babashka/fs/issues/166): `copy-tree` now fails fast on attempt to copy parent to child ([@lread](https://github.com/lread))
  - [#152](https://github.com/babashka/fs/issues/152): an empty-string path `""` is now (typically) understood to be the current working directory (as per underlying JDK file APIs) ([@lread](https://github.com/lread))
  - [#155](https://github.com/babashka/fs/issues/155): `fs/with-temp-dir` clj-kondo linting refinements ([@lread](https://github.com/lread))
  - [#162](https://github.com/babashka/fs/issues/162): `unixify` no longer expands into absolute path on Windows (potentially BREAKING)
  - Add return type hint to `read-all-bytes`

- [process](https://github.com/babashka/process): Clojure library for shelling out / spawning sub-processes
  - [#181](https://github.com/babashka/process/issues/181): support `:discard` or `ProcessBuilder$Redirect` as `:out` and `:err` options

Contributions to third party projects:

- [ClojureScript](https://github.com/clojure/clojurescript)
  - CLJS-3466: support qualified method in return position
  - CLJS-3468: :refer-global should not make unrenamed object available

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
- [nbb](https://github.com/babashka/nbb): Scripting in Clojure on Node.js using SCI
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

</details>

