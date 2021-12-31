Like the previous [OSS highlights](oss-highlights-sep-oct-2021.html), I'll give
an overview of OSS work I did in November and December of 2021. I'd like to
emphasize that I can do this work because of sponsoring via:

- [Clojurists Together](https://www.clojuriststogether.org/)
- [Github Sponsors](https://github.com/sponsors/borkdude)
- [OpenCollective](https://opencollective.com/babashka) (also see the [clj-kondo](https://opencollective.com/clj-kondo) one)

for which I'm very grateful. Doing Clojure OSS is now my main activity.

<h2><a name="commercial-oss-support"><a href="#commercial-oss-support">Commercial OSS support</a></a></h2>

I'd like to add that I'm also offering invoiced support for my OSS projects if
you need my help on your commercial projects or if sponsoring doesn't work for
your company. Currently this is only a small part of my income, but I have had
several parties who made use of this already. It might be good to know there are
options beyond sponsoring if your company needs an extra level of support for my
OSS libraries, which are free to use. Feel free to reach out via Twitter DM,
[e-mail](michielborkent@gmail.com) or on Clojurians Slack to discuss options.

<blockquote class="twitter-tweet"><p lang="en" dir="ltr">Do you need help with <a href="https://twitter.com/hashtag/clojure?src=hash&amp;ref_src=twsrc%5Etfw">#clojure</a> <a href="https://twitter.com/hashtag/graalvm?src=hash&amp;ref_src=twsrc%5Etfw">#graalvm</a> compilation, <a href="https://twitter.com/hashtag/clojure?src=hash&amp;ref_src=twsrc%5Etfw">#clojure</a> static analysis/linting tools, or anything related to my <a href="https://twitter.com/hashtag/oss?src=hash&amp;ref_src=twsrc%5Etfw">#oss</a> projects? I&#39;m available for USD 150/hr. I also do custom license/support contracts if <a href="https://twitter.com/github?ref_src=twsrc%5Etfw">@github</a> sponsors doesn&#39;t fit your business.<a href="https://twitter.com/hashtag/cljkondo?src=hash&amp;ref_src=twsrc%5Etfw">#cljkondo</a> <a href="https://twitter.com/hashtag/babashka?src=hash&amp;ref_src=twsrc%5Etfw">#babashka</a> <a href="https://twitter.com/hashtag/sci?src=hash&amp;ref_src=twsrc%5Etfw">#sci</a></p>&mdash; (λ. borkdude) (@borkdude) <a href="https://twitter.com/borkdude/status/1475416703198707718?ref_src=twsrc%5Etfw">December 27, 2021</a></blockquote> <script async src="https://platform.twitter.com/widgets.js" charset="utf-8"></script>

## [Babashka](https://github.com/babashka/babashka)

Native, fast starting Clojure interpreter for scripting.

Releases: [0.7.3](https://github.com/babashka/babashka/blob/master/CHANGELOG.md#073-2021-12-30)
, [0.7.2](https://github.com/babashka/babashka/blob/master/CHANGELOG.md#072-2021-12-29)
, [0.7.0](https://github.com/babashka/babashka/blob/master/CHANGELOG.md#070-2021-12-10)
, [0.6.8](https://github.com/babashka/babashka/blob/master/CHANGELOG.md#068-2021-12-02)
, [0.6.7](https://github.com/babashka/babashka/blob/master/CHANGELOG.md#067-2021-11-29)
, [0.6.6](https://github.com/babashka/babashka/blob/master/CHANGELOG.md#066-2021-11-29)
, [0.6.5](https://github.com/babashka/babashka/blob/master/CHANGELOG.md#065-2021-11-13)

Highlights:

- Load tasks and deps from other bb.edn file using `--config` and `--deps-root` options [#1110](https://github.com/babashka/babashka/issues/1110)
- Add compatibility with `clojure.spec.alpha`. See
  [babashka/spec.alpha](https://github.com/babashka/spec.alpha) and this [blog
  post](https://blog.michielborkent.nl/using-clojure-spec-alpha-with-babashka.html).
- Compatibility with a [fork of
  tools.namespace](https://github.com/babashka/tools.namespace). This allows
  running the Cognitect
  [test-runner](https://github.com/cognitect-labs/test-runner) (Cognitest) from source.
- [tools.bbuild](https://github.com/babashka/tools.bbuild).
- Compatiblity with [markdown-clj](https://github.com/yogthos/markdown-clj) (see [blog](https://blog.michielborkent.nl/markdown-clj-babashka-compatible.html))

## [Clj-kondo](https://github.com/clj-kondo/clj-kondo)

A linter for Clojure (code) that sparks joy.

Releases: [2021.12.19](https://github.com/clj-kondo/clj-kondo/blob/master/CHANGELOG.md#20211219)
, [2021.12.16](https://github.com/clj-kondo/clj-kondo/blob/master/CHANGELOG.md#20211216)
, [2021.12.01](https://github.com/clj-kondo/clj-kondo/blob/master/CHANGELOG.md#20211201)

Highlights:

- Automatically load configurations from `.clj-kondo/*/*/config.edn`. This can
  be disabled with `:auto-load-configs false`.  See issue
  [#1492](https://github.com/clj-kondo/clj-kondo/issues/1492). This makes life
  easier for those using
  [clojure-lsp](https://github.com/clojure-lsp/clojure-lsp) which already uses
  the `--copy-configs` option by default.
- Several new linters: `:reduce-without-init`, `:duplicate-case-test-constant`,
  `:unexpected-recur`, `:used-underscored-binding` and a few around
  docstrings. See the
  [linters.md](https://github.com/clj-kondo/clj-kondo/blob/master/doc/linters.md)
  docs for details.
- [better-cond](https://github.com/Engelberg/better-cond) now bundles a clj-kondo config!

Note that most new linters in clj-kondo are going to default to `:level :off`
unless they detect a compile time error, like `:duplicate-case-test-constant`.

Special thanks to new contributor [@mknoszlig](https://github.com/mknoszlig) who
has been quite active in adding new linters in collaboration with me.

## [SCI](https://github.com/babashka/sci)

Configurable Clojure interpreter suitable for scripting and Clojure DSLs.

Releases: [0.2.8](https://github.com/babashka/sci/blob/master/CHANGELOG.md#v028)

Highlights:

- Add `copy-ns` macro for copying an entire Clojure or ClojureScript namespace into a SCI context
- `clojure.core/read` improvements
- Lots of little 'devil is in the details' correctness improvements

## [Nbb](https://github.com/babashka/nbb)

Ad-hoc CLJS scripting on Node.js using SCI.

Releases: v0.0.108 - v0.1.0. See [npm](https://www.npmjs.com/package/nbb).

Highlights:

- Add `nbb.classpath` which is also available in the JS API.
- Add [cljs.bean](https://github.com/mfikes/cljs-bean) as built-in library.
- Add `--help` and `--main` command line options (finally!)

## [Neil](https://github.com/babashka/neil)

- Automatically use newest tool.build with `add build`

## [Edamame](https://github.com/borkdude/edamame)

Configurable EDN/Clojure parser with location metadata

Releases: [0.0.13](https://github.com/borkdude/edamame/blob/master/CHANGELOG.md#0013)
, [0.0.14](https://github.com/borkdude/edamame/blob/master/CHANGELOG.md#0014)
, [0.0.15](https://github.com/borkdude/edamame/blob/master/CHANGELOG.md#0015)
, [0.0.16](https://github.com/borkdude/edamame/blob/master/CHANGELOG.md#0016)
, [0.0.17](https://github.com/borkdude/edamame/blob/master/CHANGELOG.md#0017)
, [0.0.18](https://github.com/borkdude/edamame/blob/master/CHANGELOG.md#0018)
, [0.0.19](https://github.com/borkdude/edamame/blob/master/CHANGELOG.md#0019)

Many small improvements. Edamame now supports reading with a reader which is not
an indexing reader too, for compatibility with `clojure.core/read`.

## [Babashka.fs](https://github.com/babashka/fs)

File system utility library.

Releases: [0.1.2](https://github.com/babashka/fs/blob/master/CHANGELOG.md#v012)
, [0.1.1](https://github.com/babashka/fs/blob/master/CHANGELOG.md#v011)

Highlights: improve `fs/which` on Windows, add `with-temp-dir` macro.

## [Babashka.process](https://github.com/babashka/process)

Clojure wrapper for `java.lang.ProcessBuilder`.

Releases: [0.1.0](https://github.com/babashka/fs/blob/master/CHANGELOG.md#010)

Highlights:

- Resolve binaries on Windows using `fs/which`
- Support `deref` with timeout [#50](https://github.com/babashka/process/issues/50) ([@SevereOverfl0w](https://github.com/SevereOverfl0w))

## [tools.namespace](https://github.com/clojure/tools.namespace)

In this core library I contributed two ClojureScript fixes:

- [TNS-51](https://github.com/clojure/tools.namespace/commit/180c162f1330d5295b8e5d47bc65cbf3ef1e8eb2)
- [TNS-57](https://github.com/clojure/tools.namespace/commit/f40c9e58278acb152c7b8c1c21a6b10795a99e8a)
