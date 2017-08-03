(ns conjode.region-test
  (:require [clojure.test :as t :refer :all]
            [conjode
             ;[harness :as h]
             [region :as r]
             [core :as core]])
  (:import org.apache.geode.cache.Region
           (org.apache.geode.cache RegionExistsException)))


(defn once-fixture
  "Setup and Tear down which run once per namespace"
  [f]
  ;(core/connect)
  (f)
  (core/close-client))


(use-fixtures :once once-fixture)




(deftest create-client-region-test
  (let [test-client (core/connect)]

    (testing "Testing local region creation. Region does not exist on server"
      (do
        (r/create-client-region "local-region" :local test-client)
        (is (= 0 (r/size "local-region" test-client)))))

    (testing "Testing proxy region creation"
      (do
        (r/create-client-region "ccr-proxy-region" :proxy test-client)
        (is (= 0 (r/size "ccr-proxy-region" test-client)))))

    (testing "Testing caching-proxy region creation"
      (do
        (r/create-client-region "ccr-caching-proxy-region" :caching-proxy test-client)
        (is (= 0 (r/size "ccr-caching-proxy-region" test-client)))))

    (testing "Testing invalid region type"
      (let [result (r/create-client-region "DummyRegion" :not-supported-type test-client)]
        (is (= true (and (= (:error result) "Invalid region-type. Only :local :proxy :caching-proxy are supported"))))))))


(comment
  (deftest get-put-test
    "Tests simple get and put functionality"
    (let [geode-client (conjode.harness/get-geode-client)]

      (testing "gput and gget of simple String keys and Values"
        (do
          (conjode.region/gput "007" "Mr.Bond" "Customer" geode-client)
          (is (= "Mr.Bond" (conjode.region/gget "007" "Customer" geode-client))))))))

(comment (deftest put-all-test
           "Tests the gput-all api"
           (let [m {1 "abc" 2 "cde" 3 "efg" 4 "hij" 5 "klm"}
                 region "Customer"]
             (do
               (r/gput-all m region (h/get-geode-client))
               (is (= "klm" (r/gget 5 region (h/get-geode-client))))))))

(comment (deftest get-region-test
           "Tests the get-region api which takes a region name and
            returns an instance of Region"
           (let [result (r/get-region "Customer" (h/get-geode-client))]
             (is (instance? org.apache.geode.cache.Region result)))))

(comment
  (deftest get-parent-region-test
    "Tests the get-parent function which returns the parent of the
  given region"
    (let [geode-client (h/get-geode-client)
          test-region (r/get-region "Items/fmcg" geode-client)
          result-region (r/get-parent-region test-region geode-client)]
      (is (= "Items" (.getName result-region))))))

