(ns conjode.core-test
  (:require [clojure.test :refer :all]
            [conjode.core :refer :all :as c]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 1 1)))
  (testing "FIXME2, I fail."
    (is (= "b" "b")))
  (testing "FIXME3, I fail."
    (is (= :e :d))))

(def client-xml "client-cache.xml")
(def server-xml "server-cache.xml")

(deftest my_test

  (testing "Test get-client-cache"
    (not (nil? (c/get-client-cache client-xml))))

  (testing "Test get-cache"
    (not (nil? (c/get-cache server-xml)))))


(comment (def sample-region-config {:region-name "TradeRegion"
                                    :ref-id      :caching-proxy}))

(comment (c/create-region sample-region-config
                          (c/client-cache-from-xml "client-cache.xml")))

