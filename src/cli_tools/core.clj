(ns cli-tools.core
	(:require [clojure.java.io :as io][clojure.string :as string])
	(:gen-class))

(declare get-line-count)
(declare split-files)
(declare find-file-pos)
(declare parse-int)

(defn -main [filepath numberoffilestosplitinto]
  (println filepath)
  (println numberoffilestosplitinto)
  (def numberoffilestosplitinto (parse-int numberoffilestosplitinto))
  (println (read-string numberoffilestosplitinto))
  (def linecount (get-line-count filepath))
  (split-files linecount filepath numberoffilestosplitinto)
  (println linecount)
  )

(defn get-line-count [filepath]
	(def linecount 0)
	(with-open [r (io/reader filepath)]
   	(doseq [l (line-seq r)]
    (def linecount (+ 1 linecount))))
	linecount
	)

(defn split-files [linecount filepath numberoffilestosplitinto]
	;(def destfilepath "/tmp/file-1.txt")
	;(println (string/join " " ["a" numberoffilestosplitinto]))
	(def currlinenumber 1)
	(with-open [r (io/reader filepath)]
   	(doseq [l (line-seq r)]
   		(let [currpos (find-file-pos linecount currlinenumber)
			numberoffilestosplitinto (read-string numberoffilestosplitinto)
   			fileext (mod currpos numberoffilestosplitinto)]
   			(println (string/join " " [fileext "   " currlinenumber])))
	    ;(with-open [wrtr (clojure.java.io/writer "/tmp/test1.txt" :append true)]
	  	;(.write wrtr l)
	  	;(.write wrtr "\n"))
   		(def currlinenumber (+ 1 currlinenumber))
    ))
	)

(defn find-file-pos [totallines currline]
	(def currpos (int (Math/floor(* (/ currline totallines) 100))))
	currpos
	)

(defn parse-int [s]
   (Integer. (re-find  #"\d+" s )))
