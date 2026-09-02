Title: OSS updates July and August 2026
Date: 2026-09-02
Tags: clojure, oss updates
Description: My Clojure OSS updates for July and August 2026

In this post I'll give updates about open source I worked on during July and August 2026.

To see previous OSS updates, go [here](https://blog.michielborkent.nl/tags/oss-updates.html).

## Sponsors

I'd like to thank all the sponsors and contributors who make this work
possible. Without you, the projects below would not be as mature or would not
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

- [GitHub Sponsors](https://github.com/sponsors/borkdude)
- The [Babashka](https://opencollective.com/babashka) or [Clj-kondo](https://opencollective.com/clj-kondo) OpenCollective
- [Ko-fi](https://ko-fi.com/borkdude)
- [Patreon](https://www.patreon.com/borkdude)
- [Clojurists Together](https://www.clojuriststogether.org/)

</details>


## Updates

In the past two months it was summertime in Europe. Due to a couple of
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

In the second half of July I spent significant time on improving babashka tasks with automatic help and completions, backed by [babashka.cli](https://github.com/babashka/cli).
You can read all about that in this blog post: [Babashka tasks with automatic help and completions](https://blog.michielborkent.nl/babashka-tasks-cli.html)

In August I had the pleasure of giving a talk about [Reagami](https://github.com/borkdude/reagami) at [Func Prog Sweden](https://www.meetup.com/func-prog-sweden/events/315394699/). In the talk I gave an interactive demo of how to use Reagami in a
Squint project through a REPL. I also went into detail on the algorithm that
powers the fast DOM diffing. While preparing for the talk, I added SSR to Reagami too.
You can view the talk on YouTube:

<iframe width="560" height="315" src="https://www.youtube.com/embed/X0PowSdliXs" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>

The last few weeks of August I created [`babashka.ffi`](https://github.com/babashka/ffi), a new namespace in babashka to call C libraries.
See my previous blog post: [Babashka 1.13.220 gets FFI](https://blog.michielborkent.nl/babashka-ffi.html).
To validate the design I wrote four libraries with it:
[babashka.sqlite](https://github.com/babashka/babashka.sqlite),
[babashka.duckdb](https://github.com/babashka/babashka.duckdb),
[babashka.postgres](https://github.com/babashka/babashka.postgres) and
[filewatcher](https://github.com/babashka/filewatcher).
Each one exercised a different corner of the API, along with some examples based on raylib. PacMan is particularly cool:

<img src="assets/1.13.220-pacman.png" style="max-width:420px;width:100%" alt="pac-man running in babashka through babashka.ffi and raylib">

Right now I'm looking forward to giving a [babashka
workshop](https://2026.clojure-conj.org/workshops) at the Clojure/conj together
with Rahul Dé. We're still polishing the workshop material behind the scenes and I'm excited to see how it's turning out.
I'm sure it'll be a lot of fun and hope to catch many of you there.

In between all of this, I also worked on [squint](https://github.com/squint-cljs/squint). It now supports the core protocols, so you can plug in your own collections and use them with core functions.
E.g. you can use [Immutable.js](https://squint-cljs.github.io/squint/?src=gzip%3AH4sIAAAAAAAAE42UQW%2FjNhCF7%2FoVD%2BmFBFZKk7YXB2haFHtYNLlsgF4MHxhpHHNNUrKGUu0W%2Fe%2FFkJIdxHHaG4d6Mxp%2BfBwVGJ0zh5e%2BHUJTAGrR026wPWF5tYmx48X1NbGveHNtvR%2BieXb0y0%2FVTfXjFRaGYf1K66K4u8OX%2BXP1aDoYhsHa9hzL2hlm8G6wIcKbrlC0jxSaMh46gvXXj6YrgC8PbbsdZKVKl5ZYemwR1iuo6oUicqS1iH9lbmtroh0pZRiJc8Ioep70Y5Krsm5DNDZwuaXDfdKJamNYVLlkbkOVjT3WEk1DjiKdZL%2B1Q4jUTFWHELH0oivZ%2FkXwWfT7H1%2BpGerc23Ys%2BxRJzTVssFESpj0PtQ5YmrrGmP64hqxT60k7%2F9Y5qqNtw3yeb1JuvyoAQNk11Eh1bPt77HXawxGDCnGDPb7X8%2BpGHyVTF8ceaKYnQZLTMZEkUUtBph32WufWPvsuHuTm3%2FRIsj%2FRqR2ZfqbzeTfYMUtkJedo44Z6EdJuME5uJe3oM3M9WI4X3ZUJnBlMci44zMG%2BcliOPnSYSI4OS8H7DnOws8Mc7EXruJN13AcX7bAXYTfwBk7I%2Fw%2Fu7sTdXeDuzri7S9yfKC7gyT9Tzxvb4fmA0biBzmA%2FUXwP3xs8nA8keHg%2Bzzt4%2BISHP8AzVTNNc6r2H3T4RIcv0OEzOvyKjmpoHVD%2BbL0vgKuvVA8925HcAXUbRurjG1vyJzBFhgmNjEFGbHGcqdU3LgAx6ic8yesLDR5NV%2BGBzEgMGqk%2FxI0NLyDHBOPaQNVVASzTCFB1m%2BY3Xo8BqMn8FZQ3XW524gN5xPGoeqILIm%2B6LMqDotyOp1mRZ21%2BGGlc5NytnlejjAuVR3yl58G0SCfYzxDhD6U8pinnu7%2BXN7hdYXmDH1b%2FaF2o2Tr3b5RJpzXu7hB7ceJULNCfWZbM8W6OLpYqWyx%2F1jiFU7peSeFb3P4L62gMqiQHAAA%3D) with Squint. I'm thinking about lightweight immutable persistent data structures for squint, but so far I haven't had much need for them, outside of Advent of Code puzzles.

The above was all about making existing projects better. But I also had a few new creative ideas:

- [Choq](https://github.com/squint-cljs/choq): [Cherry](https://github.com/squint-cljs/cherry) hosted on QuickJS, nREPL included.
- [Buzz](https://github.com/borkdude/buzz): web apps with server-side state, on the JVM or babashka, no Node.js. I wrote [tube-pod](https://github.com/borkdude/tube-pod) and [multi-snake](https://github.com/borkdude/multi-snake) with it.
- [Cljbang.el](https://github.com/borkdude/cljbang.el): A Clojure-like language that runs as Emacs Lisp

Here are some highlights per project. See each project's `CHANGELOG.md` for the full list.

- [Babashka](https://github.com/babashka/babashka): native, fast-starting Clojure interpreter for scripting.
  - 1.13.220: Add experimental [`babashka.ffi`](https://github.com/babashka/ffi): call C functions in shared libraries straight from babashka and JVM Clojure! See the [guide](https://github.com/babashka/ffi/blob/main/doc/guide.md)
  - 1.13.220: On Linux, the install script installs the dynamic binary by default. It installs the static binary on musl systems and on systems with glibc older than 2.17. The `--static` and `--dynamic` options override the automatic selection
  - 1.13.220: `:exec-args` can sit directly on a task, not only under `:cli`, the way `(exec ...)` already reads it. Before, it was ignored on an `:exec-fn` or `:cmd` task
  - 1.13.220: A task's `:cli` spec adds to the runner-level `:tasks {:cli {:spec ...}}` instead of replacing it. An option from the runner level keeps its coercion and default, and `--help` lists it under `Inherited options`
  - 1.13.220: A task with `:exec-fn` runs when another task `:depends` on it. Before, it did nothing
  - 1.13.220: Options declared by an `:exec-fn` task named in `:depends` also parse for the CLI task that runs, with their coercion and default. `--help` lists them under `Inherited options`
  - 1.13.220: `:cmd` can be a symbol naming a var that holds the command tree, like `:cli`. Its namespace loads on demand
  - 1.13.220: Shell completion offers inherited options (via `:depends`) too
  - 1.13.220: SCI: call site caching for instance and static methods, constructors and fields. Interop calls are up to 5x faster
  - 1.13.219: Tasks get automatic `--help` and shell completions, through the new `:exec-fn` and `:cmd` keys. See [the blog post](https://blog.michielborkent.nl/babashka-tasks-cli.html)! These task keys should be considered experimental and may change in a future version of babashka, depending on feedback from the community
  - 1.13.219: Clojure 1.13 map destructuring: `:keys!`, `:syms!`, `:strs!`, `&` inside a directive, `:select`, `:all` and `:defaults`. Adds `req!` and `some-vals` to `clojure.core`
  - [#1321](https://github.com/babashka/babashka/issues/1321): support implementing the `clojure.core/Inst` protocol on records, types and reify, and with `extend-protocol` and `extend-type`
  - [#2054](https://github.com/babashka/babashka/issues/2054): a `proxy` of `java.io.Writer` supports the one-argument `write` and `append`, so binding `*out*` to it works
  - [#1918](https://github.com/babashka/babashka/issues/1918): fall back to `$HOME` when the OS does not supply a home directory, e.g. for LDAP users in the static binary
  - [#1994](https://github.com/babashka/babashka/issues/1994): fix `:eval` and `:print` options of `clojure.main/repl` being ignored in the interactive REPL ([@jeroenvandijk](https://github.com/jeroenvandijk))
  - Bump jline to 4.4.0: security hardening, a rewritten signal path for the FFM terminal, Kitty keyboard protocol
  - [#2021](https://github.com/babashka/babashka/issues/2021): bump http-kit to 2.9.0-beta4, which fixes four security advisories
  - Class additions by [@weavejester](https://github.com/weavejester) ([#1985](https://github.com/babashka/babashka/issues/1985), [#1986](https://github.com/babashka/babashka/issues/1986), [#1987](https://github.com/babashka/babashka/issues/1987), [#1988](https://github.com/babashka/babashka/issues/1988)), [@paintparty](https://github.com/paintparty) ([#1982](https://github.com/babashka/babashka/issues/1982)) and [@christoph-frick](https://github.com/christoph-frick) ([#2003](https://github.com/babashka/babashka/issues/2003))
  - [Full changelog](https://github.com/babashka/babashka/blob/master/CHANGELOG.md)

- [babashka.ffi](https://github.com/babashka/ffi): call C functions in shared libraries from Clojure. New library, also usable from JVM Clojure. See the [guide](https://github.com/babashka/ffi/blob/main/doc/guide.md) and the [examples](https://github.com/babashka/ffi/tree/main/examples). The API is experimental

- [babashka.sqlite](https://github.com/babashka/babashka.sqlite): SQLite for babashka through `babashka.ffi`
  - Uses the SQLite shared library that macOS, Linux and Windows already ship with, so there is nothing to install
  - `with-conn`, queries, aggregates, transactions, `last-insert-rowid`, interrupt, and `create-function!` for defining a Clojure function callable from SQL
  - CI green on three operating systems

- [babashka.duckdb](https://github.com/babashka/babashka.duckdb): DuckDB for babashka through `babashka.ffi`
  - Query CSV files directly with SQL, results as Clojure data
  - HoneySQL support, thread safety, prepared statement cleanup

- [babashka.postgres](https://github.com/babashka/babashka.postgres): PostgreSQL for babashka through `babashka.ffi` and libpq
  - `connect`, `close!`, `with-conn`, `query`, `execute!`, `with-transaction`, `in-transaction?`, `cancel!`, `json`, `jsonb`, `version`, `server-version`
  - Vectors map to arrays in both directions, maps map to JSON. Bring your own JSON library through `:read-json` and `:write-json`
  - clj-kondo export with a `with-conn` hook, CI on three operating systems

- [filewatcher](https://github.com/babashka/filewatcher): watch files and directories from babashka
  - Built on `babashka.ffi`: FSEvents on macOS, inotify on Linux, `ReadDirectoryChangesW` on Windows, and polling everywhere
  - The same event types on all three platforms, modeled after [chokidar](https://github.com/paulmillr/chokidar)
  - A watcher keeps the process alive until `close`

- [SCI](https://github.com/babashka/sci): Configurable Clojure/Script interpreter suitable for scripting
  - ClojureScript JIT compilation. SCI on CLJS compiles interpreted function bodies to JavaScript at runtime via `js/Function`. This is enabled by default and needs no configuration. When JIT is enabled, loops and numerical computations become much faster (and, in unrestricted contexts, JS interop too)
  - When `eval` is unavailable (e.g. under a Content Security Policy) SCI falls back to the interpreter. Results, error messages and error locations should be identical. And of course, it works under `:advanced` compilation
  - You can turn JIT off at runtime with `js/globalThis.SCI_DISABLE_JIT = true` before loading SCI, or in your Google Closure compile settings with `:closure-defines {sci.core/disable-jit true}`
  - More CLJS JIT performance improvements. Up to 20x on arithmetic-dense code for >2 arity. Keyword lookups, `instance?` and js globals no longer fall back to the interpreter
  - ClojureScript: native protocol support ([#639](https://github.com/babashka/sci/issues/639)). SCI code can implement CLJS protocols on `deftype`, `defrecord` and `reify`, and host code calling protocol methods on such instances dispatches into the sci implementations. Works under `:advanced` compilation
  - [#1063](https://github.com/babashka/sci/pull/1063): CLJS: `deftype` and `defrecord` fields are JS accessors on the type's prototype: `(.-field x)` works on instances, `(set! (.-field x) v)` mutates deftype fields
  - New `:unrestricted` option on `init` and `eval-string`: when `true`, evaluated code may mutate built-in vars and CLJS instance interop skips `:classes` checks. The option applies only to the context it was passed to
  - BREAKING: `enable-unrestricted-access!` now throws. Use the `:unrestricted` option instead. The old function set a process-global flag that leaked into nested contexts
  - Support async functions by adding `:async true` in the attr map of `defn`
  - Caches resolved JVM instance methods per call site for performance
  - Fix [babashka#2030](https://github.com/babashka/babashka/issues/2030): `aset` on a primitive array was reflective and 170x slower than `aset-double`
  - Errors thrown inside a `loop` now report a located stack frame for the `loop` form instead of a frame without location (all platforms, including babashka)
  - [Full changelog](https://github.com/babashka/sci/blob/master/CHANGELOG.md)

- [clj-kondo](https://github.com/clj-kondo/clj-kondo): static analyzer and linter for Clojure code that sparks joy.
  - Type checker: infer the type of a function param from how it is used in the body. E.g. `(defn f [s] (subs s 1)) (f 42)` will warn, since the evidence `(subs s 1)` tells us that `s` should be a string.
  - Type checker: infer the value type of a destructured map key from how it is used in the body. E.g. `(defn f [{:keys [x]}] (inc x)) (f {:x "foo"})` will warn. A key whose use rejects nil and that has no `:or` default is required.
  - Type checker: a destructured binding gets the value type of its key when the map's type is known, including through function return maps. E.g. `(defn cfg [] {:port "8080"}) (let [{:keys [port]} (cfg)] (inc port))` will warn.
  - Type checker: a key missing from a map literal is provably nil, also through destructuring, keyword access chains and function return maps. E.g. `(inc (:y {}))` will warn.
  - Type checker: narrow the type of a local in the then-branch of `if` or the body of `when` when it is guarded by a known predicate. E.g. `(if (string? x) (inc x) ...)` will warn.
  - Built-in analysis now uses Clojure 1.13.0-alpha4. Param type inference over the core sources grows the arg type coverage of `clojure.core` from 23 to 150 vars. E.g. `(interleave 1 [2])` and `(mod "a" 2)` will warn.
  - [#721](https://github.com/clj-kondo/clj-kondo/issues/721): NEW linter: `:constant-condition`: warn on a condition whose truthiness is the same on every run. On by default. Replaces `:condition-always-true`, whose config and ignores still apply to always-true conditions, and takes over the `cond` catch-all warning from `:unreachable-code`
  - Clojure 1.13 CLJ-2961: infer required keys from `:keys!`, `:syms!` and `:strs!` and report them at call sites
  - [#2874](https://github.com/clj-kondo/clj-kondo/issues/2874): Clojure 1.13 CLJ-2964: support `:select` in map destructuring. The bound map's keys are known to the type checker
  - Clojure 1.13 CLJ-2966: support `:defaults` in map destructuring, error when used without `:or`
  - [#2943](https://github.com/clj-kondo/clj-kondo/issues/2943): Type checker: when an `:analyze-call` hook rewrites a call, clj-kondo checks the arity of the original function but not its parameter types.
  - [#2900](https://github.com/clj-kondo/clj-kondo/issues/2900): `:discouraged-var`: new per-var `:positions` option (a set or vector of `:call` and/or `:value`) to limit the warning to call position or value position. A var passed to a higher-order function such as `map` counts as `:value`.
  - [#2851](https://github.com/clj-kondo/clj-kondo/issues/2851): NEW linter: `:seq-rest`: suggest using `(next x)` over `(seq (rest x))`. Defaults to `:off` ([@tomdl89](https://github.com/tomdl89))
  - [#1882](https://github.com/clj-kondo/clj-kondo/issues/1882): built-in support for `clojure.test.check.clojure-test/defspec`
  - [#2877](https://github.com/clj-kondo/clj-kondo/issues/2877): warn when `#_` before an unmatched reader conditional discards the next form. E.g. `[#_#?(:cljs 1) 2]` reads as `[]` in `:clj` and will warn.
  - Vars defined in `comment` forms no longer count for `:shadowed-var`, `:unused-private-var` and `:inline-def`.
  - Performance: use a record for var usages: 13.5% less allocation, ~5-10% faster linting. More performance work by [@alexander-yakushev](https://github.com/alexander-yakushev)
  - The minimum Clojure version to run clj-kondo on the JVM is now `1.11`.
  - [Full changelog](https://github.com/clj-kondo/clj-kondo/blob/master/CHANGELOG.md)

- [babashka CLI](https://github.com/babashka/cli): Turn Clojure functions into CLIs!
  - [#197](https://github.com/babashka/cli/pull/197): [`:positional`](https://github.com/babashka/cli#positional) spec marker: positional args get their own `Arguments:` help section and may not be passed as options
  - [#197](https://github.com/babashka/cli/pull/197): [`:restrict-args`](https://github.com/babashka/cli#restrict-args): error on positional args not consumed by `:args->opts`
  - [#219](https://github.com/babashka/cli/issues/219): `:cmd-aliases` on a table entry or tree node gives a command one or more alternative names.
  - A short option that declares a non-boolean `:coerce` takes the rest of its token as its value, like getopt: `-J-Dfoo=bar` binds `"-Dfoo=bar"`, `-p80` binds `80`. Flag letters may precede the valued option in a cluster: with `:b` a flag and `:a` valued, `-ba x` parses as `-b -a x`
  - [#216](https://github.com/babashka/cli/issues/216): in a cluster of flags, where no letter takes a value, an interior hyphen is an error instead of silently ending option parsing.
  - Help: show the dispatch-level `:spec` options under `Inherited options:`. The parser always accepted these options, but help did not show them
  - Help: `format-command-help` accepts `:spec`, the dispatch-level spec, so a standalone call shows the same options as `dispatch`
  - `dispatch`: the command named on the command line wins over the `:exec-args` of its ancestors. A value the user typed at an ancestor level still wins over both
  - Add ordered `:enum` values for validation, help and completion
  - Support `:doc` and `:epilog` as a vector of lines, joined with newlines
  - [#198](https://github.com/babashka/cli/pull/198): `:cmd` may be a [vector of `[name command]` pairs](https://github.com/babashka/cli#command-formats), preserving command order without `:cmd-order`
  - [#199](https://github.com/babashka/cli/pull/199): fix hang on variadic arguments that weren't "collected" (e.g. `(repeat :k)`)
  - [#203](https://github.com/babashka/cli/pull/203): `parse-opts*` resolves `:spec` so its `:coerce`/`:collect` entries steer parsing like in `parse-opts`
  - Completion: the fish snippet registers with `--keep-order`, so fish offers options in the order they are emitted, long option before its short alias, rather than sorting short options first
  - zsh completion: offer a command's options without typing a dash first, by opting the registered program names out of zsh's `prefix-needed` style
  - Thanks to [@lread](https://github.com/lread) for continued documentation review and maintenance
  - [Full changelog](https://github.com/babashka/cli/blob/master/CHANGELOG.md)

- [Squint](https://github.com/squint-cljs/squint): CLJS _syntax_ to JS compiler
  - Preparatory release before adding immutable + persistent collections in `squint.immutable`. Added a lot of protocols and made sure core functions work properly with them
  - Add the `ILookup`, `IAssociative`, `IMap`, `ICounted`, `IKVReduce`, `ICollection`, `IEmptyableCollection` and `IEquiv` protocols. `get`, `assoc`, `contains?`, `find`, `dissoc`, `count`, `reduce-kv`, `conj`, `empty` and `=` dispatch to them on custom types. Plain objects and arrays keep their fast paths
  - Add the `IStack`, `IIndexed`, `IVector`, `IWriter` and `IPrintWithWriter` protocols, `write-all`, and an `ITransientVector` `-pop!` slot; `nth`, `peek`, `pop`, `pop!`, `subvec`, `vec`, `vector?`, `sequential?`, `set?`, `map?`, `seq`, `=` and printing dispatch to custom collection types
  - Add `equiv`, `hash`, `hash-ordered-coll`, `hash-unordered-coll` and the `IHash` protocol. `hash` follows `equiv`: plain mutable objects and arrays hash by reference
  - Add the `IMeta` and `IWithMeta` protocols; `meta` and `with-meta` dispatch through them and the internal meta symbol property is gone
  - `clojure.set` dispatches through the collection protocols: results keep the input's type, membership tests against a protocol set are value-based, and `rename-keys`/`map-invert` no longer mutate a record
  - Add `defrecord`, `record?` and the `IRecord` marker protocol. Records store their fields as own string-keyed properties and implement the map-facing protocols, so keyword lookup, `keys`, `seq`, `assoc`, `conj` and `=` work through the regular core functions. `assoc` keeps the record type, `dissoc` of a basis field gives a plain map, printing gives `#TypeName{:a 1}`
  - Clojure 1.13 destructuring: `:keys!`/`:syms!`/`:strs!` for required keys, `&` inside them for keys required but not bound, `:select`, `:all`, `:defaults`, and `:or` by key
  - Fix [#975](https://github.com/squint-cljs/squint/issues/975): `& {:keys [...]}` now destructures a map instead of the raw rest args, and a seq destructured as a map is read as kwargs
  - Fix [#977](https://github.com/squint-cljs/squint/issues/977): `recur` inside `try` no longer emits an illegal `continue`
  - Support `:as-alias` in `ns` `:require` like CLJS: no runtime import, only a compile-time alias so a namespaced keyword such as `::alias/x` resolves
  - Add `:require-global` and `:refer-global` to `ns`, binding globals loaded via a script tag to consts without emitting an import
  - Add `:squint/compile-time` opt-in mechanism for macro/compile-time namespaces. See [doc/compile-time.md](https://github.com/squint-cljs/squint/blob/main/doc/compile-time.md)
  - A `defmacro` is compile-time only: no longer emitted to the runtime module, and `:refer`ing a macro no longer emits a runtime import for it, matching CLJS
  - The CLI reports the file, line and column of a compile error and exits non-zero, instead of dumping the raw exception
  - Fix [#957](https://github.com/squint-cljs/squint/issues/957): vite HMR: support `^:dev/after-load` + `^:dev/before-load` hooks similar to shadow-cljs
  - `.indexOf` on a lazy seq now uses reference equality like a JS array, not value equality. This diverges from CLJS but keeps `=` out of any bundle that only builds lazy seqs, shrinking a `conj` bundle from 3801 to 2215 bytes
  - Use `Symbol.for` for protocol method dispatch, so pulling in multiple copies of squint.core (e.g. via http://esm.sh/) does not break protocol dispatch
  - [Full changelog](https://github.com/squint-cljs/squint/blob/main/CHANGELOG.md)

- [Cherry](https://github.com/squint-cljs/cherry): Experimental ClojureScript to ES6 module compiler
  - Add `cherry.test` with `clojure.test`-compatible testing API, requirable as `cljs.test` or `clojure.test`
  - `cherry.test/report` is a multimethod dispatching on `[*current-reporter* type]` like cljs.test, so reporting can be extended with `defmethod`
  - Add a vite plugin with browser REPL over nREPL and `^:dev/after-load` / `^:dev/before-load` hot-reload hooks, sharing squint's implementation: `import cherry from 'cherry-cljs/vite.js'`
  - Add `reify`, `defmulti`/`defmethod` and the `vswap!` macro. `#'foo` emits foo's value, like squint
  - Dynamic vars compile to squint's box scheme, so `set!` and `binding` work across ESM modules. cljs.core dynamic vars are exported as accessor boxes proxying the real var
  - `defprotocol` `:extend-via-metadata` impls resolve under the fully qualified method symbol, so replicant's mutation-log renderer works: replicant's own test suite passes under cherry
  - Fix `deftype` implementing cljs.core protocols such as `Inst`, `IIterable` and `IAtom`: their marker properties were Closure-renamed in the precompiled core and missing from the emitter's core protocol set. The externs list and the set are now generated from cljs.core's protocols (`bb gen-externs`) and the build fails on drift
  - Fix [#190](https://github.com/squint-cljs/cherry/issues/190): share `PROTOCOL_SENTINEL` with coexisting CLJS runtimes in the same JS realm
  - Share the macro scan and macro lookup with squint. Namespaces flagged `{:squint/compile-time true}` load only their compile-time part into the macro environment, like squint
  - CLI: `--help`/`-h`, argument validation and error messages via babashka.cli's `dispatch`, like squint. Adds `watch` and `nrepl-server` commands, shell tab completion, and reads options from `cherry.edn` instead of `squint.edn`
  - Fix emitted import specifiers on Windows: backslashes are normalized via the path resolution now shared with squint
  - [Full changelog](https://github.com/squint-cljs/cherry/blob/main/CHANGELOG.md)

- [Choq](https://github.com/squint-cljs/choq): a ~5 MB binary running the cherry compiler on embedded QuickJS
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
  - Add `reagami.ssr` to render hiccup to an HTML string on the JVM, Babashka, Squint and CLJS. See [Server-side rendering](https://github.com/borkdude/reagami#server-side-rendering)
  - `reagami.core/render` (the regular render function) now hydrates a server-rendered page. It adopts the existing DOM instead of clearing the root
  - Add [create-reagami-app](https://github.com/borkdude/reagami/tree/main/create-reagami-app). Run `npm create reagami-app my-app` to create a Vite project with hot reload and a browser nREPL
  - **Breaking**: `:on-render` now takes a map: `(fn [{:keys [node lifecycle state save]}])`. Call `save` with a value to keep it for the next call, and read it back as `state`. In previous versions, the hook took three arguments and its return value became the state
  - Move reordered nodes with `moveBefore` where the browser has it, so a moved subtree keeps its iframe state, animations, focus and selection ([#54](https://github.com/borkdude/reagami/issues/54))
  - Set `value`, `checked`, `selected` and `disabled` on a tag with a hyphen as attributes, not as JS properties. A custom element observes attributes, so a property never had any effect. Native elements still handle them as properties
  - Custom events, e.g. `:on-rated`, now reach the element through `addEventListener`, because a browser only wires an `on*` property for standard events
  - Add [web component example](https://github.com/borkdude/reagami/tree/main/examples/web-component). A `<todo-list>` custom element, used from Squint, from JavaScript with and without Reagami
  - Fix memory leak with `:on-render` nodes and other `:on-render` improvements
  - I gave a talk about Reagami at [Func Prog Sweden](https://www.youtube.com/watch?v=X0PowSdliXs)

- [cljbang](https://github.com/borkdude/cljbang.el): a Clojure-like language that runs as Emacs Lisp
  - Compiles Clojure forms to Emacs Lisp forms and evaluates them in the running Emacs. No subprocess and no transpiled text, following the same approach as squint
  - Namespaces with per-namespace aliases, multiple arities in `fn` and `defn`, `loop`/`recur` with a tail position check, `try`/`throw`/`ex-info`, `case`, atoms, syntax quote including nesting, `&form` and `&env` in macros, regex and set literals, `#_`, `edn/read-string`, `slurp` and `spit`
  - `el!` for calling Emacs Lisp names that are not valid Clojure symbols

- [nbb](https://github.com/babashka/nbb): Scripting in Clojure on Node.js using SCI
  - ClojureScript JIT compilation. Nbb now bundles a SCI that compiles interpreted function bodies to JavaScript at runtime via `js/Function`. This is enabled by default. This makes loops, numerical computations and JS interop much faster
  - Nbb now ships [babashka.fs](https://github.com/babashka/fs) as a built-in library. The full file system API (`glob`, `copy`, `move`, `create-dirs`, `delete-tree`, `with-temp-dir`, path helpers and more) is available via `(require '[babashka.fs :as fs])`, matching Babashka
  - Support implementing CLJS protocols (e.g. `ILookup`, etc) on `deftype` and `defrecord`
  - Support [editscript](https://github.com/juji-io/editscript): CLJS `deftype`/`defrecord` field interop, `set!` on `^:unsynchronized-mutable` fields, add `cljs.core` type classes like `PersistentHashMap`, `write-all` and `goog.math.Long`
  - [#416](https://github.com/babashka/nbb/issues/416): Fix problem with `prn` in nREPL
  - SCI now covers most CLJS capabilities, so nbb should run existing CLJS libraries unless they rely on very specific macros that require the JVM. If you have anything that does not run, please report it in [#nbb](https://app.slack.com/client/T03RZGPFR/C029PTWD3HR)!

- [Scittle](https://github.com/babashka/scittle): Execute Clojure(Script) directly from browser script tags via SCI
  - ClojureScript JIT compilation. Scittle now bundles a SCI that compiles interpreted function bodies to JavaScript at runtime via `js/Function`. This is enabled by default
  - Include [helitorus demo](https://babashka.org/scittle/helitorus.html) to show improved JIT
  - Bump `reagent` to 1.2.0, `re-frame` to 1.4.7, `replicant` to 2026.06.2 and `shadow-cljs` to 3.4.11

- [squint-inline](https://github.com/squint-cljs/squint-inline): write squint functions and inline expressions in a ClojureScript project
  - New project. Squint operates on JavaScript objects and arrays, so `assoc`, `update-in` and `select-keys` work on those without `js->clj` and `clj->js`
  - Squint core is tree-shaken through `:js-provider :import`, and each function's tree-shaken size is recorded
  - Squint functions can call each other across namespaces, and JS module references work inside squint bodies

- [Edamame](https://github.com/borkdude/edamame): configurable EDN and Clojure parser with location metadata and more
  - Speed up parsing by holding parse context in record fields instead of the extmap: ~10% faster on JVM, ~4% on ClojureScript
  - Respect `:refer` + rename in `:auto-resolve-ns`
  - With `:auto-resolve-ns`, qualify syntax-quoted imported classes (e.g. `` `Date `` with `(:import [java.util Date])`) with the full classname
  - With `:auto-resolve-ns`, leave method, constructor and dotted syntax-quoted symbols (`` `.toString ``, `` `Bar. ``, `` `foo.bar ``) as-is, matching Clojure
  - Do not resolve function literal params in a syntax quote
  - ClojureDart: fix parsing zero literals and make plain readers non-indexing, matching tools.reader ([#144](https://github.com/borkdude/edamame/pull/144))

- [fs](https://github.com/babashka/fs): file system utility library for Clojure
  - Released 0.5.34, which ships the Node.js support mentioned in the previous update as the `@babashka/fs` npm package

- [http-client](https://github.com/babashka/http-client): HTTP client for Clojure and babashka
  - [#80](https://github.com/babashka/http-client/issues/80): accept a function of the request URI in `:proxy` to select a proxy per request ([@jeeger](https://github.com/jeeger))

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
  - New prototype. Renders a codebase as a static HTML page where every symbol links to its definition and usages, scope-aware, so a local is linked only within its scope
  - Runs clj-kondo as a pod and gets the classpath from [deps.clj](https://github.com/borkdude/deps.clj)

- [grasp](https://github.com/borkdude/grasp): Grep Clojure code using clojure.spec regexes
  - Babashka compatibility ([#34](https://github.com/borkdude/grasp/pull/34))

- [deps.clj](https://github.com/borkdude/deps.clj): a faithful port of the Clojure CLI Bash script to Clojure
  - As always, catching up with the most recent Clojure CLI versions

- [lein-clj-kondo](https://github.com/clj-kondo/lein-clj-kondo) and [clj-kondo-bb](https://github.com/clj-kondo/clj-kondo-bb): released alongside each clj-kondo release

<!-- Contributions to third party projects: nothing big enough to list this
     cycle. Fill this in next time. What was there and got cut: clerk (SCI
     and cherry bumps), replicant (a test:cherry task), joyride (SCI bump),
     nextjournal/markdown (#69), crustimoney (babashka support). -->

## Other projects

These are some other projects I'm involved with, but little to no activity
happened in the past two months.

<details>
<summary>Click for more details</summary>

- [quickblog](https://github.com/borkdude/quickblog): lightweight static blog engine for Clojure and babashka
- [quickdoc](https://github.com/borkdude/quickdoc): Quick and minimal API doc generation for Clojure
- [neil](https://github.com/babashka/neil): A CLI to add common aliases and features to deps.edn-based projects
- [pod-babashka-go-sqlite3](https://github.com/babashka/pod-babashka-go-sqlite3): A babashka pod for interacting with sqlite3
- [pod-babashka-gozxing](https://github.com/babashka/pod-babashka-gozxing): a babashka pod for QR code and barcode decoding/encoding
- [unused-deps](https://github.com/borkdude/unused-deps): Find unused deps in a clojure project
- [pod-babashka-fswatcher](https://github.com/babashka/pod-babashka-fswatcher): babashka filewatcher pod
- [sci.nrepl](https://github.com/babashka/sci.nrepl): nREPL server for SCI projects that run in the browser
- [babashka.nrepl-client](https://github.com/babashka/nrepl-client)
- [sci.configs](https://github.com/babashka/sci.configs): A collection of ready-to-use SCI configs.
- [rewrite-edn](https://github.com/borkdude/rewrite-edn): Utility lib on top of rewrite-clj
- [rewrite-clj](https://github.com/clj-commons/rewrite-clj): Rewrite Clojure code and edn
- [tools-deps-native](https://github.com/babashka/tools-deps-native) and [tools.bbuild](https://github.com/babashka/tools.bbuild): use tools.deps directly from babashka
- [bbin](https://github.com/babashka/bbin): Install any Babashka script or project with one command
- [graal-build-time](https://github.com/clj-easy/graal-build-time): initialize Clojure classes at build time for GraalVM native-image
- [html](https://github.com/borkdude/html): HTML generation library inspired by squint's HTML tag
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
- [pod-babashka-buddy](https://github.com/babashka/pod-babashka-buddy): A pod around buddy core (Cryptographic API for Clojure).
- [gh-release-artifact](https://github.com/borkdude/gh-release-artifact): Upload artifacts to GitHub releases idempotently
- [carve](https://github.com/borkdude/carve) - Remove unused Clojure vars
- [4ever-clojure](https://github.com/oxalorg/4ever-clojure) - Pure CLJS version of 4clojure, meant to run forever!
- [pod-babashka-lanterna](https://github.com/babashka/pod-babashka-lanterna): Interact with clojure-lanterna from babashka
- [clj2el](https://borkdude.github.io/clj2el/): transpile Clojure to elisp
- [deflet](https://github.com/borkdude/deflet): make let-expressions REPL-friendly!
- [deps.add-lib](https://github.com/borkdude/deps.add-lib): Clojure 1.12's add-lib feature for leiningen and/or other environments without a specific version of the clojure CLI
- [process](https://github.com/babashka/process): Clojure library for shelling out / spawning subprocesses
- [parmezan](https://github.com/borkdude/parmezan): fixes unbalanced or unexpected parens or other delimiters in Clojure files

</details>
