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

(def default-ctrls {"discard" \
                    "dsusp" \
                    "eof" \
                    "intr" \
                    "kill" \
                    "lnext" \
                    "reprint" \
                    "start" \
                    "status" \
                    "stop" \
                    "susp" \
                    "werase" \})

(defn relinquish-powers-to-thou!
  "Restores the default ctrl bindings of the terminal"
  []
  (process/shell
   (str "stty" (apply str (map (fn [[ctrl binding]] (str " " ctrl " '" binding "'"))
                                default-ctrls)))))

(defn take-control-over-powers-of-thee!
  "Disables default terminal ctrl binding behavior"
  []
  (process/shell
   (str "stty" (apply str (map (fn [[ctrl]] (str " " ctrl " ''"))
                               default-ctrls)))))

;;

(defn to-beyond-the-ether []
  (p "\033[?1049h"))

(defn back-to-reality []
  (p "\033[?1049l"))

(defn shine-that-thang []
  (p "\033[H\033[2J"))

(def styles {:bold "1"
             :underline "4"
             :fg/black "30"
             :fg/red "31"
             :fg/green "32"
             :fg/yellow "33"
             :fg/blue "34"
             :fg/magenta "35"
             :fg/cyan "36"
             :fg/white "37"
             :bg/black "40"
             :bg/red "41"
             :bg/green "42"
             :bg/yellow "43"
             :bg/blue "44"
             :bg/magenta "45"
             :bg/cyan "46"
             :bg/white "47"})

(defn colorize [s c]
  (str "\033[" (get styles c) "m" s "\033[0m"))
