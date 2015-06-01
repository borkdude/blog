---
layout: post
title: Porting a leiningen project to boot
date: 2015-06-01 21:00:50 +0200
comments: true
published: true
categories: [leiningen, boot, clojure, clojurescript]
---

Boot is a new build tool for Clojure. To get acquainted with it, I
decided to port a fairly non-trivial leiningen project boot.

You can find the entire project including the leiningen `project.clj`
file and boot's `build.boot` file
[here](https://github.com/borkdude/lein2boot).

Disclaimer: this is not a comprehensive Boot tutorial. For a detailed introduction into the concepts of Boot I refer to the [Boot website](http://boot-clj.com/).

## Walkthrough

First let's walk through the leiningen `project.clj` step by step and
see how it translates into a `build.boot` file.

Here we tell leiningen where our source files and resources are. Also
we declare what directories must be emptied if we want to clean up
generated files:


```clojure
:source-paths ["src"]
:resource-paths ["assets" "out"]
:clean-targets ^{:protect false} [:target-path :compile-path "out"]
```

In the `build.boot` file this is done by a call to `set-env!`:

```clojure
(set-env!
  :source-paths #{"src" "src-cljs"}
  :resource-paths #{"assets"}
  ...
```

Boot has the concept of immutable filesets. Each task receives a
fileset and produces one.  The last task outputs its fileset to the
target directory, which is `target` by default. Boot will clean stale
files from target automatically. There is never a need to clean
something in Boot.

Next we describe which dependencies the project has. In leiningen this
is done as follows:

```clojure
:dependencies [[org.clojure/clojure "1.6.0"]
               [org.clojure/clojurescript "0.0-3211"]
               [org.clojure/core.async "0.1.346.0-17112a-alpha"]
               [ring-server "0.4.0"]
               [org.webjars/bootstrap "3.2.0"]
               [cljs-http "0.1.30"]
               [compojure "1.3.4"]
               [liberator "0.13"]
               [fogus/ring-edn "0.2.0"]
               [clj-json "0.5.3"]
               [reagent "0.5.0"]
               [prismatic/schema "0.4.3"]]
```
    
In Boot this is done similarly, still inside the call to `set-env!`:

```clojure
(set-env!
    ...
    :dependencies '[[org.clojure/clojure "1.6.0"]
                    [org.clojure/clojurescript "0.0-3211"]
                    [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                    [ring-server "0.4.0"]
                    [org.webjars/bootstrap "3.2.0"]
                    [cljs-http "0.1.30"]
                    [compojure "1.3.4"]
                    [liberator "0.13"]
                    [fogus/ring-edn "0.2.0"]
                    [clj-json "0.5.3"]
                    [reagent "0.5.0"]
                    [prismatic/schema "0.4.3"]
                    ...
                    ])
```

The next line we encounter in `project.clj` is:

```clojure
:repl-options {:init-ns animals.server}
```

This makes the `animals.server` namespace the starting point for every
REPL session. In `build.boot` it is accomplished like this:

```clojure
(task-options! repl {:init-ns 'animals.server})
```

Boot has several tasks built in and `repl` is one of them. It supports the option `init-ns`. With `task-options!` you can set some default options per task that are global to the project. To see all the options that `repl` provides, you can issue `-h` from the command line:

`$ boot repl -h`

or call `(doc repl)` from a boot REPL session.














## Problems


## Conclusion

## Credits
