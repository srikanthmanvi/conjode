(ns conjode.integration-test
  (:require [clojure.test :refer :all]
            [conjode.core :as core]
            [conjode.core-test :as core-test]
            [conjode.harness :refer :all :as harness]))

;(use-fixtures :once (harness/setup-server need-running-server-test-suite))

(comment (deftest need-running-server-test-suite
           "These tests need a running server"
           (core-test/test-client-cache-using-properties)
           (core-test/test-client-cache-using-xml)))


(comment (deftest tests-which-need-a-running-server
           (with-open [cache (core/cache "resources/automation/server.properties")]
             (print "##### Automation Cache Started ####" cache)
             (core-test/test-client-cache-using-properties)
             (core-test/test-client-cache-using-xml)
             (Thread/sleep breathing-time)                  ;so that cache.close is completed
             (is (= 1 1)))))

(comment (deftest tests-that-start-server
           (core-test/test-cache-using-properties)
           (core-test/test-cache-using-xml)
           (conjode.core-test/test-get-put)
           (is (= 1 1))))

(comment (clojure.test/run-tests 'conjode.integration-test))