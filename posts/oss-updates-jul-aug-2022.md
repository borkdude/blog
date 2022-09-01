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

Cherry is similar to ClavaScript, but it does emit CLJS with the persistent data
structures. The compiler code is almost identical, but with a few tweaks here
and there. E.g. `{:a 1}` in Clava means: a JS object with a `"a"` key and `1`
value, but in cherry, `{:a 1}` means the same as in CLJS. This project should be
considered experimental.
