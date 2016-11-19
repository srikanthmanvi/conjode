(ns conjode.core-test
  (:require [clojure.test :refer :all]
            [conjode.core :refer :all :as c]))

(def client-xml "client-cache.xml")
(def server-xml "server-cache.xml")

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
              result)))))




