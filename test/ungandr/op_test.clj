(ns ungandr.op-test
  (:require [clojure.test :refer [deftest is testing]]
            [ungandr.op :as op]))

(deftest move-enemies-test
  (testing "Decreases the distance of the walls"
    (is (= (op/move-enemies {:walls [[:wall 4]]})
           {:walls [[:wall 3]]})))

  (testing "Decreases the distance of all walls"
    (is (= (op/move-enemies {:walls [[:wall 4] [:wall 11]]})
           {:walls [[:wall 3] [:wall 10]]}))))

(deftest destroy-test
  (testing "Wall disappears when attacking"
    (is (= (op/destroy {:walls [[:wall 2]]})
           {:walls []})))

  (testing "Only the first wall disappears when attacking"
    (is (= (op/destroy {:walls [[:wall 2] [:wall 11]]})
           {:walls [[:wall 11]]})))

  (testing "Wall with dist greater than 2 does not disappear"
    (is (= (op/destroy {:walls [[:wall 3]]})
           {:walls [[:wall 3]]}))))
