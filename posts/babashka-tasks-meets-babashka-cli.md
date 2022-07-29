In a previous blog post
[(link)](https://blog.michielborkent.nl/babashka-cli.html) I introduced
[babashka CLI](https://github.com/babashka/cli). It offers you similar benefits
as `clojure -X` but with a more Unixy command line interface.

In version `0.9.159` of babashka (building as I'm writing this) babashka CLI was
integrated.  It is available as a built-in library so you don't need to declare
it anymore in `:deps` in `bb.edn` unless you want to use a newer version than
the built-in one.

## -x

For invoking functions from the command line, you can use the new `-x` flag (a pun to Clojure's `-X` of course!):

```
bb -x clojure.core/identity --hello there
{:hello "there"}
```

Babashka prints the last evaluated value. What we see in the above snippet is
that a map `{:hello "there"}` is constructed by babashka CLI and then fed to the
`identity` function. After that the result is printed to the console.

What if we want to influence how things are parsed by babashka CLI and provide
some defaults? This can be done using metadata. Let's create a `bb.edn` and make
a file available on the classpath:

`bb.edn`:
``` clojure
{:paths ["."]}
```

`tasks.clj`:
``` clojure
(ns tasks
  {:org.babashka/cli {:exec-args {:ns-data 1}}})

(defn my-function
  {:org.babashka/cli {:exec-args {:fn-data 1}
                      :coerce {:num [:int]}}}
  [m]
  m)
```

Now let's invoke:

```
$ bb -x tasks/my-function -n 1 2
{:ns-data 1, :fn-data 1, :num [1 2]}
```

As you can see, the namespace options are merged with the function
options. Defaults can be provided with `:exec-args`, like you're used to from
the clojure CLI.

## Tasks

What about task integration? Let's adapt our `bb.edn`:

```
{:paths ["."]
 :tasks {doit {:task (let [x (exec tasks/my-function)]
                       (prn :x x))
               :exec-args {:task-data 1234}}
         }}
```

and invoke the task:

```
$ bb doit --cli-option :yeah
:x {:ns-data 1, :fn-data 1, :task-data 1234, :cli-option :yeah}
```

As you can see it works similar to `-x`, but you can provide another set of
defaults on the task level with `:exec-args`. Executing a function through
babashka CLI is done using the `babashka.task/exec` macro, available by default
in tasks.

Hope you will enjoy this!
