(ns render
  (:require
   [babashka.fs :as fs]
   [babashka.pods :as pods]
   [clojure.data.xml :as xml]
   [clojure.edn :as edn]
   [clojure.string :as str]
   [selmer.parser :as selmer]))

(pods/load-pod 'retrogradeorbit/bootleg "0.1.9")

(require '[pod.retrogradeorbit.bootleg.markdown :as md])
(require '[pod.retrogradeorbit.bootleg.utils :as utils])

(def posts (sort-by :date (comp - compare)
                    (edn/read-string (format "[%s]"
                                             (slurp "posts.edn")))))

(def out-dir "public")

(def post-html
  (slurp "templates/post.html"))

;;;; Sync images and CSS

(def asset-dir (fs/create-dirs (fs/file out-dir "assets")))

(fs/copy-tree "assets" asset-dir {:replace-existing true})

(spit (fs/file out-dir "style.css")
      (slurp "templates/style.css"))

;;;; Generate posts from markdown

(def post-template
  "<i>Published: {{date}}</i> - <a href=\"archive.html\">archive</a>
<h1>{{title}}</h1>
{{body | safe }}
<i>Published: {{date}}</i> - <a href=\"archive.html\">archive</a>
")

(defn markdown->html [file]
  (let [markdown (slurp file)
        markdown (str/replace markdown #"http[A-Za-z0-9/:.=#?_-]+([\s])"
                              (fn [[match ws]]
                                (format "[%s](%s)%s"
                                        (str/trim match)
                                        (str/trim match)
                                        ws)))
        markdown (str/replace markdown #"\[(.*)\n(.*)\]"
                              (fn [[match _]]
                                (str/replace match "\n" " ")))
        hiccup (md/markdown markdown :data)
        ;;hiccup (cons [:h1 title] hiccup)
        html (-> hiccup
                 (utils/convert-to :html))]
    html))

;; re-used when generating atom
(def bodies (atom {}))

(doseq [{:keys [file title date legacy]} posts]
  (let [body (markdown->html (str (fs/file "posts" file)))
        _ (swap! bodies assoc file body)
        body (selmer/render post-template {:body body
                                           :title title
                                           :date date})
        html (selmer/render post-html
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

(def archive-html (slurp "templates/archive.html"))

(defn post-links []
  [:ul
   (for [{:keys [file title date]} posts]
     [:li [:span
           [:a {:href (str/replace file ".md" ".html")}
            title]
           " - "
           date]])])

(spit (fs/file out-dir "archive.html")
      (selmer/render archive-html
                     {:body (utils/convert-to (post-links) :html)}))

;;;; Generate index page with last 3 posts

(def index-html (slurp "templates/index.html"))

(defn index []
  (for [{:keys [file title date]} (take 3 posts)]
    [:div
     [:h1 [:a {:href (str/replace file ".md" ".html")}
           title]]
     [:i "Published " date]
     (get @bodies file)]))

(spit (fs/file out-dir "index.html")
      (selmer/render index-html
                     {:body (utils/convert-to (index) :html)}))

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
        now (java.time.ZonedDateTime/of (.atStartOfDay local-date) java.time.ZoneOffset/UTC)]
    (.format now fmt)))

(defn atom-feed
  ;; validate at https://validator.w3.org/feed/check.cgi
  [posts]
  (-> (xml/sexp-as-element
       [::atom/feed
        {:xmlns "http://www.w3.org/2005/Atom"}
        [::atom/title "REPL adventures"]
        [::atom/link {:href "http://blog.michielborkent.nl/atom.xml" :rel "self"}]
        [::atom/link {:href "http://blog.michielborkent.nl"}]
        [::atom/updated (rfc-3339-now)]
        [::atom/id "http://blog.michielborkent.nl/"]
        [::atom/author
         [::atom/name "Michiel Borkent"]]
        (for [{:keys [title date file]} posts]
          [::atom/entry
           [::atom/id (str/replace file ".md" "")]
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
