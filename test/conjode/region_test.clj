(ns conjode.region-test
  (:require [clojure.test :as t :refer :all]
            [conjode
             ;[harness :as h]
             [region :as r]
             [core :as core]]
            [conjode.core :as c])
  (:import org.apache.geode.cache.Region
           (org.apache.geode.cache RegionExistsException)))


(defn once-fixture
  "Setup and Tear down which run once per namespace"
  [f]
  ;(core/connect)
  (f)
  (core/close-client))


(use-fixtures :each once-fixture)


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


(deftest get-put-test
  "Tests simple get and put functionality"

  (testing "Testing get-put"
    (let [test-client (c/connect)]

      (testing "Using ^Region"
        (do
          (let [region (r/create-client-region "gp-region" :local test-client)]

            (testing "Keyword keys"
              (r/gput :a "AAA" region)
              (is (= "AAA" (r/gget :a region))))

            (testing "String Keys"
              (r/gput "A" "AAA" region)
              (is (= "AAA" (r/gget "A" region))))

            (testing "Integer Keys"
              (r/gput 1 "AAA" region)
              (is (= "AAA" (r/gget 1 region)))))))

      (testing "Using region-name"
        (do
          (let [region-name "my-region"]
            (r/create-client-region region-name :local test-client)

            (testing "Keyword keys"
              (r/gput :a "AAA" "my-region" test-client)
              (is (= "AAA" (r/gget :a region-name test-client))))

            (testing "String Keys"
              (r/gput "A" "AAA" region-name test-client)
              (is (= "AAA" (r/gget "A" region-name test-client))))

            (testing "Integer Keys"
              (r/gput 1 "AAA" region-name test-client)
              (is (= "AAA" (r/gget 1 region-name test-client))))))))))

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


(comment (deftest destroy-client-region-test
           (let [test-client (core/connect)]

             (testing " Testing local region destroy"
               (do
                 (r/create-client-region "dcr-region" :local test-client)
                 (r/gput 1 "AA" "dcr-region" test-client)
                 (r/destroy-client-region "dcr-region" test-client)
                 (let [result (r/gput 2 "BB" "dcr-region" test-client)]
                   (is (contains? result :error))
                   (is (= "Region DCR-REGION not found." (:error result))))))

             (testing " Testing proxy region destroy"
               (do
                 (r/create-client-region "dcr-partition-region" :proxy test-client)
                 (r/gput 1 "AA" "dcr-partition-region" test-client)
                 (r/destroy-client-region "dcr-partition-region" test-client)
                 (let [result (r/gput 2 "BB" "dcr-partition-region" test-client)]
                   (is (contains? result :error))
                   (is (= "Region DCR-PARTITION-REGION not found." (:error result))))))
             )))


(deftest clear-region-test
  (let [test-client (core/connect)]

    (testing "Testing clear local valid region"
      (do
        (r/create-client-region "cr-region" :local test-client)
        (r/gput 1 "AA" "cr-region" test-client)
        (r/gput 2 "BB" "cr-region" test-client)
        (is (= 2 (r/size "cr-region" test-client)))
        (r/clear-region "cr-region" test-client)
        (is (= 0 (r/size "cr-region" test-client)))))

    (testing "Testing clear local INVALID region"
      (let [result (r/clear-region "non-existent-region" test-client)]
        (is (contains? result :error))
        (is (= "Region not found" (:error result)))))))

(deftest empty?-test
  (testing "Testing Empty Case"
    (let [test-client (core/connect)
          ^Region my-region (r/create-client-region "dummy-region" :local test-client)]
      (do
        (is (= true (r/empty-region? my-region)))
        (do
          (r/gput 1 "AA" "dummy-region" test-client)
          (is (= false (r/empty-region? my-region))))))))
