(ns ungandr.sh
  (:require [babashka.process :as process]
            [clojure.java.io :as io]))

(defn p [& ss]
  (apply print ss))

(defn pl [& ss]
  (apply println ss))

(defn make-it-so []
  (flush))

(defn read-key []
  (.read (io/reader (.reader (System/console)))))

(defn read-char-by-char! []
  (process/shell "stty -icanon -echo"))

(defn read-line-by-line! []
  (process/shell "stty icanon echo"))

;;

(defn to-beyond-the-ether []
  (p "\033[?1049h"))

(defn back-to-reality []
  (p "\033[?1049l"))

(defn shine-that-thang []
  (p "\033[H\033[2J"))
