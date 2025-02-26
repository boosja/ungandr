(ns ungandr.stringer
  (:require [clojure.string]))

(defn strr [& ss]
  (clojure.string/join (flatten ss)))

(defn pad-end [s len]
  (strr s (repeat (- len (count s)) " ")))

(defn pad-start [s n]
  (strr (repeat n " ") s))

(defn get-frame [lines]
  (->> lines
       (map #(pad-start % 2))
       (clojure.string/join "\n")))

