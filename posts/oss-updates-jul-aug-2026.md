Title: OSS updates July and August 2026
Date: 2026-09-02
Tags: clojure, oss updates
Description: My Clojure OSS updates for July and August 2026

In this post I'll give updates about open source I worked on during July and August 2026.

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

In the past two months it was summer time in Europe. Due to a couple of
heatwaves, it was the perfect time to spend inside and enjoy my new air
conditioning, while coding ;-).

The first half of July was mostly spent on improving performance and
compatibility of [SCI](https://github.com/babashka/sci) on CLJS. SCI now
JIT-compiles interpreted function bodies to JavaScript at runtime, which closes
a lot of the gap with compiled ClojureScript: a tight numeric loop went from
~175ms to ~7ms, over 20 times faster than the interpreter. Implementing core
protocols on custom types now also works. There's hardly anything you can't do
in SCI that you can do in compiled CLJS. I released new versions of [scittle](https://github.com/babashka/scittle/releases/tag/v0.8.32) and [nbb](https://github.com/babashka/nbb/releases/tag/v1.5.211) that take full advantage of this.

Also in the middle of July, [clj-kondo](https://github.com/clj-kondo/clj-kondo) got a pretty cool enhancement. It infers types of function arguments from how they are used.
E.g. when you write `(defn foo [x] (inc x))` we can infer that `foo` is a function that takes a number. I took this principle as far as I could while preventing false positives.
Of course, clj-kondo supports the latest Clojure 1.13 destructuring changes too.

In second half of July I spent significant time on improving babashka tasks with automatic help and completions, backed by [babashka.cli](https://github.com/babashka/cli).
You can read all about that in this blog post: [Babashka tasks with automatic help and completions](https://blog.michielborkent.nl/babashka-tasks-cli.html)

In August I had the pleasure to give a talk about [Reagami](https://github.com/borkdude/reagami) at [Func Prog Sweden](https://www.meetup.com/func-prog-sweden/events/315394699/). In the talk I give an interactive demo of how to use Reagami in a
Squint project through a REPL. I also went into detail on the algorithm that
powers the fast DOM diffing. While preparing for the talk, I added SSR to Reagami too.
You can view the talk on Youtube:

<iframe width="560" height="315" src="https://www.youtube.com/embed/X0PowSdliXs" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>

The last weeks of August I created [`babashka.ffi`](https://github.com/babashka/ffi), a new namespace in babashka to call C libraries.
See my previous blog post: [Babashka 1.13.220 gets FFI](https://blog.michielborkent.nl/babashka-ffi.html).
To validate the design I wrote four libraries with it:
[babashka.sqlite](https://github.com/babashka/babashka.sqlite),
[babashka.duckdb](https://github.com/babashka/babashka.duckdb),
[babashka.postgres](https://github.com/babashka/babashka.postgres) and
[filewatcher](https://github.com/babashka/filewatcher).
Each one exercised a different corner of the API and none of them forced a change to it.

<img src="assets/1.13.220-pacman.png" style="max-width:420px;width:100%" alt="pac-man running in babashka through babashka.ffi and raylib">

Right now I'm looking forward to give a [babashka
workshop](https://2026.clojure-conj.org/workshops) at the Clojure/conj together
with Rahul Dé. We're still polishing the workshop material behind the scenes and I'm excited to see how it's turning out.
I'm sure it'll be a lot of fun and hope to catch many of you there.

In between all of this, I also worked on [squint](https://github.com/squint-cljs/squint). It now has the proper core protocols so you can plug in your own collections to be used with core functions.
E.g. you can use [Immutable.js](https://squint-cljs.github.io/squint/?src=gzip%3AH4sIAAAAAAAAE42UQW%2FjNhCF7%2FoVD%2BmFBFZKk7YXB2haFHtYNLlsgF4MHxhpHHNNUrKGUu0W%2Fe%2FFkJIdxHHaG4d6Mxp%2BfBwVGJ0zh5e%2BHUJTAGrR026wPWF5tYmx48X1NbGveHNtvR%2BieXb0y0%2FVTfXjFRaGYf1K66K4u8OX%2BXP1aDoYhsHa9hzL2hlm8G6wIcKbrlC0jxSaMh46gvXXj6YrgC8PbbsdZKVKl5ZYemwR1iuo6oUicqS1iH9lbmtroh0pZRiJc8Ioep70Y5Krsm5DNDZwuaXDfdKJamNYVLlkbkOVjT3WEk1DjiKdZL%2B1Q4jUTFWHELH0oivZ%2FkXwWfT7H1%2BpGerc23Ys%2BxRJzTVssFESpj0PtQ5YmrrGmP64hqxT60k7%2F9Y5qqNtw3yeb1JuvyoAQNk11Eh1bPt77HXawxGDCnGDPb7X8%2BpGHyVTF8ceaKYnQZLTMZEkUUtBph32WufWPvsuHuTm3%2FRIsj%2FRqR2ZfqbzeTfYMUtkJedo44Z6EdJuME5uJe3oM3M9WI4X3ZUJnBlMci44zMG%2BcliOPnSYSI4OS8H7DnOws8Mc7EXruJN13AcX7bAXYTfwBk7I%2Fw%2Fu7sTdXeDuzri7S9yfKC7gyT9Tzxvb4fmA0biBzmA%2FUXwP3xs8nA8keHg%2Bzzt4%2BISHP8AzVTNNc6r2H3T4RIcv0OEzOvyKjmpoHVD%2BbL0vgKuvVA8925HcAXUbRurjG1vyJzBFhgmNjEFGbHGcqdU3LgAx6ic8yesLDR5NV%2BGBzEgMGqk%2FxI0NLyDHBOPaQNVVASzTCFB1m%2BY3Xo8BqMn8FZQ3XW524gN5xPGoeqILIm%2B6LMqDotyOp1mRZ21%2BGGlc5NytnlejjAuVR3yl58G0SCfYzxDhD6U8pinnu7%2BXN7hdYXmDH1b%2FaF2o2Tr3b5RJpzXu7hB7ceJULNCfWZbM8W6OLpYqWyx%2F1jiFU7peSeFb3P4L62gMqiQHAAA%3D) with Squint. I'm thinking about lite-weight immutable persistent data structures for squint, but so far I haven't had much need for them, outside of Advent of Code puzzles.

The above was all about making existing projects better. But I also had a few new creative ideas:

- [Choq](https://github.com/squint-cljs/choq): [Cherry](https://github.com/squint-cljs/cherry) hosted on QuickJS, nREPL included.
- [Buzz](https://github.com/borkdude/buzz): web apps with server-side state, on the JVM or babashka, no Node.js. I wrote [tube-pod](https://github.com/borkdude/tube-pod) and [multi-snake](https://github.com/borkdude/multi-snake) with it.
- [Cljbang.el](https://github.com/borkdude/cljbang.el): A Clojure-like language that runs as Emacs Lisp

Here are some highlights per project. See each project's `CHANGELOG.md` for the full list.

- [Babashka](https://github.com/babashka/babashka): native, fast starting Clojure interpreter for scripting.
  - 1.13.220: [`babashka.ffi`](https://github.com/babashka/ffi), calling C functions in shared libraries from babashka and JVM Clojure. See the [guide](https://github.com/babashka/ffi/blob/main/doc/guide.md) and the [release post](https://blog.michielborkent.nl/babashka-ffi.html)
  - 1.13.220: Linux dynamic binaries now require glibc 2.28 and link everything else statically. The install script picks the static binary on musl and on older glibc, and `--static`/`--dynamic` override it
  - 1.13.220: `:exec-args` may sit directly on a task, a task's `:cli` spec adds to the runner-level spec instead of replacing it, an `:exec-fn` task actually runs when another task `:depends` on it, and the options it declares parse for the dependent task and show up under `Inherited options`
  - 1.13.220: `:cmd` may be a symbol naming a var holding the command tree, whose namespace loads on demand
  - 1.13.219: tasks get automatic `--help` and shell completions through `:exec-fn` and `:cmd`. See the [blog post](https://blog.michielborkent.nl/babashka-tasks-cli.html). These keys are experimental and may still change based on feedback
  - SCI call site caching for instance and static methods, constructors and fields: interop calls up to 5x faster
  - Clojure 1.13 map destructuring with `:keys!`, `:syms!`, `:strs!`, `:select`, `:all` and `:defaults`, plus `req!` and `some-vals`
  - [#1321](https://github.com/babashka/babashka/issues/1321): implement `clojure.core/Inst` on records, types and `reify`
  - [#2054](https://github.com/babashka/babashka/issues/2054): a `proxy` of `java.io.Writer` supports one-argument `write` and `append`, so binding `*out*` to it works
  - [#1918](https://github.com/babashka/babashka/issues/1918): fall back to `$HOME` when the OS supplies no home directory, e.g. for LDAP users
  - [#1994](https://github.com/babashka/babashka/issues/1994): fix `:eval` and `:print` options of `clojure.main/repl` being ignored in the interactive REPL ([@jeroenvandijk](https://github.com/jeroenvandijk))
  - Class additions from [@weavejester](https://github.com/weavejester) ([#1985](https://github.com/babashka/babashka/issues/1985), [#1986](https://github.com/babashka/babashka/issues/1986), [#1987](https://github.com/babashka/babashka/issues/1987), [#1988](https://github.com/babashka/babashka/issues/1988)), [@paintparty](https://github.com/paintparty) ([#1982](https://github.com/babashka/babashka/issues/1982)) and [@christoph-frick](https://github.com/christoph-frick) ([#2003](https://github.com/babashka/babashka/issues/2003))
  - Bumps: JLine 4.4.0, GraalVM 25.0.4, http-kit 2.9.0-beta4 which fixes four security advisories, Clojure 1.12.5, hiccup 2.0.0 final, jsoup 1.23.2, selmer 1.13.5
  - [Full changelog](https://github.com/babashka/babashka/blob/master/CHANGELOG.md)

- [babashka.ffi](https://github.com/babashka/ffi): call C functions in shared libraries from Clojure. New library, also usable from JVM Clojure. See the [guide](https://github.com/babashka/ffi/blob/main/doc/guide.md) and the [examples](https://github.com/babashka/ffi/tree/main/examples). The API is experimental

- [babashka.sqlite](https://github.com/babashka/babashka.sqlite): SQLite for babashka through `babashka.ffi`
  - Uses the SQLite shared library that macOS, Linux and Windows already ship, so there is nothing to install
  - `with-conn`, queries, aggregates, transactions, `last-insert-rowid`, interrupt, and `create-function!` for defining a Clojure function callable from SQL
  - CI green on three operating systems

- [babashka.duckdb](https://github.com/babashka/babashka.duckdb): DuckDB for babashka through `babashka.ffi`
  - Query CSV files directly with SQL, results as Clojure data
  - honeysql support, thread safety, prepared statement cleanup

- [babashka.postgres](https://github.com/babashka/babashka.postgres): PostgreSQL for babashka through `babashka.ffi` and libpq
  - `connect`, `close!`, `with-conn`, `query`, `execute!`, `with-transaction`, `in-transaction?`, `cancel!`, `json`, `jsonb`, `version`, `server-version`
  - Vectors map to arrays in both directions, maps map to JSON. Bring your own JSON library through `:read-json` and `:write-json`
  - clj-kondo export with a `with-conn` hook, CI on three operating systems

- [filewatcher](https://github.com/babashka/filewatcher): watch files and directories from babashka
  - Built on `babashka.ffi`: FSEvents on macOS, inotify on Linux, `ReadDirectoryChangesW` on Windows, and polling everywhere
  - The same event types on all three platforms, modeled after [chokidar](https://github.com/paulmillr/chokidar)
  - A watcher keeps the process alive until `close`

- [SCI](https://github.com/babashka/sci): Configurable Clojure/Script interpreter suitable for scripting
  - ClojureScript JIT compilation: interpreted function bodies compile to JavaScript at runtime via `js/Function`, on by default, over 20x faster on a tight numeric loop. Falls back to the interpreter when `eval` is unavailable, and works under `:advanced`. Disable with `js/globalThis.SCI_DISABLE_JIT = true` or `:closure-defines {sci.core/disable-jit true}`
  - Native CLJS protocol support ([#639](https://github.com/babashka/sci/issues/639)): SCI code implements protocols like `ILookup` on `deftype`, `defrecord` and `reify`, and host code calling those methods dispatches into the SCI implementation, under `:advanced` too
  - CLJS: `deftype` and `defrecord` fields are JS accessors on the prototype, and `set!` on a field accepts `^:unsynchronized-mutable` and `^:volatile-mutable` ([#1063](https://github.com/babashka/sci/pull/1063))
  - New `:unrestricted` option on `init` and `eval-string`, scoped to the context it was passed to. BREAKING: `enable-unrestricted-access!` now throws and `sci.impl.unrestrict` is removed, since the old flag was process-global and leaked into nested contexts
  - Support async functions with `:async true` in the attr map of `defn`
  - Call site caching for JVM instance and static methods, constructors and fields
  - Fix [babashka#2030](https://github.com/babashka/babashka/issues/2030): `aset` on a primitive array was reflective and 170x slower than `aset-double`
  - Errors thrown inside a `loop` now report a located stack frame for the `loop` form
  - [Full changelog](https://github.com/babashka/sci/blob/master/CHANGELOG.md)

- [clj-kondo](https://github.com/clj-kondo/clj-kondo): static analyzer and linter for Clojure code that sparks joy.
  - Type checker: infer a parameter's type from how it is used in the body, type the keys and values of destructured maps, flow map types from function return values into destructured bindings, flag keys that are provably nil, and narrow a local guarded by a known predicate
  - Built-in analysis moved to Clojure 1.13.0-alpha4. Param type inference over the core sources grew argument type coverage of `clojure.core` from 23 to 150 vars
  - [#721](https://github.com/clj-kondo/clj-kondo/issues/721): NEW linter `:constant-condition`, on by default, warns on a condition whose truthiness is the same on every run. Replaces `:condition-always-true` and takes over the `cond` catch-all from `:unreachable-code`
  - Clojure 1.13 map destructuring: `:keys!`, `:syms!`, `:strs!`, `:select` and `:defaults`, with required keys reported at call sites, plus the matching syntax errors from CLJ-2961, CLJ-2954, CLJ-2964 and CLJ-2966
  - [#2943](https://github.com/clj-kondo/clj-kondo/issues/2943): when an `:analyze-call` hook rewrites a call, check the arity of the original function but not its parameter types
  - [#2900](https://github.com/clj-kondo/clj-kondo/issues/2900): `:discouraged-var` gets a per-var `:positions` option to limit the warning to call position or value position
  - [#2851](https://github.com/clj-kondo/clj-kondo/issues/2851): NEW linter `:seq-rest`, suggests `(next x)` over `(seq (rest x))`, default `:off` ([@tomdl89](https://github.com/tomdl89))
  - [#1882](https://github.com/clj-kondo/clj-kondo/issues/1882): built-in support for `clojure.test.check.clojure-test/defspec`
  - [#2877](https://github.com/clj-kondo/clj-kondo/issues/2877): warn when `#_` before an unmatched reader conditional discards the next form
  - Vars defined in `comment` forms no longer count for `:shadowed-var`, `:unused-private-var` and `:inline-def`
  - Performance: var usages and bindings are records, hot analyzer and linter functions were split so they stay under the JIT compilation size limit, and more rewrite-clj internals were optimized ([@alexander-yakushev](https://github.com/alexander-yakushev))
  - Fixes from [@jramosg](https://github.com/jramosg), [@subotac](https://github.com/subotac) and [@nikbad](https://github.com/nikbad)
  - The minimum Clojure version to run clj-kondo on the JVM is now 1.11
  - [Full changelog](https://github.com/clj-kondo/clj-kondo/blob/master/CHANGELOG.md)

- [babashka CLI](https://github.com/babashka/cli): Turn Clojure functions into CLIs!
  - [#197](https://github.com/babashka/cli/pull/197): `:positional` spec marker, giving positional args their own `Arguments:` section in help and refusing them as options, plus `:restrict-args` to error on positional args nothing consumed
  - [#219](https://github.com/babashka/cli/issues/219): `:cmd-aliases` gives a command alternative names that dispatch like the command itself
  - Attached short option values like getopt: `-J-Dfoo=bar` binds `"-Dfoo=bar"` and `-p80` binds `80`. Flag letters may precede the valued option in a cluster
  - [#216](https://github.com/babashka/cli/issues/216): an interior hyphen in a cluster of flags is an error instead of silently ending option parsing
  - Help shows the dispatch-level `:spec` under `Inherited options:`, and `format-command-help` accepts `:spec` so a standalone call shows the same options as `dispatch`
  - `dispatch`: the command named on the command line wins over the `:exec-args` of its ancestors
  - Add ordered `:enum` values for validation, help and completion, and `:doc`/`:epilog` as a vector of lines
  - Completion fixes for zsh, fish and nushell, including registering the fish snippet with `--keep-order` so options keep their emitted order
  - [#199](https://github.com/babashka/cli/pull/199): fix a hang on variadic arguments that were not collected
  - Thanks to [@lread](https://github.com/lread) for continued documentation review and maintenance
  - [Full changelog](https://github.com/babashka/cli/blob/master/CHANGELOG.md)

- [Squint](https://github.com/squint-cljs/squint): CLJS _syntax_ to JS compiler
  - A large protocol push, preparing for immutable and persistent collections in `squint.immutable`: `ILookup`, `IAssociative`, `IMap`, `ICounted`, `IKVReduce`, `ICollection`, `IEmptyableCollection`, `IEquiv`, `ISet`, `IStack`, `IIndexed`, `IVector`, `IWriter`, `IPrintWithWriter`, `IHash`, `IEncodeJS`, `IMeta`, `IWithMeta`, `ISeqable`, `IDeref`, `IReset`, `ISwap`, `IWatchable`, `IAtom` and the transient protocols. Core functions dispatch through them on custom types
  - `defrecord`, `record?` and the `IRecord` marker protocol. Records keep their type through `assoc`, degrade to a plain map on `dissoc` of a basis field, and print as `#TypeName{:a 1}`
  - `clojure.set` dispatches through the collection protocols, so results keep the input's type
  - Dozens of core functions now throw or behave exactly like CLJS on edge cases instead of the old loose JS semantics
  - Clojure 1.13 destructuring, and `& {:keys [...]}` destructures a map so both the kwargs and trailing-map call styles work ([#975](https://github.com/squint-cljs/squint/issues/975))
  - `:as-alias` in `ns` `:require`, and `:require-global`/`:refer-global` for globals loaded via a script tag
  - `:squint/compile-time` opt-in for macro and compile-time namespaces. See [doc/compile-time.md](https://github.com/squint-cljs/squint/blob/main/doc/compile-time.md)
  - A `defmacro` is compile-time only and no longer emitted to the runtime module, matching CLJS
  - The CLI reports the file, line and column of a compile error and exits non-zero
  - vite HMR supports `^:dev/after-load` and `^:dev/before-load` hooks like shadow-cljs, and `defmulti` uses `defonce` in REPL mode so a reload keeps its registered methods
  - [#977](https://github.com/squint-cljs/squint/issues/977): `recur` inside `try` no longer emits an illegal `continue`
  - `.indexOf` on a lazy seq uses reference equality like a JS array, keeping `=` out of a bundle that only builds lazy seqs and shrinking a `conj` bundle from 3801 to 2215 bytes
  - Protocol method dispatch uses `Symbol.for`, so pulling in multiple copies of `squint.core` does not break it
  - [Full changelog](https://github.com/squint-cljs/squint/blob/main/CHANGELOG.md)

- [Cherry](https://github.com/squint-cljs/cherry): Experimental ClojureScript to ES6 module compiler
  - Cherry and squint now share the macro scan and lookup, path resolution, CLI implementation, nREPL server and vite plugin
  - CLI: `--help`, argument validation, shell completion, a `watch` command, an `nrepl-server` command, `cherry.edn` instead of `squint.edn`, and compile errors reported with file, line and column
  - A vite plugin with a browser REPL over nREPL and `^:dev/after-load` / `^:dev/before-load` hooks
  - Add `cherry.test`, a `clojure.test`-compatible API requirable as `cljs.test` or `clojure.test`, with a `report` multimethod dispatching like cljs.test
  - Add `reify`, `defmulti`/`defmethod` and `vswap!`. Dynamic vars compile to squint's box scheme, so `set!` and `binding` work across ESM modules
  - `defprotocol` `:extend-via-metadata` impls resolve under the fully qualified method symbol, so Replicant's own test suite passes under cherry
  - Fix `deftype` implementing cljs.core protocols such as `Inst`, `IIterable` and `IAtom`, whose marker properties were Closure-renamed in the precompiled core. The externs list and the protocol set are now generated and the build fails on drift
  - [#190](https://github.com/squint-cljs/cherry/issues/190): share `PROTOCOL_SENTINEL` with coexisting CLJS runtimes in the same JS realm
  - Fix emitted import specifiers on Windows
  - [Full changelog](https://github.com/squint-cljs/cherry/blob/main/CHANGELOG.md)

- [Choq](https://github.com/squint-cljs/choq): a ~5MB binary running the cherry compiler on embedded QuickJS
  - New project. Runs [cherry](https://github.com/squint-cljs/cherry) inside [quickjs-ng](https://github.com/quickjs-ng/quickjs) via [rquickjs](https://github.com/DelSkayn/rquickjs)
  - No JIT, so hot code is slower than Node.js, Bun or Deno, but the binary is small, startup is fast and memory use stays low. A Hono app serves around 30k requests per second locally, using less memory than the same app on Node.js or Bun
  - An install script for macOS, Linux and Windows, and dev release binaries
  - Clojure git and Maven deps, a module table covering `url` and `util`, `@babashka/fs`, and a test runner
  - Experimental

- [Buzz](https://github.com/borkdude/buzz): write a web application with the JVM or babashka only
  - New project. Server state is watched and updated from client code. The UI compiles through [squint](https://github.com/squint-cljs/squint) and renders with [Reagami](https://github.com/borkdude/reagami), so no ClojureScript toolchain and no Node.js
  - Rendering is asynchronous by default and coalesces at 20ms, and a failing render is contained to its own connection
  - Examples: a whiteboard, a tap viewer, and a Datalevin browser with a CodeMirror query editor
  - Highly experimental, the API will change

- [tube-pod](https://github.com/borkdude/tube-pod): turn YouTube videos into a private podcast
  - New project, written with Buzz. Add a link in the browser, tube-pod downloads the audio with `yt-dlp`, writes an RSS feed and serves both
  - Rsyncs the audio and the feed to a remote after each change, since a laptop is asleep when you want to listen

- [multi-snake](https://github.com/borkdude/multi-snake): snake for as many players as show up
  - New project, written with Buzz. Everyone plays on one board, in one world, held in one atom on the server
  - Runs at [multi-snake.michielborkent.nl](https://multi-snake.michielborkent.nl)

- [Reagami](https://github.com/borkdude/reagami): A minimal zero-deps Reagent-like for Squint and CLJS
  - Add `reagami.ssr`, rendering hiccup to an HTML string on the JVM, babashka, squint and CLJS. `reagami.core/render` hydrates a server-rendered page by adopting the existing DOM instead of clearing the root
  - Add [create-reagami-app](https://github.com/borkdude/reagami/tree/main/create-reagami-app): `npm create reagami-app my-app` scaffolds a Vite project with hot reload and a browser nREPL
  - **Breaking**: `:on-render` takes a map, `(fn [{:keys [node lifecycle state save]}])`, and the hook chooses its own state through `save` instead of returning it
  - Move reordered nodes with `moveBefore` where the browser has it, so a moved subtree keeps its iframe state, animations, focus and selection ([#54](https://github.com/borkdude/reagami/issues/54))
  - Custom element support: `value`, `checked`, `selected` and `disabled` are set as attributes on a tag with a hyphen, custom events reach the element through `addEventListener`, and the same rule applies in SSR. Includes a [web component example](https://github.com/borkdude/reagami/tree/main/examples/web-component)
  - Fix a memory leak with `:on-render` nodes, and performance work on the vdom
  - I gave a talk about Reagami at [Func Prog Sweden](https://www.youtube.com/watch?v=X0PowSdliXs)

- [cljbang](https://github.com/borkdude/cljbang.el): a Clojure-like language that runs as Emacs Lisp
  - Compiles Clojure forms to Emacs Lisp forms and evaluates them in the running Emacs. No subprocess and no transpiled text, following the same approach as squint
  - Namespaces with per-namespace aliases, multiple arities in `fn` and `defn`, `loop`/`recur` with a tail position check, `try`/`throw`/`ex-info`, `case`, atoms, syntax quote including nesting, `&form` and `&env` in macros, regex and set literals, `#_`, `edn/read-string`, `slurp` and `spit`
  - `el!` for calling Emacs Lisp names that are not valid Clojure symbols

- [nbb](https://github.com/babashka/nbb): Scripting in Clojure on Node.js using SCI
  - Ships the SCI ClojureScript JIT, so loops, numerical code and JS interop are much faster
  - Ships [babashka.fs](https://github.com/babashka/fs) as a built-in library, matching babashka
  - Support implementing CLJS protocols such as `ILookup` on `deftype` and `defrecord`, which also makes [editscript](https://github.com/juji-io/editscript) work
  - [#416](https://github.com/babashka/nbb/issues/416): fix `prn` in nREPL
  - SCI is fairly complete on CLJS now, so existing CLJS libraries should mostly run under nbb. If you find one that does not, the challenge is welcome in [#nbb](https://app.slack.com/client/T03RZGPFR/C029PTWD3HR)

- [Scittle](https://github.com/babashka/scittle): Execute Clojure(Script) directly from browser script tags via SCI
  - Ships the SCI ClojureScript JIT
  - Added the [helitorus demo](https://babashka.org/scittle/helitorus.html) to show the difference
  - Bump reagent to 1.2.0, re-frame to 1.4.7, replicant to 2026.06.2 and shadow-cljs to 3.4.11

- [squint-inline](https://github.com/squint-cljs/squint-inline): write squint functions and inline expressions in a ClojureScript project
  - New project. Squint operates on JavaScript objects and arrays, so `assoc`, `update-in` and `select-keys` work on those without `js->clj` and `clj->js`
  - Squint core is tree-shaken through `:js-provider :import`, and each function's tree-shaken size is recorded
  - Squint functions can call each other across namespaces, and JS module references work inside squint bodies

- [Edamame](https://github.com/borkdude/edamame): configurable EDN and Clojure parser with location metadata and more
  - Speed up parsing by holding parse context in record fields instead of the extmap: ~10% faster on the JVM, ~4% on ClojureScript
  - With `:auto-resolve-ns`, respect `:refer` plus rename, qualify syntax-quoted imported classes with the full classname, and leave method, constructor and dotted symbols as-is, matching Clojure
  - Do not resolve function literal params in a syntax quote
  - ClojureDart: parse zero literals correctly, and make plain readers non-indexing to match tools.reader ([#144](https://github.com/borkdude/edamame/pull/144))

- [fs](https://github.com/babashka/fs): file system utility library for Clojure
  - Released 0.5.34, which ships the Node.js support mentioned in the previous update as the `@babashka/fs` npm package

- [http-client](https://github.com/babashka/http-client): HTTP client for Clojure and babashka
  - [#80](https://github.com/babashka/http-client/issues/80): `:proxy` accepts a function of the request URI, to select a proxy per request ([@jeeger](https://github.com/jeeger))

- [http-server](https://github.com/babashka/http-server): serve static assets
  - Range requests: inclusive `Content-Range` last-pos per RFC 9110, suffix ranges (`bytes=-N`, previously a 500), clamping last-pos beyond EOF, reading the full range, and a test suite ([@slagyr](https://github.com/slagyr))

- [Cream](https://github.com/borkdude/cream): Clojure + GraalVM [Crema](https://github.com/oracle/graal/issues/11327) native binary
  - Reduced the core.async virtual thread memory corruption I reported [upstream](https://github.com/oracle/graal/issues/13925) to a pure Java repro. GraalVM 25.0.3-ea.04 fixes it, and the pipeline test is back on now that the compile NPE is gone too
  - Enable the Ristretto JIT for runtime-loaded bytecode, and update the benchmarks for it
  - Clojure code runs without a JDK present, with the boot class loader warning suppressed
  - Pick up `pom.xml` when there is no `deps.edn`, and recompile Java sources when a dependency changed

- [graaljs-cherry](https://github.com/borkdude/graaljs-cherry): a native-image cherry REPL on GraalJS
  - New prototype. Compiles cherry expressions on the JVM and evaluates the resulting JS in an embedded GraalJS context
  - Two variants: a default Truffle JIT build, and a 49MB `--small` build without it

- [clj-kondo-browser](https://github.com/borkdude/clj-kondo-browser): a static Clojure source browser built from clj-kondo analysis
  - New prototype. Renders a codebase as a static HTML page where every symbol links to its definition and usages, scope aware, so a local is linked only within its scope
  - Runs clj-kondo as a pod and gets the classpath from [deps.clj](https://github.com/borkdude/deps.clj)

- [deps.clj](https://github.com/borkdude/deps.clj): a faithful port of the clojure CLI bash script to Clojure
  - As always, catching up with the most recent Clojure CLI versions

- [lein-clj-kondo](https://github.com/clj-kondo/lein-clj-kondo) and [clj-kondo-bb](https://github.com/clj-kondo/clj-kondo-bb): released alongside each clj-kondo release

Contributions to third party projects:

- [Clerk](https://github.com/nextjournal/clerk): Moldable Live Programming for Clojure. Bumped SCI through 0.15.58 and cherry, and used the cherry notebook to show the JIT timing a tight loop and to show `:async` functions ([#821](https://github.com/nextjournal/clerk/issues/821) for the dead notebook links)
- [Replicant](https://github.com/cjohansen/replicant): added a `test:cherry` task so Replicant's suite runs under cherry as well as squint, and moved `replicant.dom` back to `.cljs`
- [Joyride](https://github.com/BetterThanTomorrow/joyride): updated SCI to 0.15.56, bringing the ClojureScript JIT to VS Code scripting
- [nextjournal/markdown](https://github.com/nextjournal/markdown): default renderers for HTML nodes now point at the README instead of reporting an unknown node type ([#69](https://github.com/nextjournal/markdown/issues/69))
- [crustimoney](https://github.com/aroemers/crustimoney): added babashka support
- [grasp](https://github.com/borkdude/grasp): babashka compatibility ([#34](https://github.com/borkdude/grasp/pull/34))

## Other projects

These are (some of the) other projects I'm involved with but little to no activity
happened in the past two months.

<details>
<summary>Click for more details</summary>

- [quickblog](https://github.com/borkdude/quickblog): light-weight static blog engine for Clojure and babashka
- [quickdoc](https://github.com/borkdude/quickdoc): Quick and minimal API doc generation for Clojure
- [neil](https://github.com/babashka/neil): A CLI to add common aliases and features to deps.edn-based projects
- [pod-babashka-go-sqlite3](https://github.com/babashka/pod-babashka-go-sqlite3): A babashka pod for interacting with sqlite3
- [pod-babashka-gozxing](https://github.com/babashka/pod-babashka-gozxing): a babashka pod for QR code and barcode decoding/encoding
- [unused-deps](https://github.com/borkdude/unused-deps): Find unused deps in a clojure project
- [pod-babashka-fswatcher](https://github.com/babashka/pod-babashka-fswatcher): babashka filewatcher pod
- [sci.nrepl](https://github.com/babashka/sci.nrepl): nREPL server for SCI projects that run in the browser
- [babashka.nrepl-client](https://github.com/babashka/nrepl-client)
- [sci.configs](https://github.com/babashka/sci.configs): A collection of ready to be used SCI configs.
- [rewrite-edn](https://github.com/borkdude/rewrite-edn): Utility lib on top of rewrite-clj
- [rewrite-clj](https://github.com/clj-commons/rewrite-clj): Rewrite Clojure code and edn
- [tools-deps-native](https://github.com/babashka/tools-deps-native) and [tools.bbuild](https://github.com/babashka/tools.bbuild): use tools.deps directly from babashka
- [bbin](https://github.com/babashka/bbin): Install any Babashka script or project with one command
- [graal-build-time](https://github.com/clj-easy/graal-build-time): initialize Clojure classes at build time for GraalVM native-image
- [html](https://github.com/borkdude/html): Html generation library inspired by squint's html tag
- [qualify-methods](https://github.com/borkdude/qualify-methods): experimental tool to rewrite instance calls to use fully qualified methods (Clojure 1.12 only)
- [tools](https://github.com/borkdude/tools): a set of [bbin](https://github.com/babashka/bbin/) installable scripts
- [babashka.json](https://github.com/babashka/json): babashka JSON library/adapter
- [speculative](https://github.com/borkdude/speculative)
- [squint-macros](https://github.com/squint-cljs/squint-macros): a couple of
  macros that stand-in for
  [applied-science/js-interop](https://github.com/applied-science/js-interop)
  and [promesa](https://github.com/funcool/promesa) to make CLJS projects
  compatible with squint and/or cherry.
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
- [clj2el](https://borkdude.github.io/clj2el/): transpile Clojure to elisp
- [deflet](https://github.com/borkdude/deflet): make let-expressions REPL-friendly!
- [deps.add-lib](https://github.com/borkdude/deps.add-lib): Clojure 1.12's add-lib feature for leiningen and/or other environments without a specific version of the clojure CLI
- [process](https://github.com/babashka/process): Clojure library for shelling out / spawning sub-processes
- [parmezan](https://github.com/borkdude/parmezan): fixes unbalanced or unexpected parens or other delimiters in Clojure files

</details>
