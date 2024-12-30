Title: OSS updates November and December 2024
Date: 2024-12-31
Tags: clojure, oss updates
Description: My Clojure OSS updates for November and December 2024

In this post I'll give updates about open source I worked on during November and December 2024.

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

On to the projects that I've been working on!
</details>

<!--

sources: https://github.com/borkdude
local ~/dev and ~/dev/babashka dir (since github doesn't show all repos)

- write something about Heart of Clojure and bbslideshow?

-->

## Updates

Clojurists Together announced that I'm among the 5 developers who were voted to receive the Long Term Funding in 2025. You can see the announcement [here](https://www.clojuriststogether.org/news/clojurists-together-2025-long-term-funding-announcement/). Thanks so much!

Here are updates about the projects/libraries I've worked on in the last two months.

- [babashka](https://github.com/babashka/babashka): native, fast starting Clojure interpreter for scripting.
  - [#1771](https://github.com/babashka/babashka/issues/1771): `*e*` in REPL should contain exception thrown by user, not a wrapped one
  - [#1777](https://github.com/babashka/babashka/issues/1777) Add `java.nio.file.attribute.UserDefinedFileAttributeView`
  - [#1776](https://github.com/babashka/babashka/issues/1776) `Add java.nio.file.attribute.PosixFileAttributes`
  - [#1761](https://github.com/babashka/babashka/issues/1761) Support calling `clojure.lang.RT/iter`
  - [#1760](https://github.com/babashka/babashka/issues/1760) For compatibility with [Fireworks v0.10.3](https://github.com/paintparty/fireworks), added the following to `:instance-checks` entry in `babashka.impl.classes/classes`([@paintparty](https://github.com/paintparty))
      - `clojure.lang.PersistentArrayMap$TransientArrayMap`
      - `clojure.lang.PersistentHashMap$TransientHashMap`
      - `clojure.lang.PersistentVector$TransientVector`
      - `java.lang.NoSuchFieldException`
      - `java.util.AbstractMap`
      - `java.util.AbstractSet`
      - `java.util.AbstractList`
  - [#1760](https://github.com/babashka/babashka/issues/1760) For compatibility with [Fireworks v0.10.3](https://github.com/paintparty/fireworks), added `volatile?` entry to `babashka.impl.clojure.core/core-extras`([@paintparty](https://github.com/paintparty))
  - Bump `babashka.cli` to `0.8.61`
  - Bump `clj-yaml` to `1.0.29`
  - [#1768](https://github.com/babashka/babashka/issues/1768): Add `taoensso.timbre` `color-str` function
  - Add classes:
    - `javax.crypto.KeyAgreement`
    - `java.security.KeyPairGenerator`
    - `java.security.KeyPair`
    - `java.security.spec.ECGenParameterSpec`
    - `java.security.spec.PKCS8EncodedKeySpec`
    - `java.security.spec.X509EncodedKeySpec`
    - `java.security.Signature`
  - Add `java.util.concurrent.CompletionStage`
  - Bump `core.async` to `1.7.701`
  - Bump `org.babashka/cli` to `0.8.162`
  - Include [jsoup](https://jsoup.org/) for HTML parsing. This makes bb compatible with the [hickory](https://github.com/clj-commons/hickory) library (and possibly other libraries?).
  - [#1752](https://github.com/babashka/babashka/issues/1752): include `java.lang.SecurityException` for `java.net.http.HttpClient` support ([@grzm](https://github.com/grzm))
  - [#1748](https://github.com/babashka/babashka/issues/1748): add `clojure.core/ensure`
  - Upgrade `taoensso/timbre`to `v6.6.0`
  - Upgrade `babashka.http-client` to `v0.4.22`
  - Add `:git/sha` from build to `bb describe` output ([@lispyclouds](https://github.com/lispyclouds))
  - Fix NPE with determining if executing from self-contained executable

- [squint](https://github.com/squint-cljs/squint): CLJS _syntax_ to JS compiler
  - Fix [#255](https://github.com/squint-cljs/squint/issues/255): fn literal with rest args
  - [#596](https://github.com/squint-cljs/squint/issues/596): fix unary division to produce reciprocal
  - [#592](https://github.com/squint-cljs/squint/issues/592): fix `clj->js` to not process custom classes
  - [#585](https://github.com/squint-cljs/squint/issues/585): fix `clj->js` to realize lazy seqs into arrays
  - [#586](https://github.com/squint-cljs/squint/issues/586): support extending protocol to `nil`
  - [#581](https://github.com/squint-cljs/squint/issues/581): support docstring in `defprotocol`
  - [#582](https://github.com/squint-cljs/squint/issues/582): add `extend-protocol`
  - Add `delay`
  - Fix [#575](https://github.com/squint-cljs/squint/issues/575): `map?` should not return `true` for array
  - Fix [#577](https://github.com/squint-cljs/squint/issues/577): support `$default` + `:refer`
  - Fix [#572](https://github.com/squint-cljs/squint/issues/572): prevent vite page reload

- [CLI](https://github.com/babashka/cli): Turn Clojure functions into CLIs!
  - Fix [#109](https://github.com/babashka/cli/issues/109): allow options to start with a number

- [scittle](https://github.com/babashka/scittle): Execute Clojure(Script) directly from browser script tags via SCI
  - [#99](https://github.com/babashka/babashka/issues/99): make `js/import` work
  - [#55](https://github.com/babashka/babashka/issues/55): create gh-pages dir before using.
  - [#89](https://github.com/babashka/babashka/issues/89): allow `evaluate_script_tags` to specify individual scripts.
  - [#87](https://github.com/babashka/scittle/issues/87): prod build on fresh checkout fails


- [clj-kondo](https://github.com/clj-kondo/clj-kondo): static analyzer and linter for Clojure code that sparks joy.<br>
  - Unreleased
  - [#2272](https://github.com/clj-kondo/clj-kondo/issues/2451): Lint for nil return from if-like forms
  - Add `printf` to vars linted by `analyze-format`. ([@tomdl89](https://github.com/tomdl89))
  - [#2272](https://github.com/clj-kondo/clj-kondo/issues/2272): Report var usage in `if-let` etc condition as always truthy
  - [#2272](https://github.com/clj-kondo/clj-kondo/issues/2272): Report var usage in `if-not` condition as always truthy
  - [#2433](https://github.com/clj-kondo/clj-kondo/issues/2433): false positive redundant ignore with hook
  - Document `:cljc` config option. ([@NoahTheDuke](https://github.com/NoahTheDuke))
  - [#2439](https://github.com/clj-kondo/clj-kondo/issues/2439): uneval may apply to nnext form if reader conditional doesn't yield a form ([@NoahTheDuke](https://github.com/NoahTheDuke))
  - [#2431](https://github.com/clj-kondo/clj-kondo/issues/2431): only apply redundant-nested-call linter for nested exprs
  - Relax `:redundant-nested-call` for `comp`, `concat`, `every-pred` and `some-fn` since it may affect performance
  - [#2446](https://github.com/clj-kondo/clj-kondo/issues/2446): false positive `:redundant-ignore`
  - [#2448](https://github.com/clj-kondo/clj-kondo/issues/2448): redundant nested call in hook gen'ed code
  - [#2424](https://github.com/clj-kondo/clj-kondo/issues/2424): fix combination of :config-in-ns and :discouraged-namespace
  - 2024.11.14
  - [#2212](https://github.com/clj-kondo/clj-kondo/issues/2212): NEW linter: `:redundant-nested-call` ([@tomdl89](https://github.com/tomdl89)), set to level `:info` by default
  - Bump `:redundant-ignore`, `:redundant-str-call` linters to level `:info`
  - [#1784](https://github.com/clj-kondo/clj-kondo/issues/1784): detect `:redundant-do` in `catch`
  - [#2410](https://github.com/clj-kondo/clj-kondo/issues/2410): add `--report-level` flag
  - [#2416](https://github.com/clj-kondo/clj-kondo/issues/2416): detect empty `require` and `:require` forms ([@NoahTheDuke](https://github.com/NoahTheDuke))
  - [#1786](https://github.com/clj-kondo/clj-kondo/issues/1786): Support `gen-interface` (by suppressing unresolved symbols)
  - [#2407](https://github.com/clj-kondo/clj-kondo/issues/2407): support ignore hint on called symbol
  - [#2420](https://github.com/clj-kondo/clj-kondo/issues/2420): Detect uneven number of clauses in `cond->` and `cond->>` ([@tomdl89](https://github.com/tomdl89))
  - [#2415](https://github.com/clj-kondo/clj-kondo/issues/2415): false positive type checking issue with `str/replace` and `^String` annotation

- [nbb](https://github.com/babashka/nbb): Scripting in Clojure on Node.js using SCI
  - 1.3.196 (2024-11-25)
  - Add `locking` macro for compatibility with CLJS
  - 1.3.195 (2024-11-07)
  - [#343](https://github.com/babashka/nbb/issues/343): support `:reload` for reloading CLJS namespaces and JS code

- [rewrite-clj](https://github.com/clj-commons/rewrite-clj): Rewrite Clojure code and edn
  - Fix parsing of `b//` symbol

- [pod-babashka-go-sqlite3](https://github.com/babashka/pod-babashka-go-sqlite3): A babashka pod for interacting with sqlite3
  - [#19](https://github.com/babashka/pod-babashka-go-sqlite3/issues/19): Restore mac intel support (this time for real)

- [tools-deps-native](https://github.com/babashka/tools-deps-native) and [tools.bbuild](https://github.com/babashka/tools.bbuild): use tools.deps directly from babashka
  - Fix [#30](https://github.com/babashka/tools-deps-native/issues/30): pod won't run on newer versions of macOS

- [http-client](https://github.com/babashka/http-client): babashka's http-client<br>
  - [#73](https://github.com/babashka/http-client/issues/71): Allow implicit ports when specifying the URL as a map ([@lvh](https://github.com/lvh))
  - [#71](https://github.com/babashka/http-client/issues/71): Link back to sources in release artifact
([@lread](https://github.com/lread))

## Other projects

These are (some of the) other projects I'm involved with but little to no activity
happened in the past month.

<details>
<summary>Click for more details</summary>
- [SCI](https://github.com/babashka/sci): Configurable Clojure/Script interpreter suitable for scr- [cherry](https://github.com/squint-cljs/cherry): Experimental ClojureScript to ES6 module compil- [http-server](https://github.com/babashka/http-server): serve static assets
- [bbin](https://github.com/babashka/bbin): Install any Babashka script or project with one comman
- [sci.configs](https://github.com/babashka/sci.configs): A collection of ready to be used SCI configs.
  - Added a configuration for `cljs.spec.alpha` and related namespaces
- [qualify-methods](https://github.com/borkdude/qualify-methods)
  - Initial release of experimental tool to rewrite instance calls to use fully
    qualified methods (Clojure 1.12 only0
- [clerk](https://github.com/nextjournal/clerk): Moldable Live Programming for Clojure
  - Add support for `:require-cljs` which allows you to use `.cljs` files for render functions
  - Add support for nREPL for developing render functions
- [deps.clj](https://github.com/borkdude/deps.clj): A faithful port of the clojure CLI bash script to Clojure
  - Upgrade/sync with clojure CLI v1.12.0.1479
- [process](https://github.com/babashka/process): Clojure library for shelling out / spawning sub-processes
  - Work has started to support prepending output (in support for babashka parallel tasks). Stay tuned.
- [neil](https://github.com/babashka/neil): A CLI to add common aliases and features to deps.edn-based projects.<br>
- [edamame](https://github.com/borkdude/edamame): Configurable EDN/Clojure parser with location metadata
- [quickdoc](https://github.com/borkdude/quickdoc): Quick and minimal API doc generation for Clojure
- [fs](https://github.com/babashka/fs) - File system utility library for Clojure
- [tools](https://github.com/borkdude/tools): a set of [bbin](https://github.com/babashka/bbin/) installable scripts
- [sci.nrepl](https://github.com/babashka/sci.nrepl): nREPL server for SCI projects that run in the browser
- [html](https://github.com/borkdude/html): Html generation library inspired by squint's html tag
- [rewrite-edn](https://github.com/borkdude/rewrite-edn): Utility lib on top of
  rewrite-clj with common operations to update EDN while preserving whitespace
  and comments
- [instaparse-bb](https://github.com/babashka/instaparse-bb): Use instaparse from babashka
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
- [sql pods](https://github.com/babashka/babashka-sql-pods): babashka pods for SQL databases
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

