---
layout: post
title: "Inline def: an effective debugging technique for Clojure"
date: 2017-05-25 22:25:43 +0200
comments: true
categories: [clojure]
published: true
---

Clojure beginners often hear that inline `def` is bad style. For code
that you commit that's mostly true. Example:

``` clojure
(defn foo [x]
  (def y (+ x 1))
  y)
```

Here `(def y)` performs a side effect every time you call `foo`, by
(re)defining a global `Var`. Instead you should use `let` to make it a
local:

``` clojure
(defn foo [x]
  (let [y (+ x 1)]
    y))
```

However, inline `defs` can serve a useful purpose, namely that of a
simple debugger. Debuggers for Clojure exist, e.g. in CIDER, but I
mostly forget to use them. After `println`, inline `def` is my
debugging tool of choice.

Example:

``` clojure
(defn foo [& [{:keys [a b c] :as m}]]
  (+ a b c))
```

How should I call this function? Of course.

``` clojure
(foo :a 1 :b 2 :c 3)
;; => java.lang.NullPointerException
```

Wait, what? Inline `def` to the rescue.

``` clojure
(defn foo [& [{:keys [a b c] :as m}]]
  (def m m)
  (+ a b c))
  
(foo :a 1 :b 2 :c 3)
;; => java.lang.NullPointerException
m ;;=> :a
```

Oh, of course! What happened is that `m` was bound to the first
argument. I get it, I should have called `foo` like this:

 ```
 (foo {:a 1 :b 2 :c 3}) ;;=> 6
 ```

Time to remove the inline `def` and get on with life.

I regularly use this technique to capture a giant piece of input that
is part of some processing chain. Recently I was working on some XML
parsing and did this: 

``` clojure
(defn process-xml [xml]
  (def x xml)
  ;; processing
  )
```

That function was part of a flow that reads from a directory of
zipfiles containing XML files. After processing a few files I would
get an exception. Let's inspect `x`, the last input that triggered the
exception.

``` clojure
x ;;=> ""
```

So the last entry from the zipfile was empty. Turns out the entry
was not an XML file, but a directory represented by an empty
string. Another example involved some XML file that didn't have the
format I expected. Every time I got a new exception I could quickly
see what was going on and retry `process-xml` with `x` without kicking
off the flow from scratch.

``` clojure
(process-xml x) ;;=> ok, now it works!
```

Next time you have the urge to reach for a debugger, you might want to
become friends with inline `def` first.
