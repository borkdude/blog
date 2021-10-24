This post highlights of one of the core ideas posted in [this
blogpost](https://www.grammarly.com/blog/engineering/building-etl-pipelines-with-clojure-and-transducers/). If
you've already read it and you're intimately familiar with transducers, this
post probably won't have anything new for you. I've posted this to
[Stackoverflow](https://stackoverflow.com/a/47354316/6264) before and saving
this to my blog for archival purposes.

In the pre-transducer era, reading text files was often done like this:


``` clojure
(require '[clojure.java.io :as io])
(with-open [rdr (io/reader "/tmp/work.txt")]
  (->> (line-seq rdr)
       (mapcat #(str/split % #";"))
       (map count)
       (doall))
```

Given input `work.txt`:

    I;am;a;string
    Next;line;please

this would return `(1 2 1 6 4 4 6)`. One caveat with this approach is
you have to realize the result inside the `with-open` macro, else the
file would already be closed.

What if we want to use transducers
instead of lazy collection transformations? The ingredient you need is
something that allows you to treat the lines as a reducible collection
and which closes the reader when you're done reducing:

``` clojure
    (defn lines-reducible
      [^java.io.BufferedReader rdr]
      (reify clojure.lang.IReduceInit
        (reduce [this f init]
          (try
            (loop [state init]
              (if (reduced? state)
                @state
                (if-let [line (.readLine rdr)]
                  (recur (f state line))
                  state)))
            (finally
              (.close rdr))))))
```

#### Count the length of each 'split'

``` clojure
    (require '[clojure.string :as str])
    (require '[clojure.java.io :as io])

    (into []
          (comp
           (mapcat #(str/split % #";"))
           (map count))
          (lines-reducible (io/reader "/tmp/work.txt")))
    ;;=> [1 2 1 6 4 4 6]
```


#### Sum the length of all 'splits'

``` clojure
    (transduce
     (comp
      (mapcat #(str/split % #";"))
      (map count))
     +
     (lines-reducible (io/reader "/tmp/work.txt")))
    ;;=> 24
```

#### Sum the length of all words until we find a word that is longer than 5

``` clojure
    (transduce
     (comp
      (mapcat #(str/split % #";"))
      (map count))
     (fn
       ([] 0)
       ([sum] sum)
       ([sum l]
        (if (> l 5)
          (reduced sum)
          (+ sum l))))
     (lines-reducible (io/reader "/tmp/work.txt")))
```


or with `take-while`:

``` clojure
    (transduce
     (comp
      (mapcat #(str/split % #";"))
      (map count)
      (take-while #(> 5 %)))
     +
     (lines-reducible (io/reader "/tmp/work.txt")))
```

Read https://www.grammarly.com/blog/engineering/building-etl-pipelines-with-clojure-and-transducers/ for more details.
