{:title "OSS Updates of March - April 2022", :categories #{"clojure"}, :date "2022-04-29"}

In this post I'll give updates about open source I worked on during March and April 2022.

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

## [Clj-kondo](https://github.com/clj-kondo/clj-kondo)

A linter for Clojure (code) that sparks joy.

Published versions: 2022.03.04, 2022.03.08, 2022.03.09, 2022.04.08, 2022.04.23, 2022.04.25

Read the changelog [here](https://github.com/clj-kondo/clj-kondo/blob/master/CHANGELOG.md).

Highlights:

- New linters: `:namespace-mismatch`, `:non-arg-vec-return-type-hint`, `:keyword-binding`, `:discouraged-var`
- More analysis output for Java classes, protocols and multi-methods

## [Babashka](https://github.com/babashka/babashka)

Native, fast starting Clojure interpreter for scripting.

Published versions: 0.7.7, 0.7.8, 0.8.0, 0.8.1

Read the changelog [here](https://github.com/babashka/babashka/blob/master/CHANGELOG.md).

Highlights:

- Declarative pod configuration in `bb.edn`
- Compatibility with [specter](https://github.com/redplanetlabs/specter)
- Fixes for `reify`, calls to interface default methods are now correctly implemented

## [SCI](https://github.com/babashka/sci)

Configurable Clojure interpreter suitable for scripting and Clojure DSLs.

Published versions: 0.3.2, 0.3.3, 0.3.4

Read the changelog [here](https://github.com/babashka/sci/blob/master/CHANGELOG.md).

Summary: many small incremental improvements.

## [Nbb](https://github.com/babashka/nbb)

Ad-hoc CLJS scripting on Node.js using SCI.

Published versions: v0.2.1 - v0.3.10.

Read the changelog [here](https://github.com/babashka/nbb/blob/main/CHANGELOG.md).

Highlights:

- [Features](https://github.com/babashka/nbb/blob/main/doc/dev.md#features) mechanism which allows you to re-package nbb with your favorite CLJS libraries.
- Videos: [London Clojurians](https://youtu.be/7DQ0ymojfLg), [On the Code Again](https://youtu.be/_-G9EKaAyuI)
- `nbb.core/await` REPL helper to "block" on promises and get their results
- nREPL completion improvements

## [Joyride](https://github.com/BetterThanTomorrow/joyride)

Modify VSCode by executing ClojureScript (SCI) code in your REPL and/or run
scripts via keyboard shortcuts.

Read the changelog [here](https://github.com/BetterThanTomorrow/joyride/blob/master/CHANGELOG.md).

This is a new project!

## [Grasp](https://github.com/borkdude/grasp)

Grep Clojure code using clojure.spec regexes.

Highlights:

- Support `:keep-fn`, allowing you to keep track of the conformed value in one
  go

## [Obb](https://github.com/babashka/obb)

Ad-hoc ClojureScript scripting of Mac applications via Apple's Open Scripting
Architecture.

Published versions: 0.0.3

Read the changelog [here](https://github.com/babashka/obb/blob/main/CHANGELOG.md).

## [Neil](https://github.com/babashka/neil)

A CLI to add common aliases and features to deps.edn-based projects.

Highlights:

- List available versions
- Dependency search
- New `license` subcommand

## [Rewrite-edn](https://github.com/borkdude/rewrite-edn)

Utility lib on top of rewrite-clj with common operations to update EDN while
preserving whitespace and comments.

Read the changelog [here](https://github.com/borkdude/rewrite-edn/blob/master/CHANGELOG.md).

## [Tools.misc](https://github.com/clj-easy/tools.misc)

Tools library similar to clojure.tools.logging but for misc. tools.

So far this is just an experiment, but join the discussion if you want.

## [Fs](https://github.com/babashka/fs)

File system utility library for Clojure.

Published versions: 0.1.4.

Read the changelog [here](https://github.com/babashka/fs/blob/master/CHANGELOG.md).
