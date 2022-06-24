[Babashka CLI](https://github.com/babashka/cli) is a new library for command line argument parsing.
The main ideas:

- Put as little effort as possible into turning a clojure function into a CLI,
  similar to `-X` style invocations. For lazy people like me! If you are not
  familiar with `clj -X`, read the docs
  [here](https://clojure.org/reference/deps_and_cli#_execute_a_function).
- But with a better UX by not having to use quotes on the command line as a
  result of having to pass EDN directly: `:dir foo` instead of `:dir '"foo"'` or
  who knows how to write the latter in `cmd.exe` or Powershell.
- Open world assumption: passing extra arguments does not break and arguments
  can be re-used in multiple contexts.
- Because the line between calling functions from the command line and Clojure
  itself is blurred, validation of arguments should happen in the same way you'd
  do it in Clojure, using your favorite tools (manually, spec, schema,
  malli...). As such, the library only focuses on coercion (turning argument
  strings into data), not on validation.

Given the function:

``` clojure
(defn foo [{:keys [force dir] :as m}]
  (prn m))
```

and with a little bit of [config](https://github.com/babashka/cli#clojure-cli)
in your `deps.edn`, you can call the function from the command line using:

``` clojure
clj -M:foo --force --dir=src
```

or:

``` clojure
clj -M:foo --force --dir src
```

which will then print:

```
{:force true, :dir "src"}
```

We did not have to teach babashka CLI anything about the expected arguments.

Another accepted syntax is:

```
clj -M:foo :force true :dir src
```

but this is parsed as:

```
{:foo "true", :dir "src"}
```

Here babashka CLI needs a little argument "hinting" using metadata:

``` clojure
(defn foo
  {:org.babashka/cli {:coerce {:force :boolean}}}
  [{:keys [force dir] :as m}]
  (prn m))
```

The metadata format is set up in such a way that libraries need not have a dependency on babashka CLI itself.


After invoking this again:

```
clj -M:foo :force true :dir src
```

you will see:

``` clojure
{:force true, :dir "src"}
```

Did you notice that the `-M` invocation now becomes almost identical to `-X`,
but without quotes?

```
clj -M:foo :force true :dir src
clj -X:foo :force true :dir '"src"'
```

Let's look at a recent project,
[http-server](https://github.com/babashka/http-server), where I used babashka
CLI to serve both `-X`, and `-M` needs.

The only argument hints defined there right now are:

``` clojure
(def ^:private cli-opts {:coerce {:port :long}})
```

The `-main` function simply defers to the clojure `exec` API function (intended
for `-X` usage) with the parsed arguments:

``` clojure
(defn ^:no-doc -main [& args]
  (exec (cli/parse-opts args cli-opts)))
```

In turn, the `exec` function, adds some light logic making it suitable for
command line usage. It prints help when `:help` is true. Because I'm lazy, I just print the docstring of `serve`, the function that's going to be called:


``` clojure
(defn exec
  "Exec function, intended for command line usage. Same API as serve but
  blocks until process receives SIGINT."
  {:org.babashka/cli cli-opts}
  [opts]
  (if (:help opts)
    (println (:doc (meta #'serve)))
    (do (serve opts)
        @(promise))))
```

Also the `exec` function blocks, preventing the process from immediately
exiting.

Now when I add this function to `deps.edn` using:

``` clojure
:serve {:deps {org.babashka/http-server {:mvn/version "0.1.3"}}
        :main-opts ["-m" "babashka.http-server"]
        :exec-fn babashka.http-server/exec}
```

I can call it with both `-M` and `-X`:

``` clojure
$ clj -M:serve --port 1339
```

or:

``` clojure
$ clj -M:serve :port 1339
```

or:

``` clojure
$ clj -X:serve :port 1339
```

And help printing is supported in both styles:

```
$ clj -M:serve --help
Serves static assets using web server.
Options:
  * `:dir` - directory from which to serve assets
  * `:port` - port
```

or:

``` clojure
$ clj -X:serve :help true
```

The `-main` function can also be used in babashka scripts:

``` clojure
#!/usr/bin/env bb

(require '[babashka.deps :as deps])
(deps/add-deps
 '{:deps {org.babashka/http-server {:mvn/version "0.1.3"}}})

(require '[babashka.http-server :as http-server])

(apply http-server/-main *command-line-args*)
```

``` clojure
$ http-server --help
$ http-server --port 1339
```

I hope you're convinced that with very little code, babashka CLI can let you
support both `-M`, `-X` style invocations and babashka scripts, while improving
command line UX!

It's still up for discussion if babashka CLI should parse `"true"`, `"false"`
and numbers automatically, which would make this library even easier to use. You
can provide feedback on that
[here](https://github.com/babashka/cli/issues/10). Another idea is to support
`--no-foo` as an alias for `:foo false`. See
[this](https://github.com/babashka/cli/issues/17) issue.
