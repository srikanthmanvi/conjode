(ns conjode.harness
  (:require [conjode.core :refer :all :as c]
            [conjode.region :refer :all :as region-ns])
  (:import (org.conjode.java Customer)))


(def client-properties "resources/automation/client.properties")
(def client-xml "resources/automation/client-cache.xml")


(def server-properties "resources/geode-server.properties")
(def server-xml "server-cache.xml")

(def geode-client-atom (atom nil))

(defn- connect []
  (reset! geode-client-atom (c/get-client-cache client-properties)))

(defn create-customer [customer-id]
  (let [customer (Customer.)]
    (do (.setFirstName customer (str "FirstName" customer-id))
        (.setLastName customer (str "LastName" customer-id))
        (.setAge customer customer-id)
        (.setState customer (str "State" customer-id))
        (.setAddress customer (str "Address" customer-id))) customer))

(defn get-geode-client
  "Returns a geode client created using the conjode default properties file.
  If a client already exists, the same client is returned, if not a new one is returned"
  []
  (if (nil? @geode-client-atom)
    (do
      (connect)
      @geode-client-atom)
    @geode-client-atom))

(defn- connect []
  (reset! geode-client-atom (c/get-client-cache client-properties)))

(def sample-people {1 {:name "A"
                       :salary 40000
                       :sex "male"}
                    2 {:name "B"
                       :salary 60000
                       :sex "female"}
                    3 {:name "C"
                       :salary 70000
                       :sex "male"}
                    4 {:name "D"
                       :salary 80000
                       :sex "female"}
                    5 {:name "E"
                       :salary 55000
                       :sex "female"}})
(defn load-people
  "Loads some people data to geode customer region"
  []
  (let [client (get-geode-client)]
    (doseq [[key val] sample-people]
      (region-ns/gput key val "Customer" client))))
