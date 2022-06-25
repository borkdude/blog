(ns render
  (:require
   [babashka.fs :as fs]
   [clojure.data.xml :as xml]
   [clojure.edn :as edn]
   [clojure.string :as str]
   [hiccup2.core :as hiccup]
   #_[highlighter :as h]
   [markdown.core :as md]
   [selmer.parser :as selmer]))

(def blog-title "REPL adventures")

(def posts (sort-by :date (comp - compare)
                    (edn/read-string (format "[%s]"
                                             (slurp "posts.edn")))))

(def out-dir "public")

(def base-html
  (slurp "templates/base.html"))

;;;; Sync images and CSS

(def asset-dir (fs/create-dirs (fs/file out-dir "assets")))

(fs/copy-tree "assets" asset-dir {:replace-existing true})

(spit (fs/file out-dir "style.css")
      (slurp "templates/style.css"))

;;;; Generate posts from markdown

(def post-template
  "<h1>{{title}}</h1>
{{body | safe }}
<p>Discuss this post <a href=\"{{discuss}}\">here</a>.</p>
<p><i>Published: {{date}}</i></p>
")

(defn markdown->html [file]
  (let [_ (println "Processing markdown for file:" (str file))
        markdown (slurp file)
        ;; markdown (h/highlight-clojure markdown)
        ;; make links without markup clickable
        #_#_markdown (str/replace markdown #"https?//[A-Za-z0-9/:.=#?_-]+([\s])"
                              (fn [[match ws]]
                                (format "[%s](%s)%s"
                                        (str/trim match)
                                        (str/trim match)
                                        ws)))
        markdown (str/replace markdown #"--" (fn [_]
                                               "$$NDASH$$"))
        ;; allow links with markup over multiple lines
        markdown (str/replace markdown #"\[[^\]]+\n"
                              (fn [match]
                                (str/replace match "\n" "$$RET$$")))
        html (md/md-to-html-string markdown)
        ;; see issue https://github.com/yogthos/markdown-clj/issues/146
        html (str/replace html "$$NDASH$$" "--")
        html (str/replace html "$$RET$$" "\n")]
    html))

;; re-used when generating atom.xml
(def bodies (atom {}))

(defn html-file [file]
  (str/replace file ".md" ".html"))

(fs/create-dirs (fs/file ".work"))

(def discuss-fallback "https://github.com/borkdude/blog/discussions/categories/posts")

(doseq [{:keys [file title date legacy discuss]
         :or {discuss discuss-fallback}}
        posts]
  (let [cache-file (fs/file ".work" (html-file file))
        markdown-file (fs/file "posts" file)
        stale? (seq (fs/modified-since cache-file
                                       [markdown-file
                                        "posts.edn"
                                        "templates/base.html"
                                        "render.clj"
                                        "highlighter.clj"]))
        body (if stale?
               (let [body (markdown->html markdown-file)]
                 (spit cache-file body)
                 body)
               (slurp cache-file))
        _ (swap! bodies assoc file body)
        body (selmer/render post-template {:body body
                                           :title title
                                           :date date
                                           :discuss discuss})
        html (selmer/render base-html
                            {:title title
                             :body body})
        html-file (str/replace file ".md" ".html")]
    (spit (fs/file out-dir html-file) html)
    (let [legacy-dir (fs/file out-dir (str/replace date "-" "/")
                              (str/replace file ".md" "")
                              )]
      (when legacy
        (fs/create-dirs legacy-dir)
        (let [redirect-html (selmer/render"
<html><head>
<meta http-equiv=\"refresh\" content=\"0; URL=/{{new_url}}\" />
</head></html>"
                                          {:new_url html-file})]
          (spit (fs/file (fs/file legacy-dir "index.html")) redirect-html))))))

;;;; Generate archive page

(defn post-links []
  [:div {:style "width: 600px;"}
   [:h1 "Archive"]
   [:ul.index
    (for [{:keys [file title date preview]} posts
          :when (not preview)]
      [:li [:span
            [:a {:href (str/replace file ".md" ".html")}
             title]
            " - "
            date]])]])

(spit (fs/file out-dir "archive.html")
      (selmer/render base-html
                     {:skip-archive true
                      :title (str blog-title " - Archive")
                      :body (hiccup/html (post-links))}))

;;;; Generate index page with last 3 posts

(defn index []
  (for [{:keys [file title date preview discuss]
         :or {discuss discuss-fallback}} (take 3 posts)
        :when (not preview)]
    [:div
     [:h1 [:a {:href (str/replace file ".md" ".html")}
           title]]
     (get @bodies file)
     [:p "Discuss this post " [:a {:href discuss} "here"] "."]
     [:p [:i "Published: " date]]]))

(spit (fs/file out-dir "index.html")
      (selmer/render base-html
                     {:title blog-title
                      :body (hiccup/html {:escape-strings? false} (index))}))

;;;; Generate atom feeds

(xml/alias-uri 'atom "http://www.w3.org/2005/Atom")
(import java.time.format.DateTimeFormatter)

(defn rfc-3339-now []
  (let [fmt (DateTimeFormatter/ofPattern "yyyy-MM-dd'T'HH:mm:ssxxx")
        now (java.time.ZonedDateTime/now java.time.ZoneOffset/UTC)]
    (.format now fmt)))

(defn rfc-3339 [yyyy-MM-dd]
  (let [in-fmt (DateTimeFormatter/ofPattern "yyyy-MM-dd")
        local-date (java.time.LocalDate/parse yyyy-MM-dd in-fmt)
        fmt (DateTimeFormatter/ofPattern "yyyy-MM-dd'T'HH:mm:ssxxx")
        now (java.time.ZonedDateTime/of (.atTime local-date 23 59 59) java.time.ZoneOffset/UTC)]
    (.format now fmt)))

(def blog-root "https://blog.michielborkent.nl/")

(defn atom-feed
  ;; validate at https://validator.w3.org/feed/check.cgi
  [posts]
  (-> (xml/sexp-as-element
       [::atom/feed
        {:xmlns "http://www.w3.org/2005/Atom"}
        [::atom/title blog-title]
        [::atom/link {:href (str blog-root "atom.xml") :rel "self"}]
        [::atom/link {:href blog-root}]
        [::atom/updated (rfc-3339-now)]
        [::atom/id blog-root]
        [::atom/author
         [::atom/name "Michiel Borkent"]]
        (for [{:keys [title date file preview]} posts
              :when (not preview)
              :let [html (str/replace file ".md" ".html")
                    link (str blog-root html)]]
          [::atom/entry
           [::atom/id link]
           [::atom/link {:href link}]
           [::atom/title title]
           [::atom/updated (rfc-3339 date)]
           [::atom/content {:type "html"}
            [:-cdata (get @bodies file)]]])])
      xml/indent-str))

(spit (fs/file out-dir "atom.xml") (atom-feed posts))
(spit (fs/file out-dir "planetclojure.xml")
      (atom-feed (filter
                  (fn [post]
                    (some (:categories post) ["clojure" "clojurescript"]))
                  posts)))

;; for JVM Clojure:
(defn -main [& _args]
  (System/exit 0))
