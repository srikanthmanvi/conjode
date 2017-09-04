(ns conjode.query-test
  (:require [clojure.test :refer :all])
  (:require [conjode.query :refer :all :as q]
            [conjode.core :as c]
            [conjode.region :as r])
  (:import (org.conjode.java Customer)))

(defn once-fixture
  "Setup and Tear down which run once per namespace"
  [f]
  ;(core/connect)
  (f)
  (c/close-client))

(run-tests)
(use-fixtures :each once-fixture)


(deftest execute-query-test
  (let [test-client (c/connect)
        region (r/get-region "CustomerRegion" test-client)
        expected1 [1 2 3 4]
        expected2 ["AA" "BB" "CC" "DD"]]

    (testing "Query without params"
      (r/clear-region region)
      (r/gput 1 "AA" region)
      (r/gput 2 "BB" region)
      (r/gput 3 "CC" region)
      (r/gput 4 "DD" region)
      (is (= expected2 (q/execute-query "select * from /CustomerRegion" test-client))))

    (testing "Query with params"
      (let [john-doe-object (Customer. "John" "Doe" 12345678)
            john-smith-object (Customer. "John" "Smith" 43254543)
            josh-doe-object (Customer. "Josh" "Doe" 87656789)
            johnny-depp-object (Customer. "Johnny" "Depp" 65457897)]
        (r/clear-region region)
        (r/gput 11 john-doe-object region)
        (r/gput 12 john-smith-object region)
        (r/gput 13 josh-doe-object region)
        (r/gput 14 johnny-depp-object region)
        (is (= 2 (count (q/execute-query "select * from /CustomerRegion where firstName=$1" ["John"] test-client))))
        (is (.equals john-smith-object
                     (first (q/execute-query "select * from /CustomerRegion where firstName=$1 and lastName=$2" ["John" "Smith"] test-client))))))))