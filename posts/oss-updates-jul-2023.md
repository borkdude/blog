Title: OSS updates July 2023
Date: 2023-07-31
Tags: clojure, oss updates
Description: My Clojure OSS updates for July 2023

In this post I'll give updates about open source I worked on during July 2023.

To see previous OSS updates, go [here](https://blog.michielborkent.nl/tags/oss-updates.html).

## Sponsors

I'd like to thank all the sponsors and contributors that make
this work possible! Open the details section for more info.

<details>
<summary>Sponsor info</summary>
Top sponsors:

- [Clojurists Together](https://clojuriststogether.org/)
- [Roam Research](https://roamresearch.com/)
- [Nextjournal](https://nextjournal.com/)
- [Toyokumo](https://toyokumo.co.jp/)
- [Cognitect](https://www.cognitect.com/)
- [Kepler16](https://kepler16.com/)
- [Adgoji](https://www.adgoji.com/)

If you want to ensure that the projects I work on are sustainably maintained,
you can sponsor this work in the following ways. Thank you!

- [Github Sponsors](https://github.com/sponsors/borkdude)
- The [Babaska](https://opencollective.com/babashka) or [Clj-kondo](https://opencollective.com/clj-kondo) OpenCollective
- [Ko-fi](https://ko-fi.com/borkdude)
- [Patreon](https://www.patreon.com/borkdude)
- [Clojurists Together](https://www.clojuriststogether.org/)

If you're used to sponsoring through some other means which isn't listed above, please get in touch.

On to the projects that I've been working on!
</details>

<!--

sources: https://github.com/borkdude
local ~/dev and ~/dev/babashka dir (since github doesn't show all repos)

-->

## Updates

Early this month I've been on a vacation in Switzerland to catch some fresh air. 

- [squint](https://github.com/squint-cljs/squint): CLJS _syntax_ to JS compiler and [cherry](https://github.com/squint-cljs/cherry) Experimental ClojureScript to ES6 module compiler
  - Both projects can now be used simultaneously in one build. The use case for this is when you have projects like [clerk](https://github.com/nextjournal/clerk) that ship with multiple options for evaluating CLJS at runtime and you want to offer both squint and cherry as options.
- [sql pods](https://github.com/babashka/babashka-sql-pods): babashka pods for SQL databases
  - Version 0.1.2 was released which contains upgrades of database drivers and
    next.jdbc library. Also a bug was fixed around mssql.
- [CLI](https://github.com/babashka/cli): Turn Clojure functions into CLIs!
  - Implemented an alternative to `shutdown-agents` which does not kill threads
    when using an exec function, e.g. when spinning up a web server. Also see
    [TDEPS-198](https://clojure.atlassian.net/browse/TDEPS-198).
- [cljs-showcase](https://github.com/borkdude/cljs-showcase): Showcase CLJS libs using SCI
  - Namespace state is now preserved over multiple blocks
- [clj-kondo](https://github.com/clj-kondo/clj-kondo): static analyzer and linter for Clojure code that sparks joy.
  - Actively working towards a [new release](https://github.com/clj-kondo/clj-kondo/blob/master/CHANGELOG.md#unreleased), probably next month.
- [fs](https://github.com/babashka/fs) - File system utility library for Clojure
  - Add `gzip` and `gunzip` functions (thanks to Lauri Oherd)
- [deps.clj](https://github.com/borkdude/deps.clj): A faithful port of the clojure CLI bash script to Clojure
  - Tried to improve the situation where the downloaded tools jar may be corrupt and causes trouble when calculating the classpath, using a crc32 check. See babashka [issue](https://github.com/babashka/babashka/issues/1576).
- [SCI](https://github.com/babashka/sci): Configurable Clojure/Script interpreter suitable for scripting and Clojure DSLs
  - Support `:require-macros`
  - Introduce `eval-string+` which received an optional initial `:ns` key and also returns the last active `:ns` so you can preserve the namespace state over multiple evaluations.
  - Released v0.8.40
- [process](https://github.com/babashka/process): Clojure library for shelling out / spawning sub-processes
  - Implement `:out :bytes` to receive output as bytes (thanks Hans Bugge Grathwohl)
  - Make `:dir` option accept `java.nio.file.Path`
- [babashka](https://github.com/babashka/babashka): native, fast starting Clojure interpreter for scripting.
  - 1575: fix command line parsing problem with -e + `*command-line-args*`
  - 1576: make downloading/unzipping of deps.clj tools .zip file more robust (see deps.clj)
  - released version 1.3.181
  - 1581: bb `print-deps`: sort dependencies (thanks to Teodor Heggelund)
  - 1579: add `clojure.tools.reader/resolve-symbol` built-in

<!-- ## Contributions to other projects -->

<!-- - [clojurescript](https://github.com/clojure/clojurescript): -->
<!--   - [PR 202](https://github.com/clojure/clojurescript/pull/202): a `macroexpand` fix -->
<!--   - [PR 203](https://github.com/clojure/clojurescript/pull/203): a symbol optimization fix -->
<!-- - [malli](https://github.com/metosin/malli/commit/cf918db28ff71a2f735f465f30f0bc1028ecd7d9): cherry integration -->
<!-- - [clerk](https://github.com/nextjournal/clerk/commit/cb079b14213185d27c5a2d1cc1e80943521a4fb5): cherry integration -->
<!-- - [clojure-lsp](https://github.com/clojure-lsp/clojure-lsp/commit/60d67cca59f0747e8b68802157afbe7f61440c7f): integrated a new clj-kondo feature: showing the languages in a CLJC context -->

## Other projects

These are (some of the) other projects I'm involved with but little to no activity
happened in the past month.

<details>
<summary>Click for more details</summary>
- [scittle](https://github.com/babashka/scittle): Execute Clojure(Script) directly from browser script tags via SCI
- [nbb](https://github.com/babashka/nbb): Scripting in Clojure on Node.js using SCI
- [http-client](https://github.com/babashka/http-client): Babashka's http-client
- [sci.configs](https://github.com/babashka/sci.configs): A collection of ready to be used SCI configs.
- [edamame](https://github.com/borkdude/edamame): Configurable EDN/Clojure parser with location metadata
- [babashka.book](https://github.com/babashka/book): Babashka manual
- [pod-babashka-go-sqlite3](https://github.com/babashka/pod-babashka-go-sqlite3): A babashka pod for interacting with sqlite3
- [instaparse-bb](https://github.com/babashka/instaparse-bb)
- [jet](https://github.com/borkdude/jet): CLI to transform between JSON, EDN, YAML and Transit using Clojure
- [quickdoc](https://github.com/borkdude/quickdoc): Quick and minimal API doc generation for Clojure
- [rewrite-clj](https://github.com/clj-commons/rewrite-clj): Rewrite Clojure code and edn
- [pod-babashka-buddy](https://github.com/babashka/pod-babashka-buddy): A pod around buddy core (Cryptographic Api for Clojure).
- [gh-release-artifact](https://github.com/borkdude/gh-release-artifact): Upload artifacts to Github releases idempotently
- [neil](https://github.com/babashka/neil): A CLI to add common aliases and features to deps.edn-based projects
- [carve](https://github.com/borkdude/carve) - Remove unused Clojure vars
- [grasp](https://github.com/borkdude/grasp): Grep Clojure code using clojure.spec regexes
- [quickblog](https://github.com/borkdude/quickblog): Light-weight static blog engine for Clojure and babashka
- [lein2deps](https://github.com/borkdude/lein2deps): leiningen to deps.edn converter
- [4ever-clojure](https://github.com/oxalorg/4ever-clojure) - Pure CLJS version of 4clojure, meant to run forever!
- [pod-babashka-lanterna](https://github.com/babashka/pod-babashka-lanterna): Interact with clojure-lanterna from babashka
- [pod-babashka-fswatcher](https://github.com/babashka/pod-babashka-fswatcher): babashka filewatcher pod
- [joyride](https://github.com/BetterThanTomorrow/joyride): VSCode CLJS scripting and REPL (via [SCI](https://github.com/babashka/sci))
- [clj2el](https://borkdude.github.io/clj2el/): transpile Clojure to elisp
- [deflet](https://github.com/borkdude/deflet): make let-expressions REPL-friendly!
- [babashka.json](https://github.com/babashka/json): babashka JSON library/adapter
- [deps.add-lib](https://github.com/borkdude/deps.add-lib): Clojure 1.12's add-lib feature for leiningen and/or other environments without a specific version of the clojure CLI

</details>

<!-- - [tools-deps-native](https://github.com/babashka/tools-deps-native): Run tools.deps as a native binary-->
<!-- - [tools.bbuild](https://github.com/babashka/tools.bbuild): Library of functions for building Clojure projects-->
