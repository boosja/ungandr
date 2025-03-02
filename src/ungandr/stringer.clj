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
       (map #(pad-start % 1))
       (clojure.string/join "\n")))

(comment

  ["=-=-=-=-=-="
   "snake   [] "
   "=-=-=-=-=-="]

  "ungandr"
  (ungandr.sh/pl
   (clojure.string/join "\n"
                        ["                 _________                  "
                         " .·°˚°·..·°˚°*·./ ungandr \\.·*°˚°·..·°˚°·. "
                         "/                                         \\"
                         " \\                                       /"
                         "/                                         \\"
                         " \\                                       /"
                         "/                                         \\"
                         " \\                                       /"
                         "/                                         \\"
                         " \\                                       /"
                         "/                                         \\"
                         " \\                                       /"
                         "  ˚°·..·°˚°·..·°˚°*·._.·*°˚°·..·°˚°·..·°˚  "]))
  (ungandr.sh/pl
   (clojure.string/join "\n"
                        ["=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="
                         "   ·..·°˚  ·..·°˚  Ungandr  ˚°·..·  ˚°·..·   "
                         "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="
                         "                                             "
                         "               Brr, crash, boom!             "
                         "          They have interrupted your         "
                         "        thousand year incubation period.     "
                         "          You're groggy. You're mad.         "
                         "               You are FURIOUS!              "
                         "       Red hot anger boils up from within.   "
                         "            You. Want. To. DESTROY!          "
                         "                                             "
                         "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="
                         ".·°˚°·._             []          {}          "
                         "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="]))
  (ungandr.sh/pl
   (clojure.string/join "\n"
                        ["=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="
                         "|  ·..·°˚  ·..·°˚  Ungandr  ˚°·..·  ˚°·..·  |"
                         "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="
                         "|                                           |"
                         "|              Brr, crash, boom!            |"
                         "|         They have interrupted your        |"
                         "|       thousand year incubation period.    |"
                         "|         You're groggy. You're mad.        |"
                         "|              You are FURIOUS!             |"
                         "|      Red hot anger boils up from within.  |"
                         "|           You. Want. To. DESTROY!         |"
                         "|                                           |"
                         "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="
                         ".·°˚°·._             []          {}          "
                         "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="]))
  (ungandr.sh/pl)

  :rfc)
