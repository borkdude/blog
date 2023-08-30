Title: OSS updates August 2023
Date: 2023-08-30
Tags: clojure, oss updates
Description: My Clojure OSS updates for August 2023

In this post I'll give updates about open source I worked on during August 2023.

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
- [Pitch](https://github.com/pitch-io)

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

TODO: mention strangeloop
TODO: mention Rahul and Anu for JavaZone

- [jet](https://github.com/borkdude/jet): CLI to transform between JSON, EDN, YAML and Transit using Clojure
  - Release version `0.7.27` (see
    [changelogs](https://github.com/borkdude/jet/blob/master/CHANGELOG.md#0727-2023-08-02))
    with missing 1.11 functions and options for easier kebab/camel/etc. casing.
- [quickdoc](https://github.com/borkdude/quickdoc): Quick and minimal API doc generation for Clojure
  - No update in quickdoc, but happy to see that Github have resolved a bug on their side with local anchors in HTML, which quickdoc relies on
  - Require clojure 1.11 as the minimal clojure version
- [sci.configs](https://github.com/babashka/sci.configs): A collection of ready to be used SCI configs.
  - Worked together with [@niwinz](https://github.com/niwinz) to make
    sci.configs upgradable to promesa 10 and 11. Many thanks to Andrey for
    making promesa backward-compatible again, since sci.configs relies on Clojure libraries to be always upgradable without breaking changes.
- [nbb](https://github.com/babashka/nbb): Scripting in Clojure on Node.js using SCI
  - Bumped sci.configs and promesa
- [deps.clj](https://github.com/borkdude/deps.clj): A faithful port of the clojure CLI bash script to Clojure
  - The tools jar relocated to Github releases so deps.clj was updated to this new location, with backward compatibility
  - Per my request, Alex added a `.sha256` file to Github releases so the downloaded jar file could be verified against corruption
- [SCI](https://github.com/babashka/sci): Configurable Clojure/Script interpreter suitable for scripting and Clojure DSLs
  - Clojure compatibility: allow `(def foo/foo 1)` in namespace `foo`
  - Clojure compatibility: reset file metadata on var when it's re-evaluated from other file
  - Add `sci.async/eval-form` and `sci.async/eval-form+`
- [babashka](https://github.com/babashka/babashka): native, fast starting Clojure interpreter for scripting.
  - expose `sci.core` in babashka
  - Asahi linux support (linux on Apple m1/m2)
  - Several other library upgrades and Clojure compatibility fixes
  - Compatibility with the newest integrant version
- [pod-babashka-go-sqlite3](https://github.com/babashka/pod-babashka-go-sqlite3): A babashka pod for interacting with sqlite3
  - Upgrade sqlite version so it supports json fields
- cherry

- [clj-kondo](https://github.com/clj-kondo/clj-kondo): static analyzer and linter for Clojure code that sparks joy.
  - A big fat new release: 2023.07.23. Several new linting rules and lots of fixes. See [changelogs](https://github.com/clj-kondo/clj-kondo/blob/master/CHANGELOG.md#20230713) here.
- [http-client](https://github.com/babashka/http-client): Babashka's http-client
  - Added a websocket API, a fix for the `:ssl-context {:insecure true}` option and more. See [CHANGELOG](https://github.com/babashka/http-client/blob/main/CHANGELOG.md).
- [pod-babashka-fswatcher](https://github.com/babashka/pod-babashka-fswatcher): babashka filewatcher pod
  - The events emitted from the file watcher are now automatically deduplicated.
  - An aarch64 binary for Mac is now available
  Thanks to @fjsousa and @lispyclouds.
- [edamame](https://github.com/borkdude/edamame): Configurable EDN/Clojure parser with location metadata
  - A small bugfix release around reading malformed reader conditional expressions
- [lein-clj-kondo](https://github.com/clj-kondo/lein-clj-kondo): a leiningen plugin for clj-kondo
  - This plugin now follows the version number of clj-kondo
- [squint](https://github.com/squint-cljs/squint): CLJS _syntax_ to JS compiler and [cherry](https://github.com/squint-cljs/cherry) Experimental ClojureScript to ES6 module compiler
  - Add [`defclass`](https://github.com/squint-cljs/squint/blob/main/doc/defclass.md) in squint, inspired by shadow-cljs
  - More work on getting squint and cherry to work in one build
  - Provide UMD build which works better in Firefox Webworkers
  - cherry can now be used in a playground at [livecodes.io](https://dev.livecodes.io/?template=clojurescript)
  - Fix `doseq` and add `doall` and `dorun` in squint
- [lein2deps](https://github.com/borkdude/lein2deps): leiningen to deps.edn converter
  - Allow anonymous function literals in `project.clj`
- [scittle](https://github.com/babashka/scittle): Execute Clojure(Script) directly from browser script tags via SCI
  - Fixed a small bug with evaluating tags: when there would be whitespace + a `"src"` attribute, the whitespace would be executed and the attribute was ignored.
- [tools-deps-native](https://github.com/babashka/tools-deps-native) and [tools.bbuild](https://github.com/babashka/tools.bbuild)
  - This EXPERIMENTAL combo allows you to use tools.build from babashka. In this release a reflection issue was addressed.

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
- [sql pods](https://github.com/babashka/babashka-sql-pods): babashka pods for SQL databases
- [CLI](https://github.com/babashka/cli): Turn Clojure functions into CLIs!
- [cljs-showcase](https://github.com/borkdude/cljs-showcase): Showcase CLJS libs using SCI
- [fs](https://github.com/babashka/fs) - File system utility library for Clojure
- [process](https://github.com/babashka/process): Clojure library for shelling out / spawning sub-processes
  - Implement `:out :bytes` to receive output as bytes (thanks Hans Bugge Grathwohl)
  - Make `:dir` option accept `java.nio.file.Path`

- [babashka.book](https://github.com/babashka/book): Babashka manual
- [instaparse-bb](https://github.com/babashka/instaparse-bb)
- [rewrite-clj](https://github.com/clj-commons/rewrite-clj): Rewrite Clojure code and edn
- [pod-babashka-buddy](https://github.com/babashka/pod-babashka-buddy): A pod around buddy core (Cryptographic Api for Clojure).
- [gh-release-artifact](https://github.com/borkdude/gh-release-artifact): Upload artifacts to Github releases idempotently
- [neil](https://github.com/babashka/neil): A CLI to add common aliases and features to deps.edn-based projects
- [carve](https://github.com/borkdude/carve) - Remove unused Clojure vars
- [grasp](https://github.com/borkdude/grasp): Grep Clojure code using clojure.spec regexes
- [quickblog](https://github.com/borkdude/quickblog): Light-weight static blog engine for Clojure and babashka
- [4ever-clojure](https://github.com/oxalorg/4ever-clojure) - Pure CLJS version of 4clojure, meant to run forever!
- [pod-babashka-lanterna](https://github.com/babashka/pod-babashka-lanterna): Interact with clojure-lanterna from babashka
- [joyride](https://github.com/BetterThanTomorrow/joyride): VSCode CLJS scripting and REPL (via [SCI](https://github.com/babashka/sci))
- [clj2el](https://borkdude.github.io/clj2el/): transpile Clojure to elisp
- [deflet](https://github.com/borkdude/deflet): make let-expressions REPL-friendly!
- [babashka.json](https://github.com/babashka/json): babashka JSON library/adapter
- [deps.add-lib](https://github.com/borkdude/deps.add-lib): Clojure 1.12's add-lib feature for leiningen and/or other environments without a specific version of the clojure CLI

</details>

