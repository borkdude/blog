Title: Babashka vs cream
Date: 2026-11-02
Tags: clojure, babashka, graalvm
Description: Why babashka stays on SCI, with load time, binary size and build time numbers.

I've given a lot of thought to whether [cream](https://github.com/borkdude/cream)
should affect babashka. Cream is based on [Crema](https://github.com/oracle/graal/issues/11327), a native-image compatible
bytecode interpreter, basically a JVM inside of native-image.

What it concretely would mean to move babashka to Crema is that we could run
virtually any Clojure program without changes. This is exciting.

Crema is an impressive piece of engineering. Native-image could not define classes
at run time before, which is the reason SCI exists. With Crema, `eval`, `require`
and Java libraries from jars just work in a native binary, and cream starts as
fast as babashka.

But it has serious downsides compared to using SCI.

## Load time

The main downside is the load time of Clojure sources. Evaluating a fn in cream
runs the Clojure compiler and emits a class, which just takes more work.
Compilation accounts for about 1000ms of the load. Defining the 1670 classes takes
the rest. SCI interprets the forms and skips both steps.

Here is the time to load malli 0.16.4 from source:

```bash
DEPS='{:deps {metosin/malli {:mvn/version "0.16.4"}}}'
CP=$(clojure -Spath -Sdeps "$DEPS")
E="(time (require 'malli.core 'malli.generator))"

bb -cp "$CP" -e "$E"
clojure -Sdeps "$DEPS" -M -e "$E"
./cream -Scp "$CP" -M -e "$E"
```

```
bb     ## 0.06s
JVM    ############################ 0.93s
cream  ######################################## 1.34s
```

| | `require` | whole process |
|---|---|---|
| bb | 55ms | 0.06s |
| JVM | 610ms | 0.93s |
| cream | 1310ms | 1.34s |

Babashka on SCI is just way faster for loading bigger libraries from source and then
executing some expressions, which is commonly called "scripting". When loading a
bigger lib like malli, you might as well just use a normal JVM instead of cream.

The gap shrinks with smaller libraries, but it stays quite significant. Babashka
loads [medley](https://github.com/weavejester/medley) 9x faster than cream and
[camel-snake-kebab](https://github.com/clj-commons/camel-snake-kebab) 15x faster.

Startup is the same in both babashka and cream: about 10ms.

Cream loads malli in about 330ms from AOT-compiled classes, and a cache could do
that for you. But caches are a hassle, and babashka is faster than the AOT case anyway.

## Performance

Performance of cream is better than bb for hot JIT-ed code, but worse than a JVM.

| | bb | cream | JVM |
|---|---|---|---|
| malli validate, 1M calls | 300ms | 70ms | 35ms |
| `fib 35`, hot | 1230ms | 815ms | 73ms |
| `fib 27`, cold | 26ms | 46ms | 5ms |
| `(reduce + (map inc (range 10000000)))` | 157ms | 725ms | 80ms |

Cream's JIT makes no difference in the last row, it takes 725ms with the JIT off too.

Cream's JIT is still warming up during `fib 27`. For a script, the code does not run
long enough for that JIT to pay off. Since the core feature of bb is
scripting with good startup time, bb + SCI wins here.

## Binary size and build time

Another major downside of including Crema is the binary size. I built `bb` with
Crema and the binary goes up from 75MB to about 200MB. Cream itself is about
225MB. Maybe this isn't that important, but compile times also go up. I expect
double or triple in CI, which isn't fun at all when you build a binary for
several OSes. SCI is just way more light weight here too.

## What bb gives up

Code that needs real JVM classes, or a Java library that bb doesn't ship, stays
out of reach. Pods cover part of that. For the rest there is the JVM, or cream,
which sits in the gray area between babashka and JVM Clojure.

## Conclusion

This is not a verdict on Crema, which is still experimental and improving
fast. A native binary with full Clojure and a working JIT is very interesting
and exciting. Most of the load time comes from the Clojure compiler. It's just
not the right trade-off for babashka today. Perhaps with an experimental fork of
Clojure that reduces the number of emitted classes, this picture will change. I
do use a fork of Clojure for cream, but it only has very mild changes to make it
work with Crema. I haven't changed anything significant in the compiler.

For a while I thought SCI would be replaced with Crema in bb, but it still wins
for the scripting use case. And babashka is all about scripting. I will keep
improving SCI to make it both load and run faster over time. I was planning to
do that anyway. Now that SCI isn't going away any time soon, I'm even more
determined to make babashka the best possible scripting experience for Clojure.

You can view more numbers in the
[cream README](https://github.com/borkdude/cream#cream-vs-babashka).
