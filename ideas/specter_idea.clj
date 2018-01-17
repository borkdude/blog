(ns specter-idea
  (:require [com.rpl.specter :as specter :refer [setval ALL
                                                 NONE multi-path
                                                 selected?
                                                 pred
                                                 ]]))

(def data ;; only x and y are allowed
  [{:name "x" :rels ["x" "y"]} ;=> fine, keep as is
   {:name "y" :rels ["x" "y" "z"]} ;=> keep only allowed rels: {:name "y" :rels ["x" "y"]}
   {:name "y" :rels ["z"]} ;=> no allowed rels, remove map completely
   {:name "z" :rels ["x" "y"]}] ;=> disallowed name, remove completely
  )

(def disallowed? (complement #{"x" "y"}))

;; Vanilla Clojure

(keep
 (fn [m]
   (when-not (disallowed? (:name m))
     (when-let [new-rels (seq (remove disallowed? (:rels m)))]
       (assoc m :rels new-rels))))
 data)
;;=> ({:name "x", :rels ("x" "y")} {:name "y", :rels ("x" "y")})

;; Note that it doesn't maintain 'types', vector is converted to seq

;; Util

(defn SOME? [x]
  (not (identical? NONE x)))

;; Specter 

(setval
 [ALL
  (multi-path
   (selected? :name disallowed?)
   [SOME? :rels ALL disallowed?]
   (selected? SOME? :rels empty?))]
 NONE
 data)
;;=> [{:name "x", :rels ["x" "y"]} {:name "y", :rels ["x" "y"]}]

;; Specter solution maintains types, nice if you need it

;; Vanilla Clojure which preserves types

(vec
 (keep
  (fn [m]
    (if-not (disallowed? (:name m))
      (if-let [new-rels (seq (remove disallowed? (:rels m)))]
        (assoc m :rels (vec new-rels)))))
	data))

;; Vanilla Clojure transducer version, also preserves types

(into []
  (comp
    (remove #(-> % :name disallowed?))
    (map (fn [m]
           (println "dude!" m)
           (update-in m [:rels] #(into [] (remove disallowed?) %))))
    (remove #(-> % :rels empty?)))									
  data)

