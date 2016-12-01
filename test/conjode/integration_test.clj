(ns conjode.integration-test
  (:require [clojure.test :refer :all]
            [conjode.core-test :as core]
            [conjode.helper-test :as helper]))

(deftest tests-which-need-a-running-server
  (conjode.core-test/test-client-cache-using-properties)
  (conjode.core-test/test-client-cache-using-xml)
  (is (= 1 1)))

(deftest tests-that-start-server
  (conjode.core-test/test-cache-using-properties)
  (conjode.core-test/test-cache-using-xml)
  (conjode.core-test/test-get-put)
  (is (= 1 1)))

(clojure.test/run-tests 'conjode.integration-test)