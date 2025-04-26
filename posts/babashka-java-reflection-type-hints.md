Title: Babashka Java interop, reflection and type hints
Date: 2025-04-26
Tags: clojure, babashka
Description: Babashka Java interop, reflection and type hints

Consider the following Clojure code:

``` clojure
(def thread-pool (java.util.concurrent.Executors/newCachedThreadPool))
(def fut (.submit thread-pool (fn [] 3)))
@fut ;;=> ?
```

I didn't use any type hints, but the names of the vars should give you an idea what's happening:

- a thread pool is created
- a piece of work in the form of a function is submitted
- the thread pool returns a future which we can dereference to do a blocking wait and get the result.

What result would you expect this program to give? My initial guess would be `3`.

However, in [babashka](https://github.com/babashka/babashka) the result would
sometimes be `nil` (version `1.12.199` on macOS Apple silicon does for
example). I've seen this happen in JVM Clojure too in CI (given no type hints,
and thus relying on Clojure reflection). I discovered this problem when trying
to make bb run the [fusebox](https://github.com/potetm/fusebox) library and
executing its test suite in CI using babashka.

What's the mechanism behind this flakiness? The `.submit` method in the snippet
above is overloaded. When Clojure is doing reflection, it finds the most
suitable method for `.submit` given the instance object and the arguments. In
this case the type of the instance object (the `thread-pool` value) is of type
`java.util.concurrent.ThreadPoolExecutor`, which has three overloads (inherited
from `java.util.concurrent.AbstractExecutorService`):

- `public Future<?> submit(Runnable task)`
- `public <T> Future<T> submit(Runnable task, T result)`
- `public <T> Future<T> submit(Callable<T> task)`

Only two of those match the number of arguments we used in the snippet, so we are left with:

- `public Future<?> submit(Runnable task)`
- `public <T> Future<T> submit(Callable<T> task)`

Clojure's reflector will try to see if the argument type to `.submit` matches
any of these methods. Since we called it with a function, in this case it
matches both. If multiple methods match, it will try to pick the most specific
method using `clojure.lang.Compiler/subsumes`. Since `Runnable` and `Callable`
are two distinct types (one doesn't inherit from the other), Clojure's reflector
just returns the first method it deemed most suitable. So it could be either
method. The java documentation mentions the `submit` method with the `Runnable`
argument first, but this isn't necessarily the order in which Java reflection
will list those methods. I have found out that the order may even be
indeterministic over multiple CI runs and JVM implementations or versions. What
the exact cause of this indeterminism is, I don't know, but I found out the hard
that it exists 😅.

So when does the above snippet return `nil`? If it chooses the `Runnable`
overload. Let me show this using a snippet that uses JVM Clojure type hinting,
where we don't rely on reflection:

``` clojure
(set! *warn-on-reflection* true)

(def thread-pool (java.util.concurrent.Executors/newCachedThreadPool))
(def fut (.submit ^java.util.concurrent.ThreadPoolExecutor thread-pool ^Runnable (fn [] 3)))
@fut  ;; => nil
```

Now let's do the same with `Callable`:

``` clojure
(set! *warn-on-reflection* true)

(def thread-pool (java.util.concurrent.Executors/newCachedThreadPool))
(def fut (.submit ^java.util.concurrent.ThreadPoolExecutor thread-pool ^Callable (fn [] 3)))
@fut ;; => 3
```

So we see the issue: depending on the overload of `.submit` we get a different
value when we dereference the future, either `nil` or the value returned from
the function.

So far, babashka has relied exclusively on runtime reflection to implement Java
interop. So we do get into a problem with the above snippet, unless we add type
hints. But so far babashka, or more specifically,
[SCI](https://github.com/babashka/sci) hasn't made use of type hints to
determine the most suitable method. The Clojure _compiler_ does this, but as you
know, SCI interprets code and doesn't make use of the clojure Compiler. It does
use a forked version of `clojure.lang.Reflector`, clojure's runtime reflection
code, though. So far, pretty much the only change to that code was making some
methods public that were used internally by
[SCI](https://github.com/babashka/sci). To fix the above problem, SCI now
actually makes use of type hints. So in the newly published version of babashka,
this code:

``` clojure
(def thread-pool (java.util.concurrent.Executors/newCachedThreadPool))
(def fut (.submit thread-pool ^Callable (fn [] 3)))
@fut ;;=> 3
```

will consistently return `3` and when changing `Callable` to `Runnable`, you'll
get `nil`. Note that the above snippet will still have the ambiguous behavior in
Clojure, since it doesn't have enough type hints to figure out the right method
at compile time and `clojure.lang.Reflector` does nothing with type hints. Once
JVM Clojure finds out that you're calling `.submit` on a
`java.util.concurrent.ExecutorService` though, the code will run with the
expected method.

Can you think of more methods in the Java standard library which have this
ambiguity when relying solely on Clojure reflection? I'd love to hear about
those to see if they work reliably in babashka given some extra help through
type hints.

Thanks to [Tim Pote](https://github.com/potetm) for thinking along when fixing
the issues in babashka and reading a preview of this blog post.
