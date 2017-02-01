(ns conjode.core-test
  (:require [clojure.test :refer :all]
            [conjode.core :refer :all :as c]
            [conjode.harness :read :all :as harness]))


(def client-properties "resources/geode-client.properties")
(def client-xml "client-cache.xml")
(def server-xml "server-cache.xml")
(def server-properties "resources/geode-server.properties")


;; Test Fixture
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; (def geode-client-atom (atom (c/get-client-cache client-properties)))
(def geode-client-atom (atom nil))

(defn- close-client []
  (.close (deref geode-client-atom)))

(defn- connect []
  (reset! geode-client-atom (c/get-client-cache client-properties)))

(defn- once-fixture [f]
  (connect)
  (f)
  (close-client))

(use-fixtures :once once-fixture)
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn- geode-client [] @geode-client-atom)

(comment (deftest ^:needs-server test-client-creation

           (testing "Geode client creation using a properties file"
             (do (let [client-cache (c/get-client-cache client-properties)
                       result       (not (nil? client-cache))]
                   (do (.close client-cache)
                       (is (= true result))))))

           (testing "Geode client creation using a cache.xml file"
             (do (let [client-cache (c/get-client-cache client-xml)        ;needs running server
                       result       (not (nil? client-cache))]
                   (do (.close client-cache)
                       (is (= true result))))))))


(deftest ^:needs-server test-get-put
  "Tests the put and get api"

  (testing "With clojure maps as values"
    (let [person1 {:fname "John"
                   :lname "Doe"
                   :ssn 123}
          person2 {:fname "Jane"
                   :lname "Doe"
                   :ssn 456}]
      (do
        (c/gput 1 person1 "Customer" (geode-client))
        (c/gput 2 person2 "Customer" (geode-client))
        (is (= person1 (c/gget 1 "Customer" (geode-client))))
        (is (= person2 (c/gget 2 "Customer" (geode-client)))))))

  (testing "With Java objects as values")
  (let [customer (harness/create-customer 1010)]
    (do (c/gput 1010 customer "Customer" (geode-client))
        (is (= customer (c/gget 1010 "Customer" (geode-client)))))))



(deftest ^:needs-server test-get-region-client
  "Tests the get-region api when involked from the client side.
   Customer region is created as part of test setup in fixture"

  (is (not (nil? (c/get-region "Customer" (geode-client))))))


(comment (deftest ^:needs-server test-execute-function
           "Tests execute-function-on-region function"
           (with-open [client (c/get-client-cache client-properties)]

             (testing "Basic case, without args, without  filter"
               (let [input-map function-details]
                 (is (= true (c/execute-function input-map client))))))))

;; (execute-function-on-server "no-result-function" my-client)
