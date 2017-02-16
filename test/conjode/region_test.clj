(ns conjode.region-test
  (:require [clojure.test :as t :refer :all]
            [conjode
             [harness :as h]
             [region :as r]])
  (:import org.apache.geode.cache.Region))

(deftest parent-region-test)

(deftest get-region-test
  "Tests the get-region api which takes a region name and
   returns an instance of Region"
  (let [result (r/get-region "Customer" (h/get-geode-client))]
    (is (instance? org.apache.geode.cache.Region result))))

(deftest get-parent-region-test
  "Tests the get-parent function which returns the parent of the
given region"
  (let [geode-client (h/get-geode-client)
        test-region (r/get-region "Items/fmcg" geode-client)
        result-region (r/get-parent-region test-region geode-client)]
    (is (= "Items" (.getName result-region)))))

(deftest put-all-test
  "Tests the gput-all api"
  (let [m {1 "abc" 2 "cde" 3 "efg" 4 "hij" 5 "klm"}
        region "Customer"]
    (do
      (r/gput-all m region h/get-geode-client)
      (is (= "klm" (r/gget 5 region (h/get-geode-client)))))))
