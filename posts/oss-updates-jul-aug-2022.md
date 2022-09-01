Title: OSS updates of July - August 2022
Date: 2022-09-01
Tags: clojure, oss updates

In this post I'll give updates about open source I worked on during May and June 2022.

## Sponsors

But first off, I'd like to thank all the sponsors and contributors that make
this work possible! Top sponsors: [Clojurists
Together](https://clojuriststogether.org/), [Roam
Research](https://roamresearch.com/), [Adgoji](https://www.adgoji.com/),
[Cognitect](https://www.cognitect.com/), [Kepler16](https://kepler16.com/),
[Nextjournal](https://nextjournal.com/).

If you want to ensure that the projects I work on are sustainably maintained,
you can sponsor this work via the following organizations:

- [Github Sponsors](https://github.com/sponsors/borkdude)
- [OpenCollective](https://opencollective.com/babashka) (also see the [clj-kondo](https://opencollective.com/clj-kondo) one)
- [Clojurists Together](https://www.clojuriststogether.org/)


## Projects

<!-- https://github.com/borkdude?tab=overview&from=2022-06-01&to=2022-06-30 -->

### [ClavaScript](https://github.com/clavascript/clavascript)

This is a new project: a CLJS syntax to JS compiler for the niche use case where
you want to write JS, but do it using CLJS syntax and tooling
instead. ClavaScript comes with a standard library that resembles CLJS but is
built on bare JS ingredients. As such, Clava comes with the usual JS caveats,
but we can still have our parens.

### [Cherry](https://github.com/clavascript/cherry)

Cherry is similar to ClavaScript, but it does emit CLJS-compatible code (with
the persistent data structures, etc). The compiler code is almost identical to
Clava's, but with a few tweaks here and there. E.g. `{:a 1}` in Clava means: a
JS object with a `"a"` key and `1` value, but in cherry, `{:a 1}` means the same
as in CLJS. The goal of both Clava and Cherry are to reduce friction between
CLJS and JS tooling. Both projects should be considered experimental for
now. Challenges in both Clava and Cherry is the REPL, since both projects
compile to ES6 modules and ES6 module imports are immutable.

On [ClojureDays 2022](https://clojuredays.org/) I will give a talk titled
"ClojureScript reimagined" which will shed more light on both projects.

### [Scittle](https://github.com/babashka/scittle)

Execute Clojure(Script) directly from browser script tags via SCI.
See it in [action](https://babashka.org/scittle/).

Scittle received two new plugins: one for `promesa.core` and one for
`cljs.pprint`. Also error messages were improved.

### [Babashka toolbox](https://babashka.org/toolbox/)

Babashka toolbox is a port of [Clojure
toolbox](https://www.clojure-toolbox.com/) and gives an overview of
bb-compatible libraries and projects.

### [Babashka CLI](https://github.com/babashka/cli)

Turn Clojure functions into CLIs!

Several new options have been added: `:validate`, `:require`, `:restrict`. Also error handling was made more flexible.

Babashka CLI proper is now part of babashka. Also see my blog posts about it:

- [Babashka tasks meets babashka CLI](https://blog.michielborkent.nl/babashka-tasks-meets-babashka-cli.html)
- [Babashka CLI: turn Clojure functions into CLIs](https://blog.michielborkent.nl/babashka-cli.html)

### [Babashka](https://github.com/babashka/babashka)

Native, fast starting Clojure interpreter for scripting.

- Compatibility with [malli](https://github.com/metosin/malli#babashka)
- `-x`: a way to execute functions from the command line using babashka CLI
- Many bugfixes and enhancements

### [Nbb](https://github.com/babashka/nbb)

Scripting in Clojure on Node.js using SCI

- A new [bundle](https://github.com/babashka/nbb/tree/main/doc/bundle#bundle)
  command to bundle nbb scripts to standalone scripts, which can then be
  processed further with e.g. `esbuild` to minify and tree-shake them.
- `nbb.edn`: you can now declare dependencies within this file, e.g. from Clojars and nbb will add them automatically to the classpath, so you can `require` them.
- [Malli](https://github.com/metosin/malli) compatibilty
- Many small bugfixes and improvements

### [Bebo](https://github.com/borkdude/bebo)

Run Clojure scripts on [Deno](https://deno.land/) via SCI.

### [Quickblog](https://github.com/borkdude/quickblog)

Light-weight static blog engine for Clojure and babashka

A lot has been happening in this project, with the help of Josh Glover. Check
out the
[changelog](https://github.com/borkdude/quickblog/blob/main/CHANGELOG.md#changelog). The
blog you're currently reading is made with quickblog.

### [SCI](https://github.com/babashka/sci)

Configurable Clojure interpreter suitable for scripting and Clojure DSLs.

This is the workhorse that powers babashka, nbb, bebo, and many other projects.

Several bugfixes and enhancements were made in the last two months.

### [Neil](https://github.com/babashka/neil)

A CLI to add common aliases and features to deps.edn-based projects.

Neil now has a `new` subcommand which defers to
[deps-new](https://github.com/seancorfield/deps-new). Also `neil test` was added
to run tests using the Cognitect-labs test runner. Much thanks to
[rads](https://github.com/rads) who contributed a lot.

### [Process](https://github.com/babashka/process)

Clojure library for shelling out / spawning subprocesses

Minor updates and fixes.

### [Fs](https://github.com/babashka/fs)

File system utility library for Clojure.

Minor updates and fixes.

### [Pod-babashka-buddy](https://github.com/babashka/pod-babashka-buddy)

A babashka pod around buddy core (Cryptographic Api for Clojure).

The latest release adds wrappers for `buddy.sign.jwt` and provides an `aarch64`
binary.

### [Clj-kondo](https://github.com/clj-kondo/clj-kondo)

Static analyzer and linter for Clojure code that sparks joy

See [changelogs](https://github.com/clj-kondo/clj-kondo/blob/master/CHANGELOG.md#20220803).

### [Dynaload](https://github.com/borkdude/dynaload)

The dynaload logic from clojure.spec.alpha as a library

This library was made compatible with nbb.

### [Sci.configs](https://github.com/babashka/sci.configs)

A collection of ready to be used SCI configs

Moved `cljs.pprint` config from nbb into this project.
