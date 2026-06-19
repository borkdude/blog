Title: OSS updates May and June 2026
Date: 2026-07-06
Tags: clojure, oss updates
Description: My Clojure OSS updates for May and June 2026
Preview: true

In this post I'll give updates about open source I worked on during May and June 2026.

To see previous OSS updates, go [here](https://blog.michielborkent.nl/tags/oss-updates.html).

## Sponsors

I'd like to thank all the sponsors and contributors that make this work
possible. Without you, the below projects would not be as mature or wouldn't
exist or be maintained at all! So a sincere thank you to everyone who
contributes to the sustainability of these projects.

<img alt="gratitude" src="https://emoji.slack-edge.com/T03RZGPFR/gratitude/f8716bb6fb7e5249.png" width="50px" text-align="center">

Current top tier sponsors:

- [Clojurists Together](https://clojuriststogether.org/)
- [Roam Research](https://roamresearch.com/)
- [Nextjournal](https://nextjournal.com/)
- [Nubank](https://nubank.com.br)

Open the details section for more info about sponsoring.

<details>
<summary>Sponsor info</summary>

If you want to ensure that the projects I work on are sustainably maintained,
you can sponsor this work in the following ways. Thank you!

- [Github Sponsors](https://github.com/sponsors/borkdude)
- The [Babashka](https://opencollective.com/babashka) or [Clj-kondo](https://opencollective.com/clj-kondo) OpenCollective
- [Ko-fi](https://ko-fi.com/borkdude)
- [Patreon](https://www.patreon.com/borkdude)
- [Clojurists Together](https://www.clojuriststogether.org/)

</details>

## Updates

<!-- NOTE: write a babashka conf 2026 recap here. It happened May 8 at OBA
     Oosterdok in Amsterdam with David Nolen's keynote, followed by Dutch
     Clojure Days. How it went, talks worth highlighting, thanks to sponsors /
     attendees / speakers / volunteers. -->

<!-- PROSE: intro paragraph for the period - what got the most attention. The
     big one this cycle is babashka CLI: automatic --help and shell completions.
     Link to the dedicated post once it's published. -->

Updates per project below. Bullets are highlights; see each project's `CHANGELOG.md` for the full list.

<!-- PROSE: fill each project's bullets from its CHANGELOG for May-Jun 2026.
     Add/remove projects based on actual activity this cycle. Skeleton below
     carries the recurring projects from previous updates. -->

- [babashka](https://github.com/babashka/babashka): native, fast starting Clojure interpreter for scripting.
  <!-- PROSE: releases + highlights -->
  - [Full changelog](https://github.com/babashka/babashka/blob/master/CHANGELOG.md)

- [babashka CLI](https://github.com/babashka/cli): Turn Clojure functions into CLIs!
  <!-- PROSE: headline of the cycle - automatic `--help` for dispatch CLIs and
       shell completions (bash/zsh/fish/powershell/nushell). Link to the
       dedicated blog post. Also: completions register the script's own file
       name so ./script.clj completes; non-ASCII program names supported. -->
  - [Full changelog](https://github.com/babashka/cli/blob/master/CHANGELOG.md)

- [SCI](https://github.com/babashka/sci): Configurable Clojure/Script interpreter suitable for scripting
  <!-- PROSE: highlights -->
  - [Full changelog](https://github.com/babashka/sci/blob/master/CHANGELOG.md)

- [clj-kondo](https://github.com/clj-kondo/clj-kondo): static analyzer and linter for Clojure code that sparks joy.
  <!-- PROSE: releases + highlights -->
  - [Full changelog](https://github.com/clj-kondo/clj-kondo/blob/master/CHANGELOG.md)

- [squint](https://github.com/squint-cljs/squint): CLJS _syntax_ to JS compiler
  <!-- PROSE: releases + highlights -->
  - [Full changelog](https://github.com/squint-cljs/squint/blob/main/CHANGELOG.md)

- [cherry](https://github.com/squint-cljs/cherry): Experimental ClojureScript to ES6 module compiler
  <!-- PROSE: highlights -->

- [scittle](https://github.com/babashka/scittle): Execute Clojure(Script) directly from browser script tags via SCI
  <!-- PROSE: highlights -->

- [nbb](https://github.com/babashka/nbb): Scripting in Clojure on Node.js using SCI
  <!-- PROSE: highlights -->

- [fs](https://github.com/babashka/fs): file system utility library for Clojure
  <!-- PROSE: highlights -->

- [edamame](https://github.com/borkdude/edamame): configurable EDN and Clojure parser with location metadata and more
  <!-- PROSE: highlights -->

- [quickblog](https://github.com/borkdude/quickblog): light-weight static blog engine for Clojure and babashka
  <!-- PROSE: highlights -->

- [deps.clj](https://github.com/borkdude/deps.clj): a faithful port of the clojure CLI bash script to Clojure
  <!-- PROSE: highlights -->

<!-- PROSE: Contributions to third party projects (if any) -->

## Other projects

These are (some of the) other projects I'm involved with but little to no activity
happened in the past two months.

<details>
<summary>Click for more details</summary>

- [scittle](https://github.com/babashka/scittle): Execute Clojure(Script) directly from browser script tags via SCI
- [pod-babashka-go-sqlite3](https://github.com/babashka/pod-babashka-go-sqlite3): A babashka pod for interacting with sqlite3
- [unused-deps](https://github.com/borkdude/unused-deps): Find unused deps in a clojure project
- [pod-babashka-fswatcher](https://github.com/babashka/pod-babashka-fswatcher): babashka filewatcher pod
- [sci.nrepl](https://github.com/babashka/sci.nrepl): nREPL server for SCI projects that run in the browser
- [babashka.nrepl-client](https://github.com/babashka/nrepl-client)
- [http-server](https://github.com/babashka/http-server): serve static assets
- [sci.configs](https://github.com/babashka/sci.configs): A collection of ready to be used SCI configs.
- [html](https://github.com/borkdude/html): Html generation library inspired by squint's html tag
- [rewrite-edn](https://github.com/borkdude/rewrite-edn): Utility lib on top of rewrite-clj
- [rewrite-clj](https://github.com/clj-commons/rewrite-clj): Rewrite Clojure code and edn
- [tools-deps-native](https://github.com/babashka/tools-deps-native) and [tools.bbuild](https://github.com/babashka/tools.bbuild): use tools.deps directly from babashka
- [bbin](https://github.com/babashka/bbin): Install any Babashka script or project with one command
- [qualify-methods](https://github.com/borkdude/qualify-methods)
  - Initial release of experimental tool to rewrite instance calls to use fully
    qualified methods (Clojure 1.12 only)
- [tools](https://github.com/borkdude/tools): a set of [bbin](https://github.com/babashka/bbin/) installable scripts
- [babashka.json](https://github.com/babashka/json): babashka JSON library/adapter
- [speculative](https://github.com/borkdude/speculative)
- [squint-macros](https://github.com/squint-cljs/squint-macros): a couple of
  macros that stand-in for
  [applied-science/js-interop](https://github.com/applied-science/js-interop)
  and [promesa](https://github.com/funcool/promesa) to make CLJS projects
  compatible with squint and/or cherry.
- [lein-clj-kondo](https://github.com/clj-kondo/lein-clj-kondo): a leiningen plugin for clj-kondo
- [http-kit](https://github.com/http-kit/http-kit): Simple, high-performance event-driven HTTP client+server for Clojure.
- [jet](https://github.com/borkdude/jet): CLI to transform between JSON, EDN, YAML and Transit using Clojure
- [lein2deps](https://github.com/borkdude/lein2deps): leiningen to deps.edn converter
- [cljs-showcase](https://github.com/borkdude/cljs-showcase): Showcase CLJS libs using SCI
- [babashka.book](https://github.com/babashka/book): Babashka manual
- [pod-babashka-buddy](https://github.com/babashka/pod-babashka-buddy): A pod around buddy core (Cryptographic Api for Clojure).
- [gh-release-artifact](https://github.com/borkdude/gh-release-artifact): Upload artifacts to Github releases idempotently
- [carve](https://github.com/borkdude/carve) - Remove unused Clojure vars
- [4ever-clojure](https://github.com/oxalorg/4ever-clojure) - Pure CLJS version of 4clojure, meant to run forever!
- [pod-babashka-lanterna](https://github.com/babashka/pod-babashka-lanterna): Interact with clojure-lanterna from babashka
- [joyride](https://github.com/BetterThanTomorrow/joyride): VSCode CLJS scripting and REPL (via [SCI](https://github.com/babashka/sci))
- [clj2el](https://borkdude.github.io/clj2el/): transpile Clojure to elisp
- [deflet](https://github.com/borkdude/deflet): make let-expressions REPL-friendly!
- [deps.add-lib](https://github.com/borkdude/deps.add-lib): Clojure 1.12's add-lib feature for leiningen and/or other environments without a specific version of the clojure CLI
- [process](https://github.com/babashka/process): Clojure library for shelling out / spawning sub-processes
- [parmezan](https://github.com/borkdude/parmezan): fixes unbalanced or unexpected parens or other delimiters in Clojure files
- [reagami](https://github.com/borkdude/reagami): A minimal zero-deps Reagent-like for Squint and CLJS

</details>
