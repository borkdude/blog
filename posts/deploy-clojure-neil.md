Title: Deploying a Clojure project with neil and tools.build
Date: 2022-10-22
Tags: clojure, neil

In a [previous blog
post](https://blog.michielborkent.nl/new-clojure-project-quickstart.html), I
described how you can kick off a Clojure project with [neil](https://github.com/babashka/neil).

Today I want to show you how easy it is how to deploy a Clojure project to
Clojars with neil.

Assuming you already have a `deps.edn`, but you don't yet have a `build.clj`, you can run:

``` shell
$ neil add build
```

This creates a [tools.build](https://github.com/clojure/tools.build) `build.clj`
file with functions you typically need for building and deploying a Clojure
project.

The `build.clj` looks for the project name and version in your `deps.edn` under
`[:aliases :neil :project]`.

You can set the project name and version like this:

``` clojure
{:deps {}
 :paths ["resources"]
 :aliases
 {:neil {:project {:name io.github.clj-kondo/config-rum-rum
                   :version "1.0.0"}}
  :build ;; added by neil
  {:deps {io.github.clojure/tools.build {:git/tag "v0.8.3" :git/sha "0d20256"}
          slipset/deps-deploy {:mvn/version "0.2.0"}}
   :ns-default build}}}
```

Now all you have to do to deploy your project is run `clojure -T:build deploy`. This runs the `deploy` function in `build.clj` which uses
[deps-deploy](https://github.com/slipset/deps-deploy).

Whenever you want to bump your patch version, you can run:

``` shell
$ neil version patch
```

or set the version manually using:

``` shell
$ neil version set 1.0.1
```

and then deploy again.

The `neil version` command is similar to NPM's `version` command.

For a full project that uses this setup, check out [this
one](https://github.com/clj-kondo/clj-kondo.configs/tree/main/rum/rum) that I
just created today in a couple of keystrokes.

Happy deploying!
