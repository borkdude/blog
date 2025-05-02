Title: OSS updates March and April 2025
Date: 2025-05-02
Tags: clojure, oss updates
Description: My Clojure OSS updates for March and April 2025

In this post I'll give updates about open source I worked on during March and April 2025.

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

On to the projects that I've been working on!
</details>

<!--

sources: https://github.com/borkdude
local ~/dev and ~/dev/babashka dir (since github doesn't show all repos)

babashka sub dir checken
-->

## Blog posts

I blogged about an important improvement in babashka regarding type hints
[here](https://blog.michielborkent.nl/babashka-java-reflection-type-hints.html).

## Interviews

Also I did an interview with Jiri from Clojure Corner by Flexiana, viewable [here](https://www.youtube.com/watch?v=H7ZlwEDxzRs).

<iframe width="560" height="315" src="https://www.youtube.com/embed/H7ZlwEDxzRs?si=HQuZFsQXloxGkF9B" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>

## Updates

Here are updates about the projects/libraries I've worked on in the last two months.

- [babashka](https://github.com/babashka/babashka): native, fast starting Clojure interpreter for scripting.
  - Improve Java reflection based on provided type hints (read blog post [here](https://blog.michielborkent.nl/babashka-java-reflection-type-hints.html))
  - Add compatibility with the [fusebox](https://github.com/potetm/fusebox) library
  - Fix virtual `ThreadBuilder` interop
  - Add `java.util.concurrent.ThreadLocalRandom`
  - Add `java.util.concurrent.locks.ReentrantLock`
  - Add classes:
    - `java.time.chrono.ChronoLocalDate`
    - `java.time.temporal.TemporalUnit`
    - `java.time.chrono.ChronoLocalDateTime`
    - `java.time.chrono.ChronoZonedDateTime`
    - `java.time.chrono.Chronology`
  - [#1806](https://github.com/babashka/babashka/issues/1806): Add `cheshire.factory` namespace ([@lread](https://github.com/lread))
  - Bump GraalVM to `24`
  - Bump SCI to `0.9.45`
  - Bump edamame to `1.4.28`
  - [#1801](https://github.com/babashka/babashka/issues/1801): Add `java.util.regex.PatternSyntaxException`
  - Bump core.async to `1.8.735`
  - Bump cheshire to `6.0.0`
  - Bump babashka.cli to `0.8.65`

- [clerk](https://github.com/nextjournal/clerk): Moldable Live Programming for Clojure
  - Replace tools.analyzer with a more light-weight analyzer which also adds support for Clojure 1.12

- [squint](https://github.com/squint-cljs/squint): CLJS _syntax_ to JS compiler
  - [#653](https://github.com/squint-cljs/squint/issues/653): respect `:context expr` in `compile-string`
  - [#657](https://github.com/squint-cljs/squint/issues/657): respect `:context expr` in `set!` expression
  - [#659](https://github.com/squint-cljs/squint/issues/659): fix invalid code produced for REPL mode with respect to `return`
  - [#651](https://github.com/squint-cljs/squint/issues/651) Support `:require` + `:rename` + allow renamed value to be used in other :require clause
  - Fix [#649](https://github.com/squint-cljs/squint/issues/649): reset ns when compiling file and fix initial global object
  - Fix [#647](https://github.com/squint-cljs/squint/issues/647): emit explicit `null` when written in else branch of `if`
  - Fix [#640](https://github.com/squint-cljs/squint/issues/640): don't emit anonymous function if it is a statement ([@jonasseglare](https://github.com/jonasseglare))
  - Fix [#643](https://github.com/squint-cljs/squint/issues/643): Support lexicographic compare of arrays ([@jonasseglare](https://github.com/jonasseglare))
  - Fix [#602](https://github.com/squint-cljs/squint/issues/602): support hiccup-style shorthand for id and class attributes in `#jsx` and `#html`
  - Fix [#635](https://github.com/squint-cljs/squint/issues/635): `range` fixes
  - Fix [#636](https://github.com/squint-cljs/squint/issues/636): add `run!`
  - `defclass`: elide constructor when not provided
  - Fix [#603](https://github.com/squint-cljs/squint/issues/603): don't emit multiple returns
  - Drop constructor requirement for `defclass`

- [clj-kondo](https://github.com/clj-kondo/clj-kondo): static analyzer and linter for Clojure code that sparks joy.<br>
  - [#2522](https://github.com/clj-kondo/clj-kondo/issues/2522): support `:config-in-ns` on `:missing-protocol-method`
  - [#2524](https://github.com/clj-kondo/clj-kondo/issues/2524): support `:redundant-ignore` on `:missing-protocol-method`
  - [#1292](https://github.com/clj-kondo/clj-kondo/issues/1292): NEW linter: `:missing-protocol-method`. See [docs](doc/linters.md)
  - [#2512](https://github.com/clj-kondo/clj-kondo/issues/2512): support vars ending with `.`, e.g. `py.` according to clojure analyzer
  - [#2516](https://github.com/clj-kondo/clj-kondo/issues/2516): add new `--repro` flag to ignore home configuration
  - [#2493](https://github.com/clj-kondo/clj-kondo/issues/2493): reduce image size of native image
  - [#2496](https://github.com/clj-kondo/clj-kondo/issues/2496): Malformed `deftype` form results in `NPE`
  - [#2499](https://github.com/clj-kondo/clj-kondo/issues/2499): Fix `(alias)` bug ([@Noahtheduke](https://github.com/Noahtheduke))
  - [#2492](https://github.com/clj-kondo/clj-kondo/issues/2492): Report unsupported escape characters in strings
  - [#2502](https://github.com/clj-kondo/clj-kondo/issues/2502): add end locations to invalid symbol
  - [#2511](https://github.com/clj-kondo/clj-kondo/issues/2511): fix multiple parse errors caused by incomplete forms
  - document var-usages location info edge cases ([@sheluchin](https://github.com/sheluchin))
  - Upgrade to GraalVM 24
  - Bump datalog parser
  - Bump built-in cache

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

