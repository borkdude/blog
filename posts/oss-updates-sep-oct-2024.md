Title: OSS updates September and October 2024
Date: 2024-11-01
Tags: clojure, oss updates
Description: My Clojure OSS updates for September and October 2024

In this post I'll give updates about open source I worked on during September and October 2024.

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

Soon Clojurists Together will be opening their application for long term
funding. If you are a member, don't forget to vote!

If you're used to sponsoring through some other means which isn't listed above, please get in touch.

On to the projects that I've been working on!
</details>

<!--

sources: https://github.com/borkdude
local ~/dev and ~/dev/babashka dir (since github doesn't show all repos)

- write something about Heart of Clojure and bbslideshow?

-->

## Updates

In September I visited [Heart of Clojure](https://2024.heartofclojure.eu/) where
Christian, Teodor and I did a workshop on babashka. The first workshop was soon
fully booked so we even did a second one and had a lot of fun doing so. It was
so good to see familiar Clojure faces in real life again. Thanks Arne and Gaiwan
team for organizing this amazing conference.

Although I didn't make it to the USA for the Clojure conj in October, Alex
Miller did invite me to appear towards the end of his closing talk when he
mentioned that 90% of survey respondents used babashka.

<img src="https://files.mastodon.social/media_attachments/files/113/369/966/507/536/059/original/4707ab0b7fcb6f11.png" width="80%"></img>

<img src="https://pbs.twimg.com/media/GazP9WmbUAAACIr?format=jpg&name=4096x4096" width="80%"></img>

Here are updates about the projects/libraries I've worked on in the last two months.

- [clj-kondo](https://github.com/clj-kondo/clj-kondo): static analyzer and linter for Clojure code that sparks joy.<br>
  - Unreleased
  - [#1784](https://github.com/clj-kondo/clj-kondo/issues/1784): detect `:redundant-do` in `catch`
  - [#2410](https://github.com/clj-kondo/clj-kondo/issues/2410): add `--report-level` flag
  - 2024.09.27
  - [#2404](https://github.com/clj-kondo/clj-kondo/issues/2404): fix regression with metadata on node in hook caused by `:redundant-ignore` linter
  - 2024.09.26
  - [#2366](https://github.com/clj-kondo/clj-kondo/issues/2366): new linter: `:redundant-ignore`. See [docs](doc/linters.md)
  - [#2386](https://github.com/clj-kondo/clj-kondo/issues/2386): fix regression introduced in [#2364](https://github.com/clj-kondo/clj-kondo/issues/2364) in `letfn`
  - [#2389](https://github.com/clj-kondo/clj-kondo/issues/2389): add new `hooks-api/callstack` function
  - [#2392](https://github.com/clj-kondo/clj-kondo/issues/2392): don't skip jars that were analyzed with `--skip-lint`
  - [#2395](https://github.com/clj-kondo/clj-kondo/issues/2395): enum constant call warnings
  - [#2400](https://github.com/clj-kondo/clj-kondo/issues/2400): `deftype` and `defrecord` constructors can be used with `Type/new`
  - [#2394](https://github.com/clj-kondo/clj-kondo/issues/2394): add `:sort` option to `:unsorted-required-namespaces` linter to enable case-sensitive sort to match other tools
  - [#2384](https://github.com/clj-kondo/clj-kondo/issues/2384): recognize `gen/fmap` var in `cljs.spec.gen.alpha`

- [babashka](https://github.com/babashka/babashka): native, fast starting Clojure interpreter for scripting.
  - [#1752](https://github.com/babashka/babashka/issues/1752): include `java.lang.SecurityException` for `java.net.http.HttpClient` support
  - [#1748](https://github.com/babashka/babashka/issues/1748): add `clojure.core/ensure`
  - Upgrade to `taoensso/timbre` `v6.6.0`
  - Upgrade to GraalVM 23
  - [#1743](https://github.com/babashka/babashka/issues/1743): fix new fully qualified instance method in call position with GraalVM 23
  - Clojure 1.12 interop: method thunks, FI coercion, array notation (see below)
  - Upgrade SCI reflector based on clojure 1.12 and remove specific workaround for
  `Thread/sleep` interop
  - Add `tools.reader.edn/read`
  - Fix [#1741](https://github.com/babashka/babashka/issues/1741): `(taoensso.timbre/spy)` now relies on macros from `taoensso.encore` previously not available in bb
  - Upgrade Clojure to `1.12.0`
  - [#1722](https://github.com/babashka/babashka/issues/1722): add new clojure 1.12 vars
  - [#1720](https://github.com/babashka/babashka/issues/1720): include new clojure 1.12's `clojure.java.process`
  - [#1719](https://github.com/babashka/babashka/issues/1719): add new clojure 1.12 `clojure.repl.deps` namespace. Only calls with explicit versions are supported.
  - [#1598](https://github.com/babashka/babashka/issues/1598): use Rosetta on CircleCI to build x64 images
  - [#1716](https://github.com/babashka/babashka/issues/1716): expose `babashka.http-client.interceptors` namespace
  - [#1707](https://github.com/babashka/babashka/issues/1707): support `aset` on primitive array
  - [#1676](https://github.com/babashka/babashka/issues/1676): restore compatibility with newest [at-at](https://github.com/overtone/at-at/) version (1.3.58)
  - Bump SCI
  - Bump `fs`
  - Bump `process`
  - Bump `deps.clj`
  - Bump `http-client`
  - Bump `clj-yaml`
  - Bump `edamame`
  - Bump `rewrite-clj`
  - Add `java.io.LineNumberReader`

- [SCI](https://github.com/babashka/sci): Configurable Clojure/Script interpreter suitable for scripting and Clojure DSLs
  - Fix [#942](https://github.com/babashka/sci/issues/942): improve error location of invalid destructuring
  - Fix [#917](https://github.com/babashka/sci/issues/917): support new Clojure 1.12 Java interop: `String/new`, `String/.length` and `Integer/parseInt` as fns
  - Fix [#925](https://github.com/babashka/sci/issues/925): support new Clojure 1.12 array notation: `String/1`, `byte/2`
  - Fix [#926](https://github.com/babashka/sci/issues/926): Support `add-watch` on vars in CLJS
  - Support `aset` on primitive array using reflection
  - Fix [#928](https://github.com/babashka/sci/issues/928): record constructor supports optional meta + ext map
  - Fix [#934](https://github.com/babashka/sci/issues/934): `:allow` may contain namespaced symbols
  - Fix [#937](https://github.com/babashka/sci/issues/937): throw when copying non-existent namespace
  - Update `sci.impl.Reflector` (used for implementing JVM interop) to match Clojure 1.12

- [squint](https://github.com/squint-cljs/squint): CLJS _syntax_ to JS compiler
  - Fix watcher and compiler not overriding `squint.edn` configurations with command line options.
  - Allow passing `--extension` and `--paths` via CLI
  - Fix [#563](https://github.com/squint-cljs/squint/issues/563): prioritize refer over core built-in
  - Update `chokidar` to v4 which reduces the number of dependencies
  - BREAKING: Dynamic CSS in `#html` must now be explicitly passed as map literal: `(let [m {:color :green}] #html [:div {:style {:& m}}])`. Fixes issue when using `lit-html` in combination with `classMap`. See [demo](https://squint-cljs.github.io/squint/?src=KG5zIG15bGl0CiAgKDpyZXF1aXJlIFtzcXVpbnQuY29yZSA6cmVmZXIgW2RlZmNsYXNzIGpzLXRlbXBsYXRlXV0KICAgWyJodHRwczovL2VzbS5zaC9saXRAMy4wLjAiIDphcyBsaXRdCiAgIFsiaHR0cHM6Ly9lc20uc2gvbGl0QDMuMC4wL2RpcmVjdGl2ZXMvY2xhc3MtbWFwLmpzIiA6cmVmZXIgW2NsYXNzTWFwXV0pKQoKKGRlZmNsYXNzIE15RWxlbWVudAogIChleHRlbmRzIGxpdC9MaXRFbGVtZW50KQogICheOnN0YXRpYyBmaWVsZCBwcm9wZXJ0aWVzIHs6Y291bnQge319KQoKICAoY29uc3RydWN0b3IgW3RoaXNdCiAgICAoc3VwZXIpCiAgICAoc2V0ISB0aGlzLmNvdW50IDApCiAgICAoc2V0ISB0aGlzLm5hbWUgIkhlbGxvIikpCgogIE9iamVjdAogIChyZW5kZXIgW3RoaXNdCiAgICAjaHRtbCBebGl0L2h0bWwKICAgIFs6ZGl2CiAgICAgWzpoMSB7OmNsYXNzIChjbGFzc01hcCB7OmVuYWJsZWQgdHJ1ZX0pfQogICAgICB0aGlzLm5hbWVdCiAgICAgWzpidXR0b24geyJAY2xpY2siIHRoaXMub25DbGljawogICAgICAgICAgICAgICA6cGFydCAiYnV0dG9uIn0KICAgICAgIkNsaWNrIGNvdW50ICIgdGhpcy5jb3VudF1dKQoKICAob25DbGljayBbdGhpc10KICAgIChzZXQhIHRoaXMuY291bnQgKGluYyB0aGlzLmNvdW50KSkpKQoKKGRlZm9uY2UgZm9vCiAgKGRvCiAgICAoanMvd2luZG93LmN1c3RvbUVsZW1lbnRzLmRlZmluZSAibXktZWxlbWVudCIgTXlFbGVtZW50KQogICAgdHJ1ZSkpCgooZGVmIGFwcCAob3IgKGpzL2RvY3VtZW50LnF1ZXJ5U2VsZWN0b3IgIiNhcHAiKQogICAgICAgICAgIChkb3RvIChqcy9kb2N1bWVudC5jcmVhdGVFbGVtZW50ICJkaXYiKQogICAgICAgICAgICAgKHNldCEgLWlkICJhcHAiKQogICAgICAgICAgICAgKGpzL2RvY3VtZW50LmJvZHkucHJlcGVuZCkpKSkKCihzZXQhICguLWlubmVySFRNTCBhcHApICNodG1sIFs6ZGl2CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICBbOm15LWVsZW1lbnRdCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAjX1s6bXktZWxlbWVudF1dKQ%3D%3D)
  - [#556](https://github.com/squint-cljs/squint/issues/556): fix referring to var in other namespace via global object in REPL mode
  - Pass `--repl` opts to `watch` subcommand in CLI
  - [#552](https://github.com/squint-cljs/squint/issues/552): fix REPL output with hyphen in ns name
  - Ongoing work on browser REPL. Stay tuned.

- [cherry](https://github.com/squint-cljs/cherry): Experimental ClojureScript to ES6 module compiler
  - Fix referring to vars in other namespaces globally
  - Allow `defclass` to be referenced through other macros, e.g. as `cherry.core/defclass`
  - Fix emitting keyword in HTML
  - [#138](https://github.com/squint-cljs/cherry/issues/138): Support `#html` literals, ported from squint

- [http-client](https://github.com/babashka/http-client): babashka's http-client<br>
  - [#68](https://github.com/babashka/http-client/issues/68) Fix accidental URI path decoding in uri-with-query ([@hxtmdev](https://github.com/hxtmdev))
  - [#71](https://github.com/babashka/http-client/issues/71): Link back to sources in release artifact ([@lread](https://github.com/lread))
  - [#73](https://github.com/babashka/http-client/issues/71): Allow implicit ports when specifying the URL as a map ([@lvh](https://github.com/lvh))

- [http-server](https://github.com/babashka/http-server): serve static assets
  - [#16](https://github.com/babashka/http-server/issues/16): support range requests ([jmglov](https://github.com/jmglov))
  - [#13](https://github.com/babashka/http-server/issues/13): add an ending slash to the dir link, and don't encode the slashes ([@KDr2](https://github.com/KDr2))
  - [#12](https://github.com/babashka/http-server/issues/12): Add headers to index page (rather than just file responses)

- [bbin](https://github.com/babashka/bbin): Install any Babashka script or project with one command<br>
  - Fix #88: bbin ls with 0-length files doesn't crash

- [scittle](https://github.com/babashka/scittle): Execute Clojure(Script) directly from browser script tags via SCI
 - Add `cljs.pprint/code-dispatch` and `cljs.pprint/with-pprint-dispatch`

- [clojurescript](https://github.com/clojure/clojurescript)
  - [Throw when calling ana-api/ns-publics on non-existing ns](https://github.com/clojure/clojurescript/commit/f1fc819da94147411e353ecb17c751af6a18ce2e)

- [neil](https://github.com/babashka/neil): A CLI to add common aliases and features to deps.edn-based projects.<br>
  - [#241](https://github.com/babashka/neil/issues/241): ignore missing deps file (instead of throwing) in `neil new` ([@bobisageek](https://github.com/bobisageek))

- [sci.configs](https://github.com/babashka/sci.configs): A collection of ready to be used SCI configs.
  - Added a configuration for `cljs.spec.alpha` and related namespaces

- [nbb](https://github.com/babashka/nbb): Scripting in Clojure on Node.js using SCI
  - Include `cljs.spec.alpha`, `cljs.spec.gen.alpha`, `cljs.spec.test.alpha`

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


## Other projects

These are (some of the) other projects I'm involved with but little to no activity
happened in the past month.

<details>
<summary>Click for more details</summary>
- [edamame](https://github.com/borkdude/edamame): Configurable EDN/Clojure parser with location metadata
- [quickdoc](https://github.com/borkdude/quickdoc): Quick and minimal API doc generation for Clojure
- [CLI](https://github.com/babashka/cli): Turn Clojure functions into CLIs!<br>
- [fs](https://github.com/babashka/fs) - File system utility library for Clojure
- [tools](https://github.com/borkdude/tools): a set of [bbin](https://github.com/babashka/bbin/) installable scripts
- [sci.nrepl](https://github.com/babashka/sci.nrepl): nREPL server for SCI projects that run in the browser
- [html](https://github.com/borkdude/html): Html generation library inspired by squint's html tag
- [rewrite-edn](https://github.com/borkdude/rewrite-edn): Utility lib on top of
  rewrite-clj with common operations to update EDN while preserving whitespace
  and comments
- [instaparse-bb](https://github.com/babashka/instaparse-bb): Use instaparse from babashka
- [babashka.json](https://github.com/babashka/json): babashka JSON library/adapter
- [tools-deps-native](https://github.com/babashka/tools-deps-native) and [tools.bbuild](https://github.com/babashka/tools.bbuild): use tools.deps directly from babashka
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
- [pod-babashka-go-sqlite3](https://github.com/babashka/pod-babashka-go-sqlite3): A babashka pod for interacting with sqlite3
- [pod-babashka-fswatcher](https://github.com/babashka/pod-babashka-fswatcher): babashka filewatcher pod
- [lein2deps](https://github.com/borkdude/lein2deps): leiningen to deps.edn converter
- [sql pods](https://github.com/babashka/babashka-sql-pods): babashka pods for SQL databases
- [cljs-showcase](https://github.com/borkdude/cljs-showcase): Showcase CLJS libs using SCI
- [babashka.book](https://github.com/babashka/book): Babashka manual
- [rewrite-clj](https://github.com/clj-commons/rewrite-clj): Rewrite Clojure code and edn
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

