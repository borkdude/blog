Title: OSS updates December 2023
Date: 2023-12-31
Tags: clojure, oss updates
Description: My Clojure OSS updates for December 2023

In this post I'll give updates about open source I worked on during December 2023.

To see previous OSS updates, go [here](https://blog.michielborkent.nl/tags/oss-updates.html).

## Sponsors

I'd like to thank all the sponsors and contributors that make this work
possible! Without you, the below projects would not be as mature or wouldn't
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

## 2023.12.15
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



- [squint](https://github.com/squint-cljs/squint): CLJS _syntax_ to JS compiler
  <br>Lots of stuff happened in November with squint! You could say that I've grown a little addicted to improving this project currently, driven by how users use it and also while developing the [playground](https://squint-cljs.github.io/squint/examples/aoc/index.html), a lot of potential improvements emerged..
  <details>
  - Restore backward compatibility with code that is compiled with older versions of squint
  - Optimize various outputs for smaller size
  - Add `js-in`
  - Support `into` + `xform`
  - Support `sort` on strings
  - [#386](https://github.com/squint-cljs/squint/issues/386): allow expression in value position in map literal
  - Improvements with respect to laziness in `mapcat` and `concat`
  - Do not array mutate argument in `reverse`
  - Escape JSX attribute vector value (and more)
  - `map` + `transduce` support
  - Fix `for` in REPL mode
   - Throw when object is not iterable in `for`
  - Make next lazy when input is lazy
  - Fix playground shim (fixes issue in older versions of Safari)
  - Add `js-mod` and `quot`
  - [#380](https://github.com/squint-cljs/squint/issues/380): Don't emit space in between `#jsx` tags
  - Add `re-find`
  - Add `condp` macro
  - Use `compare` as default compare function in `sort` (which fixes numerical sorting)
  - Allow `assoc!` to be called on arbitrary classes (regression)
  - Improve `get` to call `get` method when present.
  - Allow keywords and collections to be used as functions in HOFs
  - Make filter, etc aware of truthiness
  - Reduce code size for truthiness checks
  - Add `str/split-lines`
  - Add `partition-by`
  - Add `parse-long`
  - Add `sort-by`
  - Fix top level await
  - Support multiple dimensions in `aset`
  - Add `coercive-=` as alias for `==`
  - Add `js-delete`
  - Fix `min-key` and `max-key` and improve tests
  - Add `min-key` and `max-key`
  - Fix `defonce` in REPL-mode
  - Fix `doseq` and `for` when binding name clashes with core var
  - Several REPL improvements
  - Improve [https://squint-cljs.github.io/squint/](https://squint-cljs.github.io/squint/)
  - Allow alias name to be used as object in REPL mode
  - Copy resources when using `squint compile` or `squint watch`
  - Return map when `select-keys` is called with `nil`
  - nREPL server: print values through `cljs.pprint` ([@PEZ](https://github.com/PEZ))
  - Initial (incomplete!) nREPL server on Node.js: `npx squint nrepl-server :port 1888`
  - Update/refactor [threejs](https://github.com/squint-cljs/squint/tree/main/examples/threejs) example
  - [#360](https://github.com/squint-cljs/squint/issues/360): `assoc-in!` should not mutate objects in the middle if they already exist
  - Evaluate `lazy-seq` body just once
  - Avoid stackoverflow with `min` and `max`
  - [#360](https://github.com/squint-cljs/squint/issues/360): fix assoc-in! with immutable objects in the middle
  - Add `mod`, `object?`
  - Optimize `get`
  - Add [threejs](https://github.com/squint-cljs/squint/tree/main/examples/threejs) example
  - [#357](https://github.com/squint-cljs/squint/issues/357): fix version in help text
  - Fix iterating over objects
  - Add `clojure.string`'s `triml`, `trimr`, `replace`
  - Fix `examples/vite-react` by adding `public/index.html`
  - Add `find`, `bounded-count`, `boolean?`, `merge-with`, `meta`, `with-meta`, `int?`, `ex-message`, `ex-cause`, `ex-info`
  - Fix munging of reserved symbols in function arguments

- [scittle-hoplon](https://jsfiddle.net/xbgj6v1q/1/): a custom scittle distribution with Hoplon. I helped developing the SCI configuration for Hoplon.

- [gespensterfelder](https://squint-cljs.github.io/squint/examples/threejs/playground.html): a demo that Jack Rusher wrote using Three.js ported to squint.

- [neil](https://github.com/babashka/neil): A CLI to add common aliases and features to deps.edn-based projects
  Version 0.2.63 released which adds mvn search and some bugfixes

- [CLI](https://github.com/babashka/cli): Turn Clojure functions into CLIs!
  - Small bugfix around priority of `:exec-args` and `default`

- [aoc-proxy](https://github.com/borkdude/aoc-proxy): a Cloudflare worker that can be used to fetch Advent of Code puzzle input from the browser (see [Advent of Code playground](https://squint-cljs.github.io/squint/examples/aoc/index.html))

- [squint-macros](https://github.com/squint-cljs/squint-macros): a couple of
  macros that stand-in for
  [applied-science/js-interop](https://github.com/applied-science/js-interop)
  and [promesa](https://github.com/funcool/promesa) to make CLJS projects
  compatible with squint and/or cherry.

- [sci.configs](https://github.com/babashka/sci.configs): A collection of ready to be used SCI configs.
  - A helper macro was improved such that you can define macros that are usable in SCI
  - The re-frame configuration now has support for `re-frame.alpha`. See [playground](https://babashka.org/sci.configs/).

## Other projects

These are (some of the) other projects I'm involved with but little to no activity
happened in the past month.

<details>
<summary>Click for more details</summary>
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
- [quickblog](https://github.com/borkdude/quickblog): Light-weight static blog engine for Clojure and babashka
- [4ever-clojure](https://github.com/oxalorg/4ever-clojure) - Pure CLJS version of 4clojure, meant to run forever!
- [pod-babashka-lanterna](https://github.com/babashka/pod-babashka-lanterna): Interact with clojure-lanterna from babashka
- [joyride](https://github.com/BetterThanTomorrow/joyride): VSCode CLJS scripting and REPL (via [SCI](https://github.com/babashka/sci))
- [clj2el](https://borkdude.github.io/clj2el/): transpile Clojure to elisp
- [deflet](https://github.com/borkdude/deflet): make let-expressions REPL-friendly!
- [babashka.json](https://github.com/babashka/json): babashka JSON library/adapter
- [deps.add-lib](https://github.com/borkdude/deps.add-lib): Clojure 1.12's add-lib feature for leiningen and/or other environments without a specific version of the clojure CLI

</details>

