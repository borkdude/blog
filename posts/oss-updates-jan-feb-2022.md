Title: OSS Updates of January - February 2022
Date: 2022-02-27
Tags: clojure, oss updates

In this post I'll give updates about open source I worked on during January and February 2022.

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

## [Babashka](https://github.com/babashka/babashka)

Native, fast starting Clojure interpreter for scripting.

Published versions: 0.7.4 - 0.7.6.

Read the changelog [here](https://github.com/babashka/babashka/blob/master/CHANGELOG.md).

Highlights:

- Add new namespace from clojure 1.11: `clojure.math`
- Performance improvements (see SCI).
- Compatibility with more Clojure libraries and lots of small bug fixes.

I also submitted a PR to [cli-matic](https://github.com/l3nz/cli-matic) to fix
the long standing issue about babashka compatibility.

Please leave some feedback about babashka in the [Q1 Survey](https://forms.gle/ko3NjDg2SwXeEoNQ9)!

## [Clj-kondo](https://github.com/clj-kondo/clj-kondo)

A linter for Clojure (code) that sparks joy.

Published versions: 2022.01.13, 2022.01.15, 2022.02.09.

Read the changelog [here](https://github.com/clj-kondo/clj-kondo/blob/master/CHANGELOG.md).

Highlights:

- Maybe new linters added: `:conflicting-fn-arity`, `:clj-kondo-config`,
  `:reduce-without-init`, `:redundant-fn-wrapper`, linting for `bb.edn` config
  files.
- Improvements with regards to type inferences and map results
- Contributed to clj-kondo config in
  [better-cond](https://github.com/Engelberg/better-cond/tree/master/resources/clj-kondo.exports/better-cond/better-cond)
  so everyone who uses the library gets correct and useful linting feedback.

## [SCI](https://github.com/babashka/sci)

Configurable Clojure interpreter suitable for scripting and Clojure DSLs.

Published versions: v0.2.9 - v0.3.1.

Read the changelog [here](https://github.com/babashka/sci/blob/master/CHANGELOG.md).

- Fix compatibility with Graal.js
- Performance improvements for loops and vararg function calls.
- JS interop fixes / improvements

## [Nbb](https://github.com/babashka/nbb)

Ad-hoc CLJS scripting on Node.js using SCI.

Published versions: v0.1.1 - v0.2.0.

Read the changelog [here](https://github.com/babashka/nbb/blob/main/CHANGELOG.md).

Highlights:

- See SCI improvements.
- Bundled library updates

## [Scittle](https://github.com/babashka/scittle) The Small Clojure Interpreter exposed for usage in browser script tags

Published versions: v0.1.0 - v0.1.2.

Read the changelog [here](https://github.com/babashka/scittle/blob/main/CHANGELOG.md).

Highlights:

- Upgrade SCI with performance and JS interop improvements
- Expose more Reagent features

## [Obb](https://github.com/babashka/obb)

Ad-hoc ClojureScript scripting of Mac applications via Apple's Open Scripting
Architecture.

Published versions: 0.0.1 - 0.0.2

Read the changelog [here](https://github.com/babashka/obb/blob/main/CHANGELOG.md).

Highlights:

- This is a new project!

## [Fs](https://github.com/babashka/fs)

File system utility library for Clojure.

Published versions: 0.1.3.

Read the changelog [here](https://github.com/babashka/fs/blob/master/CHANGELOG.md).

Highlights:

- New functions `create-temp-file` and `zip`
- Compatibility with `com.google.cloud/google-cloud-nio`

## [Process](https://github.com/babashka/process)

Clojure wrapper for `java.lang.ProcessBuilder`.

Published versions: 0.1.1.
Read the changelog [here](https://github.com/babashka/process/blob/master/CHANGELOG.md).

Highlights:

- Support appending to files in addition to overwriting them.

## [Pod-babashka-go-sqlite-3](https://github.com/babashka/pod-babashka-go-sqlite3)

A babashka pod for interacting with sqlite3.

Released versions: 0.1.0.

Highlights:

- Bump sqlite version and support single string rather than wrapped in vector.

## [Pod-babashka-buddy](https://github.com/babashka/pod-babashka-buddy)

A pod around buddy core (Cryptographic Api for Clojure).

Released versions: 0.1.0.

Highlights: expose more namespaces.

## [Pod-babashka-aws](https://github.com/babashka/pod-babashka-aws)

AWS pod wrapping the Cognitect aws-api library.

Released versions: 0.1.1-0.1.2.

Read the changelog [here](https://github.com/babashka/pod-babashka-aws/blob/main/CHANGELOG.md).

Highlights:

- Provide aarch64 binary.
