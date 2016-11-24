(ns conjode.integration-test
  (:require [clojure.test :refer :all]
            [conjode.core-test :as core]
            [conjode.helper-test :as helper]))

(deftest tests-which-need-a-running-server
  (conjode.core-test/test-client-cache-using-properties)
  (conjode.core-test/test-client-cache-using-xml))