Title: Running tests with babashka
Date: 2022-11-24
Tags: clojure, babashka

For running tests with babashka, you can write your own test runner from
scratch, which is easy enough:

``` clojure
(ns my-test.runner
  (:require
   [clojure.test :as t]))

(def test-namespaces '[my-test])

(defn -main [& _]
  (doseq [test-ns test-namespaces] (require test-ns))
  (let [{:keys [fail error]}
        (apply t/run-tests test-namespaces)]
    (when (and fail error (pos? (+ fail error)))
      (System/exit 1))))
```

and then run it with `bb -m my-test.runner`.

Not too bad, but still, it's work and boilerplate and even more so when you want
to make a runner with CLI argument parsing which only runs a subset of your
tests. Since a year or so, you can use the
[cognitect-labs/test-runner](https://github.com/cognitect-labs/test-runner) with
babashka. But this required a [fork of tools
namespace](https://github.com/babashka/tools.namespace) to be on your babashka
classpath (using `:deps` in your `bb.edn` file).

No more! Since babashka version 1.0.166 you can use
[org.clojure/tools-namespace](https://github.com/clojure/tools.namespace)
unmodified. The fix for this was to add the `clojure.tools.reader` namespace
with the `read` function in babashka as a built-in. Babashka doesn't support the
whole `clojure.tools.reader` namespace yet, but this is a good start to make it
compatible with tools.namespace and now also the cognitect test runner.

To use it with babashka, add the following to your `bb.edn`.

``` clojure
{:tasks
 {test:bb {:extra-paths ["test"]
           :extra-deps {io.github.cognitect-labs/test-runner
                        {:git/tag "v0.5.1" :git/sha "dfb30dd"}}
           :task (exec 'cognitect.test-runner.api/test)
           :exec-args {:dirs ["test"]}
           :org.babashka/cli {:coerce {:nses [:symbol]
                                       :vars [:symbol]}}}}}
```


The `exec` function call, `:exec-args` and `:org.babashka/cli` coercion is there so we can
call a Clojure function from the command line.  See [Babashka tasks meets
babashka
CLI](https://blog.michielborkent.nl/babashka-tasks-meets-babashka-cli.html) for
more details.

Now create a test file in `test/my_test.clj`:

``` clojure
(ns my-test
  (:require [clojure.test :refer [deftest is testing]]))

(deftest my-first-test
  (testing "equality works"
    (is (= 1 1))))

(deftest my-second-test
  (testing "equality still works"
    (is (= 2 2))))
```

And run the tests:


``` shell
$ bb test:bb

Running tests in #{"test"}

Testing my-test

Ran 2 tests containing 2 assertions.
0 failures, 0 errors.
```

To run a single test you can specify the name of a var:

``` shell
$ bb test:bb --vars my-test/my-second-test

Running tests in #{"test"}

Testing my-test

Ran 1 tests containing 1 assertions.
0 failures, 0 errors.

$ bb test:bb --vars my-test/my-first-test my-test/my-second-test

Running tests in #{"test"}

Testing my-test

Ran 2 tests containing 2 assertions.
0 failures, 0 errors.
```

Perhaps this will come in handy for [Advent of Code 2022](https://adventofcode.com/2022)!
