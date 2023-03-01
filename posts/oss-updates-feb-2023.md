Title: OSS updates February 2023
Date: 2023-03-01
Tags: clojure, oss updates
Description: My Clojure OSS updates for February 2023

In this post I'll give updates about open source I worked on during February 2023.

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

> **Attention**
<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/1/17/Warning.svg/260px-Warning.svg.png" width="20px">

> If you are using Github Sponsors and are making payments via Paypal, please
update to a creditcard since Github Sponsors won't support Paypal from February
23rd 2023. Read their statement
[here](https://github.blog/changelog/2023-01-23-github-sponsors-will-stop-supporting-paypal/). If
you are not able to pay via a creditcard, you can still sponsor me via one of
the ways mentioned above.

## Projects

<!--

sources: https://github.com/borkdude
local ~/dev and ~/dev/babashka dir (since github doesn't show all repos)

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

### [Babashka](https://github.com/babashka/babashka)

compat: https://github.com/greglook/clj-multiformats
compat: https://github.com/lambdaisland/kaocha

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
- [debux](https://github.com/philoskim/debux) - A trace-based debugging library for Clojure and ClojureScript

Check the [changelog](https://github.com/babashka/babashka/blob/master/CHANGELOG.md) for all the changes!

### [Http-client](https://github.com/babashka/http-client)

The new babashka http-client project mostly replaces [babashka.curl](https://github.com/babashka/babashka.curl).

This month support for `:multipart` uploads was added, mostly inspired by [hato](https://github.com/gnarroway/hato)'s implementation.

### [Clj-kondo](https://github.com/clj-kondo/clj-kondo)

Static analyzer and linter for Clojure code that sparks joy

TODO [Check the
changelog](https://github.com/clj-kondo/clj-kondo/blob/master/CHANGELOG.md) for
details.

Some highlights:

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

### [Fs](https://github.com/babashka/fs)

File system utility library for Clojure.

Fs has gotten a few new functions:

- `unifixy`, to turn a Windows path into a path with Unix-style path
separators. Note that that style is supported by the JVM and this offers a more
reliable way to e.g. match filenames via regexes.
- several `xdg-*-home` helper functions, contributed by [@eval](https://github.com/eval)

See [changelog](https://github.com/babashka/fs/blob/master/CHANGELOG.md#changelog) for more details.

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

### [Process](https://github.com/babashka/process)

Clojure library for shelling out / spawning sub-processes

This month I looked into wrapping output of processes with a prefix so when ran in parallel, you can easily distuingish them. A preliminary solution is in [this thread](https://github.com/babashka/process/discussions/102#discussioncomment-4903758).

### TODO

- [pod-babashka-lanterna](https://github.com/babashka/pod-babashka-lanterna): a pod to interact with clojure-lanterna from babashka

Mention [tetris example](https://github.com/borkdude/console-tetris/tree/pod-babashka-lanterna)

- [nbb](https://github.com/babashka/nbb): Scripting in Clojure on Node.js using SCI
changelogs

- [joyride](https://github.com/BetterThanTomorrow/joyride): VSCode CLJS scripting and REPL (via [SCI](https://github.com/babashka/sci))
rewrite-clj

- [cljs-showcase](https://github.com/borkdude/cljs-showcase)

### Brief mentions

The following projects also got updates, mostly in the form of maintenance and
performance improvements. This post would get too long if I had to go into
detail about them, so I'll briefly mention them in random order:

- [CLI](https://github.com/babashka/cli): Turn Clojure functions into CLIs!
- [quickdoc](https://github.com/borkdude/quickdoc): Quick and minimal API doc generation for Clojure
- [process](https://github.com/babashka/process): Clojure library for shelling out / spawning sub-processes
- [rewrite-clj](https://github.com/clj-commons/rewrite-clj): Rewrite Clojure code and edn
- [sql pods](https://github.com/babashka/babashka-sql-pods): babashka pods for SQL databases
- [squint](https://github.com/squint-cljs/squint): CLJS _syntax_ to JS compiler

### Other projects

These are some of the other projects I'm involved with but little to no activity
happened in the past month.

- [carve](https://github.com/borkdude/carve) - Remove unused Clojure vars
- [deps.clj](https://github.com/borkdude/deps.clj): A faithful port of the clojure CLI bash script to Clojure
- [edamame](https://github.com/borkdude/edamame): Configurable EDN/Clojure parser with location metadata
- [cherry](https://github.com/squint-cljs/cherry): Experimental ClojureScript to ES6 module compiler
- [grasp](https://github.com/borkdude/grasp): Grep Clojure code using clojure.spec regexes
- [tools-deps-native](https://github.com/babashka/tools-deps-native): Run tools.deps as a native binary
- [tools.bbuild](https://github.com/babashka/tools.bbuild): Library of functions for building Clojure projects
- [scittle](https://github.com/babashka/scittle): Execute Clojure(Script) directly from browser script tags via SCI
- [pod-babashka-buddy](https://github.com/babashka/pod-babashka-buddy): A pod around buddy core (Cryptographic Api for Clojure).
- [neil](https://github.com/babashka/neil)
- [process](https://github.com/babashka/process): Clojure library for shelling out / spawning sub-processes
- [SCI](https://github.com/babashka/sci): Configurable Clojure/Script interpreter suitable for scripting and Clojure DSLs
- [scittle](https://github.com/babashka/scittle): Execute Clojure(Script) directly from browser script tags via SCI
- [sci.configs](https://github.com/babashka/sci.configs): A collection of ready to be used SCI configs
