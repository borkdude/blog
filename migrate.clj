(ns migrate
  (:require
   [clojure.edn :as edn]
   [babashka.fs :as fs]))

#_(doseq [post (edn/read-string (format "[%s]" (slurp "posts.edn")))]
  (let [file (fs/file "posts" (:file post))
        contents (slurp file)
        with-meta (str (dissoc post :file :title) "\n\n" "# " (:title post) "\n\n" contents)]
    (spit file with-meta)))
