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
and Java libraries just work in a native binary, and cream starts as
fast as babashka.

But it has serious downsides compared to using SCI.

## Load time

The main downside is the load time of Clojure sources. Evaluating a fn in cream
runs the Clojure compiler and emits a class which then has to be loaded by a
classloader. This takes longer than it takes SCI to construct an interpreted function.

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

With AOT-compiled classes, malli loads in about 300ms in both cream and the JVM.
Cream spends 10ms on startup and 290ms on loading classes, the JVM 200ms on
startup and 100ms on loading classes. Run-time performance is worse in cream, so
if you AOT anyway, the JVM is the better choice. Cream could AOT-compile
libraries on first use and cache the classes, but caches are a hassle, and
babashka is faster than the AOT case anyway. So it seems something Crema-based
in between bb and a JVM doesn't offer us much benefit here.

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

Another major downside of including Crema is the binary size. Even when I only
use Crema for loading Java classes, as described below, the binary goes up from
75MB to about 200MB. Cream itself is about
225MB. Maybe this isn't that important, but compile times also go up. I expect
double or triple in CI, which isn't fun at all when you build a binary for
several OSes. SCI is just way more light weight here too.

## Combining SCI and Crema

Another option is to combine both approaches: SCI keeps interpreting Clojure and
Crema is only used to load Java classes that are not in the image. I tried this on the
[crema-hybrid](https://github.com/babashka/babashka/tree/crema-hybrid) branch and
it works for many libraries. A tiny script with BouncyCastle, commons-compress or
Caffeine takes about 40ms for the whole process, against about 320ms on the JVM.
Loading malli is slow in cream because Clojure compiles every form and defines
about 1670 classes. A Java library is already compiled, and a single call only
loads the classes it needs.

Load time of Clojure code is not the problem here, since SCI still does that. The
main downside of this hybrid is that a JDK package that wasn't preserved at build
time aborts the process instead of throwing an exception, and you only find out
by running the library. Of course we can protect users from this with extensive
testing.

The two other downsides are the same as for cream:

- Binary size: this hybrid is what goes from 75MB to about 200MB, for every user,
  even for scripts that never load a class. A class loaded from a jar calls JDK
  methods by name. In a native image those names are normally gone, and methods
  bb doesn't use are not compiled in at all. So Crema has to keep a table of all
  compiled classes and methods, and whole JDK packages have to be preserved.
- Build time: compile times go up as well, as described above.

If GraalVM shrinks that table or turns the abort into an exception, I'll look at
this again.

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

The hybrid is more tempting, since it keeps SCI's load time and loads Java
libraries faster than a JVM. But today it costs every user about 125MB and slower
builds, and a library can still abort the process. So for now it stays on a branch.

For a while I thought SCI would be replaced with Crema in bb, but it still wins
for the scripting use case. And babashka is all about scripting. I will keep
improving SCI to make it both load and run faster over time. I was planning to
do that anyway. Now that SCI isn't going away any time soon, I'm even more
determined to make babashka the best possible scripting experience for Clojure.

You can view more numbers in the
[cream README](https://github.com/borkdude/cream#cream-vs-babashka).
