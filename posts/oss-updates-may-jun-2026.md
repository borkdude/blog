Title: OSS updates May and June 2026
Date: 2026-07-06
Tags: clojure, oss updates
Description: My Clojure OSS updates for May and June 2026
Preview: true

In this post I'll give updates about open source I worked on during May and June 2026.

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
you can sponsor this work in the following ways. If you work for a company that
uses my OSS, please ask your employer, that would be even better. Thank you!

- [Github Sponsors](https://github.com/sponsors/borkdude)
- The [Babashka](https://opencollective.com/babashka) or [Clj-kondo](https://opencollective.com/clj-kondo) OpenCollective
- [Ko-fi](https://ko-fi.com/borkdude)
- [Patreon](https://www.patreon.com/borkdude)
- [Clojurists Together](https://www.clojuriststogether.org/)

</details>

## Updates

A lot happened in the past two months! Not just coding but also...

### Babashka Conf 2026 and Dutch Clojure Days

Three years after the initial installment, [Babashka Conf 2026](https://babashka.org/conf/) happened on May 8th at the OBA Oosterdok library in Amsterdam, with David Nolen, primary maintainer of ClojureScript, as our keynote speaker. Thanks to our sponsors Nubank, Exoscale, [Bob](https://github.com/bobisageek), [Flexiana](https://flexiana.com) and [Itonomi](https://itonomi.com), to Wendy Randolph for hosting, and to all the speakers, volunteers and attendees who made it such an inspiring day. You can watch all the videos [here](https://www.youtube.com/watch?v=c5RTAtodh3M&list=PLaN-rC-CjQqCClhXmwzE7XhGWbrbB-A7u). Thanks to Ray for recording!
The day after, [Dutch Clojure Days 2026](https://clojuredays.org/) rounded out a full weekend of Clojure in Amsterdam, where I did a presentation about [ClojureScript and async/await](https://clojurescript.org/reference/async-functions). The video of that is hopefully coming soon.

<img src="assets/babashka-conf-2026.jpg" width="70%" align="center" alt="Babashka Conf 2026 speakers and organizers">

<em>From left to right: David Nolen, Jen Myers, Adrian Smith, Josh Glover, Rahul Dé, Arne Brasseur, Christoph Neumann, Timo Kramer, Jynn Nelson, Wendy Randolph.</em>

### Upcoming: babashka workshop at the Clojure/conj

I'm pleased to announce that Rahul Dé and I will be hosting a babashka workshop at the [Clojure Conj 2026](https://2026.clojure-conj.org/workshops). The workshop will showcase various use cases of babashka. This hands-on workshop covers the whole lifecycle of a babashka tool, from a quick script to a published, installable CLI app. We assume you know the basics of Clojure and won't explain the language itself. Topics include:

- Setting up your dev environment
- Managing projects with babashka tasks (`bb.edn`)
- A tour of built-in libraries (fs, process, http-client, and more)
- Writing and running tests
- Building a CLI with subcommands and automatic help
- Programming a terminal UI (TUI)
- Producing a small web app
- Publishing via GitHub or as an installable tool with `bbin`

Every concept comes with an exercise, building toward one culminating CLI app. There will be lots of interaction and fun!

### Blog posts

Besides this update I published two blog posts in the past two months:

- [Babashka CLI: automatic `--help` and shell completions](https://blog.michielborkent.nl/babashka-cli-help-and-completions.html)
- [Finding transitive var usages with clj-kondo](https://blog.michielborkent.nl/clj-kondo-call-graph-metadata.html)

and a ClojureScript reference on async functions:

- [Async functions](https://clojurescript.org/reference/async-functions)

### Projects

Babashka CLI got the most attention this cycle. I added automatic `--help` generation for `dispatch`-based CLIs and shell tab completion for bash, zsh, fish, PowerShell and Nushell. There's a dedicated post with a "build your own git" walkthrough linked above. I also made Babashka CLI [Squint](https://github.com/squint-cljs/squint) compatible, so CLIs built with it run on Node.js and in the browser, published as the `@babashka/cli` npm package. Also ClojureDart support for Babashka CLI got added.

Squint saw a large amount of work that kept going right into early July: a [browser nREPL](https://github.com/squint-cljs/squint#browser-repl), dynamic vars and `binding` that survive across separately-compiled ESM modules, an EDN reader, cached lazy seqs, `defrecord` and a wide set of core protocols, and a big compatibility push to make it pass jank's clojure-test-suite. [Replicant](https://github.com/squint-cljs/squint/tree/main/examples/replicant) now runs on Squint too. I added key diffing to [Reagami](https://github.com/borkdude/reagami) and did some [benchmarks](https://github.com/borkdude/reagami#benchmarks), showing that Reagami on squint performs in the ballpark of React. The benchmark also shows that Replicant on Squint performs even a tad better than on ClojureScript. Not that this makes a huge difference in practice, but it's nice to validate the idea that Squint, for typical apps, can be a valid CLJS replacement while not giving up that much in terms of Clojure features.

A security issue in SCI deserves a callout. A string type-hint could bypass the `:classes` allowlist and statically initialize any class on the classpath at analysis time. If you sandbox untrusted code with SCI, upgrade to 0.13.53. ClojureDart support and fine-grained interop control (which was needed for cljd support since it has no reflection) also got added. You can now make REPLs for your mobile apps!

Since porting was a theme these past months, I'll mention another one: [babashka.fs](https://github.com/babashka/fs) now runs on Node.js via ClojureScript and squint, published as the `@babashka/fs` npm package.

Here are some highlights per project. See each project's `CHANGELOG.md` for the full list.

- [babashka CLI](https://github.com/babashka/cli): Turn Clojure functions into CLIs!
  - Automatic `--help` generation for `dispatch` CLIs, plus shell completions for bash, zsh, fish, PowerShell and Nushell ([#112](https://github.com/babashka/cli/issues/112), [#24](https://github.com/babashka/cli/issues/24), [#95](https://github.com/babashka/cli/pull/95)). I wrote a full post on it with a "write your own git" walkthrough: [babashka CLI: automatic --help and shell completions](https://blog.michielborkent.nl/babashka-cli-help-and-completions.html)
  - Exposed the underlying building blocks so you can roll your own custom CLI parsing: `parse-opts*`, `coerce-opts`, `validate-opts`, `apply-defaults`, `table->tree`
  - `dispatch` now accepts a tree directly (as returned by `table->tree`), and subcommand order is preserved in printed help and completions
  - Squint support and a new `@babashka/cli` npm package
  - ClojureDart support ([#182](https://github.com/babashka/cli/issues/182))
  - `opts->table` accepts `:columns` to override the auto-detected columns ([#148](https://github.com/babashka/cli/issues/148), thanks Jan Seeger)
  - Better error messages: negation errors now name the base option, `--no-foo` on a non-boolean option errors instead of silently coercing, and `:edn` `:coerce` now requires an explicit value ([#166](https://github.com/babashka/cli/issues/166), [#174](https://github.com/babashka/cli/issues/174))
  - Thanks to [@lread](https://github.com/lread) for a lot of documentation review and general maintenance during this cycle
  - [Full changelog](https://github.com/babashka/cli/blob/master/CHANGELOG.md)

- [Squint](https://github.com/squint-cljs/squint): CLJS _syntax_ to JS compiler
  - Browser nREPL support landed, followed by a number of REPL/nREPL fixes: [#815](https://github.com/squint-cljs/squint/issues/815) (`str` wrapping tripping esbuild), [#819](https://github.com/squint-cljs/squint/issues/819) (macro changes not picked up in watch mode), [#820](https://github.com/squint-cljs/squint/issues/820) (`:macros` option ignored from JS callers) and [#832](https://github.com/squint-cljs/squint/issues/832) (nREPL server hanging on advertised-but-unimplemented ops)
  - The CLI now gets its `--help`, usage and error handling from babashka.cli's `dispatch`, plus shell tab completion
  - Dynamic vars and `binding` now work via a mutable box, safe across separately-compiled ESM modules; syntax-quote resolves symbols through the current namespace and aliases like Clojure. `defprotocol` got `:extend-via-metadata` support.
  - `reify` added
  - `clojure.walk` added
  - Added `squint.edn`/`clojure.edn` with a ~300-line EDN reader
  - Printing is now done through `*print-fn*`, `print`, `pr` and `with-out-str`, like CLJS
  - Lazy seqs are now cached instead of recomputed on every consumption, matching CLJS's chunked-seq behavior
  - A big push for compatibility with jank's clojure-test-suite: dozens of core functions (`sorted-map`, `hash-map`, `subvec`, `pop`, `merge`, `keys`/`vals`, `peek`, transducers, `=` on dates/regexes/lazy seqs, and more) now throw or behave exactly like CLJS instead of the old loose JS semantics, alongside full built-in `cljs.test` support
  - [#771](https://github.com/squint-cljs/squint/issues/771): dead-code elimination for varargs/multi-arity functions, now emitted via `...` spread
  - Replicant support landed, with an example
  - Added `defrecord`, `record?` and the `IRecord` marker protocol. Records store their fields as own string-keyed properties and implement the map-facing protocols, so keyword lookup, `keys`, `seq`, `assoc`, `conj` and `=` all work through the regular core functions; the generated implementations are shared runtime functions imported only by files that use `defrecord`
  - Added a large set of core protocols so custom types participate in the standard functions: `ILookup`, `IAssociative`, `IMap`, `ICounted`, `ICollection`, `IEquiv`, `ISet`, the transient protocols, and `IAtom`/`IDeref`/`IReset`/`ISwap`/`IWatchable` (so a reagent-style reactive atom can be a plain `deftype`)
  - Compile-time namespace resolution: `cljs.analyzer.api/resolve` now sees vars of built-in library namespaces like `clojure.string`, plus `:squint/compile-time` forms and fixes for macro self-use
  - [Full changelog](https://github.com/squint-cljs/squint/blob/main/CHANGELOG.md)

- [clj-kondo](https://github.com/clj-kondo/clj-kondo): static analyzer and linter for Clojure code that sparks joy.
  - NEW: macros from source. A `defmacro` (plus any supporting `defn`/`defn-`/`def`) tagged with `{:clj-kondo/macroexpand-hook true}` is automatically extracted into `.clj-kondo/` and registered as a `:macroexpand` hook on the next run. See [doc/hooks.md](https://github.com/clj-kondo/clj-kondo/blob/master/doc/hooks.md#macros-from-source)
  - Support for `async`/`await` in ClojureScript: bumped built-in CLJS analysis to 1.12.145 and added the `:await-without-async-fn` and `:misplaced-async-metadata` linters
  - [#2822](https://github.com/clj-kondo/clj-kondo/issues/2822): NEW linter `:alias-same-as-ns`, warns when an alias equals the namespace it aliases (default `:off`) ([@tomdl89](https://github.com/tomdl89))
  - [#2807](https://github.com/clj-kondo/clj-kondo/issues/2807): NEW linter `:conditional-build-up`, warns on successive `(if pred (assoc m ...) m)` rebinding and suggests `cond->` (default `:off`) ([@walber-araujo](https://github.com/walber-araujo))
  - [#2062](https://github.com/clj-kondo/clj-kondo/issues/2062): NEW linter `:if-x-x-y`, suggests `(or x y)` instead of `(if x x y)` (default `:off`) ([@jramosg](https://github.com/jramosg))
  - [#2818](https://github.com/clj-kondo/clj-kondo/issues/2818): fix `:redefined-var` false positive across files declaring the same namespace
  - [#2814](https://github.com/clj-kondo/clj-kondo/issues/2814): fix `:protocol-method-arity-mismatch` false positive for `definterface` declaring the same method with multiple arities ([@jramosg](https://github.com/jramosg))
  - [#2817](https://github.com/clj-kondo/clj-kondo/issues/2817): warn on `recur` inside a vector, map or set literal, since `recur` is never in tail position there
  - [#2854](https://github.com/clj-kondo/clj-kondo/issues/2854): fix `:invalid-arity` false positive when an inner binding or fn param shadows a local function name ([@yuhan0](https://github.com/yuhan0))
  - Performance work on the rewrite-clj parser and analysis internals: efficient `get-in`/`select-keys`, faster `sexpr`, leaner node allocation ([@alexander-yakushev](https://github.com/alexander-yakushev))
  - Deprecation notice: 2026.05.25 is the last release to include the clj-kondo LSP server and VS Code extension; use [clojure-lsp](https://clojure-lsp.io/) instead, which embeds clj-kondo
  - Queued for the next release: early support for the Clojure 1.13 map destructuring keys (`:keys!`/`:syms!`/`:strs!`), including inferring required keys and reporting them at call sites ([#2870](https://github.com/clj-kondo/clj-kondo/pull/2870))
  - [Full changelog](https://github.com/clj-kondo/clj-kondo/blob/master/CHANGELOG.md)

- [SCI](https://github.com/babashka/sci): Configurable Clojure/Script interpreter suitable for scripting
  - ClojureDart support, with a [Flutter REPL example](https://github.com/babashka/sci/tree/master/examples/cljd-flutter-repl)
  - Instance/static method and field overrides plus a `:closed` allowlist for `:classes`, giving fine-grained control over host interop; see the [interop control docs](https://github.com/babashka/sci/blob/master/doc/interop-control.md). Also 1.6x faster instance-method interop on babashka
  - Security fix (sandbox escape): a string type-hint (e.g. `^"some.Class" x`) bypassed the `:classes` allowlist, loading and static-initializing any class on the classpath at analysis time. Only affects sandboxing of untrusted code via `:classes`; upgrade to 0.13.53
  - Add an `:interrupt-fn` option: a zero-arg function called on every interpreted fn entry, so host code can interrupt or cancel a running SCI eval (thanks [@whilo](https://github.com/whilo))
  - Add `sci.interrupt/interrupt!` to throw an interrupt that sandboxed `try`/`catch` cannot catch, and gate `finally` and the regex functions (`re-matches`/`re-find`/`re-seq`, JVM) through `:interrupt-fn` too, closing off ways to mask an interrupt and escape the sandbox [#1044](https://github.com/babashka/sci/issues/1044)
  - Fix `copy-var` incorrectly marking a function as inlined when its unqualified name collided with a `clojure.core`/`cljs.core` inlined var (e.g. a custom `get`), silently breaking `with-redefs` ([@verberktstan](https://github.com/verberktstan))
  - Fix cross-namespace `defrecord`/`deftype` type symbol resolution via alias (e.g. `(instance? r/Foo x)`), fixing [nbb#410](https://github.com/babashka/nbb/issues/410)
  - Fix a self-require (a namespace requiring itself) being reported as a cyclic load dependency
  - [Full changelog](https://github.com/babashka/sci/blob/master/CHANGELOG.md)

- [fs](https://github.com/babashka/fs): file system utility library for Clojure
  - Released 0.5.34 with Node.js support ([#265](https://github.com/babashka/fs/issues/265)): fs now runs on Node.js via ClojureScript and Squint / JavaScript, published as the `@babashka/fs` npm package. Most functions are supported. The JVM behavior is the reference implementation so all operations are synchronous, and the glob syntax is reimplemented from scratch to match the JVM. File times are BigInt nanoseconds to preserve sub-millisecond precision. `zip` is left out since Node.js has no native support for it
  - Added `spit` and `slurp` on both the JVM and Node.js
  - `exec-paths` returns `[]` when `PATH` is unset or blank instead of throwing
  - [@lread](https://github.com/lread) did a thorough review pass making the return values of `copy`, `copy-tree`, `delete-tree`, `zip`/`unzip`, `gunzip` and the setters explicit and documented/tested ([#197](https://github.com/babashka/fs/issues/197))

- [Babashka](https://github.com/babashka/babashka): native, fast starting Clojure interpreter for scripting.
  - Working towards a new release integrating all the newest updates in Babashka CLI and babashka.fs. Most importantly I'm working on autocompletions added for tasks defined in `bb.edn`.
  - [#1979](https://github.com/babashka/babashka/issues/1979): fix `with-redefs` on copied vars (e.g. `org.httpkit.client/get`) incorrectly treated as inlined
  - Add `org.jline.keymap.BindingReader` for reading key bindings in terminal applications, completing the input side of the bundled JLine API
  - [#1982](https://github.com/babashka/babashka/issues/1982): add `clojure.lang.ChunkedCons`, `clojure.lang.APersistentVector$SubVector`, `clojure.lang.ArraySeq`, `clojure.lang.PersistentVector$ChunkedSeq`, `java.util.AbstractCollection` and `java.util.Queue` to `:instance-checks` ([@paintparty](https://github.com/paintparty))
  - Added a terminal tetris example (`examples/tetris.clj`) built on JLine's `Display` and `AttributedString`, showing off the new terminal APIs
  - [Full changelog](https://github.com/babashka/babashka/blob/master/CHANGELOG.md)

- [Reagami](https://github.com/borkdude/reagami): A minimal zero-deps Reagent-like for Squint and CLJS
  - Added keyed reconciliation ([#40](https://github.com/borkdude/reagami/issues/40)): support `:key` on children for stable node identity, so diffing reuses nodes instead of recreating them
  - Fixed CLJS `:lite-mode` compatibility and added it to CI ([#41](https://github.com/borkdude/reagami/issues/41))
  - Added a benchmarks page comparing reagami against CLJS React wrappers and React-free solutions, with mermaid charts to visualize the results ([#42](https://github.com/borkdude/reagami/issues/42), [#43](https://github.com/borkdude/reagami/issues/43))
  - Expanded the README with an ADR on the unkeyed reconciliation algorithm

- [Cream](https://github.com/borkdude/cream): Clojure + GraalVM [Crema](https://github.com/oracle/graal/issues/11327) native binary
  - I was finally able to reproduce an issue with core.async and filed this [upstream](https://github.com/oracle/graal/issues/13925)
  - Once this is fixed I'm going to consider crema more seriously and play with the thought that this could be a substrate for "Babashka next".

- [html](https://github.com/borkdude/html): Html generation library inspired by squint's html tag
  - Fixed inline `style` maps emitting a literal `\n` between declarations via `pr-str`, which produced invalid CSS and dropped every declaration after the first ([@cycl1st](https://github.com/cycl1st))
  - Only render a map attribute value as CSS when the key is `style`; other map-like values (e.g. records) now render via `str` ([@telekid](https://github.com/telekid))
  - Fixed a symbol-valued attribute resolving to its runtime value instead of its literal name

- [Edamame](https://github.com/borkdude/edamame): configurable EDN and Clojure parser with location metadata and more
  - Added [ClojureDart](https://github.com/tensegritics/ClojureDart) support (non-indexing plain readers matching tools.reader, zero-literal parsing fix, and more)
  - With `:auto-resolve-ns`, bare syntax-quoted symbols now resolve to the current namespace, matching Clojure's behavior

- [Neil](https://github.com/babashka/neil): A CLI to add common aliases and features to deps.edn-based projects
  - [#261](https://github.com/babashka/neil/issues/261): `neil dep upgrade` now upgrades unstable deps (e.g. release candidates) to a newer unstable version when no newer stable version exists
  - Added a README note on `brew trust` for users who installed neil before Homebrew introduced tap trust
  - The next neil version will make use of the new Babashka CLI features which is already prepared in a PR

- [Nbb](https://github.com/babashka/nbb): Scripting in Clojure on Node.js using SCI
  - [#410](https://github.com/babashka/nbb/issues/410): fixed a regression, introduced by the async/await work in [#408](https://github.com/babashka/nbb/issues/408), where a `defrecord`/`deftype` type symbol referenced through a namespace alias (e.g. `(instance? r/Foo x)`) failed to resolve

- [deps.clj](https://github.com/borkdude/deps.clj): a faithful port of the clojure CLI bash script to Clojure
  - As always, catching up with the most recent Clojure CLI versions

- [Pod-babashka-gozxing](https://github.com/babashka/pod-babashka-gozxing): a babashka pod for QR code and barcode decoding/encoding, backed by [gozxing](https://github.com/makiuchi-d/gozxing)
  - Initial release 0.0.1, installable via the pod registry

- [Graal-build-time](https://github.com/clj-easy/graal-build-time): initialize Clojure classes at build time for GraalVM native-image
  - [#55](https://github.com/clj-easy/graal-build-time/pull/55): munge package names for namespaces with special characters

Contributions to third party projects:

- [ClojureScript](https://github.com/clojure/clojurescript): documented the `async`/`await` support from last cycle on the ClojureScript site, including an enhanced reference ([#423](https://github.com/clojure/clojurescript-site/pull/423), [#424](https://github.com/clojure/clojurescript-site/pull/424))
- [Nexus](https://github.com/cjohansen/nexus): a data-driven state management library by Christian Johansen. I ported the core engine and test suite to run under squint and added a cljs test runner alongside the existing kaocha setup, so both babashka and squint stay covered in CI ([#15](https://github.com/cjohansen/nexus/pull/15), [#16](https://github.com/cjohansen/nexus/pull/16), merged)
- [Replicant](https://github.com/cjohansen/replicant): a data-driven DOM rendering library by Christian Johansen. I made Replicant itself run under Squint (converting `dom.cljs` to `.cljc`, adjusting `core.cljc` for portability), added babashka/squint test runners and wired them into CI, and fixed a multi-root render bug under squint by switching DOM state tracking to a node-map ([#71](https://github.com/cjohansen/replicant/pull/71), [#72](https://github.com/cjohansen/replicant/pull/72), merged)

## Other projects

These are (some of the) other projects I'm involved with but little to no activity
happened in the past two months.

<details>
<summary>Click for more details</summary>

- [scittle](https://github.com/babashka/scittle): Execute Clojure(Script) directly from browser script tags via SCI
- [cherry](https://github.com/squint-cljs/cherry): Experimental ClojureScript to ES6 module compiler
- [quickblog](https://github.com/borkdude/quickblog): light-weight static blog engine for Clojure and babashka
- [quickdoc](https://github.com/borkdude/quickdoc): Quick and minimal API doc generation for Clojure
- [clerk](https://github.com/nextjournal/clerk): Moldable Live Programming for Clojure
- [pod-babashka-go-sqlite3](https://github.com/babashka/pod-babashka-go-sqlite3): A babashka pod for interacting with sqlite3
- [unused-deps](https://github.com/borkdude/unused-deps): Find unused deps in a clojure project
- [pod-babashka-fswatcher](https://github.com/babashka/pod-babashka-fswatcher): babashka filewatcher pod
- [sci.nrepl](https://github.com/babashka/sci.nrepl): nREPL server for SCI projects that run in the browser
- [babashka.nrepl-client](https://github.com/babashka/nrepl-client)
- [http-server](https://github.com/babashka/http-server): serve static assets
- [sci.configs](https://github.com/babashka/sci.configs): A collection of ready to be used SCI configs.
- [rewrite-edn](https://github.com/borkdude/rewrite-edn): Utility lib on top of rewrite-clj
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
- [lein-clj-kondo](https://github.com/clj-kondo/lein-clj-kondo): a leiningen plugin for clj-kondo
- [http-kit](https://github.com/http-kit/http-kit): Simple, high-performance event-driven HTTP client+server for Clojure.
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
- [process](https://github.com/babashka/process): Clojure library for shelling out / spawning sub-processes
- [parmezan](https://github.com/borkdude/parmezan): fixes unbalanced or unexpected parens or other delimiters in Clojure files

</details>
