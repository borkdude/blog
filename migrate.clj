(ns migrate
  (:require
   [clojure.edn :as edn]
   [babashka.fs :as fs]))

(doseq [post (edn/read-string (format "[%s]" (slurp "posts.edn")))]
  (let [file (fs/file "posts" (:file post))
        contents (slurp file)
        with-meta (str (dissoc post :file) "\n\n" contents)]
    (spit file with-meta)))
