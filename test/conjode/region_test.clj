(ns conjode.region-test
  (:require [clojure.test :as t :refer :all]
            [conjode
             [harness :as h]
             [region :as r]])
  (:import org.apache.geode.cache.Region))

(deftest get-put-test
  "Tests simple get and put functionality"
  (let [geode-client (conjode.harness/get-geode-client)]

    (testing "gput and gget of simple String keys and Values"
      (do
        (conjode.region/gput "007" "Mr.Bond" "Customer" geode-client)
        (is (= "Mr.Bond" (conjode.region/gget "007" "Customer" geode-client)))))))

(deftest put-all-test
  "Tests the gput-all api"
  (let [m {1 "abc" 2 "cde" 3 "efg" 4 "hij" 5 "klm"}
        region "Customer"]
    (do
      (r/gput-all m region (h/get-geode-client))
      (is (= "klm" (r/gget 5 region (h/get-geode-client)))))))

(deftest get-region-test
  "Tests the get-region api which takes a region name and
   returns an instance of Region"
  (let [result (r/get-region "Customer" (h/get-geode-client))]
    (is (instance? org.apache.geode.cache.Region result))))

(comment
  (deftest get-parent-region-test
    "Tests the get-parent function which returns the parent of the
  given region"
    (let [geode-client (h/get-geode-client)
          test-region (r/get-region "Items/fmcg" geode-client)
          result-region (r/get-parent-region test-region geode-client)]
      (is (= "Items" (.getName result-region))))))
