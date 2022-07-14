{:date "2017-10-10", :categories #{"clojure" "spec"}, :legacy true}

# Parsing a circuit with clojure.spec

[Advent of Code](http://adventofcode.com/) is a collection of
self-contained programming problems, one for each day during
[Advent](https://en.wikipedia.org/wiki/Advent). My favorite problem of
2015's edition was [Day 7: Some Assembly
Required](http://adventofcode.com/2015/day/7).  You can read the full
description on the site. I'll explain it briefly. A circuit looks like this:

``` abap
123 -> x
456 -> y
x AND y -> d
x OR y -> e
x LSHIFT 2 -> f
y RSHIFT 2 -> g
NOT x -> h
NOT y -> i
```

On the left you have one or more inputs combined by a logical gate which is
then wired to the output on the right.

If you run this circuit, it will produce the following state:

```
d: 72
e: 507
f: 492
g: 114
h: 65412
i: 65079
x: 123
y: 456
```

The problem can be subdivided into parsing and processing. When I
solved this problem in 2015 I used good old `string/split` and
regexes. But it can also be solved quite elegantly with
[clojure.spec](https://clojure.org/guides/spec) as we shall see.

I'll walk you through the circuit spec in reverse.


An expression is the left hand side followed by the symbol `->` followed by the right hand side.

``` clojure
(s/def ::expr (s/cat :lhs ::lhs :arrow #{'->} :rhs ::rhs))
```

The right hand side is always a variable name.

``` clojure
(s/def ::rhs ::varname)
```

The left hand side is a little bit more involved. It is either a
simple value (a variable name or concrete value: `x`, `456`, `1337`,
`ac`), a binary expression (`x AND y`) or something which is negated
(`NOT x`).

``` clojure
(s/def ::lhs (s/alt :simple-value ::val
                    :binary-expression
                    ::binary-expression
                    :not ::not))
```

A negated expression is the symbol `NOT` followed by a value.


``` clojure
(s/def ::not (s/cat :not #{'NOT} :operand ::val))
```

A binary expression is a value followed by an operator followed by another value.


``` clojure
(s/def ::binary-expression (s/cat
                            :left-operand ::val
                            :operator ::binary-operator
                            :right-operand ::val))
```

A binary operator is one of the symbols in the set.

``` clojure
(s/def ::binary-operator #{'LSHIFT 'RSHIFT 'AND 'OR})
```

A value is either a variable name or a non-negative integer.


``` clojure
(s/def ::val (s/alt :name ::varname
                    :value nat-int?))
```


Lastly, a variable name is a symbol. I could have specified this in
more detail by restricting the allowed characters and the length of
the symbol, but this was not needed to succesfully parse my input.

``` clojure
(s/def ::varname
  (s/with-gen symbol?
    (fn [] varname-gen)))
```

However, to generate a variable name which looks like my input, for
the sake of playing around with spec, I need to provide my own
generator. Scanning through my input, I discovered that a variable
name's length is either 1 or 2 and only alphabetic characters may be
used.

``` clojure
(def varname-gen
  (gen/fmap (fn [chars]
              (symbol
               (str/lower-case
                (apply str chars))))
            (gen/vector (gen/char-alpha)
                        1
                        2)))
```

Running the generator yields for example:

``` clojure
(gen/sample varname-gen) ;;=> (g dv de pw hi a c j bz y)
```

We can now generate entire expressions:

``` clojure
(gen/sample (s/gen ::expr)) ;;=> ((NOT g -> sw) (NOT 0 -> j) (gl LSHIFT 0 -> q) (NOT 1 -> ly) (NOT 2 -> j) (ug -> o) (p RSHIFT 0 -> p) (NOT oj -> dz) (ih -> m) (NOT 5 -> fc))
```

Looks good.

To read the lines from the input file I cheated a little bit by using
`edn/read-string` which parses raw strings to a vector of symbols and
numbers for me:

``` clojure
(defn get-lines []
  (str/split-lines
   (slurp "input-day7.txt")))

(defn parsed-lines [lines]
  (mapv (fn [l]
          (let [edn (edn/read-string
                     (format "[%s]" l))]
            (s/conform ::expr edn)))
        lines))
```

I could have used `line-seq` or a [text transducer](https://stackoverflow.com/a/47354316/6264)
to save some memory, but as Knuth says, if you optimize everything you will always
be unhappy.

Let's peek at the first conformed expression which corresponds to the
line `bn RSHIFT 2 -> bo `:

``` clojure
(first (parsed-lines (get-lines))) ;;=> {:lhs [:binary-expression {:left-operand [:name bn], :operator RSHIFT, :right-operand [:value 2]}], :arrow ->, :rhs bo}
```

Yay! Now we have to write some code that processes these lines and
calculates the values for each variable. To do this, we are going to
build up a map of symbols to their values:

``` clojure
(def context (atom {}))
```

The right hand side of an expression is always a variable name. So we
`assoc` it to the `context` where the value is a `delay` of the
evaluation of the left hand side.

``` clojure
(defn evaluate-expr! [expr]
  (let [rhs (:rhs expr)
        lhs (:lhs expr)]
    (swap! context assoc rhs
           (delay
            (evaluate* lhs)))))
```

The reason we are using a `delay` for the values of the context map,
is twofold: delaying and caching.  Firstly, not all values that the
variable depends on are already added to the context, so we have to
delay calculation until every expression has been processed. Secondly,
once a value of a variable is known, we do not want to recalculate
it. My circuit file is 339 lines long and without caching this becomes
terribly slow.

The API we need to get the solution for our Advent of Code puzzle is a
function from symbol to integer.

``` clojure
(defn value-by-symbol [sym]
  @(get @context sym))
```

The double `deref` is needed because we're dealing with an `atom` and a `delay`.

The last bit we need to is evaluate the left hand side of an
expression. Here we pattern match on the kind of expression and
evaluate accordingly!


``` clojure
(defn evaluate* [[kind tree-or-val]]
  (case kind
    :value tree-or-val
    :name (value-by-symbol tree-or-val)
    :simple-value (evaluate* tree-or-val)
    :not (bit-not
          (evaluate*
           (:operand tree-or-val)))
    :binary-expression
    (let [l (evaluate* (:left-operand tree-or-val))
          r (evaluate* (:right-operand tree-or-val))
          operator (case (:operator tree-or-val)
                     AND bit-and
                     OR bit-or
                     LSHIFT bit-shift-left
                     RSHIFT bit-shift-right)]
      (operator l r))))
```


Finally, after evaluating all the lines we can ask for the value of a
symbol in the circuit.

``` clojure
(value-by-symbol 'a) ;;=> 46065
```

which happened to be the correct value for my input!

Thanks for reading. The full code is available on
[Github](https://github.com/borkdude/aoc2015_day7). Constructive
feedback and criticisms are welcome.
