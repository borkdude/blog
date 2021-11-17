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
compilation in babashka, but bootleg didn't expose an option that
[markdown-clj](https://github.com/yogthos/markdown-clj), the underlying library
supports. The PR fixes that.

Then I wondered, can babashka run markdown-clj from source, rather than via a
pod? Babashka supports a large subset of Clojure and a large subset of classes
from the JVM. By now it can run a fair share of Clojure libraries from source,
but sometimes minor tweaks are necessary. I looked at the dependencies of
markdown-clj and it uses
[clj-commons/clj-yaml](https://github.com/clj-commons/clj-yaml) which is luckily
included in babashka. If it wasn't, I'm pretty sure that dependency could be
made optional for those that do not need any YAML support in their markdown
compilation. When looking closer, I learned that this dependency is used to
parse front-matters. I stripped those out when I [migrated from Octopress to
babashka](migrating-octopress-to-babashka.html), but I realize now I could have
left those in. Anyway, let's continue on the quest to make markdown-clj
babashka-compatible.

I cloned the repo locally and tried this:

``` shell
$ git clone git@github.com:yogthos/markdown-clj.git
$ cd markdown-clj
$ bb -cp src/clj:src/cljc -e "(require '[markdown.core :as md]) (md/md-to-html-string \"# 100\")"
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
$ bb -cp src/clj:src/cljc -e "(require '[markdown.core :as md]) (md/md-to-html-string \"# 100\")"
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

I submitted a [PR](https://github.com/yogthos/markdown-clj/pull/173) with these
changes to the markdown-clj repository. But for now I added my fork in the
`:deps` of the `bb.edn` of this blog:

``` clojure
:deps {babashka/markdown-clj {:git/url "https://github.com/babashka/markdown-clj"
                              :git/sha "20f65255d8056c52923fe82d1998dcd8a6cf6e3c"}}
```

After that change, I could use `markdown-clj` directly in the code for rendering
this blog. You can see the diff
[here](https://github.com/borkdude/blog/commit/5ab3eeb6601e81fb0166e9449cc8054bc99da46a).
Previously I also used bootleg for hiccup, but babashka already has hiccup as a
built-in dependency so that wasn't necessary anymore either.  So the blog
rendering code is pure babashka now.

What about performance? Previous re-rendering all of the blog posts took 4
seconds and now it takes 5 seconds. Runnning markdown-clj from source is slower
than using the pod since the code in the pod is all pre-compiled and doesn't run
through SCI. Compiling a single blog post isn't noticabley slower. The
difference is small enough to move forward with markdown-clj from source for
now. Since it's easy to move between pure babashka, using the bootleg pod or
running JVM Clojure, I keep my options open.
