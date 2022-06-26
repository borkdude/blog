[Babashka CLI](https://github.com/babashka/cli) is a new library for command line argument parsing.
The main ideas:

- Put as little effort as possible into turning a Clojure function into a CLI,
  similar to `-X` style invocations. For lazy people like me! If you are not
  familiar with `clj -X`, read the docs
  [here](https://clojure.org/reference/deps_and_cli#_execute_a_function).
- But with a better UX by not having to use quotes on the command line as a
  result of having to pass EDN directly: `:dir foo` instead of `:dir '"foo"'` or
  who knows how to write the latter in `cmd.exe` or Powershell.
- Open world assumption: passing extra arguments does not break and arguments
  can be re-used in multiple contexts.
- Because the line between calling functions from the command line and Clojure
  itself is blurred, validation of arguments should happen in your Clojure
  function, using your favorite tools (manually, spec, schema, malli...). As
  such, the library only focuses on coercion: turning argument strings into data
  which is then passed to your function.

Given the function:

``` clojure
(defn foo [{:keys [force dir] :as m}]
  (prn m))
```

and with a little bit of [config](https://github.com/babashka/cli#clojure-cli)
in your `deps.edn`, you can call the function from the command line using:

``` text
clj -M:foo --force --dir=src
```

or:

``` text
clj -M:foo --force --dir src
```

which will then print:

``` clojure
{:force true, :dir "src"}
```

We did not have to teach babashka CLI anything about the expected arguments.

Another accepted syntax is:

``` text
clj -M:foo :force false :dir src
```

and this is parsed as:

``` clojure
{:force false, :dir "src"}
```

Booleans, numbers and keywords are auto-coerced, but if you want to make things
strict, you can use metadata. E.g. if we want to accept a keyword for the option `:mode`:

``` text
clj -M:foo :force false :dir src :mode overwrite
```

and parse it as:

``` clojure
{:force false, :dir "src" :mode :overwrite}
```

you can teach babashka CLI using metadata:

``` clojure
(defn foo
  {:org.babashka/cli {:coerce {:mode :keyword}}}
  [{:keys [force dir mode] :as m}]
  (prn m))
```

A leading colon is also accepted:

``` clojure
clj -M:foo :force false :dir src :mode :overwrite
```

The metadata format is set up in such a way that libraries need not have a dependency on babashka CLI itself.

Did you notice that the `-M` invocation now becomes almost identical to `-X`,
but without quotes?

``` text
clj -M:foo :force true :dir src :mode :overwrite
clj -X:foo :force true :dir '"src"' :mode :overwrite
```

Let's look at a recent project,
[http-server](https://github.com/babashka/http-server), where I used babashka
CLI to serve both `-X`, and `-M` needs.

The only argument hints defined there right now are:

``` clojure
(def ^:private cli-opts {:coerce {:port :long}})
```

although that could have been left out since numbers are auto-coerced.

The `-main` function simply defers to the clojure `exec` API function (intended
for `-X` usage) with the parsed arguments:

``` clojure
(defn ^:no-doc -main [& args]
  (exec (cli/parse-opts args cli-opts)))
```

In turn, the `exec` function adds some light logic making it suitable for
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

it can be called both with `-M` and `-X`:

``` text
$ clj -M:serve --port 1339
```

or:

``` text
$ clj -M:serve :port 1339
```

or:

``` text
$ clj -X:serve :port 1339
```

And help printing is supported in both styles:

``` text
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
