(ns proglanguages.core
  (:gen-class)
  (:require [clojure-csv.core :as csv])
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as str])
  (:use [clojure-csv.core]))

(def header
  "digraph g {
  ranksep=.75; size =\"7.5,7.5,7.5\";
  {
    node [shape=plaintext, fontsize=16];\n
  "
   )

(def footer
  "}")


(defn years
  [langlist]
  (str (clojure.string/join " -> "(map second langlist)) ";}")
  )

(defn ranks
  [langlist]
  (clojure.string/join "\n" (list
                              "node [shape=box];"
                              (map
                                #(let [[lang year] %]
                                   (str "{rank = same;" year ";" \" lang \" ";}"))
                                langlist)))
  )

(defn lang-connections
  [[language year & rest]]
  ;;(for [lang rest]
  (map
    #(str language "->" % ";\n") rest))
  ;;(map #(str language "->" % ";\n") rest))

(defn connections
  [langlist]
  ;;(clojure.string/join "\n"
                       (for [lang langlist] (lang-connections lang))
  )

(defn dot-file
  [langs]
  (clojure.string/join "\n"
                       (list header
                             (years langs)
                             (ranks langs)
                             (connections langs)
                             footer))
  )

(defn take-csv
  "Takes file name and reads data"
  [fname]
  (with-open [file (io/reader fname)]
    (csv/parse-csv (slurp file))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (dot-file (take-csv "/tmp/languages.csv"))))
