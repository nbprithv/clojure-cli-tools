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
  	["-m" "--mode" "Mode of operation"]
  	;; Path to the input file to be split up
	["-i" "--inputfile" "Input file path"]
	;; Output directory for the split up files
	["-o" "--destdir" "Destination file path"]
	;; Number of files to produce from the input file
	["-n" "--count" "Number of files you want to split into"]
   	;; Get the new updates for yesterday
	["-u" "--update" "Get the updates for yesterday"]
	["-h" "--help"]])

(defn -main [& args]
	(let [ optionsmap (get (parse-opts args cli-options) :options)
	  		
	  		;numberoffilestosplitinto (read-string (get optionsmap :count))
	  ]
		(if (nil? (get optionsmap :help))			
  			(if (nil? (get optionsmap :mode))
	  			(println "Option -m missing") 
			  	(if (= 0 (compare "split" (get optionsmap :mode)))
			  		(if (nil? (get optionsmap :inputfile))
			  			(println "Input file path is missing")
			  			(if (nil? (get optionsmap :destdir))
			  				(println "Output dir is missing")
			  				(do (if (nil? (get optionsmap :count))
			  						(do (def numberoffilestosplitinto "2")
			  						(println "Defaulting number of files to 2"))
			  						(def numberoffilestosplitinto (get optionsmap :count))
		  						)
								(let [ filepath (get optionsmap :inputfile)
									destdir (get optionsmap :destdir)
									linecount (get-line-count filepath)
									numberoffilestosplitinto (read-string numberoffilestosplitinto)
									linesperfile (Math/floor(/ linecount numberoffilestosplitinto))
									]
									(println numberoffilestosplitinto)
									(split-files linecount filepath linesperfile destdir)
									)
		  					)
			  			)
			  		)
		  			(if (= 0 (compare "update" (get optionsmap :mode)))
		  				()
		  				(println "I dont know what mode you want. split or update")
		  			) 
			  	)
			)
			(do (println "triggered help mode")
				(println (get (parse-opts args cli-options) :summary)))
		)
	;(clojure.pprint/pprint (parse-opts args cli-options))
	)
  )

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
