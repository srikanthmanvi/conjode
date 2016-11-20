(ns conjode.tools
  (:require [conjode.core :refer :all :as c])
  (:import [org.conjode.java Customer]))

(comment)
(c/put 500 (Customer. "fname4" "lname2" "addr2") "Customer" (c/get-client-cache "client-cache.xml"))
(comment)
(c/get 500 "Customer" (c/get-client-cache "client-cache.xml"))

(comment (defn create-customer [i]
           (let [customer (Customer.)]
             (do (.setFirstName customer (str "FirstName" i))
                 (.setLastName customer (str "LastName" i))
                 (.setAge customer i)
                 (.setState customer (str "State" i))
                 (.setAddress customer (str "Address" i))) customer)))

(comment
  (time (let [cache (c/client-cache-from-xml cache-xml)]
          (doseq [i (range 10)] (c/put i (create-customer i) "Customer" cache)))))

(comment (c/cache-from-xml "server-cache.xml"))