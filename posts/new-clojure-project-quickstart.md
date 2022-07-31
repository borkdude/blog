Clojure beginners sometimes struggle with setting up a new Clojure `deps.edn`
project compared to setting up a [lein](https://leiningen.org/) project. This is
one of the reasons I've built [neil](https://github.com/babashka/neil). But not
only for beginners, I've been using `neil` myself a ton too, to add common
features to existing `deps.edn` projects. You may think that `neil` is a pun on
`lein`. Of course it is. But the name is also an hommage to [Neil
Peart](https://en.wikipedia.org/wiki/Neil_Peart), one of the greatest
progressive rock drummers to have ever lived.

The intent of this post is to give you a starting point from where you can
figure out things further. This post isn't going to explain any details of how
`deps.edn` and related tooling works. For that I'm going to refer you to
[here](https://clojure.org/guides/deps_and_cli).

Let's install `neil` which is available for
[brew](https://github.com/babashka/neil#homebrew-linux-and-macos),
[scoop](https://github.com/babashka/neil#scoop-windows) (Windows),
[nix](https://github.com/babashka/neil#nix), [Clojure
JVM](https://github.com/babashka/neil#clojure) or can easily be installed
[manually](https://github.com/babashka/neil#manual). Unless you use clojure JVM,
neil runs with [babashka](https://babashka.org/) for fast startup time.

If you've already installed `babashka` (perhaps indirectly by installing `neil`)
but didn't yet install the [clojure
CLI](https://clojure.org/guides/deps_and_cli) or have problems doing so, then
you can run `bb clojure` instead of `clojure` for launching Clojure. Instead of
`clj`, on linux/macOS you'll want to use `rlwrap bb clojure`. If you are on
Windows and struggle with the official `clojure` Powershell-based installation,
`bb clojure` may come in handy too.

## New

To start a new Clojure project, run `neil new --name myproject`. This produces a
`myproject` directory with a very basic project layout based on the
[deps-new](https://github.com/seancorfield/deps-new) `scratch` template. Now we
are are going to incrementally add some functionality to this project.

## Adding library

Let's decide that we're going to need a library to deal with files. Let's search for one:

``` clojure
$ neil dep search "file system"
:lib fs/fs :version 1.3.3 :description "File system utilities for clojure"
:lib me.raynes/fs :version 1.4.6 :description "File system utilities for clojure"
:lib babashka/fs :version 0.1.6 :description "Babashka file system utilities."
```

Let's go with the [babashka/fs](https://github.com/babashka/fs) library:

```
$ neil dep add :lib babashka/fs :version 0.1.6
```

Now the library is added to `deps.edn` and we can use it in our project:

``` clojure
$ clj
Clojure 1.11.0
user=> (require '[babashka.fs :as fs])
nil
```

## Test

Let's start by adding a test runner. Type: `neil add test`. After doing this,
you will be able to run:

``` shell
clojure -M:test
```

We don't have any tests in this project, so let's add one by adding a file:

`test/myproject/core_test.clj`
``` clojure
(ns myproject.core-test
  (:require [clojure.test :as t :refer [deftest is testing]]))

(deftest failing-test
  (testing "TODO: fix test"
    (is (= 3 4))))
```

Now run `clojure -M:test` again:

``` shell
$ clojure -M:test

Running tests in #{"test"}

Testing myproject.core-test

FAIL in (failing-test) (core_test.clj:6)
TODO: fix test
expected: (= 3 4)
  actual: (not (= 3 4))

Ran 1 tests containing 1 assertions.
1 failures, 0 errors.
```

The test runner we added is the Cognitect Labs
[test-runner](https://github.com/cognitect-labs/test-runner) so check out the
README of that project if you need to know more.

## REPL

Run:

``` shell
neil add nrepl
```

to add a `:nrepl` alias to your project. Now you can run `clj -M:nrepl` to get a
console REPL, but also a running nREPL server that you can connect to from your
editor. Note that many editors also support `jack-in` and if you prefer to use
that, you won't need this.

## Uberjar

What's the equivalent of `lein uberjar` in the `deps.edn` world? You're going to need [tools.build](https://github.com/clojure/tools.build). To create a `build.clj` file (the program that is going to build your uberjar), run:

``` shell
neil add build
```

Since the default `build.clj` file is going to assume your project is under
`git` version control, let's initialize a git repo first:

``` shell
git init
git add src test
git commit -m "initial commit"
```

Before creating the uberjar, we have to add `:gen-class` to `src/scratch.clj`:

``` clojure
(ns scratch
  (:gen-class))
```

and we add `:main 'scratch` in the call to `b/uber`:

``` clojure
(b/uber {:class-dir class-dir
         :uber-file uber-file
         :basis basis
         :main 'scratch})
```

Now let's compile the uberjar:

``` shell
clojure -T:build uber
```

And then let's run it:

``` shell
$ java -jar target/lib1-1.2.1-standalone.jar 1 2 3
-main with (1 2 3)
```

## Babashka tasks

If you have difficulty remembering the above invocations, you can write a
`bb.edn` with some [tasks](https://book.babashka.org/#tasks):

`bb.edn`:
``` clojure
{:tasks
 {:requires ([babashka.fs :as fs])

  test {:doc "Run tests"
        :task (apply clojure "-M:test" *command-line-args*)}

  nrepl {:doc "Start REPL"
         :task (if (fs/windows?)
                 (clojure "-M:nrepl")
                 ;; rlwrap:
                 (shell "clj -M:nrepl"))}

  uber {:doc "Build uberjar"
        :task (clojure "-T:build uber")}}}
```

The `clojure` function is built into babashka and is a drop-in replacement for
the clojure CLI which does not require any installation. With

``` clojure
(apply ... *command-line-args*)
```

we send any args you pass to a task invocation to clojure. So to run a specific test, you can write:

``` shell
bb test -v myproject.core-test/failing-test

```

If you prefer to use the installed clojure CLI, you can do this by using:

``` clojure
(apply shell "clojure" ... *command-line-args*)
```

Now whenever you forget what to do in the current project, run `bb tasks`:

``` shell
$ bb tasks
The following tasks are available:

test  Run tests
nrepl Start REPL
uber  Build uberjar
```

Hope this helps!
