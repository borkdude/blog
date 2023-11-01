Title: OSS updates October 2023
Date: 2023-11-01
Tags: clojure, oss updates
Description: My Clojure OSS updates for October 2023

In this post I'll give updates about open source I worked on during October 2023.

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

Here are updates about the projects/libraries I've worked on last month.

- [babashka](https://github.com/babashka/babashka): native, fast starting Clojure interpreter for scripting.
  - [Support self-contained binaries as uberjars!](https://github.com/babashka/babashka/wiki/Self-contained-executable#uberjar)
  - Add `java.security.KeyFactory`, `java.security.spec.PKCS8EncodedKeySpec`, `java.net.URISyntaxException`
  - Fix babashka.process/exec wrt `babashka.process/*defaults*`
  - [#1632](https://github.com/babashka/babashka/issues/1632): Partial fix for `(.readPassword (System/console))`
  - Enable producing self-contained binaries using [uberjars](https://github.com/babashka/babashka/wiki/Self-contained-executable#uberjar)
  - Bump httpkit to `2.8.0-beta3` (fixes GraalVM issue with virtual threads)
  - Bump `deps.clj` and `fs`
  - Expose `taoensso.timbre.appenders.core`
  - nREPL: implement `ns-list` op
  - SCI: optimize `swap!`, `deref` and `reset!` for normal atoms (rather than user-created `IAtom`s)
- [sci.configs](https://github.com/babashka/sci.configs): A collection of ready to be used SCI configs.
  - A configuration for [hoplon](https://github.com/hoplon/hoplon) and [javelin](https://github.com/hoplon/javelin) was added. You can play around with hoplon in a SCI-enabled environment [here](https://babashka.org/sci.configs/?gist=e83da19df3d2739861334171779f79d5)
- [clj-kondo](https://github.com/clj-kondo/clj-kondo): static analyzer and linter for Clojure code that sparks joy.
  - [#2207](https://github.com/clj-kondo/clj-kondo/issues/2207): New `:condition-always-true` linter, see [docs](doc/linters.md)
  - [#2013](https://github.com/clj-kondo/clj-kondo/issues/2013): Fix NPE and similar errors when linting an import with an illegal token
    <br>Published a new version (2023.10.20) with these changes:
  - [#1804](https://github.com/clj-kondo/clj-kondo/issues/1804): new linter `:self-requiring-namespace`
  - [#2065](https://github.com/clj-kondo/clj-kondo/issues/2065): new linter `:equals-false`, counterpart of `:equals-true` ([@svdo](https://github.com/svdo))
  - [#2199](https://github.com/clj-kondo/clj-kondo/issues/2199): add `:syntax` check for var names starting or ending with dot (reserved by Clojure)
  - [#2179](https://github.com/clj-kondo/clj-kondo/issues/2179): consider alias-as-object usage in CLJS for :unused-alias linter
  - [#2183](https://github.com/clj-kondo/clj-kondo/issues/2183): respect `:level` in `:discouraged-var` config
  - [#2184](https://github.com/clj-kondo/clj-kondo/issues/2184): Add missing documentation for `:single-logical-operand` linter ([@wtfleming](https://github.com/wtfleming))
  - [#2187](https://github.com/clj-kondo/clj-kondo/issues/2187): Fix type annotation of argument of `clojure.core/parse-uuid` from `nilable/string` to string ([@dbunin](https://github.com/dbunin))
  - [#2192](https://github.com/clj-kondo/clj-kondo/issues/2192): Support `:end-row` and `:end-col` in `:pattern` output format ([@joshgelbard](https://github.com/joshgelbard))
  - [#2182](https://github.com/clj-kondo/clj-kondo/issues/2182): Namespace local configuration does not silence `:missing-else-branch`
  - [#2186](https://github.com/clj-kondo/clj-kondo/issues/2186): Improve warning when `--copy-configs` is enabled but no config dir exists
  - [#2190](https://github.com/clj-kondo/clj-kondo/issues/2190): false positive with `:unused-alias` and namespaced map
  - [#2200](https://github.com/clj-kondo/clj-kondo/issues/2200): include optional `:callstack` in analysis
- [squint](https://github.com/squint-cljs/squint): CLJS _syntax_ to JS compiler
  <br>Lots of stuff happened in October with squint!
  - [#350](https://github.com/squint-cljs/squint/issues/350): `js*` should default to `:context :expr`
  - [#352](https://github.com/squint-cljs/squint/issues/352): fix `zero?` in return position
  - Add `NaN?` ([@sher](https://github.com/sher))
  - [#347](https://github.com/squint-cljs/squint/issues/347): Add `:pre` and `:post` support in `fn`
  - Add `number?`
  - Support `docstring` in `def`
  - Handle multipe source `:paths` in a more robust fashion
  - [#344](https://github.com/squint-cljs/squint/issues/344): macros can't be used via aliases
  - Add `squint.edn` support, see [docs](README.md#squintedn)
  - Add `watch` subcommand to watch `:paths` from `squint.edn`
  - Make generated `let` variable names in JS more deterministic, which helps hot reloading in React
  - Added a [vite + react example project](examples/vite-react).
  - Resolve symbolic namespaces `(:require [foo.bar])` from `:paths`
  - Add `bit-and` and `bit-or`
  - Include `lib/squint.core.umd.js` which defines a global `squint.core` object (easy to use in browsers, see [docs](README.md#compile-on-a-server-use-in-a-browser))
  - Add `subs`, `fn?`, `re-seq`
  - Add `squint.edn` with `:paths` to resolve macros from (via `:require-macros`)
- [neil](https://github.com/babashka/neil): A CLI to add common aliases and features to deps.edn-based projects
  Version 0.2.62 released
  - Fix NPE during `neil dep upgrade`
- [clojure-mode](https://github.com/nextjournal/clojure-mode)
  - Porting this CLJS project such that it can run with [squint](https://github.com/squint-cljs/squint) also. You can now use this library directly from NPM as a JS library. See [this page](https://squint-cljs.github.io/squint/) for a demo on how to use it directly from a CDN! This work is funded by [Nextjournal](https://nextjournal.com/).
- [cherry](https://github.com/squint-cljs/cherry): Experimental ClojureScript to ES6 module compiler
  - Released version 0.1.9
- [grasp](https://github.com/borkdude/grasp): Grep Clojure code using clojure.spec regexes
  - Fix self-requiring namespace (which clj-kondo now also catches via optional linter!)
- [lein-clj-kondo](https://github.com/clj-kondo/lein-clj-kondo): a leiningen plugin for clj-kondo
  - Bumped clj-kondo version
- [http-kit](https://github.com/http-kit/http-kit): Simple, high-performance event-driven HTTP client+server for Clojure.
  - [#543](https://github.com/http-kit/http-kit/issues/543) Migrate away from `SimpleDateFormat` to `java.time`, fixes native-image issue and virtual threads
- [http-client](https://github.com/babashka/http-client): babashka's http-client
  - A number of small bugfixes and additions
- [SCI](https://github.com/babashka/sci): Configurable Clojure/Script interpreter suitable for scripting and Clojure DSLs
  - Expose `destructure` to scripts
  - Macroexpand `(.foo bar)` form in `macroexpand-1`
  - Optimize `deref`, `swap!`, `reset!` for host values
  - Add `time` macro
- [nbb](https://github.com/babashka/nbb): Scripting in Clojure on Node.js using SCI
  - `sci.core` itself was exposed to nbb users
- [fs](https://github.com/babashka/fs) - File system utility library for Clojure
  - Minor fixes in `glob` by [@eval](https://github.com/eval), thanks!
- [deps.clj](https://github.com/borkdude/deps.clj): A faithful port of the clojure CLI bash script to Clojure
  - Get home directory via environment variable rather than system property by [@DerGuteMoritz](https://github.com/DerGuteMoritz), thanks!
- [babashka.nrepl](https://github.com/babashka/babashka.nrepl): The nREPL server from babashka as a library, so it can be used from other SCI-based CLIs
  - Fix `classpath` op
  - Implement `ns-list` op

## Other projects

These are (some of the) other projects I'm involved with but little to no activity
happened in the past month.

<details>
<summary>Click for more details</summary>
- [rewrite-edn](https://github.com/borkdude/rewrite-edn): Utility lib on top of
  rewrite-clj with common operations to update EDN while preserving whitespace
  and comments
- [tools-deps-native](https://github.com/babashka/tools-deps-native) and [tools.bbuild](https://github.com/babashka/tools.bbuild): use tools.deps directly from babashka
- [CLI](https://github.com/babashka/cli): Turn Clojure functions into CLIs!
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

