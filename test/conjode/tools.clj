(ns conjode.tools
  (:require [conjode.core :refer :all :as c])
  (:import [com.sm.geode.ref.domain Customer]))


(def cache-xml "/Users/smanvi/Workspace/geode-ref/server-bootstrap/src/main/resources/client-cache.xml")

(comment (c/put 23 (Customer. "fname2" "lname2" "addr2") "Customer" (c/client-cache-from-xml cache-xml)))
(comment (c/get 23 "Customer" (c/client-cache-from-xml cache-xml)))

(defn create-customer [i]
  (let [customer (Customer.)]
    (do (.setFirstName customer (str "FirstName" i))
        (.setLastName customer (str "LastName" i))
        (.setAge customer i)
        (.setState customer (str "State" i))
        (.setAddress customer (str "Address" i))
        ;(.setData customer (byte 100))
        )
    customer))

(comment)
(time (let [cache (c/client-cache-from-xml cache-xml)]
        (doseq [i (range 10)] (c/put i (create-customer i) "Customer" cache))))