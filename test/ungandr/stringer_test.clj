(ns ungandr.stringer-test
  (:require [clojure.test :refer [deftest is testing]]
            [ungandr.stringer :as stringer]))

(deftest strr-test
  (testing "Concats strings together"
    (is (= (stringer/strr "a" "b" "c")
           "abc")))

  (testing "Concats vector of strings together"
    (is (= (stringer/strr ["a" "b"] ["c" "d"])
           "abcd")))

  (testing "Concats both strings and vector of strings"
    (is (= (stringer/strr "a" ["b" "c"])
           "abc")))

  (testing "Concats nested vector of strings"
    (is (= (stringer/strr "a" ["b" ["c" ["d"]]])
           "abcd"))))

(deftest pad-end-test
  (testing "Pads the end of the string with surplus spaces"
    (is (= (stringer/pad-end "hello" 16)
           "hello           ")))

  (testing "Does not pad if string surpasses the len"
    (is (= (stringer/pad-end "hello" 3)
           "hello"))))
