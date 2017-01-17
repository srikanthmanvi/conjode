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


(defn setup-server [f]
  "Starts a geode cache server, which can be used to run various tests"
  (with-open [cache  (c/cache server-properties)]
    (do (Thread/sleep 30000)
        (f))))
