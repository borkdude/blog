Title: OSS updates March 2023
Date: 2023-04-07
Tags: clojure, oss updates
Description: My Clojure OSS updates for March 2023

In this post I'll give updates about open source I worked on during March 2023.

## Sponsors

But first off, I'd like to thank all the sponsors and contributors that make
this work possible! Top sponsors:

- [Clojurists Together](https://clojuriststogether.org/)
- [Roam Research](https://roamresearch.com/)
- [Nextjournal](https://nextjournal.com/)
- [Toyokumo](https://toyokumo.co.jp/)
- [Cognitect](https://www.cognitect.com/)
- [Kepler16](https://kepler16.com/)
- [Adgoji](https://www.adgoji.com/)

If you want to ensure that the projects I work on are sustainably maintained,
you can sponsor this work in the following ways. Thank you!

- [Github Sponsors](https://github.com/sponsors/borkdude)
- The [Babaska](https://opencollective.com/babashka) or [Clj-kondo](https://opencollective.com/clj-kondo) OpenCollective
- [Ko-fi](https://ko-fi.com/borkdude)
- [Patreon](https://www.patreon.com/borkdude)
- [Clojurists Together](https://www.clojuriststogether.org/)

If you're used to sponsoring through some other means which isn't listed above, please get in touch.

On to the projects that I've been working on!

<!--

sources: https://github.com/borkdude
local ~/dev and ~/dev/babashka dir (since github doesn't show all repos)

-->

## [cherry](https://github.com/squint-cljs/cherry)

Experimental ClojureScript to ES6 module compiler

This month I've been preparing cherry as a compiler that you can embed in your
existing CLJS / shadow-cljs applications. This makes cherry an additional
alternative to [SCI](https://github.com/babashka/sci) and self-hosted CLJS.

Read about embedding cherry into your application
[here](https://github.com/squint-cljs/cherry/blob/main/doc/embed.md).

I've been working on several PRs to include cherry as an additional evaluator in:

- [clerk](https://github.com/nextjournal/clerk/pull/446)
- [malli](https://github.com/metosin/malli/pull/888)
- One project that is still unpublished but will be coming soon!

## [scimacs](https://github.com/jackrusher/scimacs)

The Small Clojure Interpreter (SCI) integrated with emacs as a loadable module.

This is a new project by Jack Rusher and I've helped him with the SCI integration.

## [clj2el](https://borkdude.github.io/clj2el/)

Transpile clojure to elisp. A brand new project to transpile Clojure to
Elisp. It might be of value for those who know Clojure better than Elisp and
want to have something to get started. It's far from complete. Try it in the
playground [here](https://borkdude.github.io/clj2el/).

## [deflet](https://github.com/borkdude/deflet)

Make let-expressions REPL-friendly!

The `deflet` macro lets your write inline-def expressions, while expanding those
to regular let expressions, giving you the benefits of REPL-driven development
without polluting production code with top level vars.

## [babashka](https://github.com/babashka/babashka)
Native, fast starting Clojure interpreter for scripting

New release: 1.3.175 (2023-03-18)), 1.3.176 (2023-03-18)

Highlights:

The `clojure.core.async/go` macro now uses virtual threads.

Many small fixes and upgrades.

See the complete [CHANGELOG](https://github.com/babashka/babashka/blob/master/CHANGELOG.md).

### [babashka.json](https://github.com/babashka/json)

This is a JSON abstraction library that you can include in babashka and JVM
projects while also including your own favorite JSON implementation. The idea is
that babashka libraries can include this, while JVM projects don't have to
switch their JSON implementation to cheshire.core.

### Babashka compatibility in external libs

I worked together with the maintainers of the following libraries to make them compatible with babashka:

- [martian](https://github.com/oliyh/martian/commit/f0f7679364f58eb4ef558e9ad2340274b9742542): HTTP abstraction library for Clojure/script, supporting OpenAPI, Swagger, Schema, re-frame and more

## [clj-kondo](https://github.com/clj-kondo/clj-kondo)
Static analyzer and linter for Clojure code that sparks joy

New release: 2023.03.17

Some highlights:

- [#2010](https://github.com/clj-kondo/clj-kondo/issues/2010): Support inline macro configuration. See [docs](https://github.com/clj-kondo/clj-kondo/blob/master/doc/config.md#inline-macro-configuration)
- [#2010](https://github.com/clj-kondo/clj-kondo/issues/2010): Short syntax to disable linters: `{:ignore [:unresolved-symbol]}` or `{:ignore true}`, valid in ns-metadata, `:config-in-ns`, `:config-in-call`
- [#2009](https://github.com/clj-kondo/clj-kondo/issues/2009): new `:var-same-name-except-case` linter: warn when vars have names that differ only in case (important for AOT compilation and case-insensitive filesystems)
- [#1269](https://github.com/clj-kondo/clj-kondo/issues/1269): warn on `:jvm-opts` in top level of `deps.edn`
- [#2003](https://github.com/clj-kondo/clj-kondo/issues/2003): detect invalid arity call for function passed to `update`, `update-in`, `swap!`, `swap-vals!`, `send`, `send-off`, and `send-via`

[Check the
changelog](https://github.com/clj-kondo/clj-kondo/blob/master/CHANGELOG.md) for
details.

## [SCI](https://github.com/babashka/sci)
Configurable Clojure/Script interpreter suitable for scripting and Clojure DSLs

New release: 0.7.39 (2023-03-07)

- [#874](https://github.com/babashka/sci/issues/874): Keyword arguments as map support for CLJS

See [changelog](https://github.com/babashka/sci/blob/master/CHANGELOG.md) for more.

## Contributions to other projects

- [clojurescript](https://github.com/clojure/clojurescript):
  - [PR 202](https://github.com/clojure/clojurescript/pull/202): a `macroexpand` fix
  - [PR 203](https://github.com/clojure/clojurescript/pull/203): a symbol optimization fix
- [clojure-lsp](https://github.com/clojure-lsp/clojure-lsp/commit/b7111ef6b5f9c1d93b2272683ff4b6eb58b240c9): fix reflection issue on JDK 19
- [http-kit](https://github.com/http-kit/http-kit/commit/e2d71039ea2617e789a08606a0c404c41367dca8): add native-image tests
- [promesa](https://github.com/funcool/promesa/commit/18fea52fd99b24a65927907eff6879b970c71dfd): fix GraalVM native-image compilation
- [etaoin](https://github.com/clj-commons/etaoin/commit/706f342216af69d23de671803ac67c3e1f515941): JDK 19 + babashka issue

## Brief mentions

The following projects also got updates, mostly in the form of maintenance and
performance improvements. This post would get too long if I had to go into
detail about them, so I'll briefly mention them in random order:

- [CLI](https://github.com/babashka/cli): Turn Clojure functions into CLIs!
- [quickdoc](https://github.com/borkdude/quickdoc): Quick and minimal API doc generation for Clojure
- [rewrite-clj](https://github.com/clj-commons/rewrite-clj): Rewrite Clojure code and edn
- [sql pods](https://github.com/babashka/babashka-sql-pods): babashka pods for SQL databases
- [squint](https://github.com/squint-cljs/squint): CLJS _syntax_ to JS compiler
- [pod-babashka-buddy](https://github.com/babashka/pod-babashka-buddy): A pod around buddy core (Cryptographic Api for Clojure).
- [gh-release-artifact](https://github.com/borkdude/gh-release-artifact): Upload artifacts to Github releases idempotently
- [edamame](https://github.com/borkdude/edamame): Configurable EDN/Clojure parser with location metadata
- [Nbb](https://github.com/babashka/nbb): Scripting in Clojure on Node.js using SCI
- [neil](https://github.com/babashka/neil): A CLI to add common aliases and features to deps.edn-based projects
- [jet](https://github.com/borkdude/jet): CLI to transform between JSON, EDN, YAML and Transit using Clojure
- [http-client](https://github.com/babashka/http-client): Babashka's http-client

## Other projects

These are (some of the) other projects I'm involved with but little to no activity
happened in the past month.

- [cljs-showcase](https://github.com/borkdude/cljs-showcase): Showcase CLJS libs using SCI
- [carve](https://github.com/borkdude/carve) - Remove unused Clojure vars
- [deps.clj](https://github.com/borkdude/deps.clj): A faithful port of the clojure CLI bash script to Clojure
- [grasp](https://github.com/borkdude/grasp): Grep Clojure code using clojure.spec regexes
- [scittle](https://github.com/babashka/scittle): Execute Clojure(Script) directly from browser script tags via SCI
- [quickblog](https://github.com/borkdude/quickblog): Light-weight static blog engine for Clojure and babashka
- [sci.configs](https://github.com/babashka/sci.configs): A collection of ready to be used SCI configs
- [lein2deps](https://github.com/borkdude/lein2deps): leiningen to deps.edn converter
- [4ever-clojure](https://github.com/oxalorg/4ever-clojure) - Pure CLJS version of 4clojure, meant to run forever!
- [instaparse-bb](https://github.com/babashka/instaparse-bb)
- [fs](https://github.com/babashka/fs) - File system utility library for Clojure
- [process](https://github.com/babashka/process): Clojure library for shelling out / spawning sub-processes
- [pod-babashka-lanterna](https://github.com/babashka/pod-babashka-lanterna): Interact with clojure-lanterna from babashka
- [joyride](https://github.com/BetterThanTomorrow/joyride): VSCode CLJS scripting and REPL (via [SCI](https://github.com/babashka/sci))

<!-- - [tools-deps-native](https://github.com/babashka/tools-deps-native): Run tools.deps as a native binary-->
<!-- - [tools.bbuild](https://github.com/babashka/tools.bbuild): Library of functions for building Clojure projects-->
