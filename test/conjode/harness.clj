(ns conjode.harness
  (:require [conjode.core :refer :all :as c])
  (:import (org.conjode.java Customer)))


(def client-properties "resources/automation/client.properties")
(def client-xml "resources/automation/client-cache.xml")


(def server-properties "resources/geode-server.properties")
(def server-xml "server-cache.xml")


(defn create-customer [customer-id]
  (let [customer (Customer.)]
    (do (.setFirstName customer (str "FirstName" customer-id))
        (.setLastName customer (str "LastName" customer-id))
        (.setAge customer customer-id)
        (.setState customer (str "State" customer-id))
        (.setAddress customer (str "Address" customer-id))) customer))
