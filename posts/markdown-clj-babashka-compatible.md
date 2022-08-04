Title: Making markdown-clj babashka-compatible
Date: 2021-11-17
Tags: clojure

Recently I migrated the highlighting of Clojure code in this blog from CLJS to
pure [babashka](https://babashka.org/) code. See
[this](writing-clojure-highlighter.html) blog post.

For rendering markdown files to html files I was using
[bootleg](https://github.com/retrogradeorbit/bootleg) as a
[pod](https://github.com/retrogradeorbit/bootleg#babashka-pod-usage). A pod is a
binary which can act as an RPC server for babashka. Learn more about that
[here](https://github.com/babashka/pods).

Today I noticed a [new PR](https://github.com/retrogradeorbit/bootleg/pull/76)
in bootleg. The user who submitted the PR also used bootleg for markdown
compilation in babashka, but bootleg didn't expose an option that the underlying
library, [markdown-clj](https://github.com/yogthos/markdown-clj), supports. The
PR fixes that.

Then I wondered, can babashka run markdown-clj from source, rather than via a
pod? Babashka supports a large subset of Clojure and a large subset of classes
from the JVM. By now it can run a fair share of Clojure libraries from source,
but sometimes minor tweaks are necessary. I looked at the dependencies of
markdown-clj. The only dependency it uses is
[clj-commons/clj-yaml](https://github.com/clj-commons/clj-yaml) which is luckily
included in babashka. If it wasn't, I'm pretty sure that dependency could be
made optional for those that do not need any YAML support in their markdown
compilation. When looking closer, I learned that this dependency is used in a
small corner of markdown-clj to parse front-matters. I stripped those
front-matters out when I [migrated from Octopress to
babashka](migrating-octopress-to-babashka.html), but I realize now I could have
left those in. Since the rest of markdown-clj is pure Clojure, there's a
reasonable chance it will work with babashka. Let's try and see.

I cloned the repo locally and tried this:

``` shell
$ git clone git@github.com:yogthos/markdown-clj.git
$ cd markdown-clj
$ bb -cp src/clj:src/cljc -e "(require '[markdown.core :as md])
  (md/md-to-html-string \"# 100\")"
----- Error --------------------------------------------------------------------
Type:     java.lang.IllegalStateException
Message:  Can't dynamically bind non-dynamic var #'markdown.common/*substring*
Location: 73:3
```

The issue here is that [SCI](https://github.com/babashka/sci), the interpreter
running Clojure code in babashka, doesn't understand this way of defining
dynamic vars yet:

``` clojure
(declare ^{:dynamic true} *substring*)
```

Of course this should be fixed, so I filed an issue with SCI
[here](https://github.com/babashka/sci/issues/630).

But changing the code in markdown-clj to:

``` clojure
(def ^{:dynamic true} *substring*)
```

doesn't seem to have any downsides and would make that part compatible with
babashka. There was another dynamic var that had the same problem. After
fixing that, I got:

``` shell
$ bb -cp src/clj:src/cljc -e "(require '[markdown.core :as md])
  (md/md-to-html-string \"# 100\")"
"<h1>100</h1>"
```

It worked!

To be sure all markdown-clj tests pass with babashka, I added a test runner. The
Cognitect Labs [test-runner](https://github.com/cognitect-labs/test-runner)
(Cognitest :-D) works with babashka, provided that you include the babashka
compatible [tools.namespace](https://github.com/babashka/tools.namespace) fork
in your dependencies. The `bb.edn` so far:

``` clojure
{:deps {markdown-clj/markdown-clj {:local/root "."}}
 :tasks
 {test:bb {:doc "Runs tests with babashka"
           :extra-paths ["test"]
           :extra-deps {io.github.cognitect-labs/test-runner
                        {:git/tag "v0.5.0" :git/sha "b3fd0d2"}
                        org.clojure/tools.namespace
                        {:git/url "https://github.com/babashka/tools.namespace"
                         :git/sha "3625153ee66dfcec2ba600851b5b2cbdab8fae6c"}}
           :requires ([cognitect.test-runner :as tr])
           :task (apply tr/-main "-d" "test" *command-line-args*)}
 ,,,}}
```

After that, you can run `bb test:bb`:

``` shell
Running tests in #{"test"}
----- Error --------------------------------------------------------------------
Type:     clojure.lang.ExceptionInfo
Message:  Could not resolve symbol: org.apache.commons.lang.StringEscapeUtils/unescapeHtml
Location: /private/tmp/markdown-clj/test/markdown/md_test.cljc:331:11
Phase:    analysis
```

Ouch. The class `org.apache.commons.lang.StringEscapeUtils`, which is used in
the tests, isn't available in babashka. Let's comment that one out. Luckily, the
tests are already in a `.cljc` file, so using the `:bb` reader conditional lets
us make changes specifically for babashka while leaving it the same for the
other targets:

``` clojure
#?(:bb nil
   :default
   (is (= "<p><a href=\"mailto:abc@google.com\">abc@google.com</a></p>"
          (#?(:clj  org.apache.commons.lang.StringEscapeUtils/unescapeHtml
              :cljs goog.string/unescapeEntities)
           (entry-function "<abc@google.com>")))))

#?(:bb nil
   :default
   (is (= "<p><a href=\"mailto:abc_def_ghi@google.com\">abc_def_ghi@google.com</a></p>"
          (#?(:clj  org.apache.commons.lang.StringEscapeUtils/unescapeHtml
              :cljs goog.string/unescapeEntities)
           (entry-function "<abc_def_ghi@google.com>")))))
```

Yes, reader conditionals can be nested. You didn't see that one coming did you?
After this change, bingo!

```
$ bb test:bb

Running tests in #{"test"}

Testing markdown.md-file-test

Testing markdown.md-test

Ran 84 tests containing 146 assertions.
0 failures, 0 errors.
```

While I was at it, I also added a `deps.edn` and tasks for running the Clojure and ClojureScript tests.

`deps.edn`:

``` clojure
{:paths ["src/clj" "src/cljs" "src/cljc"]
 :deps {clj-commons/clj-yaml {:mvn/version "0.7.107"}}
 :aliases
 {:test
  {:extra-paths ["test"]
   :extra-deps {io.github.cognitect-labs/test-runner
                {:git/tag "v0.5.0" :git/sha "b3fd0d2"}
                criterium/criterium {:mvn/version "0.4.4"}
                commons-lang/commons-lang {:mvn/version "2.6"}}
   :main-opts ["-m" "cognitect.test-runner"]
   :exec-fn cognitect.test-runner.api/test}
  :cljs-test
  {:extra-paths ["test"]
   :extra-deps {olical/cljs-test-runner {:mvn/version "3.8.0"}
                org.clojure/clojure {:mvn/version "1.10.1"}
                org.clojure/clojurescript {:mvn/version "1.10.520"}}
   :main-opts ["-m" "cljs-test-runner.main"]}}}
```

`bb.edn`:

``` clojure
{:deps {markdown-clj/markdown-clj {:local/root "."}}
 :tasks
 {,,,
  test:clj {:doc "Runs tests with JVM Clojure"
            :task (clojure "-X:test")}
  test:cljs {:doc "Runs tests with ClojureScript"
             :task (clojure "-M:cljs-test")}}}
```

``` shell
$ bb test:clj

Running tests in #{"test"}

Testing markdown.md-file-test

Testing markdown.md-test

Ran 84 tests containing 148 assertions.
0 failures, 0 errors.

$ bb test:cljs

Testing markdown.md-test

Ran 75 tests containing 136 assertions.
0 failures, 0 errors.
```

After that change, I could use `markdown-clj` directly in the code for rendering
this blog. You can see the diff
[here](https://github.com/borkdude/blog/commit/5ab3eeb6601e81fb0166e9449cc8054bc99da46a).
Previously I also used bootleg for hiccup, but babashka already has hiccup as a
built-in dependency so that wasn't necessary anymore either.  So the blog
rendering code is pure babashka now.

I submitted a [PR](https://github.com/yogthos/markdown-clj/pull/173) with these
changes to the markdown-clj repository. This PR was merged and a [new
version](https://clojars.org/markdown-clj/versions/1.10.7) was published tn
Clojars, which is used in the `deps.edn` of this blog.

## Performance considerations

What about performance? Previous re-rendering all of the blog posts took 4
seconds and now it takes a second longer. Running markdown-clj from source is slower
than using the pod since the code in the pod is all pre-compiled and doesn't run
through SCI. Compiling a single blog post isn't noticeably slower. The
difference is small enough to move forward with markdown-clj from source for
now. Since it's easy to move between pure babashka, using the bootleg pod or
running JVM Clojure, I keep my options open.

After writing the last paragraph, I made this blog's code run with JVM
Clojure. Let's compare the time for recompiling all blog posts (which is
triggered by a change to e.g. `render.clj`):

``` shell
$ touch render.clj
$ time bb render
...
bb render   3.51s  user 0.17s system 75% cpu 4.891 total

$ touch render.clj
$ clojure -M -m render
...
clojure -M -m render   21.97s  user 1.21s system 276% cpu 8.386 total
```

Recompiling the entire blog with babashka is still faster, likely because the
startup time on the JVM isn't that good because Clojure has to load more
libraries at startup.

If we AOT those libraries then JVM Clojure becomes faster but still the startup
time doesn't outweigh the performance of the JVM:

``` shell
$ mkdir -p classes
$ clojure -M -e "(compile 'render)"
$ time clojure -M -m render
...
clojure -M -m render   13.91s  user 1.05s system 259% cpu 5.753 total
```

Most of the time I won't recompile all my blog posts but just one:

``` shell
$ touch posts/markdown-clj-babashka-compatible.md
$ time bb render
Processing markdown for file: posts/markdown-clj-babashka-compatible.md
bb render   0.42s  user 0.12s system 79% cpu 0.682 total

$ touch posts/markdown-clj-babashka-compatible.md
$ time clojure -M -m render
Processing markdown for file: posts/markdown-clj-babashka-compatible.md
clojure -M -m render   5.58s  user 0.60s system 189% cpu 3.266 total
```

## nbb

Dmitri Sotnikov, the author of markdown-clj, suggested that markdown-clj could
also be made compatible with [scittle](https://github.com/babashka/scittle) and
[nbb](https://github.com/babashka/nbb). The only change I had to make to the
original source was to change `cljs.reader` into `clojure.edn` and then it
worked:

``` shell
$ nbb -cp src/clj:src/cljc:src/cljs -e "(require '[markdown.core :as md])
  (md/md->html \"# 100\")"
"<h1>100</h1>"
```

After adding a runner:

``` clojure
(ns nbb-runner
  (:require [clojure.string :as str]
            [clojure.test :refer [run-tests]]
            [nbb.classpath :as cp]))

(cp/add-classpath (str/join ":" ["src/cljs" "src/cljc" "test"]))

(require '[markdown.md-test])

(run-tests 'markdown.md-test)
```

and a few minor tweaks to the tests, the library runs with nbb:

```
Testing markdown.md-test

Ran 75 tests containing 134 assertions.
0 failures, 0 errors.
```
