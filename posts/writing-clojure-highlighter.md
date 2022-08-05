Title: Writing a Clojure highlighter from scratch
Date: 2021-11-08
Tags: clojure

In the aftermath of my [previous blog post](better-clojure-highlighting.html)
about using Nextjournal's
[clojure-mode](https://github.com/nextjournal/clojure-mode) for better
highlighting, I tried optimizing the JS output and got a look at the internals
of [CodeMirror 6](https://codemirror.net/6/). I realized that writing a Clojure
highlighter from scratch wasn't that hard if you had the right tools at hand:

- A tool which can break Clojure code into parts, tells you what each part is
  (symbol, keyword, vector, etc.)  and also provides a way to put the parts
  together again, with preservation of whitespace. This tool is already
  available in [babashka](https://babashka.org/) and is a library called
  [rewrite-clj](https://github.com/clj-commons/rewrite-clj).
- A tool that can provide additional semantic information: is a symbol a local
  or a var? The static analysis output of
  [clj-kondo](https://github.com/clj-kondo/clj-kondo) provides that
  information. This part is optional for incrementally better highlighting.

I spent my Sunday afternoon combining these tools which resulted in a 160 line
script called `highlighter.clj` which is now used to do the highlighting of this
blog.

This blog post is a high level walkthrough of the code. Let's begin with the
first step.

### 1. Parse blocks of Clojure code from markdown and apply highlighting.

``` clojure
(defn highlight-clojure [markdown]
  (str/replace markdown #"(?m)```\s*clojure\n([\s\S]+?)\n\s*```"
               (fn [[_ code]]
                 (try (-> (str/trim code)
                          (htmlize)
                          (str/replace "[" "&#91;")
                          (str/replace "]" "&#93;")
                          (str/replace "*" "&#42;")
                          (str/replace "_" "&#95;"))
                      (catch Exception e
                        (log "Could not highlight: " (ex-message e) code)
                        markdown)))))
```

Parsing blocks of Clojure code from a markdown post is done using a basic
regex. Then we pass the Clojure code to the `htmlize` function. After that we
escape some markdown-specific characters, so the markdown compiler won't be
confused by them.. If the highlighting failed for some reason, we log it and
fall back on the unprocessed markdown. During the implementation I found several
snippets of Clojure code with unbalanced parens which I had to fix, since
rewrite-clj doesn't accept it. So all examples from this blog should be
copy-pastable into your Clojure editor without problems from now on.

### 2. Parse and analyze Clojure using clj-kondo and rewrite-clj:
``` clojure
(defn htmlize [code]
  (binding [*analysis*
            (let [ana (analysis code)]
              {:locals (locals ana)
               :var-defs (var-defs ana)})]
    (let [html (-> code p/parse-string-all node->html)]
      (format "<pre><code class=\"clojure hljs\">%s</code></pre>" html))))
```

Clj-kondo provides information about vars, keywords and locals. We will apply
special styling to var definitions and locals and their usages.

### 3. Clj-kondo analysis

``` clojure
(pods/load-pod 'clj-kondo/clj-kondo "2021.10.19")

(require '[pod.borkdude.clj-kondo :as clj-kondo])

(defn analysis [code]
  (let [tmp (doto (fs/file (fs/create-temp-dir) "code.clj")
              fs/delete-on-exit)]
    (spit tmp code)
    (-> (clj-kondo/run!
         {:lint [(str tmp)]
          :config {:output {:analysis {:locals true}}}})
        :analysis)))
```

To call clj-kondo from babashka, we use the binary from the [pod
 registry](https://github.com/babashka/pod-registry) which is automatically
 downloaded via `load-pod` if you provide a fully qualified symbol and
 version. We write the code to a temp file and lint it. We ask for the static
 [analysis](https://github.com/clj-kondo/clj-kondo/blob/master/analysis/README.md)
 data. Locals are not included by default, so we set `:locals` to `true`. Later
 on we want to detect if a symbol is a local or a var. We do this by making a
 set of locations from the analysis data for each group:

``` clojure
(defn locals [analysis]
  (->> analysis
       ((juxt :locals :local-usages))
       (apply concat)
       (map (juxt :row :col)) set))

(defn var-defs [analysis]
  (->> analysis
       :var-definitions
       (map (juxt :name-row :name-col)) set))
```

### 4. Rewrite-clj nodes

Next, we parse the code to rewrite-clj nodes. Each node has a tag for which we
write a multi-method to dispatch on:

``` clojure
(defmulti node->html tag)
```

For each kind of node we will emit a `<span>` element with an associated `class`.
For instance, `:foo` will become `<span class="keyword">:foo</span>` and so on.

A small helper function:

``` clojure
(defn span [class contents]
  (format "<span class=\"%s\">%s</span>"
          class contents))
```

Here is the implementation for a map node:

``` clojure
(defmethod node->html :map [node]
  (span "map" (format "{%s}"
                      (str/join (map node->html (:children node))))))
```

A map node has `:children` so we just call `node->html` for each child and join
the strings together.

I wrote a `:default` implementation that logs a warning for nodes that I hadn't implemented yet:

``` clojure
(defmethod node->html :default [node]
  (log "Unhandled tag:" (tag node))
  (span (name (tag node))
        (if (:children node)
          (str/join "" (map node->html (:children node)))
          (str node))))
```

and added implementations for all of the nodes that occurred in Clojure snippets
in all the posts of this blog so far, by working through the list of unhandled tags.

Rewrite-clj doesn't give different tags for symbols, strings, numbers and so on:
it groups them under the `:token` tag. So there is some extra work needed to get
different highlighting for different types of tokens. I wrote a function that
returns a CSS class by looking at the contents of the node or at the type of
value of the node. For a symbol node, I want different highlighting for vars and
locals. This is where I check in the clj-kondo analysis if the symbol on that
location is a local or var and else fall back on the general symbol CSS class.

``` clojure
(defn token-class [node]
  (cond (:k node) "keyword"
        (:lines node) "string"
        (contains? node :value)
        (let [v (:value node)]
          (cond (number? v) "number"
                (string? v) "string"
                (boolean? v) "boolean"
                (nil? v) "nil"
                (symbol? v)
                (cond (contains? (:locals *analysis*)
                                 ((juxt :row :col) (meta node)))
                      "local"
                      (contains? (:var-defs *analysis*)
                                 ((juxt :row :col) (meta node)))
                      "def"
                      :else
                      "symbol")
                :else (name (tag node))))
        ;; fallback, log missing case
        :else (log (tag node) (keys node) (sexpr node) (type (sexpr node)))))

(defmethod node->html :token [node]
  (span (token-class node)
        (escape (str node))))
```

### 5. Styling

Finally I wrote some styling:

``` css
.def { color: #00f; }
.symbol { color: #708; }
.local { color: cadetblue; }
.string { color: #a11; }
.number { color: blue; }
.keyword { color: #219; }
.uneval { filter: opacity(0.5); }
```

For `:uneval` nodes, which is rewrite-clj's name for expressions that are ignored using the reader underscore dispatch macro: `#_(+ 1 2 3)`, I set opacity to `0.5`. Can you see the difference?

``` clojure
(+ 1 2 3)
#_(+ 1 2 3)
```

That's it really. A Sunday afternoon well spent. The code for the highlighter is
[here](https://github.com/borkdude/blog/blob/main/highlighter.clj). In the
future I might pull out this code into a library. The renderer could support
ANSI escape code sequences for the terminal as well. Let me know what you think.
