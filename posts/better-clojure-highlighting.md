{:categories #{"clojure"}, :date "2021-11-05", :discuss "https://github.com/borkdude/blog/discussions/4"}

# Better Clojure highlighting with nextjournal/clojure-mode

Almost two weeks ago I [wrote](migrating-octopress-to-babashka.html) about how I
replaced my Octopress blog with about 200 lines of
[babashka](https://github.com/babashka/babashka). I also wrote that I used
[highlight.js](https://highlightjs.org/) for highlighting. This blog is mostly
about Clojure/Script, so improvements in highlighting of Clojure code are
welcome. Highlight.js does a good job, but it's not as good as it can
be. E.g. Nextjournal's
[clojure-mode](https://nextjournal.github.io/clojure-mode/), which is a
CodeMirror 6 integration for Clojure, covers a lot more detail. So I tried to
strip the demo of clojure-mode to the bare mininum for just for highlighting. I
ended up with about 320kb (117kb gzipped) of optimized JS based on a CLJS
implementation. Trying to squeeze the last bit out by rewriting it to JS and
bundling it with [Rollup.js](https://rollupjs.org/guide/en/) like the
[CodeMirror docs](https://codemirror.net/6/examples/bundle/) suggest yielded
600kb at first. But it turned out I needed to enable the terser [output
plugin](https://rollupjs.org/guide/en/#using-output-plugins) and this got the
bundle size down to 200kb (79kb gzipped).

In the future it might be possible to get even smaller builds when CodeMirror 6
gets better support for 'just highlighting'. There is a discussion about it
[here](https://discuss.codemirror.net/t/only-syntax-highlighting/2635/5).

To automate building the highlighter code when I re-render this blog, I again
used [babashka tasks](https://book.babashka.org/#tasks):

``` clojure
{:paths ["."]
 :tasks
 {:requires ([babashka.fs :as fs]
             [tasks-helper :as th])
  ...
  build-highlighter {:doc "Build Clojure highlighter JS"
                     :task
                     (when
                         (seq (fs/modified-since "public/clojure_highlighter.js"
                               ["src"]))
                       (shell "npx rollup -c rollup.config.js"))}
  render {:doc "Render blog"
          :depends [build-highlighter]
          :task (load-file "render.clj")}
  ...}}
```

If the `public/clojure_highlighter.js` file is already there and JavaSceipt
sources hasn't changed, the `build-higlighter` step won't take any significant
time due to the use of `fs/modified-since` which I blogged about
[here](speeding-up-builds-fs-modified-since.html).

``` shell
$ bb clean
$ time bb render

src/clojure_highlighter/main.js → public/clojure_highlighter.js...
created public/clojure_highlighter.js in 2.7s
bb render   6.26s  user 0.84s system 155% cpu 4.557 total

$ time bb render
bb render   0.09s  user 0.05s system 81% cpu 0.174 total
```

The above snippet is still highlighted with Highlight.js, but
`<code class="clojure">...</code>` is highlighted with clojure-mode.

To show off the highlighting, here is the CLJS code for the highlighter, which I
ported to JS later on.

``` clojure
(ns clojure-highlighter.main
  (:require ["@codemirror/highlight" :as highlight :refer [tags]]
            ["@codemirror/language" :as language]
            ["@codemirror/state" :refer [EditorState]]
            ["@codemirror/view" :as view :refer [EditorView]]
            ["lezer-clojure" :as lezer-clj]
            [clojure.string :as str]
            [goog.object]))

(def theme
  (.theme EditorView
          #js {".cm-content" #js {:white-space "pre-wrap"
                                  :padding "10px 0"}
               "&.cm-focused" #js {:outline "none"}
               ".cm-line" #js {:padding "0 9px"
                               :line-height "1.6"
                               :font-size "16px"
                               :font-family "var(--code-font)"}
               ".cm-matchingBracket"
               #js {:border-bottom "1px solid var(--teal-color)"
                    :color "inherit"}
               ".cm-gutters" #js {:background "transparent"
                                  :border "none"}
               ".cm-gutterElement" #js {:margin-left "5px"}
               ;; only show cursor when focused
               ".cm-cursor" #js {:visibility "hidden"}
               "&.cm-focused .cm-cursor" #js {:visibility "visible"}}))

(def style-tags
  (doto #js {:DefLike (.-keyword tags)
             "Operator/Symbol" (.-keyword tags)
             "VarName/Symbol" (.definition tags (.-variableName tags))
             :Boolean (.-atom tags)
             "DocString/..." (.-emphasis tags)
             :Discard! (.-comment tags)
             :Number (.-number tags)
             :StringContent (.-string tags)
             :Keyword (.-atom tags)
             :Nil (.-null tags)
             :LineComment (.-lineComment tags)
             :RegExp (.-regexp tags)}
    (goog.object/set "\"\\\"\"" (.-string tags))))

(def parser lezer-clj/parser)

(def the-parser
  (.configure parser
              #js {:props #js [(highlight/styleTags style-tags)]}))

(def syntax
  (.define language/LezerLanguage
           #js {:parser the-parser}
           #js {:languageData
                #js {:commentTokens {:line ";;"}}}))

(defonce extensions #js[theme
                        (.of (.-editable EditorView) false)
                        highlight/defaultHighlightStyle
                        #js[syntax]])

(defn ^:dev/after-load render []
  (let [elts (js/document.querySelectorAll "code.clojure")]
    (.forEach elts
              (fn [elt]
                (new EditorView
                     #js {:state
                          (.create EditorState
                                   #js{:doc (str/trim (.-innerText elt))
                                       :extensions #js [extensions]})
                          :parent elt})
                (.remove (.-firstChild elt))))))
```

You can find the code for this blog on
[Github](https://github.com/borkdude/blog), including the above code.

Edits:

- 2021-11-06: Include Rollup.js `terser` output plugin in blog post.
