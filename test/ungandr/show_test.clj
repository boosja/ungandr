(ns ungandr.show-test
  (:require [clojure.test :refer [deftest is testing]]
            [ungandr.show :as show]))

(deftest materialize-ungandr-test
  (testing "Lager str length antall steg"
    (is (= (count (show/materialize-ungandr ".·°˚°·._"))
           8)))

  (testing "Animerer riktig veien"
    (is (= (-> (show/materialize-ungandr ".·°˚°·._")
               (get 0))
           ".·°˚°·._"))
    (is (= (-> (show/materialize-ungandr ".·°˚°·._")
               (get 1))
           "·°˚°·._."))
    (is (= (-> (show/materialize-ungandr ".·°˚°·._")
               (get 5))
           "·._.·°˚°"))))

(deftest glorify-test
  (testing "Returns ungandr with glory"
    (is (= (show/glorify ".·°˚°·._" [:fg/red])
           ".·°˚°·.\033[31m_\033[0m"))))

(deftest render-walls-test
  (is (= (show/render-walls [[:wall 23]])
         "                       []"))
  (is (= (show/render-walls [[:wall 0]])
         "[]"))
  (is (= (show/render-walls [[:wall -1]])
         "[]"))
  (is (= (show/render-walls [[:wall 4] [:wall 16]])
         "    []          []")))

(deftest walls?-test
  (testing "Returns true if walls there be"
    (is (true? (show/walls? {:walls [[:wall 3]]}))))

  (testing "Returns false if walls there be not"
    (is (false? (show/walls? {:walls []})))))

(deftest generate-line-test
  (testing "Renders at least the snake"
    (is (= (show/generate-line 0 nil)
           ".·°˚°·._")))

  (testing "Renders the snake when there are no enemies"
    (is (= (show/generate-line 0 {:walls []})
           ".·°˚°·._")))

  (testing "Renders the wall"
    (is (= (show/generate-line 0 {:walls [[:wall 23]]})
           ".·°˚°·._                       []"))))
