(ns cli-tools.core
	(:require [clojure.java.io :as io])
	(:gen-class))

(declare get-line-count)

(defn -main
  "I don't do a whole lot."
  [filepath]
  ;(println "Hello, World!")
  (println filepath)
  (def linecount (get-line-count filepath)) 
  (println linecount)
  )

(defn get-line-count
	[filepath]
	(def linecount 0)
	(with-open [r (clojure.java.io/reader filepath)]
   	(doseq [l (line-seq r)]
    (def linecount (+ 1 linecount))))
	linecount
	)
