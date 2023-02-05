Title: OSS updates of January 2023
Date: 2023-01-29
Tags: clojure, oss updates
Description: My Clojure OSS updates for November and December 2022

In this post I'll give updates about open source I worked on during January 2023.

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
- [Clojurists Together](https://www.clojuriststogether.org/)

If you're used to sponsoring through some other means which isn't listed above, please get in touch.

> **Warning**

> If you are using Github Sponsors and are making payments via Paypal, please
update to a creditcard since Github Sponsors won't support Paypal from February
23rd 2023. Read their statement
[here](https://github.blog/changelog/2023-01-23-github-sponsors-will-stop-supporting-paypal/). If
you are not able to pay via a creditcard, you can still sponsor me via one of
the ways mentioned above.

## Projects

<!--

sources: https://github.com/borkdude
local ~/dev dir (since github doesn't show all repos)

- quickblog*
- babashka *
- babashka/pod-babashka-instaparse 31 commits *
- instaparse.bb *
- nbb *
- http-client *
- neil *
- clj-kondo / clj-kondo-bb / lein clj-kondo *
- edamame *
- https://github.com/borkdude/jna-native-image-sci *
- carve *
- jet *
- deps.clj *
- 4ever-clojure *
- joyride *
- squint / cherry *
- tools-deps-native / tools.bbuild *
- scittle *
- lein2deps *
- pod-babashka-buddy *
- edamame *
- nbb *
- babashka.cli *
- fs *
- process *
- deps.clj *
- sci *

-->

### [Http-client](https://github.com/babashka/http-client)

The new babashka http-client aims to become default HTTP client solution in babashka, mostly replacing [babashka.curl](https://github.com/babashka/babashka.curl).

This month the default client was improved to accept `gzip` and `deflate` as encodings by default, reflecting what `babashka.curl` did.

Also `babashka.http-client` is now available as a built-in namespace in `babashka` v1.1.171 and higher.

### [Babashka](https://github.com/babashka/babashka)

Native, fast starting Clojure interpreter for scripting.

New releases in the past month: 1.0.170 - 1.1.173
Highlights:

- Support for `data_readers.clj(c)`
- Include [http-client](https://github.com/babashka/http-client) as built-in library
- Compatibility with [clojure.tools.namespace.repl/refresh](https://github.com/clojure/tools.namespace)
- Compatibility with [clojure.java.classpath](https://github.com/clojure/java.classpath) (and other libraries which rely on `java.class.path` and `RT/baseLoader`)
- Compatibility with [eftest](https://github.com/weavejester/eftest) test runner (see demo)
- Compatibility with [cljfmt](https://github.com/weavejester/cljfmt)
- Support for `*loaded-libs*` and `(loaded-libs)`
- Support `add-watch` on vars (which adds compatibility with `potemkin.namespaces`)
- BREAKING: make printing of script results explicit with `--prn`

#### Babashka compatibility in external libs

I contributed changes to the following libraries to make them compatible with babashka:

- [cljfmt](https://github.com/weavejester/cljfmt) - A tool for formatting Clojure code
- [carve](https://github.com/borkdude/carve) - Remove unused Clojure vars
- [debux](https://github.com/philoskim/debux) - A trace-based debugging library for Clojure and ClojureScript

Check the [changelog](https://github.com/babashka/babashka/blob/master/CHANGELOG.md) for all the changes!

### [Instaparse-bb](https://github.com/babashka/instaparse-bb)

This is a new project and gives you access to a subset of
[instaparse](https://github.com/Engelberg/instaparse) via a
[pod](https://github.com/babashka/pod-babashka-instaparse).

Instaparse was request a few times to have as a library in babashka and
instaparse-bb is a good first step, without making a decision on that yet. See
the relevant discussion
[here](https://github.com/babashka/babashka/discussions/1335).

### [Carve](https://github.com/borkdude/carve)

Remove unused Clojure vars

In the [0.3.5](https://github.com/borkdude/carve/blob/master/CHANGELOG.md#035) version, Carve got the following updates:

- Upgrade clj-kondo version

- Make babashka compatible by using the [clj-kondo-bb](https://github.com/clj-kondo/clj-kondo-bb) library

- Discontinue the `carve` binary in favor of invocation with babashka.
  Instead you can now install carve with [bbin](https://github.com/babashka/bbin):

  ```
  bbin install io.github.borkdude/carve
  ```

- Implement [babashka.cli](https://github.com/babashka/cli) integration

- Implement `--help`

### [Jet](https://github.com/borkdude/jet)

CLI to transform between JSON, EDN, YAML and Transit using Clojure

Version `0.4.23`:

- [#123](https://github.com/borkdude/jet/issues/123): Add `base64/encode` and `base64/decode`
- Add `jet/paths` and `jet/when-pred`
- Deprecate interactive mode
- Deprecate `--query` in favor of `--thread-last`, `--thread-first` or `--func`

### [Clj-kondo](https://github.com/clj-kondo/clj-kondo)

Static analyzer and linter for Clojure code that sparks joy

Three new releases with many fixes and improvements in the last month. [Check the
changelog](https://github.com/clj-kondo/clj-kondo/blob/master/CHANGELOG.md) for
details.

Some highlights:

- [#1742](https://github.com/clj-kondo/clj-kondo/issues/1742): new linter `:aliased-namespace-var-usage`: warn on var usage from namespaces that were used with `:as-alias`. See [demo](https://twitter.com/borkdude/status/1613524896625340417/photo/1).
- [#1926](https://github.com/clj-kondo/clj-kondo/issues/1926): Add keyword analysis for EDN files. This means you can find references for keywords throughout your project with clojure-lsp, now including in EDN files.
- [#1902](https://github.com/clj-kondo/clj-kondo/issues/1902): provide `:symbols` analysis for navigation to symbols in quoted forms or EDN files. See [demo](https://twitter.com/borkdude/status/1612773780589355008).

The symbol analysis is used from clojure-lsp for which I provided a patch
[here](https://github.com/borkdude/clojure-lsp/commit/f662adab1b17d5dbc3648d6d8208334dc920aa0e).

A new project around clj-kondo is
[clj-kondo-bb](https://github.com/clj-kondo/clj-kondo-bb) which enables you to
use clj-kondo from babashka scripts.

Also [lein-clj-kondo](https://github.com/clj-kondo/lein-clj-kondo) got an update.

### [Fs](https://github.com/babashka/fs)

File system utility library for Clojure.

Fs has gotten a few new functions:

- `unifixy`, to turn a Windows path into a path with Unix-style path
separators. Note that that style is supported by the JVM and this offers a more
reliable way to e.g. match filenames via regexes.
- several `xdg-*-home` helper functions, contributed by [@eval](https://github.com/eval)

See [changelog](https://github.com/babashka/fs/blob/master/CHANGELOG.md#changelog) for more details.

### [Neil](https://github.com/babashka/neil)

A CLI to add common aliases and features to `deps.edn`-based projects.

This month there were several small fixes, one of them being to always pick
stable versions when adding or upgrading libraries. See full
[changelog](https://github.com/babashka/neil/blob/main/CHANGELOG.md) for
details.

### [Quickblog](https://github.com/borkdude/quickblog)

Light-weight static blog engine for Clojure and babashka.

The blog you're currently reading is made with quickblog.

Version
[0.2.3](https://github.com/borkdude/quickblog/blob/main/CHANGELOG.md#023-2023-01-30)
was released with contributions from several people, mostly enabling you to
tweak your own blog even more, while having good defaults.

Instances of quickblog can be seen here:

- [Michiel Borkent's blog](https://blog.michielborkent.nl)
- [Josh Glover's blog](https://jmglov.net/blog)
- [Jeremy Taylor's blog](https://jdt.me/strange-reflections.html)
- [Luc Engelen's blog](https://blog.cofx.nl/) ([source](https://github.com/cofx22/blog))
- [Rattlin.blog](https://rattlin.blog/)

If you are also using quickblog, please let me know!


A collection of ready to be used SCI configs for e.g. Reagent, Promesa, Re-frame
and other projects that are used in nbb, joyride, scittle, etc.  See recent
[commits](https://github.com/babashka/sci.configs/commits/main) for what's been
improved.

### [Edamame](https://github.com/borkdude/edamame)

Edamame got a new function: `parse-next+string` which returns the original
string along with the parsed s-expression.

[Changelog](https://github.com/borkdude/edamame/blob/master/CHANGELOG.md)

### [lein2deps](https://github.com/borkdude/lein2deps)

Lein to deps.edn converter

This tool can convert a `project.edn` file to a `deps.edn` file. It even
supports Java compilation and evaluation of code within `project.clj`. There is
now a lein plugin which enables you to sync your `project.clj` with your
`deps.edn` every time you start `lein`. Several other minor enhancements were
made.  See
[changelog](https://github.com/borkdude/lein2deps/blob/main/CHANGELOG.md).

### [4ever-clojure](https://github.com/oxalorg/4ever-clojure)

I added the ability to build and deploy 4ever-clojure to Github Actions. Every
time a commit is merged, the site is automatically updated.

### Brief mentions

The following projects also got updates so I'll briefly mention them as well:

- [Jna-native-image-sci](https://github.com/borkdude/jna-native-image-sci): Compile a program that uses JNA to native-image and allow dynamic evaluation using [SCI](https://github.com/babashka/sci)!
- [Deps.clj](https://github.com/borkdude/deps.clj): A faithful port of the clojure CLI bash script to Clojure
- [Joyride](https://github.com/BetterThanTomorrow/joyride): VSCode CLJS scripting and REPL (via [SCI](https://github.com/babashka/sci))
- [Squint](https://github.com/squint-cljs/squint): CLJS _syntax_ to JS compiler
- [Tools-deps-native](https://github.com/babashka/tools-deps-native): Run tools.deps as a native binary
- [Tools.bbuild](https://github.com/babashka/tools.bbuild): Library of functions for building Clojure projects
- [Scittle](https://github.com/babashka/scittle): Execute Clojure(Script) directly from browser script tags via SCI
- [Pod-babashka-buddy](https://github.com/babashka/pod-babashka-buddy): A pod around buddy core (Cryptographic Api for Clojure).
- [Nbb](https://github.com/babashka/nbb): Scripting in Clojure on Node.js using SCI
- [CLI](https://github.com/babashka/cli)
- [Process](https://github.com/babashka/process)
- [SCI](https://github.com/babashka/sci)
- [Scittle](https://github.com/babashka/scittle)
- [Sci.configs](https://github.com/babashka/sci.configs)
