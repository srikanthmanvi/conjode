(ns conjode.core-test
  (:require [clojure.test :refer :all]
            [conjode.core :refer :all :as c]
            [conjode.harness :read :all :as harness]))


(def client-properties "resources/geode-client.properties")
(def client-xml "client-cache.xml")
(def server-xml "server-cache.xml")
(def server-properties "resources/geode-server.properties")


(deftest ^:needs-server test-client-cache-using-properties                 ;needs running server
  (do (let [client-cache (c/get-client-cache client-properties)
            result       (not (nil? client-cache))]
        (do (.close client-cache)
            (is (= true result))))))

(deftest ^:needs-server test-client-cache-using-xml
  (do (let [client-cache (c/get-client-cache client-xml)        ;needs running server
            result       (not (nil? client-cache))]
        (do (.close client-cache)
            (is (= true result))))))


(deftest ^:starts-server test-cache-using-xml
  "Tests the cache function, which returns a new Geode Cache,
  essentially creates a new server process"
  (let [cache  (c/cache server-xml)
        result (not (nil? cache))
        close  (.close cache)]
    (is (= true result))))

(deftest ^:starts-server test-cache-using-properties
  "Tests the cache function, which returns a new Geode Cache,
  essentially creates a new server process"
  (let [cache  (c/cache server-properties)
        result (not (nil? cache))
        close (.close cache)]
    (is (= true result))))


(deftest ^:needs-server test-get-put
  "Tests the put and get api"
  (with-open [client-cache (c/get-client-cache client-properties)]
    (let [customer (harness/create-customer 1010)]
      (do (c/gput 1010 customer "Customer" client-cache)
          (is (= customer (c/gget 1010 "Customer" client-cache)))))))

(deftest ^:needs-server test-get-region-client
  "Tests the get-region api when involked from the client side"
  (with-open [client (c/get-client-cache client-properties)]
    (is (not (nil? (c/get-region "Customer" client))))))

(comment (deftest ^:needs-server test-execute-function
           "Tests execute-function-on-region function"
           (with-open [client (c/get-client-cache client-properties)]

             (testing "Basic case, without args, without  filter"
               (let [input-map function-details]
                 (is (= true (c/execute-function input-map client))))))))
