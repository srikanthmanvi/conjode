(ns conjode.helper-test
  (:require [clojure.test :refer :all]))


(deftest helper-test1
  (is (= 1 1)))

(deftest helper-test2
  (is (= 2 2)))

(deftest helper-standalone-tests
  (helper-test1)
  (helper-test2))

;(run-tests 'conjode.helper-test)