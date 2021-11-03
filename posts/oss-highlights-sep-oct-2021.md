This year I'm receiving a long term grant from [Clojurists
Together](https://www.clojuriststogether.org/). CT asked me to do a bimonthly
report on my activities or write a blog post and link to that. That's what I'm
doing here. I'll be going over the releases of existing and new projects and
will mention highlights in some of them. Often, the devil is in the details and
there aren't any new user-facing features, but still a lot of work has gone into
refining existing features.

Donations from [Github Sponsors](https://github.com/sponsors/borkdude),
OpenCollective and the year long CT grant directly allow me to spend more time
on open source. The list you are seeing here is a result of this. I love working
on open source so thank you for allowing me to do this. In
[this](https://soundcloud.com/user-959992602/s4-e40-oss-with-michiel-borkent)
[ClojureScript podcast](https://clojurescriptpodcast.com/) episode, Jacek Schae
interviewed me on my decision to quit my previous job and work more on Clojure
OSS. Have a listen if you want to know more about my motivations. If aside from
sponsoring, you or your company needs work done on any of these or related
projects and want to make a custom arrangement, please do reach out.

Note that the projects listed below are not exclusively my effort and are worked
on by a number or regular contributors. My thanks also goes out to them.

# [Nbb](https://github.com/babashka/nbb)

Ad-hoc CLJS scripting on Node.js using SCI.

Many releases on [npm](https://www.npmjs.com/package/nbb).

Highlights:

- Console REPL. If you have Node.js simply type `npx nbb` and you will be dropped into a REPL.
- Socket server REPL
- nREPL server for development with Calva
- Add `clojure.test` so you can now use nbb to develop e.g. browser tests using puppeteer or playwright
- Support for reader conditionals using `:org.babashka/nbb`
- Print nicer stacktrace when error happens (similar to bb)
- Misc. fixes and enhancements.
- Add `js-interop` module
- Add Javascript API

Many exciting things are happening around this project in the community. It's
now possible to run nbb on
[lambda](https://github.com/vharmain/nbb-lambda-adapter). [Exercism](https://github.com/babashka/nbb/discussions/91#discussioncomment-1510273) is using babashka and nbb to run Clojure exercise submissions. [Sitefox](https://github.com/chr15m/sitefox) is a CLJS + Node.js web framework that works well with nbb.

# [Clj-kondo](https://github.com/clj-kondo/clj-kondo)

A linter for Clojure (code) that sparks joy.

Releases: [2021.10.19](https://github.com/clj-kondo/clj-kondo/blob/master/CHANGELOG.md#20211019)
, [2021.09.25](https://github.com/clj-kondo/clj-kondo/blob/master/CHANGELOG.md#20210925)
, [2021.09.15](https://github.com/clj-kondo/clj-kondo/blob/master/CHANGELOG.md#20210915)
, [2021.09.14](https://github.com/clj-kondo/clj-kondo/blob/master/CHANGELOG.md#20210914)

Highlights:

- New linter: warn on missing `gen-class` when writing `-main` function. See [docs](https://github.com/clj-kondo/clj-kondo/blob/master/doc/linters.md#main-without-gen-class).
- New `loop` without `recur` linter. See [docs](https://github.com/clj-kondo/clj-kondo/blob/master/doc/linters.md#loop-without-recur).
- Several inference improvements, e.g. `(def f (fn [])) (f 1 2)` will now give
  an arity warning.
- Return arbitrary metadata in analysis data. See [docs](https://github.com/clj-kondo/clj-kondo/blob/master/analysis/README.md).

# [Babashka](https://github.com/babashka/babashka)

Native, fast starting Clojure interpreter for scripting.

Releases: [0.6.4](https://github.com/babashka/babashka/blob/master/CHANGELOG.md#064)
, [0.6.3](https://github.com/babashka/babashka/blob/master/CHANGELOG.md#063)
, [0.6.2](https://github.com/babashka/babashka/blob/master/CHANGELOG.md#062)
, [0.6.1](https://github.com/babashka/babashka/blob/master/CHANGELOG.md#061)
, [0.6.0](https://github.com/babashka/babashka/blob/master/CHANGELOG.md#060)

Highlight:

- Support for java.net HTTP Client (via raw Java interop). This enables running
  [java-http-clj](https://github.com/schmee/java-http-clj) from source.

# [Api-diff](https://github.com/borkdude/api-diff)

Print API diffs between library versions.

This is a new project.

# [SCI](https://github.com/babashka/sci)

Configurable Clojure interpreter suitable for scripting and Clojure DSLs.

## 0.2.7

Releases: [0.2.7](https://github.com/babashka/sci/blob/master/CHANGELOG.md#v027)

Highlights:

- Loads of JS improvements coming from usage in
  [nbb](https://github.com/borkdude/nbb). Printing in JS targets can now be
  controlled via `*print-fn*` like in ClojureScript.

# [Neil](https://github.com/babashka/neil)

A CLI to add common aliases and features to deps.edn-based projects.

This is a new project.

# [Edamame](https://github.com/borkdude/edamame)

Configurable EDN/Clojure parser with location metadata

Releases: [0.0.12](https://github.com/borkdude/edamame/blob/master/CHANGELOG.md#0012)

# [Scittle](https://github.com/babashka/scittle)

The Small Clojure Interpreter exposed for usage in browser script tags.

Releases: [0.0.4](https://github.com/babashka/scittle/blob/main/CHANGELOG.md#v004)

# [Babashka.fs](https://github.com/babashka/fs)

File system utility library.

Releases: [0.1.0](https://github.com/babashka/fs/releases/tag/v0.1.0)

Highlight: add `fs/zip` function.

# [Deps.clj](https://github.com/borkdude/deps.clj)

A faithful port of the clojure CLI bash script to Clojure. Used as native CLI,
deps resolver in babashka and getting started REPL in Calva.

Various [releases](https://github.com/borkdude/deps.clj/releases)

# [Graal-build-time](https://github.com/clj-easy/graal-build-time)

Library to initialize Clojure packages at build time with GraalVM native-image.

This is a new project.

# [Graal-config](https://github.com/clj-easy/graal-config)

GraalVM native-image configurations distribution for Clojure libraries.

This is a new project.

# [Jet](https://github.com/borkdude/jet)

Releases: [0.0.16](https://github.com/borkdude/jet/blob/master/CHANGELOG.md#0016)

Highlight: allow keywordize fn to access all available conversion functions from
camel-snake-kebab lib. e.g. `csk/->PascalCase`.

# [Digest](https://github.com/clj-commons/digest)

Digest algorithms (md5, sha1 ...) for Clojure.

I took over this library from Miki Tebeka and I'm maintaining it under
clj-commons.

# [Carve](https://github.com/borkdude/carve)

Carve out the essentials of your Clojure app.

Fixed a regression.
