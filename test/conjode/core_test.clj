(ns conjode.core-test
  (:require [clojure.test :refer :all]
            [conjode.core :refer :all :as c]))


(def client-properties "resources/geode-client.properties")
(def server-properties "resources/geode-server.properties")

(def client-xml "client-cache.xml")
(def server-xml "server-cache.xml")

(deftest test-client-cache

  (testing "Testing client-cache by passing properties."
    (do (let [client-cache (c/client-cache client-properties)
              result       (not (nil? client-cache))]
          (do (.close client-cache)
              (is (= true result)))))))

(comment
  (deftest cache-tests

    (testing "Test get-client-cache"
      (do (println "Running Test get-client-cache")
          (let [client-cache (c/get-client-cache client-xml)
                result       (not (nil? client-cache))]
            (do (.close client-cache)
                result))))

    (testing "Test get-cache"
      (do (println "Running Test get-cache")
          (let [cache  (c/get-cache server-xml)
                result (not (nil? cache))]
            (do (.close cache)
                result))))))

(comment (deftest client-api-tests
           (with-open [server-cache start-server-cache]
             "Theses tests test the apis which use clientCache and require a server running"
             (testing "Test get-client-cache")
             ())))