(require '[pod.babashka.filewatcher :as fw])

(watch "posts"
          (fn [_]
            (println "Re-rendering")
            (load-file "render.clj")))

(watch "templates"
          (fn [_]
            (println "Re-rendering")
            (load-file "render.clj")))

@(promise)
