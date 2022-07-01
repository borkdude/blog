In this post I'll give updates about open source I worked on during May and June 2022.

## Sponsors

But first off, I'd like to thank all the sponsors and contributors that make
this work possible! Top sponsors: [Clojurists
Together](https://clojuriststogether.org/), [Roam
Research](https://roamresearch.com/), [Adgoji](https://www.adgoji.com/),
[Cognitect](https://www.cognitect.com/),
[Nextjournal](https://nextjournal.com/).

If you want to ensure that the projects I work on are sustainably maintained,
you can sponsor this work via the following organizations:

- [Github Sponsors](https://github.com/sponsors/borkdude)
- [OpenCollective](https://opencollective.com/babashka) (also see the [clj-kondo](https://opencollective.com/clj-kondo) one)
- [Clojurists Together](https://www.clojuriststogether.org/)

## Projects

### [Babashka CLI](https://github.com/babashka/cli)

Turn Clojure functions into CLIs!

This is one of my newest projects. It aims to close the gap between good command
line UX and calling Clojure functions. It is very much inspired by the clojure
CLI, but solves a problem which sometimes causes frustration, especially among
Windows users: having to use quotes in a shell. It also offers support for
subcommands. One project benefiting from that is
[neil](https://github.com/babashka/neil). I blogged about babashka CLI
[here](https://blog.michielborkent.nl/babashka-cli.html).

### [Http-server](https://github.com/babashka/http-server)

Serve static assets.

Another new project is http-server, which can be used in Clojure JVM and
babashka to serve static assets in an http server.

### [Clj-kondo workshop](https://github.com/clj-kondo/hooks-workshop-clojured-2022)

In June I had the honor and pleasure to give a workshop about [clj-kondo](https://github.com/clj-kondo/clj-kondo) at [ClojureD](https://clojured.de/).
You can work through the material yourself if you'd like [here](https://github.com/clj-kondo/hooks-workshop-clojured-2022). 
Feel free to join the clj-kondo channel on Clojurians Slack for questions. Here are some [pictures](https://twitter.com/borkdude/status/1542521071588347905) from the event.

### [Jet](https://github.com/borkdude/jet)

CLI to transform between JSON, EDN and Transit, powered with a minimal query
language.

[Changelog](https://github.com/borkdude/jet/blob/master/CHANGELOG.md)

The `jet` binary is now available for Apple Silicon and adds
[specter](https://github.com/redplanetlabs/specter) as part of the standard
library for transforming data. Also, the output is colorized and pretty-printed
using [puget](https://github.com/greglook/puget) now.

### [Edamame](https://github.com/borkdude/edamame)

[Changelog](https://github.com/borkdude/edamame/blob/master/CHANGELOG.md)

Configurable EDN/Clojure parser with location metadata. It has been stable for a
while and reached version 1.0.0. The API is exposed now in
[babashka](https://github.com/babashka/babashka) and
[nbb](https://github.com/babashka/nbb) as well.

### [Quickdoc](https://github.com/borkdude/quickdoc)

Quickdoc is a tool to generate documentation from namespace/var analysis done by
clj-kondo. It's fast and spits out an `API.md` file in the root of your project,
so you can immediately view it on Github. It has undergone significant
improvements in the last two months. I'm using quickdoc myself in several
projects.

### [Nbb](https://github.com/babashka/nbb)

Scripting in Clojure on Node.js using SCI.

[Changelog](https://github.com/babashka/nbb/blob/main/CHANGELOG.md)

Added `edamame.core`, `cljs.math`, nREPL improvements and now has significant
faster startup due to an improvement in [SCI](https://github.com/babashka/sci).

### [Clojure-lsp](https://github.com/clojure-lsp/clojure-lsp)

Clojure/Script Language Server (LSP) implementation.

This project is driven by the static analysis done by
[clj-kondo](https://github.com/clj-kondo/clj-kondo) and used by many people to
get IDE-like features in editors like emacs and VSCode.

I added support for Apple Silicon using [Cirrus CI](https://cirrus-ci.org/).

### [Babashka](https://github.com/babashka/babashka)

Native, fast starting Clojure interpreter for scripting.

[Changelog](https://github.com/babashka/babashka/blob/master/CHANGELOG.md)

Two new version of babashka were released:

0.8.2 and 0.8.156. The last segment of the version number now indicates the
release count, so the last release is the 156th release.

Babashka now also has a new Apple Silicon binary built on [Cirrus
CI](https://cirrus-ci.org/).  What is very exciting is that babashka can now
execute [schema](https://github.com/plumatic/schema) from source. Compatibility
with [malli](https://github.com/metosin/malli/pull/718) is underway.

### [Clj-kondo](https://github.com/clj-kondo/clj-kondo)

A linter for Clojure code that sparks joy.

[Changelog](https://github.com/clj-kondo/clj-kondo/blob/master/CHANGELOG.md)

New linters:

- [`:warn-on-reflection`](https://github.com/clj-kondo/clj-kondo/blob/master/doc/linters.md#warn-on-reflection)
- [`:redundant-call`](https://github.com/clj-kondo/clj-kondo/blob/master/doc/linters.md#redundant-call)
- [`:discouraged-namespace`](https://github.com/clj-kondo/clj-kondo/blob/master/doc/linters.md#discouraged-namespace)

Clj-kondo now also has a new Apple Silicon binary built on [Cirrus
CI](https://cirrus-ci.org/).

### [SCI](https://github.com/babashka/sci)

Configurable Clojure interpreter suitable for scripting and Clojure DSLs.
Powering babashka, nbb, joyride and many other projects.

[Changelog](https://github.com/babashka/sci/blob/master/CHANGELOG.md)

New releases: 0.3.5 - 0.3.32

Highlights:

- New `sci.async` namespace for asynchronous loading of code
- Reduce advanced compiled JS output with about 20% (~900kb -> ~740kb)
- Many improvements to `defrecord`

### [SCI configs](https://github.com/babashka/sci.configs)

A collection of ready to be used SCI configurations.

This project contains configurations for reagent, promesa, etc. and are used in
nbb, clerk and other projects.

A recent addition was a configuration for `cljs.test` which is now shared by nbb
and [joyride](https://github.com/BetterThanTomorrow/joyride).

### [Process](https://github.com/babashka/process)

[Changelog](https://github.com/babashka/process/blob/master/CHANGELOG.md)

New releases: 0.1.2 - 0.1.4

Highlights:

Support `exec` call in GraalVM `native-images` - this means you can replace the current process with another one.

### [Scittle](https://github.com/babashka/scittle)

The Small Clojure Interpreter exposed for usage in browser script tags.

Added support developing CLJS via nREPL. See [docs](https://github.com/babashka/scittle/tree/main/doc/nrepl).

### [Etaoin](https://github.com/clj-commons/etaoin)

Pure Clojure Webdriver protocol implementation.

This project is now compatible with babashka! Most of the work on this project
was done by Lee Read. If you appreciate his work on this, or other projects like
[rewrite-clj](https://github.com/clj-commons/rewrite-clj), consider
[sponsoring](https://github.com/sponsors/lread) him.

### Misc

Brief mentions of miscellaneous other projects I worked on:

- [nbb-action-example](https://github.com/borkdude/nbb-action-example) - example of how to build a Github Action with [nbb](https://github.com/babashka/nbb).
- [joyride](https://github.com/BetterThanTomorrow/joyride) - Making VS Code Hackable since 2022
- [dynaload](https://github.com/borkdude/dynaload) - The dynaload logic from clojure.spec.alpha as a library
- [deps.clj](https://github.com/borkdude/deps.clj) - A faithful port of the clojure CLI bash script to Clojure.
- [fs](https://github.com/babashka/fs) - File system utility library for Clojure
- [fly-io-clojure](https://github.com/borkdude/fly_io_clojure) - A fly.io example for Clojure. Also see examples for [babashka](https://github.com/babashka/babashka/tree/master/doc/fly_io) and [nbb](https://github.com/babashka/nbb/tree/main/doc/fly_io).
- [pod-babashka-etaoin](https://github.com/babashka/pod-babashka-etaoin) - Babashka pod wrapping Etaoin
- [pod-babashka-fswatcher](https://github.com/babashka/pod-babashka-fswatcher) - Babashka filewatcher pod
