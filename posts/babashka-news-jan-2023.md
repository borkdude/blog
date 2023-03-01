Title: Babashka news of January 2023
Date: 2023-02-05
Tags: clojure, babashka, babashka news

If you want to help me keep track of babashka-related news, please contribute to
[news.md](https://github.com/babashka/babashka/blob/master/doc/news.md) or use
the `#babashka` hashtag on
[Twitter](https://twitter.com/search?q=%28%23babashka%20OR%20babashka%28&src=typed_query&f=live)
or [Mastodon](https://mastodon.social/tags/babashka).

### Releases

New releases in the past month: 1.0.170 - 1.1.173
Release highlights:

- Support for `data_readers.clj(c)`
- Include [http-client](https://github.com/babashka/http-client) as built-in library
- Compatibility with [clojure.tools.namespace.repl/refresh](https://github.com/clojure/tools.namespace)
- Compatibility with [clojure.java.classpath](https://github.com/clojure/java.classpath) (and other libraries which rely on `java.class.path` and `RT/baseLoader`)
- Compatibility with [eftest](https://github.com/weavejester/eftest) test runner (see demo)
- Compatibility with [cljfmt](https://github.com/weavejester/cljfmt)
- Support for `*loaded-libs*` and `(loaded-libs)`
- Support `add-watch` on vars (which adds compatibility with `potemkin.namespaces`)
- BREAKING: make printing of script results explicit with `--prn`

### Events

- [Babashka Workshop](https://clojure.stream/workshops/babashka) at ClojureStream with Rahul De
- [Blambda! The sound of Babashka and Lambda colliding](https://www.meetup.com/sthlm-clj/events/291204199/?utm_medium=referral&utm_campaign=share-btn_savedevents_share_modal&utm_source=twitter): sthlm.clj (Stockholm, Sweden)

### Articles

One new book this month:

- [Babooka: write command line Clojure](https://www.braveclojure.com/quests/babooka/) by Daniel Higginbotham

and several blog posts:

- [Blambda analyses sites](https://jmglov.net/blog/2023-01-04-blambda-analyses-sites.html) by Josh Glover
- [Babashka: How GraalVM Helped Create a Fast-Starting Scripting Environment for Clojure](https://logico-jp.io/2023/01/07/babashka-how-graalvm-helped-create-a-fast-starting-scripting-environment-for-clojure/) in Japanese
- [The wizard of HOP - How we built the web based HOP CLI Settings Editor using Babashka and Scittle](https://www.gethop.dev/post/the-wizard-of-hop-how-we-built-the-web-based-hop-cli-settings-editor-using-babashka-and-scittle) by Bingen Galartza
- [Simple TUIs with Babashka and Gum](https://rattlin.blog/bbgum.html) by Rattlin.blog
- [Babashka And Dialog Part Ii: Announcing The Bb-Dialog Library](https://www.pixelated-noise.com/blog/2023/01/20/bb-dialog-announcement/index.html) by A.C. Danvers
- [Re-Writing a GlobalProtect OpenConnect VPN Connect script in Babashka](https://tech.toryanderson.com/2023/01/14/re-writing-a-globalprotect-openconnect-vpn-connect-script-in-babashka/) by Tory Anderson

### Projects

Projects that were new, had updates or were made compatible with babashka:

- [asdf-babashka](https://github.com/pitch-io/asdf-babashka): babashka plugin for the asdf version manager
- [babashka-htmx-todoapp](https://github.com/prestancedesign/babashka-htmx-todoapp): Quick example of a todo list SPA using Babashka and HTMX
- [bblgum](https://github.com/lispyclouds/bblgum): An extremely tiny and simple wrapper around charmbracelet/gum
- [bb-dialog](https://github.com/pixelated-noise/bb-dialog): A simple wrapper library for working with dialog from Babashka
- [carve](https://github.com/borkdude/carve): Remove unused Clojure vars
- [chr](https://github.com/ThaddeusJiang/chr): native commands history report for the default terminal users
- [clj-kondo-bb](https://github.com/clj-kondo/clj-kondo-bb): Invoke clj-kondo from babashka scripts!
- [cljfmt](https://github.com/weavejester/cljfmt): A tool for formatting Clojure code
- [drepl](https://github.com/claytn/drepl): Node JS dependency-repl. The node repl you already know with easy library installations
- [instaparse-bb](https://github.com/babashka/instaparse-bb): Wrapper library aroud pod-babashka-instaparse
- [lein2deps](https://github.com/borkdude/lein2deps): Lein project.clj to deps.edn converter
- [neil](https://github.com/babashka/neil): A CLI to add common aliases and features to deps.edn-based projects
- [obsidian-babashka](https://github.com/filipesilva/obsidian-babashka): Run Obsidian Clojure(Script) codeblocks in Babashka
- [pod-babashka-buddy](https://github.com/babashka/pod-babashka-buddy): A pod around buddy core (Cryptographic Api for Clojure)
- [quickblog](https://github.com/borkdude/quickblog): Light-weight static blog engine for Clojure and babashka
- [solenoid](https://github.com/adam-james-v/solenoid): A small clojure tool for making little control UIs while using the REPL!
- [tools.bbuild](https://github.com/babashka/tools.bbuild): babashka version of tools.build
- [weather](https://gist.github.com/yogthos/f86e63b856e1413180b2262024ece977): command line util for grabbing current weather for a city using OpenWeather API
