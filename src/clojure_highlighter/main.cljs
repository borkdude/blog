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
