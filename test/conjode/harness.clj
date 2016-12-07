(ns conjode.harness
  (:require [conjode.core :refer :all :as c])
  (:import (org.conjode.java Customer)))

(defn create-customer [customer-id]
  (let [customer (Customer.)]
    (do (.setFirstName customer (str "FirstName" customer-id))
        (.setLastName customer (str "LastName" customer-id))
        (.setAge customer customer-id)
        (.setState customer (str "State" customer-id))
        (.setAddress customer (str "Address" customer-id))) customer))

(def server-properties "resources/automation/server.properties")

(def breathing-time 30000)                                  ; to allow tests to close client cache

(defn setup-server [f]
  "Starts a geode cache server, which can be used to run various tests"
  (with-open [cache  (c/cache server-properties)]
    (do (Thread/sleep 30000)
        (f))))


