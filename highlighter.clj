(ns highlighter
  (:require
   [babashka.fs :as fs]
   [clojure.string :as str]
   [pod.borkdude.clj-kondo :as clj-kondo]
   [rewrite-clj.node :refer [sexpr tag]]
   [rewrite-clj.parser :as p]
   [selmer.parser :as selmer]))

(defn log [& xs]
  (binding [*out* *err*]
    (println (str/join " " (map pr-str xs)))))

(defn analysis [code]
  (let [tmp (doto (fs/file (fs/create-temp-dir) "code.clj")
              fs/delete-on-exit)]
    (spit tmp code)
    (-> (clj-kondo/run!
         {:lint [(str tmp)]
          :config {:output {:analysis {:locals true}}}})
        :analysis)))

(defn locals [analysis]
  (->> analysis
       ((juxt :locals :local-usages))
       (apply concat)
       (map (juxt :row :col)) set))

(defn var-defs [analysis]
  (->> analysis
       :var-definitions
       (map (juxt :name-row :name-col)) set))

(defmulti node->html tag)

(defn escape [x]
  (-> (selmer/render "{{x}}" {:x x})
      (str/replace "^" "&#94;")))

(defn span [class contents]
  (format "<span class=\"%s\">%s</span>"
          class contents))

(defmethod node->html :map [node]
  (span "map" (format "{%s}"
                      (str/join (map node->html (:children node))))))

(defmethod node->html :list [node]
  (span "list" (format "(%s)"
                       (str/join (map node->html (:children node))))))

(defmethod node->html :vector [node]
  (span "vector" (format "[%s]"
                      (str/join (map node->html (:children node))))))

(defmethod node->html :set [node]
  (span "set" (format "#{%s}"
                      (str/join (map node->html (:children node))))))

(def ^:dynamic *analysis*)

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
                (cond (contains? (:locals *analysis*) ((juxt :row :col) (meta node)))
                      "local"
                      (contains? (:var-defs *analysis*) ((juxt :row :col) (meta node)))
                      "def"
                      :else
                      "symbol")
                :else (name (tag node))))
        ;; fallback, log missing case
        :else (log (tag node) (keys node) (sexpr node) (type (sexpr node)))))

(defmethod node->html :token [node]
  (span (token-class node)
        (escape (str node))))

(defmethod node->html :newline [node]
  (str/join (repeat (count (:newlines node)) "<br>")))

(defmethod node->html :whitespace [node]
  (:whitespace node))

(defmethod node->html :multi-line [node]
  (span "string"
        (str/join "<br>" (map escape (:lines node)))))

(defmethod node->html :quote [node]
  (str "'" (str/join (map node->html (:children node)))))

(defmethod node->html :uneval [node]
  (span "uneval" (str "#_" (node->html (first (:children node))))))

(defmethod node->html :forms [node]
  (span (name (tag node))
        (str/join "" (map node->html (:children node)))))

(defmethod node->html :reader-macro [node]
  (span (name (tag node))
        (str "#"
             (str/join "" (map node->html (:children node))))))

(defmethod node->html :meta [node]
  (span (name (tag node))
        (str "&#94;"
             (str/join "" (map node->html (:children node))))))

(defmethod node->html :comment [node]
  (span (name (tag node))
        (str/replace node "\n" "<br>")))

(defmethod node->html :regex [node]
  (span (name (tag node))
        (format "#\"%s\"" (escape (:pattern node)))))

(defmethod node->html :deref [node]
  (span (name (tag node))
        (str "@" (str/join (map node->html (:children node))))))

(defmethod node->html :fn [node]
  (span (name (tag node))
        (format "#(%s)" (str/join (map node->html (:children node))))))

(defmethod node->html :comma [node]
  (span (name (tag node))
        (str node)))

(defmethod node->html :default [node]
  (log "Unhandled tag:" (tag node))
  (span (name (tag node))
        (if (:children node)
          (str/join "" (map node->html (:children node)))
          (str node))))

(defn htmlize [code]
  (binding [*analysis*
            (let [ana (analysis code)]
              {:locals (locals ana)
               :var-defs (var-defs ana)})]
    (let [html (-> code p/parse-string-all node->html)]
      (format "<pre><code class=\"clojure hljs\">%s</code></pre>" html))))

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
