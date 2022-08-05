Title: Using clojure.spec.alpha with babashka
Date: 2021-12-10
Tags: clojure

## Tl;dr

If you want to use `clojure.spec.alpha` with babashka, upgrade to 0.7.0 or newer
and use [babashka/spec.alpha](https://github.com/babashka/spec.alpha) as a
library.

## The issue

[Spec](https://github.com/clojure/spec.alpha) is a library for validating and
conforming (destructuring) data that comes with Clojure. For over a year there
was an [open issue](https://github.com/babashka/babashka/issues/558) in the
[babashka](https://babashka.org/) Github repo about whether to include
clojure.spec into babashka, a fast starting native Clojure scripting
environment. Clojure.spec is used in many libraries and adding it would add
compatibility with more of them. I already knew that from a technical
perspective [SCI](https://github.com/babashka/sci) (the interpreter used by
babashka) and clojure.spec can work together well. I've put this into practice
in [grasp](https://github.com/borkdude/grasp), a tool to "grep" Clojure source
using clojure specs. It provides a binary which includes clojure spec in the
same way that babashka would. The question is: should we include
clojure.spec.alpha in babashka?

## What does built-in mean?

Adding a library as a built-in in babashka means that it is compiled into
babashka's native image. The functions are pre-compiled to native machine code
and aren't run through the SCI interpreter. This is much better for performance,
but also for startup time. Loading libraries from source in babashka involved
parsing and analyzing code before using it. Adding a library as a built-in means
that this work is already done at compile time, by the Clojure compiler instead
of SCI.

## Alpha

So far all built-in third party libraries in babashka are well established
projects that have been around for years and whose APIs are unlikely to
change. Boring in the good sense. Clojure spec, as of now, is alpha. That
doesn't mean that `clojure.spec.alpha` is unstable, but what does it mean? Will
it ever disappear from Clojure once it's not alpha anymore? In 2019 Alex Miller
gave an inspiring [talk](https://youtu.be/KeZNRypKVa4) at ClojuTRE in which he
announced that spec 2 was soon coming. I'm still hoping it will soon come, but I
understand that a lot of good things happened at Cognitect and they had other
things to work on. In the open babashka issue I've asked for feedback on including
`clojure.spec.alpha` as it is and most people were in favor of waiting for
clojure spec 2. I decided to be patient and investigate alternatives. If we
aren't going to include `clojure.spec.alpha` as a built-in, what alternatives do
we have? We could try to run `clojure.spec.alpha` from source instead of a
built-in so people can use it as an optional library.

## Test.check

The [test.check](https://github.com/clojure/test.check) library is used by
clojure.spec to generate random data from specs. Libraries operating in the same
space as spec, like [Malli](https://github.com/metosin/malli), are using this
library for the same purpose. I decided to go ahead and add this library in
babashka as a preparation for the soon coming spec 2. This was done in
[0.2.8](https://github.com/babashka/babashka/blob/master/CHANGELOG.md#v028),
released in January 2021. Since then you could already use namespaces from
test.check in babashka, without using it with spec.

## Spartan.spec

When babashka was only a few months old, it didn't cover as many clojure
features as it does today. Running `clojure.spec.alpha` from source wasn't
something within reach, mostly because babashka didn't support
protocols. Protocols were introduced in version
[0.1.1](https://github.com/babashka/babashka/blob/master/CHANGELOG.md#v011-2020-06-10)
in June of 2020, but by then I already made a rewrite of clojure.spec.alpha that
used plain hashmaps instead of protocols. This version was called
[spartan.spec](https://github.com/borkdude/spartan.spec): spartan, because it
supported the basic features of spec, but not all. It did not support
generators, `fdef` and instrumentation. Although I could have made that work,
just validating and conforming data is what most people were doing with spec in
the libraries that I've been trying to run with babashka. Upon requiring
`spartan.spec` it would create the namespace `clojure.spec.alpha` for you, so
libraries loaded after that point would think they were using the original. This
approach proved out to work pretty well. Even tools like
[expound](https://github.com/bhb/expound) worked with spartan.spec. But
spartan.spec was a rewrite of spec and needed more work if we wanted to cover
the other features. An effort that could be spared if babashka could just load
the original spec from source.

## Clojure.spec.alpha from source

When introducing protocols, I hadn't thought of revisiting running
clojure.spec.alpha from source again. For some reason this only occurred to me
in the past few days. I discovered that the remaining incompatibilities were
minimal. Here are some things I found and what I did in a fork to solve the
compatibility with babashka:

- Spec uses `clojure.lang.Compiler/demunge` to turn function names into symbols
  for displaying information about them. This functionality is also provided by
  `clojure.main/demunge` and `clojure.repl/demunge`, and both functions were
  already in babashka. I didn't want to expose `clojure.lang.Compiler` in
  babashka, since babashka doesn't have a compiler at all, so I replaced these
  calls with the `clojure.main/demunge` function. I raised an issue on
  [ask.clojure](https://ask.clojure.org/index.php/11371/consider-adding-demunge-into-clojure-core)
  to see if we can get this function into `clojure.core`.
- Spec uses Java interop on `clojure.lang.Var` to get the namespace and name
  symbols from a var. In babashka there is no `clojure.lang.Var`: SCI has its
  own var type which works both in the JVM / GraalVM native and in JavaScript. I
  used the metadata on SCI vars to get this information instead.
- The function `clojure.spec.alpha/multi-spec-impl` uses Java interop on
  multimethods, so I had to expose `clojure.lang.MultiFn` in babashka to make
  that possible. I noticed this when trying to use `multi-spec` in babashka when
  I thought I was already done porting. Surprisingly spec's tests didn't catch
  this, which I had been using to verify if the port worked. I pulled out some
  examples from the [spec guide](https://clojure.org/guides/spec) and turned
  those into tests for `multi-spec`. Alex Miller included those tests upstream
  after I poked him about it. Thanks Alex!  The `multi-spec-impl` function pulls
  out the dispatch function from a multimethod with `(.dispatchFn mm)`. This is
  not a method call, since `dispatchFn` is a public field. I needed to improve
  SCI to support public field lookups in Java classes. The expression could have
  been written like `(.-dispatchFn mm)` to make the field lookup explicit and
  this is what I changed it to, in order to not have to support this ambiguity
  in SCI. This issue would have been easier to deal with if `clojure.core`
  exposed `dispatch-fn` like ClojureScript does. Vote
  [here](https://ask.clojure.org/index.php/10261/please-add-dispatch-fn-to-clojure-core)
  to make that happen. Also, `get-method` is a core function, the `.getMethod`
  interop wasn't really necessary, but perhaps it is there for performance
  reasons. This is now supported in babashka without changes.
- `clojure.lang.RT/checkSpecAsserts` is not available in babashka. I replaced
  this with an atom in the `clojure.spec.alpha` namespace.

With these minimal changes and some functions I needed to expose in babashka
which weren't there before (`inst-ms` being one of them), babashka is now able
to run clojure.spec. All tests are passing:

```
$ bb test

Testing clojure.test-clojure.spec

Testing clojure.test-clojure.instr

Testing clojure.test-clojure.multi-spec

Ran 13 tests containing 168 assertions.
0 failures, 0 errors.
```

In addition to spec's own tests, I've added tests from the following libraries
that are using spec to babashka's CI:
[better-cond](https://github.com/Engelberg/better-cond),
[coax](https://github.com/exoscale/coax),
[orchestra](https://github.com/jeaye/orchestra) and
[integrant](https://github.com/weavejester/integrant).

The benefit of maintaining a fork with minimal changes is that I can easily pull
in changes from upstream.  The fork is available
[here](https://github.com/babashka/spec.alpha) and works with the newly released
babashka 0.7.0.

A demo of data generation using spec:

``` clojure
(require '[babashka.deps :as deps])

(deps/add-deps '{:deps {org.babashka/spec.alpha {:git/url "https://github.com/babashka/spec.alpha"
                                                 :git/sha "644a7fc216e43d5da87b07471b0f87d874107d1a"}}})

(require '[clojure.spec.alpha :as s]
         '[clojure.spec.gen.alpha :as gen]
         '[clojure.string :as str])

(prn (gen/sample (s/gen int?)))

(s/def ::street (s/and string? (complement str/blank?)))
(s/def ::street-number int?)
(s/def ::address (s/keys :req-un [::street ::street-number]))
(s/def ::name (s/and string? (complement str/blank?)))
(s/def ::contact (s/keys :req-un [::name ::address]))
(prn (gen/sample (s/gen ::contact)))
```

``` clojure
(0 -1 0 -1 -6 -1 -2 0 9 1)
({:name "r", :address {:street "Y", :street-number -1}} {:name "m", :address {:street "k8", :street-number -1}} {:name "5", :address {:street "s1E", :street-number 0}} {:name "o4H", :address {:street "4", :street-number -1}} {:name "nkhf", :address {:street "4Fh92", :street-number 1}} {:name "e", :address {:street "X", :street-number 4}} {:name "5v76a9B", :address {:street "bEf7e", :street-number 23}} {:name "213V", :address {:street "fNX3wr", :street-number 5}} {:name "8336lvbb9", :address {:street "fVP", :street-number -1}} {:name "x8X", :address {:street "utPQA", :street-number -7}})
```

The above example executes in about 120ms on my machine.
