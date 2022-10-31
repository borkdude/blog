Title: OSS updates of September - October 2022
Date: 2022-10-31
Tags: clojure, oss updates

In this post I'll give updates about open source I worked on during September and October 2022.

## Sponsors

But first off, I'd like to thank all the sponsors and contributors that make
this work possible! Top sponsors:

- [Clojurists Together](https://clojuriststogether.org/)
- [Roam Research](https://roamresearch.com/)
- [Nextjournal](https://nextjournal.com/)
- [Toyokumo](https://toyokumo.co.jp/)
- [Cognitect](https://www.cognitect.com/)
- [Kepler16](https://kepler16.com/)

If you want to ensure that the projects I work on are sustainably maintained,
you can sponsor this work via the following organizations. Thank you!

- [Github Sponsors](https://github.com/sponsors/borkdude)
- [OpenCollective](https://opencollective.com/babashka) (also see the [clj-kondo](https://opencollective.com/clj-kondo) one)
- [Clojurists Together](https://www.clojuriststogether.org/)


## Projects

<!-- September: https://github.com/borkdude?tab=overview&from=2022-09-01&to=2022-09-31 -->
<!-- October: https://github.com/borkdude?tab=overview&from=2022-10-01&to=2022-10-31 -->

### [Babashka](https://github.com/babashka/babashka)

Native, fast starting Clojure interpreter for scripting.

- The first [1.0
release](https://github.com/babashka/babashka/blob/master/CHANGELOG.md#10164-2022-10-17)
 was released!
- Optimizations for `let` (in SCI) which is now up to 8x faster.
- Many small improvements. See the
 [changelogs](https://github.com/babashka/babashka/blob/master/CHANGELOG.md).

### [Squint](https://github.com/squint-cljs/squint) and [Cherry](https://github.com/squint-cljs/cherry)

Squint and cherry are two flavors of the same CLJS compiler.

Squint is a CLJS _syntax_ to JS compiler for use case where you want to write JS, but do it
using CLJS syntax and tooling instead. Squint comes with a standard library that
resembles CLJS but is built on bare JS ingredients. As such, squint comes with
the usual JS caveats, but we can still have our parens and enjoy a slim bundle
size.

Cherry comes with the CLJS standard library and is as such much closer to the
normal ClojureScript, but the minimal amount of JS is a little bigger.

I've working on unifying the compiler code of cherry and squint into one code
base, which is still in progress. I've also worked on REPL code.

I've also given a [presentation on squint and
cherry](https://twitter.com/borkdude/status/1586662315805450240) at the [Dutch
Clojure Days](https://clojuredays.org/). The video will appear online in the future!

### [Clj-kondo](https://github.com/clj-kondo/clj-kondo)

Static analyzer and linter for Clojure code that sparks joy

Two new releases with many fixes and improvements. [Check the
changelogs](https://github.com/clj-kondo/clj-kondo/blob/master/CHANGELOG.md) for
details.

Among several new linters, there is a new `:unused-value` linter which detects
unused values, which is particularly helpful for detecting unused transient
operation results which can lead to bugs.

### [Clj-kondo configs](https://github.com/clj-kondo/configs)

Library configurations as dependencies for clj-kondo.

The idea of this repository is that you can add configuration for libraries as a
dependency to your `deps.edn` or `project.clj`.  If you invoke the right command
or if you are using Clojure LSP, then the configuration is written into your
`.clj-kondo` directory and clj-kondo will understand custom constructs in your
library.  Normally you can provide these configurations as part of your library,
but this is not always an option, so the remaining configurations can live over
here.

### [SCI](https://github.com/babashka/sci)

Configurable Clojure interpreter suitable for scripting and Clojure DSLs.

This is the workhorse that powers babashka, nbb, Joyride, and many other projects.

Several bugfixes and enhancements were made in the last two months in two new releases.
Performance of `let` bindings are now up to 8x faster, as already mentioned in the babashka entry of this post.

See [changelogs](https://github.com/babashka/sci/blob/master/CHANGELOG.md) for more details.

### [Nbb](https://github.com/babashka/nbb)

Scripting in Clojure on Node.js using SCI

The first 1.0 version was released.

Many small bugfixes and improvements in the last two months. See [changelogs](https://github.com/babashka/nbb/blob/main/CHANGELOG.md).

### [Clj-yaml](https://github.com/clj-commons/clj-yaml)

In the past two month, I became one of the maintainers, together with [@lread](https://github.com/lread), of [clj-yaml](https://github.com/clj-commons/clj-yaml).
Clj-yaml is a built-in library of babashka.

### [Deps.clj](https://github.com/borkdude/deps.clj)

A faithful port of the clojure CLI bash script to Clojure

A lot of Windows improvements in the last two months. Deps.clj is now also available as part of an [MSI installer](https://github.com/casselc/clj-msi/releases) that installs `deps.exe` as `clj.exe`.
This installer might form the basis for an official Clojure MSI installer.

### [Gh-release-artifact](https://github.com/borkdude/gh-release-artifact)

Upload artifacts to Github releases idempotently

This tool has been in use within babashka, clj-kondo and other projects to
automate uploading release artifacts from various CI systems to Github releases,
idempotently. It is now open source and ready to be used by others.

### [Jet](https://github.com/borkdude/jet)

CLI to transform between JSON, EDN, YAML and Transit, powered with a minimal
query language.

The latest release adds support for YAML (by using clj-yaml), thanks to [@qdzo](https://github.com/qdzo).

### [Babashka CLI](https://github.com/babashka/cli)

Turn Clojure functions into CLIs!

See [changelogs](https://github.com/babashka/cli/blob/main/CHANGELOG.md).

### [Process](https://github.com/babashka/process)

Clojure library for shelling out / spawning subprocesses

Minor updates and fixes. See [changelogs](https://github.com/babashka/process/blob/master/CHANGELOG.md).

### [Quickdoc](https://github.com/borkdude/quickdoc)

Quickdoc is a tool to generate documentation from namespace/var analysis done by
clj-kondo. It's fast and spits out an `API.md` file in the root of your project,
so you can immediately view it on Github. It has undergone significant
improvements in the last two months. I'm using quickdoc myself in several
projects. In the last two months, there have been improvements in the table of
contents linking and linking to source code.

### [Fs](https://github.com/babashka/fs)

File system utility library for Clojure.

Minor updates and fixes. See [changelogs](https://github.com/babashka/fs/blob/master/CHANGELOG.md#changelog).

### [Carve](https://github.com/borkdude/carve)

Carve out the essentials of your Clojure app by removing unused vars

Version 0.2.0 was released, after a long hiatus, with an updated version of clj-kondo and some minor fixes.

### [Grasp](https://github.com/borkdude/grasp)

Grep Clojure code using clojure.spec regexes.

I use this tool to analyze code patterns to make informed choices for e.g. SCI and clj-kondo.
E.g. see [this](https://github.com/borkdude/grasp/blob/master/examples/let_bindings.clj) example that shows how many let bindings are typically used.
See the example in action [here](https://twitter.com/borkdude/status/1582320503049826304).

A new version was released with minor fixes.

### [Rewrite-edn](https://github.com/borkdude/rewrite-edn)

Utility lib on top of rewrite-clj with common operations to update EDN while preserving whitespace and comments.

Minor fixes and enhancements. Repeated usage of `assoc` is now a safe
operation. Thanks to [@lread](https://github.com/lread) for the improvements.

### [lein2deps](https://github.com/borkdude/lein2deps)

Lein to deps.edn converter

This new little tool can convert a `project.edn` file to a `deps.edn` file. It even
supports Java compilation and evaluation of code within `project.clj`.

### [Neil](https://github.com/babashka/neil)

A CLI to add common aliases and features to `deps.edn`-based projects.

Neil now comes with a `dep upgrade` command, thanks to [@teodorlu](https://github.com/teodorlu) and [@russmatney](https://github.com/russmatney), together with other improvements.

### [Respeced](https://github.com/borkdude/respeced)

Finally, after 4 years, a new release of respeced, a testing library for clojure.spec fdefs.

### [Quickblog](https://github.com/borkdude/quickblog)

Light-weight static blog engine for Clojure and babashka

Small improvements. See [changelog](https://github.com/borkdude/quickblog/blob/main/CHANGELOG.md#changelog). 
The blog you're currently reading is made with quickblog.

### [Sci.configs](https://github.com/babashka/sci.configs)

A collection of ready to be used SCI configs

Added a `doseq` macro in [promesa](https://github.com/funcool/promesa) which also is available via this
configuration. Sci.configs is used in [Clerk](https://github.com/nextjournal/clerk), [nbb](https://github.com/babashka/nbb), [Joyride](https://github.com/BetterThanTomorrow/joyride/) and other SCI-based
CLJS projects.
