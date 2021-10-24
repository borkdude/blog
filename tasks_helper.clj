(ns tasks-helper)

(require '[clojure.string :as str])

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
