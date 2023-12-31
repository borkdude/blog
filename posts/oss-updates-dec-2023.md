Title: OSS updates December 2023
Date: 2023-12-31
Tags: clojure, oss updates
Description: My Clojure OSS updates for December 2023

In this post I'll give updates about open source I worked on during December 2023.

To see previous OSS updates, go [here](https://blog.michielborkent.nl/tags/oss-updates.html).

## Happy new year!

First all, as this is the last day of 2023, I wish you all a happy new
year. Hopefully many goods things may happen in the Clojure ecosystem.

## Sponsors

I'd like to thank all the sponsors and contributors that make this work
possible. Without _you_, the below projects would not be as mature or wouldn't
exist or be maintained at all.
Open the details section for more info.

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

Here are updates about the projects/libraries I've worked on last month.

- [clj-kondo](https://github.com/clj-kondo/clj-kondo): static analyzer and linter for Clojure code that sparks joy.
  Released 2023.12.15
  - [#1990](https://github.com/clj-kondo/clj-kondo/issues/1990): Specify `:min-clj-kondo-version` in config.edn and warn when current version is too low ([@snasphysicist](https://github.com/snasphysicist))
  - [#1753](https://github.com/clj-kondo/clj-kondo/issues/1753): New linter `:underscore-in-namespace` ([@cosineblast](https://github.com/cosineblast))
  - [#2207](https://github.com/clj-kondo/clj-kondo/issues/2207): New `:condition-always-true` linter, see [docs](doc/linters.md)
  - [#2235](https://github.com/clj-kondo/clj-kondo/issues/2235): New
    `:multiple-async-in-deftest` linter: warn on multiple async blocks in
    `cljs.test/deftest`, since only the first will run.
  - [#2013](https://github.com/clj-kondo/clj-kondo/issues/2013): Fix NPE and similar errors when linting an import with an illegal token ([@cosineblast](https://github.com/cosineblast))
  - [#2215](https://github.com/clj-kondo/clj-kondo/issues/2215): Passthrough hook should not affect linting
  - [#2232](https://github.com/clj-kondo/clj-kondo/issues/2232): Bump analysis for clojure 1.12 (partitionv, etc)
  - [#2223](https://github.com/clj-kondo/clj-kondo/issues/2223): Do not consider classes created with `deftype` a var that is referred with `:refer :all`
  - [#2236](https://github.com/clj-kondo/clj-kondo/issues/2236): `:line-length` warnings cannot be `:clj-kondo/ignore`d
  - [#2224](https://github.com/clj-kondo/clj-kondo/issues/2224): Give `#'foo/foo` and `(var foo/foo)` the same treatment with respect to private calls
  - [#2239](https://github.com/clj-kondo/clj-kondo/issues/2239): Fix printing of unresolved var when going through `:macroexpand` hook

- [quickblog](https://github.com/borkdude/quickblog): Light-weight static blog engine for Clojure and babashka
  v0.3.3 and v0.3.4 released
  - Fix caching in watch mode
  - [#86](https://github.com/borkdude/quickblog/issues/86): group archive page by year
  - [#85](https://github.com/borkdude/quickblog/issues/85): don't render discuss links when `:discuss-link` isn't set
  - [#84](https://github.com/borkdude/quickblog/issues/84): sort tags by post count
  - [#80](https://github.com/borkdude/quickblog/issues/80): Generate an `about.html` when a template exists ([@elken](https://github.com/elken))
  - [#78](https://github.com/borkdude/quickblog/issues/78): Allow configurable :page-suffix to omit `.html` from page links ([@anderseknert](https://github.com/anderseknert))
  - [#76](https://github.com/borkdude/quickblog/pull/76): Remove livejs script tag
    on render. ([@jmglov](https://github.com/jmglov))
  - [#75](https://github.com/borkdude/quickblog/pull/75): Omit preview posts from
    index. ([@jmglov](https://github.com/jmglov))
  - Support capitalization of tags
  - [#66](https://github.com/borkdude/quickblog/issues/66): Unambigous ordering of posts, sorting by date (descending), post title, and then file name.  ([@UnwarySage](https://github.com/UnwarySage))

- [squint](https://github.com/squint-cljs/squint): CLJS _syntax_ to JS compiler
  <br>Lots of stuff happened in December with squint! Too many to mention here, just check the [CHANGELOG.md](https://github.com/squint-cljs/squint/blob/main/CHANGELOG.md)

- [clojure-mode](https://github.com/nextjournal/clojure-mode): Clojure/Script mode for CodeMirror 6.
  - Improved the eval-region extension: when you evaluate `#_(+ 1 2 3)|` the expression `(+ 1 2 3)` is evaluated
    Test it in the [squint playground](https://squint-cljs.github.io/squint/?repl=true&src=I18oKyAxIDIgMyk%3D).

- [fs](https://github.com/babashka/fs) - File system utility library for Clojure
  Released 0.5.20:
  - [#119](https://github.com/babashka/fs/issues/119): `fs/delete-tree`: add `:force` flag to delete read-only directories/files. Set the flag to true in  `fs/with-temp-dir` ([@jlesquembre](https://github.com/jlesquembre))
  - [#102](https://github.com/babashka/fs/issues/102): add `gzip` and `gunzip` functions
  - [#113](https://github.com/babashka/fs/issues/113): `fs/glob`: enable `:hidden` (when not already set) when `pattern` starts with dot ([@eval](https://github.com/eval)).
  - [#117](https://github.com/babashka/fs/issues/117): fix `fs/match` and `fs/glob` not finding files in root-folder ([@eval](https://github.com/eval)).

- [cherry](https://github.com/squint-cljs/cherry): Experimental ClojureScript to ES6 module compiler
  - Released version 0.1.16 which adds which catches up with the latest compiler improvements in squint

- [http-server](https://github.com/babashka/http-server): serve static assets
  - Released 0.1.12 with several new features

- [babashka](https://github.com/babashka/babashka): native, fast starting Clojure interpreter for scripting.
  - Working towards a new release, planned for next month.

## Other projects

These are (some of the) other projects I'm involved with but little to no activity
happened in the past month.

<details>
<summary>Click for more details</summary>
- [neil](https://github.com/babashka/neil): A CLI to add common aliases and features to deps.edn-based projects

- [CLI](https://github.com/babashka/cli): Turn Clojure functions into CLIs!

- [squint-macros](https://github.com/squint-cljs/squint-macros): a couple of
  macros that stand-in for
  [applied-science/js-interop](https://github.com/applied-science/js-interop)
  and [promesa](https://github.com/funcool/promesa) to make CLJS projects
  compatible with squint and/or cherry.

- [sci.configs](https://github.com/babashka/sci.configs): A collection of ready to be used SCI configs.

- [grasp](https://github.com/borkdude/grasp): Grep Clojure code using clojure.spec regexes

- [lein-clj-kondo](https://github.com/clj-kondo/lein-clj-kondo): a leiningen plugin for clj-kondo

- [http-kit](https://github.com/http-kit/http-kit): Simple, high-performance event-driven HTTP client+server for Clojure.

- [http-client](https://github.com/babashka/http-client): babashka's http-client

- [nbb](https://github.com/babashka/nbb): Scripting in Clojure on Node.js using SCI

- [deps.clj](https://github.com/borkdude/deps.clj): A faithful port of the clojure CLI bash script to Clojure

- [babashka.nrepl](https://github.com/babashka/babashka.nrepl): The nREPL server from babashka as a library, so it can be used from other SCI-based CLIs

- [rewrite-edn](https://github.com/borkdude/rewrite-edn): Utility lib on top of
  rewrite-clj with common operations to update EDN while preserving whitespace
  and comments
- [tools-deps-native](https://github.com/babashka/tools-deps-native) and [tools.bbuild](https://github.com/babashka/tools.bbuild): use tools.deps directly from babashka
- [jet](https://github.com/borkdude/jet): CLI to transform between JSON, EDN, YAML and Transit using Clojure
- [quickdoc](https://github.com/borkdude/quickdoc): Quick and minimal API doc generation for Clojure
- [pod-babashka-go-sqlite3](https://github.com/babashka/pod-babashka-go-sqlite3): A babashka pod for interacting with sqlite3
- [pod-babashka-fswatcher](https://github.com/babashka/pod-babashka-fswatcher): babashka filewatcher pod
- [edamame](https://github.com/borkdude/edamame): Configurable EDN/Clojure parser with location metadata
- [lein2deps](https://github.com/borkdude/lein2deps): leiningen to deps.edn converter
- [scittle](https://github.com/babashka/scittle): Execute Clojure(Script) directly from browser script tags via SCI
- [sql pods](https://github.com/babashka/babashka-sql-pods): babashka pods for SQL databases
- [cljs-showcase](https://github.com/borkdude/cljs-showcase): Showcase CLJS libs using SCI
- [process](https://github.com/babashka/process): Clojure library for shelling out / spawning sub-processes
- [babashka.book](https://github.com/babashka/book): Babashka manual
- [instaparse-bb](https://github.com/babashka/instaparse-bb)
- [rewrite-clj](https://github.com/clj-commons/rewrite-clj): Rewrite Clojure code and edn
- [pod-babashka-buddy](https://github.com/babashka/pod-babashka-buddy): A pod around buddy core (Cryptographic Api for Clojure).
- [gh-release-artifact](https://github.com/borkdude/gh-release-artifact): Upload artifacts to Github releases idempotently
- [carve](https://github.com/borkdude/carve) - Remove unused Clojure vars
- [4ever-clojure](https://github.com/oxalorg/4ever-clojure) - Pure CLJS version of 4clojure, meant to run forever!
- [pod-babashka-lanterna](https://github.com/babashka/pod-babashka-lanterna): Interact with clojure-lanterna from babashka
- [joyride](https://github.com/BetterThanTomorrow/joyride): VSCode CLJS scripting and REPL (via [SCI](https://github.com/babashka/sci))
- [clj2el](https://borkdude.github.io/clj2el/): transpile Clojure to elisp
- [deflet](https://github.com/borkdude/deflet): make let-expressions REPL-friendly!
- [babashka.json](https://github.com/babashka/json): babashka JSON library/adapter
- [deps.add-lib](https://github.com/borkdude/deps.add-lib): Clojure 1.12's add-lib feature for leiningen and/or other environments without a specific version of the clojure CLI

</details>

