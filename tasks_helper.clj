(ns tasks-helper
  (:require
   [babashka.fs :as fs]
   [clojure.string :as str]
   [selmer.parser :as selmer]))

(defn parse-opts
  ([opts] (parse-opts opts nil))
  ([opts opts-def]
   (let [[cmds opts] (split-with #(not (str/starts-with? % ":")) opts)]
     (reduce
      (fn [opts [arg-name arg-val]]
        (let [k (keyword (subs arg-name 1))
              od (k opts-def)
              v ((or (:parse-fn od) identity) arg-val)]
          (if-let [c (:collect-fn od)]
            (update opts k c v)
            (assoc opts k v))))
      {:cmds cmds}
      (partition 2 opts)))))

(def opts (parse-opts *command-line-args*))

(def post-template
  (str/triml "
{:title {{title | safe }}
 :file {{file | safe }}
 :categories {{categories}}
 :date {{date | safe }}}\n"))

(defn now []
  (pr-str
   (.format (java.time.LocalDate/now)
            (java.time.format.DateTimeFormatter/ofPattern "yyyy-MM-dd"))))

(defn new []
  (let [{:keys [file title]} opts]
    (assert file "Must give title")
    (assert title "Must give filename")
    (let [post-file (fs/file "posts" file)]
      (when-not (fs/exists? post-file)
        (spit (fs/file "posts" file) "TODO: write blog post")
        (spit (fs/file "posts.edn")
              (selmer/render post-template
                             {:title (pr-str title)
                              :file (pr-str file)
                              :date (now)
                              :categories #{"clojure"}})
              :append true)))))
