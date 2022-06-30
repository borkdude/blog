In this post I'll give updates about open source I worked on during May and June 2022.

## Sponsors

But first off, I'd like to thank all the sponsors and contributors that make
this work possible. To name a few: [Clojurists
Together](https://clojuriststogether.org/), [Roam
Research](https://roamresearch.com/), [Adgoji](https://www.adgoji.com/),
[Cognitect](https://www.cognitect.com/),
[Nextjournal](https://nextjournal.com/).

If you'd like to sponsor this work or see who else is sponsoring, you can do so via the
following channels:

- [Github Sponsors](https://github.com/sponsors/borkdude)
- [OpenCollective](https://opencollective.com/babashka) (also see the [clj-kondo](https://opencollective.com/clj-kondo) one)
- [Clojurists Together](https://www.clojuriststogether.org/)

## Projects

### Babashka CLI

One of my newest projects is [babashka CLI](https://github.com/babashka/cli). It
aims to close the gap between good command line UX and calling Clojure
functions. It is very much inspired by the clojure CLI, but solves a problem
which sometimes causes frustration, especially among Windows users: having to
use quotes in a shell. It also offers support for subcommands. One project
benefitting from that is [neil](https://github.com/babashka/neil). I blogged
about babashka CLI [here](https://blog.michielborkent.nl/babashka-cli.html).

### Http-server

Another new project is [http-server](https://github.com/babashka/http-server)
which can be used in Clojure JVM and babashka to serve static assets in an http
server.

### Clj-kondo workshop

In June I had the honor to give a workshop about [clj-kondo](https://github.com/clj-kondo/clj-kondo) at [ClojureD](https://clojured.de/).
You can work through the material yourself if you'd like [here](https://github.com/clj-kondo/hooks-workshop-clojured-2022). 
Feel free to join the clj-kondo channel on Clojurians Slack for questions. Here are some [pictures](https://twitter.com/borkdude/status/1542521071588347905) from the event.

### Jet

CLI to transform between JSON, EDN and Transit, powered with a minimal query
language.

After a long while, [jet](https://github.com/borkdude/jet) finally got another
update. The binary is now available for Apple Silicon and adds
[specter](https://github.com/redplanetlabs/specter) as part of the standard
library for transforming data. Also, the output is colorized and pretty-printed
using [puget](https://github.com/greglook/puget) now.

### [Edamame](https://github.com/borkdude/edamame)

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

Added `edamame.core`, `cljs.math`, nREPL improvements and now has significant
faster startup due to an improvement in [SCI](https://github.com/babashka/sci).

### [Clojure-lsp](https://github.com/clojure-lsp/clojure-lsp)

Clojure/Script Language Server (LSP) implementation.

This project is driven by the static analysis done by
[clj-kondo](https://github.com/clj-kondo/clj-kondo) and used by many people to
get IDE-like features in editors like emacs and VSCode.

I added support for Apple Silicon using [Cirrus CI](https://cirrus-ci.org/).

### [Babashka](https://github.com/babashka/babashka)

Two new version of babashka were released:

0.8.2 and 0.8.156. The last segment of the version number now indicates the
release count, so the last release is the 156th release.

Babashka now also has a new Apple Silicon binary built on [Cirrus
CI](https://cirrus-ci.org/).  What is very exciting is that babashka can now
execute [schema](https://github.com/plumatic/schema) from source. Compatibility
with [malli](https://github.com/metosin/malli/pull/718) is underway.

### [Clj-kondo](https://github.com/clj-kondo/clj-kondo)

A linter for Clojure code that sparks joy.

New linters:

- [`:warn-on-reflection`](https://github.com/clj-kondo/clj-kondo/blob/master/doc/linters.md#warn-on-reflection)
- [`:redundant-call`](https://github.com/clj-kondo/clj-kondo/blob/master/doc/linters.md#redundant-call)
- [`:discouraged-namespace`](https://github.com/clj-kondo/clj-kondo/blob/master/doc/linters.md#discouraged-namespace)

Clj-kondo now also has a new Apple Silicon binary built on [Cirrus
CI](https://cirrus-ci.org/).


- [SCI](https://github.com/babashka/sci)

- [nbb-action-example](https://github.com/borkdude/nbb-action-example)
- [sci.configs](https://github.com/babashka/sci.configs)

- [process](https://github.com/babashka/process)
- [dynaload](https://github.com/borkdude/dynaload)

- [clojure-lsp](https://github.com/clojure-lsp/clojure-lsp)
- [joyride](https://github.com/BetterThanTomorrow/joyride)
- [deps.clj](https://github.com/borkdude/deps.clj)
- [fs](https://github.com/babashka/fs)
- [scittle](https://github.com/babashka/scittle)
- [sci.nrepl](https://github.com/babashka/sci.nrepl)

- [fly-io-clojure](https://github.com/borkdude/fly_io_clojure)
- [pod-babashka-etaoin](https://github.com/babashka/pod-babashka-etaoin)
- [pod-babashka-fswatcher](https://github.com/babashka/pod-babashka-fswatcher)
