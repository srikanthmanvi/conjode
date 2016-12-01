(ns conjode.core-test
  (:require [clojure.test :refer :all]
            [conjode.core :refer :all :as c]
            [conjode.harness :read :all :as harness]))


(def client-properties "resources/geode-client.properties")
(def server-properties "resources/geode-server.properties")

(def client-xml "client-cache.xml")
(def server-xml "server-cache.xml")


(deftest test-client-cache-using-properties                 ;needs running server
  (do (let [client-cache (c/client-cache client-properties)
            result       (not (nil? client-cache))]
        (do (.close client-cache)
            (is (= true result))))))

(deftest test-client-cache-using-xml
  (do (let [client-cache (c/client-cache client-xml)        ;needs running server
            result       (not (nil? client-cache))]
        (do (.close client-cache)
            (is (= true result))))))


(deftest test-cache-using-xml
  "Tests the cache function, which returns a new Geode Cache,
  essentially creates a new server process"
  (let [cache  (c/cache server-xml)
        result (not (nil? cache))
        close  (.close cache)]
    (is (= true result))))

(deftest test-cache-using-properties
  "Tests the cache function, which returns a new Geode Cache,
  essentially creates a new server process"
  (let [cache  (c/cache server-properties)
        result (not (nil? cache))
        close  (.close cache)]
    (is (= true result))))

(deftest test-get-put
  "Tests get and put apis"

  (deftest test-get-put
    "Tests the get api"
    (with-open [cache        (c/cache server-properties)
                client-cache (c/client-cache client-properties)]
      (do (c/put 100 (harness/create-customer 100) "Customer" client-cache)
          (let [result (c/get 1010 "Customer" client-cache)
                dummy  (println "$$$$$$$ RETRIEVED RESULT:" result)]
            result)
          (is (= true (c/get 1010 "Customer" client-cache))))))

  (is true))