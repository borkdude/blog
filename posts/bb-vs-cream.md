Title: bb vs cream
Date: 2026-11-02
Tags: clojure, babashka, graalvm
Description: Why babashka stays on SCI, with load time, binary size and build time numbers.

I've given it a lot of thought if [cream](https://github.com/borkdude/cream)
should affect babashka. Cream is based on Crema, a native-image compatible
bytecode interpreter, basically a JVM inside of native-image.

What it concretely would mean to move babashka to Crema is that we could run
virtually any Clojure program without changes. But it has serious downsides
compared to using SCI.

## Load time

The main downside is the load time of Clojure sources. Evaluating a fn in cream
emits a class, which just takes more work. Loading malli defines about 1670
classes, at about 0.2ms per class. SCI interprets the forms and skips that step.

Here is the time to load malli 0.16.4 from source:

```bash
DEPS='{:deps {metosin/malli {:mvn/version "0.16.4"}}}'
CP=$(clojure -Spath -Sdeps "$DEPS")
E="(time (require 'malli.core 'malli.generator))"

bb -cp "$CP" -e "$E"
clojure -Sdeps "$DEPS" -M -e "$E"
./cream -Scp "$CP" -M -e "$E"
```

| | `require` | whole process |
|---|---|---|
| bb | 55ms | 0.06s |
| JVM | 610ms | 0.93s |
| cream | 1310ms | 1.34s |

bb + SCI is just way faster for loading bigger libraries from source and then
executing some expressions, which is commonly called "scripting". When loading a
bigger lib like malli, you might as well just use a normal JVM instead of cream.

The gap shrinks with smaller libraries, but it stays large. bb loads medley 9
times faster than cream and camel-snake-kebab 15 times faster.

Startup alone is a tie: both start in about 10ms.

Cream loads malli in about 330ms from AOT-compiled classes, and a cache could do
that for you. But scripting doesn't want caches, they are a hassle.

## Performance

Performance of cream is better than bb for hot JIT-ed code, but worse than a JVM.

| | bb | cream | JVM |
|---|---|---|---|
| malli validate, 1M calls | 388ms | 70ms | |
| 100k `DigestUtils/sha256Hex` | | 124ms | 42ms |
| `fib 27`, cold | 25ms | 43ms | |

For a script, the code does not run long enough for the JIT to pay off. Since the core feature of bb is
scripting with good startup time, bb + SCI wins here.

## Binary size and build time

Another major downside of including Crema is the binary size. I built bb with
Crema and the binary goes up from 75MB to about 200MB. Cream itself is about
225MB. Maybe this isn't that important, but the build also takes about 1.6 times
longer, 64s instead of 40s on my machine, which isn't fun at all when you build a
binary in CI for several OSes. SCI is just way more light weight.

## What bb gives up

Code that needs real JVM classes, or a Java library that bb doesn't ship, stays
out of reach. Pods cover part of that. For the rest there is the JVM, or cream if
you want to try something experimental.

## Conclusion

For a while I thought SCI would be replaced with Crema in bb, but it still wins
for the scripting.

You can view more numbers in the
[cream README](https://github.com/borkdude/cream#cream-vs-babashka).
