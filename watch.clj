(require '[pod.babashka.filewatcher :as fw])

(fw/watch "posts"
          (fn [_]
            (println "Re-rendering")
            (load-file "render.clj")))

(fw/watch "templates"
          (fn [_]
            (println "Re-rendering")
            (load-file "render.clj")))

@(promise)
