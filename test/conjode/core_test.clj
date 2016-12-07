(ns conjode.core-test
  (:require [clojure.test :refer :all]
            [conjode.core :refer :all :as c]
            [conjode.harness :read :all :as harness]))


(def client-properties "resources/automation/client.properties")
(def client-xml "resources/automation/client-cache.xml")


(def server-properties "resources/geode-server.properties")
(def server-xml "server-cache.xml")


(deftest ^:needs-server test-client-cache-using-properties                 ;needs running server
  (do (let [client-cache (c/client-cache client-properties)
            result       (not (nil? client-cache))]
        (do (.close client-cache)
            (is (= true result))))))

(deftest ^:needs-server test-client-cache-using-xml
  (do (let [client-cache (c/client-cache client-xml)        ;needs running server
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
  (with-open [client-cache (c/client-cache client-properties)]
    (let [customer (harness/create-customer 1010)]
      (do (c/put 1010 customer "Customer" client-cache)
          (is (= customer (c/get 1010 "Customer" client-cache)))))))

;(def my-client (conjode.core/client-cache "resources/automation/client.properties"))
