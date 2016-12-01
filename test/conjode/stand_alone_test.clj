(ns conjode.stand-alone-test
  (:require [clojure.test :refer :all]
            [conjode.util-test :refer :all]))

(deftest stand-alone-tests
  "These are tests that dont need a running Geode server, so can be run as part of CI"
  (conjode.util-test/test-read-properties-file)
  (is (= 1 1)))


