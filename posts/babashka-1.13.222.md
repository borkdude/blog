Title: Babashka 1.13.222: the conj release
Date: 2026-09-14
Tags: clojure, babashka
Description: Native dependency resolution, tools.build and expanded nREPL support in babashka 1.13.222.

Babashka 1.13.222 is the "conj" release! I'll be giving a [babashka workshop](https://2026.clojure-conj.org/workshops) at Clojure/conj together with Rahul De. Hope to see you there!

## Dependencies without a JVM

Babashka now resolves dependencies without a JVM by default. Here's a demo: We will use an alternative `:mvn/local-repo` such that we force downloading deps.

`deps-example.clj`:
```clojure
(require '[babashka.deps :as deps]
         '[babashka.fs :as fs])

(deps/add-deps
 {:mvn/local-repo (str (fs/create-temp-dir {:prefix "bb-mvn-"}))
  :deps '{medley/medley {:mvn/version "1.4.0"}}})

(require '[medley.core :as medley])

(prn (medley/index-by :id [{:id 1 :name "Ada"}
                           {:id 2 :name "Grace"}]))
```

To make sure this example runs without Java, we will remove it from the `PATH` and set `JAVA_HOME` to a non-existing directory as well:

```shell
# Find bb before clearing PATH.
bb_executable=$(command -v bb)
env PATH= JAVA_HOME=/does-not-exist "$bb_executable" -Sforce deps-example.clj
# {1 {:id 1, :name "Ada"}, 2 {:id 2, :name "Grace"}}
```

If you encounter a bug in the new native resolver, switch back to the JVM resolver:

```shell
export BABASHKA_DEPS_RESOLVER=jvm
bb deps-example.clj
```

To select the JVM resolver per project, set this in `bb.edn`:

```clojure
{:deps-resolver :jvm}
```

## Bundles tools.deps

The native resolver is built on top of `clojure.tools.deps`. You can now use that directly from bb without adding a dependency:

```clojure
(require '[clojure.tools.deps :as deps]
         '[babashka.fs :as fs])

(def basis
  (deps/create-basis
   {:root nil :user nil :project nil
    :extra '{:deps {medley/medley {:mvn/version "1.4.0"}}}}))

(mapv fs/file-name (:classpath-roots basis))
;;=> ["medley-1.4.0.jar" "clojure-1.9.0.jar"
;;    "core.specs.alpha-0.1.24.jar" "spec.alpha-0.1.143.jar"]
```

## Bundled tools.build

In this release, we're going one step further. Since `tools.build` is built on `tools.deps` and we now have it in bb, it was only a small leap to also add `tools.build`.

This example packages a `src` directory with a `build.clj` running in babashka:

```clojure
(ns build
  (:require [clojure.tools.build.api :as b]))

(def class-dir "target/classes")

(defn jar [_]
  (b/copy-dir {:src-dirs ["src"]
               :target-dir class-dir})
  (b/jar {:class-dir class-dir
          :jar-file "target/app.jar"}))
```

Add a task in `bb.edn`:

```clojure
{:paths ["."]
 :tasks {jar {:exec-fn build/jar}}}
```

```shell
bb jar
```

Everything runs in babashka and we did not need to add a dependency on tools.build. When invoking tasks that require Java compilation, of course, a JVM will be necessary still.

## deps.deploy

To make sure typical `build.clj` files will run fully in babashka, a bb-compatible variant of the excellent slipset/deps-deploy library was made: [babashka/deps-deploy](https://github.com/babashka/deps-deploy). I consulted with Eric Assum to perhaps make this library bb-compatible, but his reply was: what if people just used yours from now on... oops ;).

```clojure
{:paths ["."]
 :deps {io.github.babashka/deps-deploy {:mvn/version "0.0.1"}}
 :tasks {jar {:exec-fn build/jar}
         deploy {:exec-fn build/deploy}}}
```

## CLI support

Add these requires and the `deploy` function to `build.clj`. The `:org.babashka/cli` metadata supplies option parsing, help and completions:

```clojure
(ns build
  (:require [babashka.deps-deploy :as dd]
            [clojure.tools.build.api :as b]))

(def lib 'io.github.yourname/app)
(def version [0 1 0])
(def class-dir "target/classes")

(defn deploy
  {:org.babashka/cli
   {:spec {:bump {:coerce :boolean
                  :desc "Increment the patch version before deploying"}}}}
  [{:keys [bump]}]
  (let [version (cond-> version bump (update 2 inc))]
    (b/write-pom {:class-dir class-dir
                  :lib lib
                  :version (apply format "%s.%s.%s" version)
                  :basis (b/create-basis {:project {:paths ["src"]}})
                  :src-dirs ["src"]})
    (jar nil)
    (dd/deploy {:artifact "target/app.jar"
                :pom-file (b/pom-path {:lib lib
                                       :class-dir class-dir})})))
```

Like with slipset/deps-deploy, set `CLOJARS_USERNAME` and `CLOJARS_PASSWORD` to your Clojars username and deploy token.

```shell
bb deploy --help
bb deploy
bb deploy --bump
```

To enable completions in zsh after `compinit`:

```shell
source <(bb org.babashka.cli/completions snippet --shell zsh)
```

For other shells, check the [completions](https://github.com/babashka/cli#completions) section in babashka.cli's docs.
Completions should now show this:

```text
$ bb deploy --<TAB>
--bump  -- Increment the patch version before deploying
--help  -- Show this help
```

A few fixes went into babashka to help with task `:exec-fn`s that depend on each other which should now work smoother as well.

## nREPL

Babashka already had nREPL support, but this release extends it to the wider nREPL Clojure ecosystem.
As you were used to, you can start a bb nREPL like this:

```shell
bb nrepl-server 1667
```

You can now connect from another terminal and you'll get a nice jline REPL in which you can talk to your nREPL server:

```shell
bb repl --connect 1667
```

Completion, eldoc, documentation lookup and Ctrl-C interruption all should work. Of course you can talk to any nREPL server, including a normal JVM Clojure one.

The built-in nREPL server now runs nREPL 1.7.0, with CIDER inspector and test runner middleware support (the most frequently missing feature in bb nREPL). It now binds to `127.0.0.1` by default instead of `0.0.0.0`.

Programmers (or LLM agents) can use the bundled `nrepl.core` client to evaluate code in a running REPL. Save as `eval.clj`:

```clojure
(require '[nrepl.core :as nrepl])

(with-open [conn (nrepl/connect :port 1667)]
  (prn (-> (nrepl/client conn 1000)
           (nrepl/message {:op "eval"
                          :code "(reduce + (range 10))"})
           nrepl/response-values)))
```

```shell
bb eval.clj
# [45]
```


Here's an example of custom middleware.
Log the code your editor or client evaluates. Save as `middleware.clj`:

```clojure
(require '[nrepl.server :as server]
         '[nrepl.middleware :refer [set-descriptor!]])

(defn wrap-log-eval [handler]
  (fn [{:keys [op code] :as msg}]
    (when (= "eval" op)
      (spit "nrepl-eval.log" (str code "\n") :append true))
    (handler msg)))

(set-descriptor! #'wrap-log-eval
                 {:requires #{"clone"}
                  :expects #{"eval"}
                  :handles {}})

(def nrepl-server
  (server/start-server :bind "127.0.0.1"
                       :port 1667
                       :handler (server/default-handler #'wrap-log-eval)))

```

Start the server:

```shell
bb middleware.clj
```

Connect from another terminal:

```shell
bb repl --connect 1667
```

```clojure
user=> (reduce + (range 10))
45
```

Inspect the log on the server:

```shell
tail -f nrepl-eval.log
# (reduce + (range 10))
```

## Wrapping up

Hope you like these additions to babashka:

- Fetching dependencies without a JVM
- Bundled tools.deps and tools.build support
- `babashka.deps-deploy` which completes the gap of running `build.clj` fully in bb now
- You can get completions for your `build.clj` functions by adding them as `:exec-fn` in `bb.edn`. You can get enhanced option parsing and completions by adding `:org.babashka/cli` metadata to your `build.clj` functions.
- Extended nREPL support, including custom middleware and CIDER inspector + test runner middleware.
- `nrepl.core` and related namespaces are now exposed and used in babashka's own nREPL server. You can use it to connect to otherservers as well. Babashka's `repl` has a new `--connect` option which uses this through its own jline REPL.


[Full changelog](https://github.com/babashka/babashka/blob/master/CHANGELOG.md).
