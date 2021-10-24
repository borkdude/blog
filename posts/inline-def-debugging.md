Clojure beginners often hear that inline `def` is bad style. For code
you commit that's mostly true. Example:

``` clojure
(defn foo [x]
  (def y (+ x 1))
  y)
```

Here `def` performs a side effect every time you call `foo`, by
(re)defining a `Var` `y` which is global to the current
namespace. Instead you should use `let` to make it a local:

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
  (def m m) ;; TODO: remove this line before commit
  (+ a b c))
  
(foo :a 1 :b 2 :c 3)
;; => java.lang.NullPointerException
m ;;=> :a
```

Oh, of course! What happened is that `m` was bound to the first
argument. I get it, I should have called `foo` like this:

``` clojure
(foo {:a 1 :b 2 :c 3}) ;;=> 6
```

Time to remove the inline `def` and get on with life.

I regularly use this technique to capture input that is part of some
processing chain and I'm not sure what data is flowing through, or
when the input is hard to simulate from the REPL, e.g. coming form a
large HTTP request body. Recently I was working on some XML
parsing. For every XML file this function was called:

``` clojure
(defn process-xml [xml-as-str]
  (def xml-as-str xml-as-str) ;; TODO: remove this line before commit
  ;; processing
  )
```

That function was part of larger flow that reads from a directory of
zipfiles containing XML files. After processing a few files I would
get an exception. Let's inspect `xml-as-str`, the last input that
triggered the exception.

``` clojure
xml-as-str ;;=> ""
```

So the last entry from the zipfile was empty. Turns out the entry was
not an XML file, but a directory represented by an empty
string. Another example involved some XML file that didn't have the
format I expected. Every time I got a new exception I could quickly
see what was going on and retry `process-xml` with `xml-as-str`
without kicking off the flow from scratch.

``` clojure
(process-xml xml-as-str) ;;=> ok, now it works!
```

Next time you have the urge to reach for a debugger, you might want to
become friends with inline `def` first.

Creating temporary vars with the same name as function arguments has
the added benefit of being able to evaluate expressions within your
function. This is discussed the
blogpost
[REPL Debugging: no stacktrace required](http://blog.cognitect.com/blog/2017/6/5/repl-debugging-no-stacktrace-required) by
[Stuart Halloway](stuarthalloway).

*) [And somewhat controversial](https://www.reddit.com/r/Clojure/comments/6dcmv2/an_effective_debugging_technique_for_clojure/)
