(ns conjode.core-test
  (:require [clojure.test :refer :all]
            [conjode.core :refer :all :as c]))


(def client-properties "resources/geode-client.properties")
(def server-properties "resources/geode-server.properties")

(def client-xml "client-cache.xml")
(def server-xml "server-cache.xml")


(deftest test-client-cache-using-properties
  (do (let [client-cache (c/client-cache client-properties)
            result       (not (nil? client-cache))]
        (do (.close client-cache)
            (is (= true result))))))

(deftest test-client-cache-using-xml
  (do (let [client-cache (c/client-cache client-xml)
            result       (not (nil? client-cache))]
        (do (.close client-cache)
            (is (= true result))))))


;(deftest test-cache-using-xml
;  "Tests the cache function, which returns a new Geode Cache,
;  essentially creates a new server process"
;  )

(comment
  (deftest cache-tests

    (testing "Test get-cache"
       (let [cache  (c/cache server-xml)
               result (not (nil? cache))]
           (do (.close cache)
               result)))))


(comment
  (def my-cache (conjode.core/cache "/Users/smanvi/Workspace_clj/conjode/resources/geode-server.properties")))

(deftest core-test1
  (is (= 1 1)))

(deftest core-test2
  (is (= 2 2)))

(deftest core-standalone-tests
  (core-test1)
  (core-test2))