## Octopress

Seven years ago I started writing about my adventures with Clojure. I started
using [Octopress](http://octopress.org/) back then and have been using it for
seven years. But in recent years I became frustrated with it since on every new
system I had to install a version of Ruby that happened to work with Octopress,
which wasn't always the case. When it did work, I started getting deprecation
warnings from Ruby while compiling this blog. Octopress 1, the version I
adopted in 2014 seemed no longer maintained and the Octopress website was
announcing that version 3 was coming soon. At least, the website announced it in
2015 but I haven't seen any updates since then. It felt time for me to move on.

## Requirements for replacement

I started thinking of what was essential for me when writing blog posts and came
up with this list of requirements / wishes:

- Port the existing blog with as little effort as possible
- Writing blog posts in a convenient format: markdown is good enough, let's keep it
- Don't use a framework like Octopress again: it isn't worth the learning curve for me
- Don't get stuck in a language I don't use every day (the deprecation warnings in Ruby scared me)

My blog has the following essential components:

- Archive page with list of all blog posts written so far
- Index page that lists the most recent blog posts
- Each post has its own HTML page
- An `atom.xml` feed for [Planet Clojure](http://planet.clojure.in/)
- Highlighting for Clojure code snippets: not super important, but I'd like to
  at least have this back after a rewrite.

The primary goal of this blog is to share some of my experiences. All other
things, like being able to react on a blog post, a fancy design, are secondary
and can be figured out later.

The language I am most fluent in is Clojure. [Babashka](https://babashka.org/)
is a scripting tool that has similar startup characteristics as Ruby so it would
be nice to keep that from my Octopress experience. Let's see if I can rewrite my
blog with a babashka script.

## Rewriting in babashka

I started with copying each markdown file in `source/_posts` and moved them to a
directory `posts`.  In Octopress, blog posts start with a section like:

```
---
layout: post
title: Figwheel keep Om turning!
date: 2014-09-25 21:00:50 +0200
comments: true
published: true
categories: [clojure, clojurescript, figwheel, om]
---
```

I removed these sections replaced them with maps in a file called `posts.edn`:

``` clojure
{:title "Figwheel keep Om turning!"
 :file "figwheel-keep-om-turning.md"
 :date "2014-09-25"
 :categories #{"clojure" "clojurescript" "figwheel" "om"}
 :legacy true}
```

The maps are top level values so I can easily append new ones programmatically.

Then I started writing the `render.clj` script which iterates over every entry
in `posts.edn` and renders markdown files to HTML.

To do markdown rendering from babashka I'm using
[bootleg](https://github.com/retrogradeorbit/bootleg) by Crispin Wellington
which is available in the [pod
registry](https://github.com/babashka/pod-registry/blob/master/examples/bootleg.clj).

I had to fix a couple of things to get the rendering back that I had with
Octopress. Links without markup, so `https://foobar.com` instead of
`[foobar](https://foobar.com)` were rendered as an `a` element before, but
bootleg, which uses [markdown-clj](https://github.com/yogthos/markdown-clj)
doesn't do this out of the box.

Another tweak I had to implement is support line breaks in the middle of
markdown link syntax, since emacs's `fill-paragraph` sometimes causes that to
happen:

``` markdown
[foo
bar](https://foobar.com)
```

The tweaks:

``` clojure
(pods/load-pod 'retrogradeorbit/bootleg "0.1.9")

(require '[pod.retrogradeorbit.bootleg.markdown :as md])

(defn markdown->html [file]
  (let [markdown (slurp file)
        ;; make links without markup clickable
        markdown (str/replace markdown #"http[A-Za-z0-9/:.=#?_-]+([\s])"
                              (fn [[match ws]]
                                (format "[%s](%s)%s"
                                        (str/trim match)
                                        (str/trim match)
                                        ws)))
        ;; allow links with markup over multiple lines
        markdown (str/replace markdown #"\[[^\]]+\n"
                              (fn [match]
                                (str/replace match "\n" "$RET$")))
        hiccup (md/markdown markdown :data)
        html (-> hiccup
                 (utils/convert-to :html))
        html (str/replace html "$RET$" "\n")]
    html))
```

After those workaround, it was pretty straightforward to support the most
essential things my Octopress blog did.

## Feeds

The generation of `atom.xml` and `planetclojure.xml` (which only contains
Clojure-related posts) with a bit of `clojure.data.xml` code:

``` clojure
(def blog-root "http://blog.michielborkent.nl/")

(defn atom-feed
  ;; validate at https://validator.w3.org/feed/check.cgi
  [posts]
  (-> (xml/sexp-as-element
       [::atom/feed
        {:xmlns "http://www.w3.org/2005/Atom"}
        [::atom/title "REPL adventures"]
        [::atom/link {:href "http://blog.michielborkent.nl/atom.xml" :rel "self"}]
        [::atom/link {:href "http://blog.michielborkent.nl"}]
        [::atom/updated (rfc-3339-now)]
        [::atom/id blog-root]
        [::atom/author
         [::atom/name "Michiel Borkent"]]
        (for [{:keys [title date file]} posts]
          [::atom/entry
           [::atom/id (str blog-root (str/replace file ".md" ".html"))]
           [::atom/title title]
           [::atom/updated (rfc-3339 date)]
           [::atom/content {:type "html"}
            [:-cdata (get @bodies file)]]])])
      xml/indent-str))

(spit (fs/file out-dir "atom.xml") (atom-feed posts))
(spit (fs/file out-dir "planetclojure.xml")
      (atom-feed (filter
                  (fn [post]
                    (some (:categories post) ["clojure" "clojurescript"]))
                  posts)))
```

This [feed validator](https://validator.w3.org/feed/check.cgi) gave pretty good
feedback on what should and should not go into this XML file.

## Rake -> bb tasks

Finally I wrote a `bb.edn` file with [babashka
tasks](https://book.babashka.org/#tasks) to create new blog posts. E.g. I
created this very blog post using:

``` shell
$ bb new :file migrating-octopress-to-babashka.md :title "Migrating this blog from octopress to babashka in 160 lines of Clojure"
```

All implemented tasks so far:

``` shell
$ bb tasks
The following tasks are available:

new     Create new blog article
render  Render blog
watch   Watch posts and templates and call render on file changes
publish Publish to blog.michielborkent.nl
```

This replaces the `rake` stuff I used to have with Octopress.

## Conclusion

I'm pretty happy with how far I got with a couple of hours hacking the babashka
script together. I feel in control over my own blog again. From here on I can
look for a plugin lets users respond to blog posts and perhaps a fancier
design. If you anything useful to share regarding this, please let me know on
e.g. [Twitter](https://twitter.com/borkdude).

The code for this blog is available [here](https://github.com/borkdude/blog).
