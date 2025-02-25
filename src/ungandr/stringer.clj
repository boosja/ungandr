(ns ungandr.stringer
  (:require [clojure.string]))

(defn strr [& ss]
  (clojure.string/join (flatten ss)))

(defn pad-end [s len]
  (strr s (repeat (- len (count s)) " ")))
