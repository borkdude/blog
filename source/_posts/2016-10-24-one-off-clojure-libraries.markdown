---
layout: post
title: "One off experiments with Clojure and ClojureScript libraries"
date: 2016-10-19 16:19:43 +0200
comments: true
categories: [clojure,leiningen, boot, planck]
published: true
---

Did you ever need to know the date and time 30 hours from now,
because that is the time you could check into your plane to
EuroClojure 2016 (and you're too lazy to do this in your head)? Or
maybe you just saw an interesting Clojure library on Twitter or Reddit
that you wanted to try out?  How convenient would it be if you didn't
have to create a project for such one off experiments.

There are several good options in Clojure for this. In this post let's
assume we were going to try
out [`clj-time`](https://github.com/clj-time/clj-time), an excellent
date and time library based on Joda Time. We'll show how to make a
script that gives you almost instantaneous access to this library from
the command line using [Boot](http://boot-clj.com). And then we'll
make it even faster using [Planck](http://planck-repl.org/).

## Leiningen

For [Leiningen](https://leiningen.org), there
is [lein try](https://github.com/rkneufeld/lein-try). This is a plugin
you can install into `~/.lein/profiles.clj`. Then from the command
line, just type `lein try clj-time` and we're good to go:

``` clojure
user=> (require '[clj-time.core :as t])
nil
user=> (require '[clj-time.local :as l])
nil
user=> (t/plus (l/local-now) (t/hours 30))
#object[org.joda.time.DateTime 0x3b3c5f5f "2016-10-25T16:53:58.154+02:00"]
```

## Boot

For [Boot](http://boot-clj.com) this story seems even simpler as there
is no need to install a plugin. Boot supports an option for including
dependencies from the command line. Just type `boot -d clj-time repl`
to get a REPL with the latest `clj-time` as a dependency:

``` clojure
$ boot -d clj-time repl
;;; output omitted
boot.user=> (require '[clj-time.core :as t])
;;; etc.
```

Note that Boot's `repl` task also supports the `--eval` option (`-e`
for short), so we can already put the `require` on the command line:

``` clojure
$ boot -d clj-time repl -e "(require '[clj-time.core :as t]))"
Clojure 1.8.0
;;; output omitted
boot.user=> (t/plus (l/local-now) (t/hours 30))
#object[org.joda.time.DateTime 0x3489e3ab "2016-10-25T17:11:18.560+02:00"
```

How convenient this is. This allows us to write it as a script:

``` bash
#!/usr/bin/env bash

echo "Example: (t/plus (l/local-now) (t/hours 30))"
boot -d clj-time repl -e "(do (require '[clj-time.core :as t]) (require '[clj-time.local :as l]))"
```

Great to have handy for EuroClojure 2017!

Note that Boot allows us to load dependencies dynamically. Suppose
you're experimenting but need another library. No need to restart the REPL. You can just type:

``` clojure
boot.user=> (set-env! :dependencies '[[org.clojure/core.async "RELEASE"]])
;;; output omitted
boot.user=> (require '[clojure.core.async :refer [go-loop]])
;;; continue experimenting
```

## Plank

Wouldn't it be great if we could also experiment with ClojureScript
libraries from a REPL? For
example [`cljs-time`](https://github.com/andrewmcveigh/cljs-time)? The
easiest way to get here is [Planck](http://planck-repl.org/), which as
of now runs only on OS X.

Planck can use jar files from `~/.m2`, but you have to specify the
full classpath. This is easily done with the help of Boot:

``` bash
$ boot --dependencies org.clojars.micha/boot-cp            # load with-cp task that helps exporting minimal classpath to file
       --dependencies com.andrewmcveigh/cljs-time:"0.4.0"  # load dependency you actually want to try
       with-cp -w --file .classpath                        # write classpath to a file `.classpath`
```

The list of dependencies is now written to `.classpath`. You can re-use this file if your dependency hasn't changed.

Now we're ready to start the Planck REPL. It's fast! Even faster when
you use the `K` option which caches compiled ClojureScript.

``` bash
$ planck -Kc `cat .classpath` -e "(require '[cljs-time.core :as t])" -r
cljs.user=> (require '[cljs-time.local :as l])
cljs.user=> (str (-> (t/plus (t/now) (t/hours 30)) (t/to-default-time-zone)))
"20161025T220847"
```
    
## Final thoughts
Typing directly in a REPL only goes so far. For larger expressions it
is more convenient to write in a text editor and then send the code to
the REPL. For experiments started with Leiningen or Boot you can use
an nREPL client. I
use [CIDER](https://github.com/clojure-emacs/cider). For Planck you
can use [inf-clojure](http://planck-repl.org/ides.html).

That's it. I hope this also helpful to beginners. Performing little
Clojure experiments can grow into an addiction. Before you know it,
you're soaked into your first Clojure project.
