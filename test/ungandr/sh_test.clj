(ns ungandr.sh-test
  (:require [clojure.test :refer [deftest is testing]]
            [ungandr.sh :as sh]))

(deftest colorize-test
  (testing "Returns a string with ansi escape codes"
    (is (= (sh/colorize "GLORY" :fg/red)
           "\033[31mGLORY\033[0m")))

  (testing "Returns string when input is char"
    (is (= (sh/colorize \c :fg/red)
           "\033[31mc\033[0m")))

  (testing "Applies all styles when passing in vector"
    (is (= (sh/colorize "Yellow bg with black text" [:fg/black :bg/yellow])
           "\033[30m\033[43mYellow bg with black text\033[0m"))))
