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

The language I am most fluent in is Clojure. [Babashka](https://babashka.org/)
is a scripting tool that has similar startup characteristics as Ruby so it would
be nice to keep that from my Octopress experience. Let's see if I can rewrite my
blog with a babashka script.

## Rewriting in babashka

A started with copying each markdown file in `source/_posts` and moved them to a directory `posts`.
In Octopress, blog posts start with a section like:

```
---
layout: post
title:  "Welcome to Jekyll!"
---
```

