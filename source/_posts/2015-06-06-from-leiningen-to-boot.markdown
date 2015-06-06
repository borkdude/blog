---
layout: post
title: Migrating a Leiningen project to boot
date: 2015-06-06 17:00:50 +0200
comments: true
published: true
categories: [leiningen, boot, clojure, clojurescript, figwheel]
---

Boot is a new build tool for Clojure. To get acquainted with it, I
decided to migrate a fairly non-trivial Leiningen project to Boot.

You can find the entire project including the Leiningen `project.clj`
file and Boot's `build.boot` file
[here](https://github.com/borkdude/lein2boot).

Disclaimer: this is not a comprehensive Boot tutorial. For a detailed
introduction to the concepts of Boot I refer to the
[Boot website](http://boot-clj.com/).

## Requirements

I wanted my Boot project to have the same features as my Leiningen
project:

- Managing dependencies
- Setting source and resource paths
- Building ClojureScript
- Automatic reloading of Clojure and ClojureScript source code during
  development
- A Clojure and ClojureScript REPL 
- Setting the initial namespace for a REPL
- Setting a global var like `*print-length*`
- Packaging the project as a standalone jar that runs in an embedded
  server

## Walkthrough

First let's walk through the Leiningen `project.clj` step by step and
see how it translated into a `build.boot` file.

### Paths

Here we tell Leiningen where our source files and resources are. Also
we declare what directories must be emptied if we want to clean up
generated files:

```clojure
:source-paths ["src"]
:resource-paths ["assets" "out"]
:clean-targets ^{:protect false} [:target-path :compile-path "out/public/out"]
```

In the `build.boot` file this is done by a call to `set-env!`:

```clojure
(set-env!
  :source-paths #{"src" "src-cljs"}
  :resource-paths #{"assets"}
  ...
```

Boot has the concept of immutable filesets. Each task receives a
fileset and produces one.  The last task outputs its fileset to a
target directory, which is `target` by default. Boot will clean stale
files from target automatically before it emits a new fileset
there. There is never a need to clean something in Boot.

### Dependencies

Next we describe which dependencies the project has. In Leiningen this
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

### Initial REPL namespace

The next line we encounter in `project.clj` is:

```clojure
:repl-options {:init-ns animals.server}
```

This makes the `animals.server` namespace the starting point for every
REPL session. In `build.boot` it is accomplished like this:

```clojure
(task-options! repl {:init-ns 'animals.server})
```

Boot has several tasks built in and `repl` is one of them. It supports
the option `init-ns`. With `task-options!` you can set some default
options per task that are global to the project. To see all the
options that `repl` provides, you can issue `-h` from the command
line:

`$ boot repl -h`

or call `(doc repl)` from a Boot REPL session.

### Global var setting

Next is this line from `project.clj`:

```clojure
:global-vars {*print-length* 20}
```

This sets the var `clojure.core/*print-length*` to `20`. If we print collections we'll never see more than 20 items:

```clojure
user=> (println (range))
(0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 ...)
nil
```

In Boot I attempted to do it like this:

```clojure
(alter-var-root (var *print-length*) (fn [v] 20))
```

Unfortunately this caused a
[bug](https://github.com/boot-clj/boot/issues/218) in Boot's `jar`
task. Later I [learned](https://github.com/boot-clj/boot/issues/218#issuecomment-109582851) that it's not a good idea at all to do this in Boot, because there can be multiple Clojure runtimes (pods) in one JVM. Since I was going to use this setting only in the REPL, this is a better solution:

```clojure
(task-options!
 repl {:init-ns 'animals.server
       :eval '(set! *print-length* 20)})
```

### Task dependencies

Leiningen has the concept of
[plugins](https://github.com/technomancy/Leiningen/blob/master/doc/PLUGINS.md). Plugins
typically perform a task as part of a Leiningen build. Two popular
plugins are
[lein cljsbuild](https://github.com/emezeske/lein-cljsbuild) and
[lein figwheel](https://github.com/bhauman/lein-figwheel). `lein
cljsbuild` is an interface to the ClojureScript compiler. `lein
Figwheel` lets you push resources to a browser, typically freshly
compiled ClojureScript or CSS. It also gives you a ClojureScript REPL
and a web server which allows you to serve some static files or even a
Ring handler. In this example I don't use Figwheel's web server for
running my Ring handler, because I use Ring's standalone Jetty server
that comes with automatic code reloading middleware and allows for an
initial function to be executed before the handler is started:
features that are lacking from Figwheel as far as I know.

In Leiningen plugins are included like this:

```clojure
:plugins [[lein-cljsbuild "1.0.5"]
          [lein-figwheel "0.3.1"]]
```

In Boot tasks are included as normal dependencies and scoped with `:test`:

```clojure
(set-env!
  ...
  :dependencies '[[adzerk/boot-cljs "0.0-3269-2" :scope "test"]
                  [adzerk/boot-cljs-repl "0.1.9" :scope "test"]
                  [adzerk/boot-reload "0.2.6" :scope "test"]
                  [pandeiro/boot-http "0.6.3-SNAPSHOT" :scope "test"]])
```

The first dependency is Boot's interface to the ClojureScript
compiler. The latter three dependencies together offer more or less
the same as Figwheel: a ClojureScript, live reloading of resources in the browser
and a web server to serve static files or a Ring handler.

### Building ClojureScript

In Leiningen ClojureScript is built using the `lein cljsbuild` plugin. My
configuration for this plugin looks as follows:

```clojure
:cljsbuild {:builds {:dev {:source-paths ["src-cljs" "src-cljs-dev"]
                           :figwheel {:on-jsload "animals.main/fig-reload"}
                           :compiler {:output-to "out/public/main.js"
                                       :output-dir "out/public/out"
                                       :optimizations :none
                                       :asset-path "out"
                                       :main "animals.main"
                                       :source-map true}}
                     :prod {:source-paths ["src-cljs" "src-cljs-prod"]
                              :compiler {:output-to "out/public/main.js"
                                         :optimizations :advanced}}}}
```

In Boot configuring the location of where generated JavaScript will
end up is done by placing an `.edn` file at the corresponding location
in the resources tree. In this project I placed it in
`resources/public` and named it `main.cljs.edn` with the following
content:

```clojure
{:require [animals.main]
 :compiler-options {:asset-path "out"}}
```

This gives you the same config as in the Leiningen example with
respect to the name of the main JavaScript file, `:asset-path`
and the `:main` namespace.

I use different source folders for development and production, so I
can have some environment specific configuration. For example, in
development I enable console print and define a function for Figwheel
that will be executed when new ClojureScript is pushed to the browser:

```clojure
(enable-console-print!)

(defonce init
  (do (println "starting application") 
      (reagent/render [crud/animals]
                      (js/document.getElementById "app"))))

(defn fig-reload []
  (println "reloading reagent")
  (reagent/render [crud/animals]
                  (js/document.getElementById "app")))
```

In my production ClojureScript I set `*print-fn*` to `identity`, because else I would get errors when there was still a `println` around in my code:

```clojure
;; no println output in production code
(set! cljs.core/*print-fn* identity)

(reagent/render [crud/animals]
                (js/document.getElementById "app"))
```

### Developing

In Leiningen I would start my development like this.

In one terminal, I would start the web server:

    lein repl
    (start-server)

In another terminal, I would start Figwheel:

    lein clean && lein figwheel dev

Figwheel invokes the ClojureScript compiler and the ClojureScript
compiler outputs JavaScript to the `out` directory.

Note that using this setup, I need to run two JVMs.

In Boot the development flow is one task composed of multiple tasks:

```clojure
(deftask dev []
  (set-env!
   :source-paths #(conj % "src-cljs-dev"))
  (comp
   (serve :handler 'animals.api/handler
          :reload true
          :init 'animals.api/init)
   (watch)
   (reload :on-jsload 'animals.main/fig-reload)
   (cljs-repl)
   (cljs)))
```

Only one JVM needed!

There is no need to worry about cleaning directies, since each task
outputs an immutable fileset that the next task can use. Generated
files end up in `target` by default, which gets cleaned before a new
fileset is committed there.

The `serve` task will be the first one to be invoked. By default `serve` runs a Jetty server, but it is possible to select `http-kit`. It will reload Clojure files automatically, is able to run a Ring handler and also executes an initial function before the handler is started. 

The next task in our Boot pipeline is `watch`. This task waits for
file changes in any of the source or resource paths and then invokes
the rest of the pipeline. The rest of the pipeline is also invoked one
time for the initial fileset. An example:

```clojure
(deftask watch-example []
  (comp
    (watch)
    (show :fileset true)))
```

In this example `show` prints out the fileset that it received as a
tree. When we invoke it we see the initial fileset tree. When we add a
file, the watch task will invoke `show` again and we would see the new
fileset tree with the added file.

Back to our development pipeline:

```clojure
(deftask dev []
  (set-env!
   :source-paths #(conj % "src-cljs-dev"))
  (comp
   (serve :handler 'animals.api/handler
          :reload true
          :init 'animals.api/init)
   (watch)
   (reload :on-jsload 'animals.main/fig-reload)
   (cljs-repl)
   (cljs)))
```

Whenever a file changes, the `reload` task is invoked. This will send
changed assets to the browser via a websocket connection. The task
after that, `cljs-repl` starts an nrepl server in which it is possible
to start a ClojureScript REPL. This task also covers our requirement
to have a normal Clojure REPL session with our program. Finally, the
`cljs` task compile ClojureScript to JavaScript. I am not sure if it
matters if `cljs-repl` comes before or after `watch`, but `cljs`
surely has to come after it, since it has to see new filesets for
incremental compilation.

### Standalone jar

The final requirement is producing a standalone jar. In Leiningen this
is done with the `uberjar` task. We need to tell Leiningen where it
can find the main namespace that will have the `-main` function that
will be invoked when the jar is run. Also we need to aot that namespace:

```clojure
:aot [animals.uberjar]
:main animals.uberjar
```

Before producing a standalone jar, we must invoke the ClojureScript
compiler to produce production worthy JavaScript. For convenience we
can make an `alias` that combines all these steps:

```clojure
:aliases {"build" ["do" "clean" ["cljsbuild" "once" "prod"] "uberjar"]}
```

Note that `uberjar` will invoke `clean` also. One problem that arose
while writing this blog was that I had the entire `out` directory in
`:clean-targets`, so when `cljsbuild` was done, `uberjar` would remove
its output again. You will never have this kind of problem with Boot.

In Boot our `build` task looks like this:

```clojure
(deftask build-cljs []
  (set-env!
   :source-paths #(conj % "src-cljs-prod"))
  (cljs :optimizations :advanced))

(deftask build []
  (comp
   (build-cljs)
   (aot :namespace '#{animals.uberjar})
   (pom :project 'animals
        :version "1.0.0")
   (uber)
   (jar :main 'animals.uberjar)))
```

First we invoke the sub-task `build-cljs` which includes sources from
`src-cljs-prod` and produces optimized JavaScript. The next task performs aot on the main namespace. Then a pom file is produced. The `uber` task adds jar entries from dependencies to the fileset. Finally, the `jar` task produces a jar file from the fileset with the main namespace set to `animals.uberjar`. I love how Boot decomposes these tasks, so you can actually see what is going on when you produce a standalone artifact.

## Issues

During this blog post I ran into a couple of issues with Boot.

The first issue had to do with dependency resolution and Clojure versions. This [issue](https://github.com/boot-clj/boot/issues/210) has been solved. Thanks Alan Dipert! 

Another issue was that the `reload` task didn't have the concept of an `asset-path`.
I needed to work around this by creating an extra route in my handler:

```clojure
(defroutes routes
  ...
  (resources "/")
  (resources "/public") ;; extra route
  ...
```

This problem will be solved in a future version of `reload`. See this
[issue](https://github.com/adzerk-oss/boot-reload/issues/18).

## Conclusion

Leiningen is a battle tested tool and probably the safest bet if
you're just starting with Clojure. However, Boot has certainly sparked
my interest. It has an elegant design and a more functional feel to
it. I'll certainly use it on a future project.

Here are my recommendations based on my brief experience with Boot.

Use Leiningen if you:

- want to get started fast and like to get help from the majority of
  the Clojure community
- don't want to take risks in terms of stability
- like configuration and convenience over programmability and composition

Use Boot if you:

- like programs more than configuration files
- don't like to think about cleaning directories
- need to run one JVM for the entire development process (in
  Leiningen I needed two: one for Clojure and one for ClojureScript)
- need to perform builds tasks with mutually exclusive dependencies

Thanks for reading my blogpost. Feedback is appreciated. Did I
misunderstand something about Boot? Please let me know!

## Credits

Thanks to
[Alan Dipert](http://tailrecursion.com/~alan/index.cgi/index),
[Martin Klepsch](http://www.martinklepsch.org/),
[Earl S. Sauver](http://estsauver.com/) and some other fine folks in
the #boot channel on [Slack](http://clojurians.net).
