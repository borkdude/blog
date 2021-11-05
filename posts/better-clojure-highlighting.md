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
implementation. Trying to squeeze the last bit out by rewriting it to JS didn't
pan out the way I hoped:

<blockquote class="twitter-tweet"><p lang="en" dir="ltr">Trying to optimize a JS bundle by rewriting something from CLJS to JS and bundling it with Rollup which supposedly has tree-shaking.<br><br>CLJS + Google Closure advanced bundle: 319kb<br>Pure JS + Rollup tree-shaking: 600kb<br><br>🤔 <a href="https://t.co/6VY8SGM5AW">pic.twitter.com/6VY8SGM5AW</a></p>&mdash; (λ. borkdude) (@borkdude) <a href="https://twitter.com/borkdude/status/1456574352888115200?ref_src=twsrc%5Etfw">November 5, 2021</a></blockquote> <script async src="https://platform.twitter.com/widgets.js" charset="utf-8"></script>

So I'll stick with the CLJS approach for now. I tried write raw JS interop as
much as possible to not pull in any CLJS functions that might increase the size.

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
                         (seq (fs/modified-since "templates/clojure_highlighter.js"
                               ["src"]))
     (shell "npx shadow-cljs release highlighter"))}
  render {:doc "Render blog"
          :depends [build-highlighter]
          :task (load-file "render.clj")}
  ...}}
```

If the `template/clojure_highlighter.js` file is alreasdy there and the CLJS
hasn't changed, the `build-higlighter` step won't take any significant time due
to the use of `fs/modified-since` which I blogged about
[here](speeding-up-builds-fs-modified-since.html).

``` shell
$ time bb render
shadow-cljs - starting via "clojure"
[:highlighter] Compiling ...
[:highlighter] Build completed. (56 files, 0 compiled, 0 warnings, 7,35s)
bb render   62.02s  user 2.47s system 328% cpu 19.632 total

$ time bb render
bb render   0.09s  user 0.05s system 81% cpu 0.174 total
```

To show off the highlighting, here is the CLJS code:

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
