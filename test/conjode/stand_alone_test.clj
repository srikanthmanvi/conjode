(ns conjode.stand-alone-test
  (:require [clojure.test :refer :all]
            [conjode.core-test :as core-test]
            [conjode.helper-test :as helper-test]))

(deftest run-all-standalone-tests
  (core-test/core-standalone-tests)
  (helper-test/helper-standalone-tests))


