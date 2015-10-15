(ns cli-tools.core
	(:require [clojure.java.io :as io]
		[clojure.string :as string]
		[clojure.tools.cli :refer [parse-opts]])
	(:gen-class))

(declare get-line-count)
(declare split-files)
(declare find-file-pos)

(def cli-options
  [
  	;; Possible values "split" or "update"
  	["-m" "--mode STRING" "Mode of operation"]
  	;; Path to the input file to be split up
	["-i" "--inputfile FILEPATH" "Input file path"]
	;; Output directory for the split up files
	["-o" "--destdir FILEPATH" "Destination file path"]
	;; Number of files to produce from the input file
	["-n" "--count NUMBER" "Number of files you want to split into"]
   	;; Get the new updates for yesterday
	["-u" "--update"]])

(defn -main [& args]
  (let [ optionsmap (get (parse-opts args cli-options) :options)
  		filepath (get optionsmap :inputfile)
  		linecount (get-line-count filepath)
  		numberoffilestosplitinto (read-string (get optionsmap :count))
  ]
  ;(if)
  (clojure.pprint/pprint optionsmap)))

(comment
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
)

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
   			(if (.contains l "/mbst/") (println (string/join "" ["[IGNORE]" l])) 
   			(with-open [wrtr (clojure.java.io/writer (string/join "" [destdir "file-" fileext ".txt"]) :append true)]
	  		(.write wrtr l)
	  		(.write wrtr "\n")))
		)	    
   		(def currlinenumber (+ 1 currlinenumber))
    )))

(defn find-file-pos [totallines currline]
	(def currpos (int (Math/floor(* (/ currline totallines) 100))))
	currpos
	)
