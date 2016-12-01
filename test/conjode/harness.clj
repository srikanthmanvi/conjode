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
