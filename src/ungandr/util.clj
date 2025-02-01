(ns ungandr.util)

(defn llast [s]
  (nth s (- (count s) 2) nil))

(defn cutlass [s]
  (take (- (count s) 2) s))
