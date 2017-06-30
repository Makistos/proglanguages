(ns proglanguages.core
  (:gen-class)
  (:require [clojure-csv.core :as csv])
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as str])
  (:use [clojure-csv.core]))

(def header
  "digraph g {
  ranksep=.75;
  {
    node [shape=plaintext, fontsize=16];\n
  "
   )

(def footer
  "}")


(defn years
  [langlist]
  (str (clojure.string/join " -> "(apply sorted-set (map second langlist))) ";}")
  )

(defn ranks
  [langlist]
  (clojure.string/join "\n" (list
                              "node [shape=box];"
                              (apply str (map
                                #(let [[lang year] %]
                                   (str "{rank = same;" year ";" \" lang \" ";}"))
                                langlist))))
  )

(defn lang-connections
  [[language year & rest]]
  ;;(for [lang rest]
  (apply str (map
    #(str \" % \" "->" \" language "\";\n") rest)))

(defn connections
  [langlist]
  (apply str
                       (for
                         [lang langlist] (lang-connections lang)))
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
  [& args]
  (println (dot-file (take-csv "languages.csv"))))
