(ns proglanguages.core
  (:gen-class)
  (:require [clojure-csv.core :as csv])
  (:require [clojure.java.io :as io])
  (:use [clojure-csv.core]))

(defn take-csv
  "Takes file name and reads data"
  [fname]
  (with-open [file (io/reader fname)]
    (csv/parse-csv (slurp file))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (take-csv "/tmp/languages.csv")))
