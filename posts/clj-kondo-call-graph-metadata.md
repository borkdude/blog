Title: Finding transitive var usages with clj-kondo
Date: 2026-06-10
Tags: clojure, clj-kondo, babashka
Description: Using clj-kondo analysis data and a babashka pod to find all transitive var relations

Today fellow Clojurian Søren Knudsen asked the following question on [Clojurians Slack](https://clojurians.net/):

> Say I'd like an overview of which fns in my Clojurescript app don't have `:x` metadata and aren't children of functions that have `:x`.
> I'd love this overview as data.
>
> Anyone know a relevant analysis tool for this purpose?

Let's represent this problem in code form. Read it from bottom to top.

```clojure
(defn grandchild [] ; no :x, but reachable via child: ignore
  :leaf)

(defn child [] ; no :x, called by ^:x grandparent: ignore
  (grandchild))

(defn ^:x grandparent [] ;; has :x metadata, ignore
  (child))

(defn standalone [] ;; has no :x metadata and not reachable from anything with :x metadata, include
  :other)
```

It turns out that clj-kondo [analysis
data](https://github.com/clj-kondo/clj-kondo/tree/master/analysis) is well
suited to solve this problem. In this blog post, let's write a babashka script,
that uses the clj-kondo pod.  This bit of setup lets you do that. Of course you
could also use clj-kondo as a regular JVM dependency, but we're going for ease
here, since it's just a tiny script at this point.

``` clojure
#!/usr/bin/env bb
(require '[babashka.pods :as pods]
         '[clojure.set :as set])

(pods/load-pod 'clj-kondo/clj-kondo "2025.06.05")
(require '[pod.borkdude.clj-kondo :as clj-kondo])
```
Clj-kondo lets you find var-definitions and var-usages. Clj-kondo can also include var metadata. The arguments to `clj-kondo`'s `run!` API function then should look like this:

```clojure
(def analysis
  (-> (clj-kondo/run! {:lint ["src"]
                       :config {:analysis {:var-definitions {:meta [:x]}
                                           :var-usages true}}})
      :analysis))
```


To illustrate how it works, we'll introduce a multi-namespace project:

```clojure
;; src/app/core.cljs
(ns app.core
  (:require [app.util :as util]))

(defn ^:x grandparent []
  (util/child))

(defn standalone []
  :other)

;; a top level var usage, not inside any var definition:
(util/child)
```

```clojure
;; src/app/util.cljs
(ns app.util)

(defn grandchild []
  :leaf)

(defn child []
  (grandchild))
```

To illustrate what a var usage looks like in clj-kondo's analysis data, let's
look at the usage in `app.core` of `util/child`:

```clojure
{:from app.core
 :from-var grandparent
 :to app.util
 :name child
 ...}
```

The `:from` key describes from which namespace the reference was used. The
`:from-var` key describes in which var definition the var was used, and this is
the key ingredient of tracking transitive var usages. The `:to` + `:name` keys
describe which var was used.

## Vars

In clj-kondo's analysis you can request metadata from vars with `:meta [:x]` (or all metadata with `true`).
To distinguish all project vars from those that have `:x` metadata we can do the following:

```clojure
(defn fq [ns name] (symbol (str ns) (str name)))

(def defs (:var-definitions analysis))
(def project-vars (set (map #(fq (:ns %) (:name %)) defs)))
(def with-x (set (keep #(when (-> % :meta :x) (fq (:ns %) (:name %))) defs)))
```

Here `project-vars` is a set of symbols of all the project vars and `with-x` are
only those that have `:x` metadata.

## Building the call graph

Now we're ready to build the call graph that lets us solve our problem. In the
following we're making a map that looks like: `caller -> callees`, but we limit
callees only to project vars since we're not interested in vars like
`cljs.core/assoc`, `reagent.core/atom` etc.

```clojure
(def graph
  (reduce (fn [g {:keys [from from-var to name]}]
            (let [callee (fq to name)]
              (if (and from-var (contains? project-vars callee))
                (update g (fq from from-var) (fnil conj #{}) callee)
                g)))
          {}
          (:var-usages analysis)))
```

The `from-var` condition leaves out any top level var usages.
The `(contains? project-vars callee)` takes care of filtering only on project vars.
After running this, we'll end up with a graph (map) that looks like:

```clojure
{app.core/grandparent #{app.util/child}
 app.util/child       #{app.util/grandchild}}
```

So `app.core/grandparent` calls `app.util/child` and `app.util/child` calls `app.util/grandchild`.

## Transitive reachability

Next we write a function to find out what vars are reachable from a set of vars `starts`.

```clojure
(defn reachable [starts]
  (loop [seen #{}
         todo (set starts)]
    (if (empty? todo)
      seen
      (let [seen (into seen todo)
            used-vars (set (mapcat graph todo))
            unvisited (set/difference used-vars seen)]
        (recur seen unvisited)))))

(def children (set/difference (reachable with-x) with-x))

(prn {:graph graph
      :with-x with-x
      :children-of-x children
      :without-x (set/difference project-vars with-x children)})
```

The reachable function just calculates the transitive closure of the graph,
given a set of starting nodes (vars). The `children` var is the set of reachable vars without the starting points (the vars with `:x` metadata).

```clojure
{:graph {app.core/grandparent #{app.util/child}
         app.util/child #{app.util/grandchild}}
 :with-x #{app.core/grandparent}
 :children-of-x #{app.util/child app.util/grandchild}
 :without-x #{app.core/standalone}}
```

So the answer we were looking for is `#{app.core/standalone}`. This function is
neither a transitive child of any function with `:x` metadata, nor does it have
any `:x` metadata itself.

## The code

Here's the full script once again.

```clojure
#!/usr/bin/env bb
(require '[babashka.pods :as pods]
         '[clojure.set :as set])

(pods/load-pod 'clj-kondo/clj-kondo "2025.06.05")
(require '[pod.borkdude.clj-kondo :as clj-kondo])

(def analysis
  (-> (clj-kondo/run! {:lint ["src"]
                       :config {:analysis {:var-definitions {:meta [:x]}
                                           :var-usages true}}})
      :analysis))

(defn fq [ns name] (symbol (str ns) (str name)))

(def defs (:var-definitions analysis))
(def project-vars (set (map #(fq (:ns %) (:name %)) defs)))
(def with-x (set (keep #(when (-> % :meta :x) (fq (:ns %) (:name %))) defs)))

;; caller -> callees, project vars only
(def graph
  (reduce (fn [g {:keys [from from-var to name]}]
            (let [callee (fq to name)]
              (if (and from-var (contains? project-vars callee))
                (update g (fq from from-var) (fnil conj #{}) callee)
                g)))
          {}
          (:var-usages analysis)))

(defn reachable [starts]
  (loop [seen #{} todo (set starts)]
    (if (empty? todo)
      seen
      (let [seen (into seen todo)
            used-vars (set (mapcat graph todo))
            unvisited (set/difference used-vars seen)]
        (recur seen unvisited)))))

(def children (set/difference (reachable with-x) with-x))

(prn {:graph graph
      :with-x with-x
      :children-of-x children
      :without-x (set/difference project-vars with-x children)})
```

## Conclusion

I hope you learned how useful clj-kondo analysis data can be for tracking relations between vars and that you can use this data in casual babashka scripts as well!
