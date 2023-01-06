Title: OSS updates of November - December 2022
Date: 2023-01-06
Tags: clojure, oss updates

In this post I'll give updates about open source I worked on during November and December 2022.

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
you can sponsor this work via the following organizations. Thank you!

- [Github Sponsors](https://github.com/sponsors/borkdude)
- [OpenCollective](https://opencollective.com/babashka) (also see the [clj-kondo](https://opencollective.com/clj-kondo) one)
- [Clojurists Together](https://www.clojuriststogether.org/)

## Projects

### [http-client](https://github.com/babashka/http-client)

The new babashka http-client aims to become default HTTP client solution in babashka.

Babashka has several built-in options for making HTTP requests, including:

- [babashka.curl](https://github.com/babashka/babashka.curl)
- [http-kit](https://github.com/http-kit/http-kit)
- [java.net.http](https://docs.oracle.com/en/java/javase/17/docs/api/java.net.http/java/net/http/package-summary.html)

In addition, it allows to use several libraries to be used as a dependency:

- [java-http-clj](https://github.com/schmee/java-http-clj)
- [hato](https://github.com/gnarroway/hato)
- [clj-http-lite](https://github.com/clj-commons/clj-http-lite)

The built-in clients come with their own trade-offs. E.g. babashka.curl shells
out to `curl` which on Windows requires your local `curl` to be
updated. Http-kit buffers the entire response in memory. Using `java.net.http`
directly can be a bit verbose.

Babashka's http-client aims to be a good default for most scripting use cases
and is built on top of `java.net.http` and can be used as a dependency-free JVM
library as well. The API is mostly compatible with babashka.curl so it can be
used as a drop-in replacement. The other built-in solutions will not be removed
any time soon.

### [Babashka](https://github.com/babashka/babashka)

Native, fast starting Clojure interpreter for scripting.

I had the honor to write a guest blog post for the GraalVM blog about
babashka. You can read it
[here](https://medium.com/graalvm/babashka-how-graalvm-helped-create-a-fast-starting-scripting-environment-for-clojure-b0fcc38b0746).

Daniel Higginbotham from Brave Clojure wrote [Babashka
Babooka](https://www.braveclojure.com/quests/babooka/) which I helped reviewing.

I also wrote a blog on how to [test babashka scripts](https://blog.michielborkent.nl/babashka-test-runner.html).

Versions 1.0.165 - 1.0.169 were released. Visit the [changelog](https://github.com/babashka/babashka/blob/master/CHANGELOG.md) for details.
Highlights:

- Compatibility with Cognitest [test-runner](https://github.com/cognitect-labs/test-runner) and [tools.namespace](https://github.com/clojure/tools.namespace)
- [#1433](https://github.com/babashka/babashka/issues/1433): spec source as built-in fallback. When not including the
  [clojure.spec.alpha](https://github.com/babashka/spec.alpha) fork as a
  library, babashka loads a bundled version, when `clojure.spec.alpha` is required.
- Many performance improvements (via SCI)
- Several non-special forms are now regular macros rather than treated special (via SCI)
- Update to babashka process to v0.4.13: support `(process opts? & args)` syntax everywhere

### [Squint](https://github.com/squint-cljs/squint) and [Cherry](https://github.com/squint-cljs/cherry)

Squint and cherry are two flavors of the same CLJS compiler.

Squint is a CLJS _syntax_ to JS compiler for use case where you want to write
JS, but do it using CLJS syntax and tooling instead.  Cherry comes with the CLJS
standard library and is as such much closer to the normal ClojureScript, but the
minimal amount of JS is a little bigger.

The [video](https://www.youtube.com/watch?v=oCd74TQ-gf4) of the talk I did on ClojureDays 2022 came online!

In the past two months, I've been restructuring code between squint and cherry:
a bit boring but necessary to keep going forward. Along with some minor bugfixes
and features, one new JSX feature landed: you can pass a props map using a new
notation inspired by [helix](https://github.com/lilactown/helix).

You can read details in the
[changelog](https://github.com/squint-cljs/squint/blob/main/CHANGELOG.md).

### [SCI](https://github.com/babashka/sci)

Configurable Clojure interpreter suitable for scripting and Clojure DSLs.

This is the workhorse that powers babashka, nbb, Joyride, and many other projects.

Many improvements have happened over the last two months, both in Clojure
compatibility and performance. JS and JVM interop has become up to 5x
faster. All of these changes benefit babashka, nbb, joyride, etc.

See
[changelog](https://github.com/babashka/sci/blob/master/CHANGELOG.md) for more
details.

### [Clj-kondo](https://github.com/clj-kondo/clj-kondo)

Static analyzer and linter for Clojure code that sparks joy

Two new releases with many fixes and improvements. [Check the
changelog](https://github.com/clj-kondo/clj-kondo/blob/master/CHANGELOG.md) for
details.

Some highlights:

- [#609](https://github.com/clj-kondo/clj-kondo/issues/609): typecheck var usage, e.g. `(def x :foo) (inc x)` will now give a warning
- [#1846](https://github.com/clj-kondo/clj-kondo/issues/1846): new linters: `:earmuffed-var-not-dymamic` and `:dynamic-var-not-earmuffed`. See [docs](https://github.com/clj-kondo/clj-kondo/blob/master/doc/linters.md#dynamic-vars).
- [#1875](https://github.com/clj-kondo/clj-kondo/issues/1875): add `:duplicate-field-name` linter for deftype and defrecord definitions.

### [Scittle](https://github.com/babashka/scittle)

Execute Clojure(Script) directly from browser script tags via SCI.
See it in [action](https://babashka.org/scittle/).

Version 0.4.11 introduced the [re-frame](https://github.com/day8/re-frame)
plugin.  You can play around with it in the playground
[here](https://babashka.org/scittle/codemirror.html).  Several other releases
were made. Details in the
[changelog](https://github.com/babashka/scittle/blob/main/CHANGELOG.md).

### [Process](https://github.com/babashka/process)

Clojure library for shelling out / spawning subprocesses

This library traditionally had the syntax: `(process [ & cmd-args ] ?opts)` but
in practice, it turned out that having the syntax `(process ?opts & cmd-args)`
is more convenient, since you can use it with `apply` and
`*command-line-args*`. All functions in `babashka.process` have been rewritten
to support this syntax.

See [changelog](https://github.com/babashka/process/blob/master/CHANGELOG.md) for details.

### [Quickdoc](https://github.com/borkdude/quickdoc)

Quickdoc is a tool to generate documentation from namespace/var analysis done by
clj-kondo. It's fast and spits out an `API.md` file in the root of your project,
so you can immediately view it on Github. Minor fixes and improvements were made.

### [Fs](https://github.com/babashka/fs)

File system utility library for Clojure.

Fs has gotten one new function: `update-file`, that alters the contents of a
(text) file using a function. The function is reminiscent of `swap!`.

See [changelog](https://github.com/babashka/fs/blob/master/CHANGELOG.md#changelog) for more details.

### [Neil](https://github.com/babashka/neil)

A CLI to add common aliases and features to `deps.edn`-based projects.

A `NEIL_GITHUB_TOKEN` can now be configured to avoid hitting the rate limit of
the Github API, thanks to Russ Matney.

### [Quickblog](https://github.com/borkdude/quickblog)

Light-weight static blog engine for Clojure and babashka.

The blog you're currently reading is made with quickblog.

Version [0.1.0](https://github.com/borkdude/quickblog/blob/main/CHANGELOG.md#010-2022-12-11) was finally released with much thanks to Josh Glover.
See [changelog](https://github.com/borkdude/quickblog/blob/main/CHANGELOG.md#changelog) for more details.

### [Rewrite-edn](https://github.com/borkdude/rewrite-edn)

Utility lib on top of rewrite-clj with common operations to update EDN while preserving whitespace and comments.

Minor fixes and enhancements. Several functions have been added like `fnil` and `conj`. See [changelog](https://github.com/borkdude/rewrite-edn/blob/master/CHANGELOG.md).

### [Sci.configs](https://github.com/babashka/sci.configs)

A collection of ready to be used SCI configs for e.g. Reagent, Promesa, Re-frame
and other projects that are used in nbb, joyride, scittle, etc.  See recent
[commits](https://github.com/babashka/sci.configs/commits/main) for what's been
improved.

### [Nbb](https://github.com/babashka/nbb)

Scripting in Clojure on Node.js using SCI

Because it's so easy to deploy to npm, I usually publish a new version for each issue that is resolved.

No big changes, but many small bugfixes and improvements in the last two
months. See
[changelog](https://github.com/babashka/nbb/blob/main/CHANGELOG.md).

### [Edamame](https://github.com/borkdude/edamame)

Configurable EDN/Clojure parser with location metadata. It has been stable for a
while and reached version 1.0.0. The API is exposed now in
[babashka](https://github.com/babashka/babashka) and
[nbb](https://github.com/babashka/nbb) as well.

[Changelog](https://github.com/borkdude/edamame/blob/master/CHANGELOG.md)

### [lein2deps](https://github.com/borkdude/lein2deps)

Lein to deps.edn converter

This tool can convert a `project.edn` file to a `deps.edn` file. It even
supports Java compilation and evaluation of code within `project.clj`. Several minor enhancements were made.
See [changelog](https://github.com/borkdude/lein2deps/blob/main/CHANGELOG.md).

### [Joyride](https://github.com/BetterThanTomorrow/joyride)

Modify VSCode by executing ClojureScript (SCI) code in your REPL and/or run
scripts via keyboard shortcuts.

I'm working on this project together with Peter Strömberg (known for his work on
Calva) and I'm mostly reviewing Peter's PR instead of writing code.

Read the changelog [here](https://github.com/BetterThanTomorrow/joyride/blob/master/CHANGELOG.md).

### [Deps.clj](https://github.com/borkdude/deps.clj)

Regular maintainance, keeping up with the official Clojure CLI and tools jar!

### [Clj-kondo configs](https://github.com/clj-kondo/configs)

Library configurations as dependencies for clj-kondo.

The claypoole configuration was improved.

### [Babashka CLI](https://github.com/babashka/cli)

Turn Clojure functions into CLIs!

Minor fixes. See [changelog](https://github.com/babashka/cli/blob/main/CHANGELOG.md).

### Babashka pods

The [pods](https://github.com/babashka/pods) library contains the code that
supports using pods in babashka and the JVM. A critical error was fixed that
would hang babashka and a new JVM release was pushed to Clojars (v0.1.0).

### Babashka compatibility in external libs

I contributed to [RCF](https://github.com/hyperfiddle/rcf),
[deep-diff2](https://github.com/lambdaisland/deep-diff2) and
[clj-diff](https://github.com/lambdaisland/clj-diff) to make these libraries
babashka compatible.
