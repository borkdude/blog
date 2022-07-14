{:title "Clj-kondo hooks", :date "2020-06-21", :categories #{"clojure" "clj-kondo"}, :legacy true}

[Clj-kondo](https://github.com/borkdude/clj-kondo/) is a Clojure linter that
uses static analysis. This means it only looks at source code, but does not
execute it. While the information available to produce good lint warnings is
more limited with static analysis, static analyzing is generally more
performant, and works independently from a runtime (JVM, nodeJS, browser,
etc.). Static analysis does not suffer from causing unwanted side effects when
executing code. It often yields good enough results. Where static analysis falls
short, clj-kondo offers
[configuration](https://github.com/borkdude/clj-kondo/blob/master/doc/config.md)
options where the user can help clj-kondo understand more of their code.

One area where static analysis of Clojure code becomes hard is macros. Macros
can introduce new syntactical constructs. Often macros are syntactically similar
to existing Clojure core macros. This is where you can use clj-kondo's
`:lint-as` configuration. In places where this isn't possible, for example
because the macro had irregular binding patterns, one could use
`:unresolved-symbol` + `:exclude` which would simply ignore unresolved symbol
errors in an entire s-expression.

I've been asking myself the following question for a while now: can clj-kondo
make more sense of custom macros with a little help from the user? Clj-kondo
could invent some DSL to express a transformation, but DSLs often cover just 80%
of what you want to achieve. To get 20% more power, you'd have to turn the DSL
into something like Clojure itself. So why not just use Clojure directly?

Clj-kondo is distributed in a couple of different ways. A widely used
distribution is the binary compiled with GraalVM. One limitation of a
GraalVM-compiled binary is that one cannot introduce new classes at runtime. And
this is what `clojure.core/eval` does, so that's off the table. Since August
2019 I've been working on the [Small Clojure
Interpreter](https://github.com/borkdude/sci). It's not a compiler, like
Clojure, but it allows you to interpret Clojure expressions within a GraalVM
binary. The interpreter is used in [babashka](http://babashka.org/) but it has
other uses as well and also works in JavaScript.

This interpreter can be used in clj-kondo to execute hooks that users can
provide to transform custom macro calls into constructs that clj-kondo can
understand. And this is what I've worked on.

Clj-kondo uses a vendored version of
[rewrite-clj](https://github.com/xsc/rewrite-clj) to analyze source code. My
first attempt at the hooks API was to transform the rewrite-clj nodes into
Clojure s-expressions. Then the user's hook function would transform these
s-expressions in a similar fashion as the macro would, returning new
s-expressions. Lastly clj-kondo would then translate these s-expressions back
into rewrite-clj nodes and continue analysis. Ostensibly this worked great for
several test cases, but ultimately it wasn't good enough. The main problem is
that numbers, strings and keywords cannot carry metadata. Metadata on sexprs was
used to keep track of the original locations. When (some of) these locations are
lost, clj-kondo cannot accurately position lint warnings anymore. And this is
unacceptable in my opinion. You can read more about this problem on ClojureVerse
[here](https://clojureverse.org/t/feedback-wanted-on-new-clj-kondo-macroexpansion-feature/6043)
and in the issue on Github
[here](https://github.com/borkdude/clj-kondo/issues/811).

After more experimentation I decided that the transformation should happen
direcly on rewrite-clj nodes in order to preserve location information. This led
to the current implementation of the `:analyze-call` hook, documented
[here](https://github.com/borkdude/clj-kondo/blob/master/doc/config.md#hooks).
Additionally, some library specific example config + hook code is provided
[here](https://github.com/borkdude/clj-kondo/tree/master/examples), showing how
to make clj-kondo understand [Rum](https://github.com/tonsky/rum)'s `defc` macro
and [slingshot](https://github.com/scgilardi/slingshot)'s `try+` macro.

I consider this new feature a powerful feature but not an easy to use one. It
does provide a higher degree of linting quality while still enjoying the
benefits of static analysis. Luckily we only have to figure out the right code
for each library once. I urge library authors and users to contribute their
configurations to the clj-kondo
[repository](https://github.com/borkdude/clj-kondo/tree/master/examples) so we
can all benefit.

[Clojurist Together](https://www.clojuriststogether.org/) has sponsored this
work as part of their [Summer of
Bugs](https://www.clojuriststogether.org/news/announcing-summer-of-bugs/)
program. Thanks to the people who have made this possible: the Clojurists
Together staff and of course the people who donate.

Hope you enjoy. Happy linting!

Michiel Borkent (a.k.a. [@borkdude](https://twitter.com/borkdude))
