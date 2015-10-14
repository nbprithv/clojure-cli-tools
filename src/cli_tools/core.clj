(ns cli-tools.core
	(:require [clojure.java.io :as io][clojure.string :as string])
	(:gen-class))

(declare get-line-count)
(declare split-files)
(declare find-file-pos)

(defn -main [filepath numberoffilestosplitinto destdir]
	(println filepath)
	(println numberoffilestosplitinto)
	(let [linecount (get-line-count filepath)
		numberoffilestosplitinto (read-string numberoffilestosplitinto)
		linesperfile (Math/floor(/ linecount numberoffilestosplitinto))
		]
		(println linecount)
		(println linesperfile)
		(split-files linecount filepath linesperfile destdir)))

(defn get-line-count [filepath]
	(def linecount 0)
	(with-open [r (io/reader filepath)]
   	(doseq [l (line-seq r)]
    (def linecount (+ 1 linecount))))
	linecount)

(defn split-files [linecount filepath linesperfile destdir]
	;(def destfilepath "/tmp/file-1.txt")
	(def currlinenumber 1)
	(println linesperfile)
	(with-open [r (io/reader filepath)]
   	(doseq [l (line-seq r)]
   		(let [currpos (find-file-pos linecount currlinenumber)
			;numberoffilestosplitinto (Math/floor(/ 100 (read-string numberoffilestosplitinto)))
   			fileext (Math/floor(/ currlinenumber linesperfile))
   			]
   			(println (string/join "    " [fileext currlinenumber linesperfile (string/join "" [destdir "file-" fileext ".txt"])]))
   			(with-open [wrtr (clojure.java.io/writer (string/join "" [destdir "file-" fileext ".txt"]) :append true)]
	  		(.write wrtr l)
	  		(.write wrtr "\n"))
		)	    
   		(def currlinenumber (+ 1 currlinenumber))
    )))

(defn find-file-pos [totallines currline]
	(def currpos (int (Math/floor(* (/ currline totallines) 100))))
	currpos
	)
