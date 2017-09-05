(ns playground
  (:require [conjode.core :as c]
            [conjode.region :as r]
            [conjode.query :as q :refer :all]
            [conjode.function :as f :refer :all])
  (:import (org.conjode.java Customer)))

;Namespace to quickly run conjode functions

; connect to locator running on localhost[10334]
(def my-client (c/connect))


; alternatively, connect using a geode.properties file
;(def my-client (c/connect "<PATH_TO_/geode.properties"))

; creates a proxy region on the client
;(def customer-region
;  (r/create-client-region "CustomerRegion" :proxy my-client))

;OR get hold of a existing region which was loaded through client-cache.xml via
; geode.properties
(def customer-region (r/get-region "CustomerRegion" my-client))

; put key-values in a region. ggput takes key, value, region as arguments.
(r/gput 1 "AA" customer-region)
(r/gput "fName" "John" customer-region)
(r/gput :lname "Doe" customer-region)

;; gput-all puts a map in a region
(def customer-map
  {1 "John"
   2 "Sam"
   3 "Sri"})

(r/gput-all customer-map customer-region)


;get values from a region.
(r/gget 1 customer-region)                        ; "AA"
(r/gget :lname customer-region)                   ; "Doe"

;ggetAll takes a set of keys and region
(r/ggetAll #{1 2 3} customer-region)              ;{1 "John", 2 "Sam", 3 "Sri"}

;put/get java objects
(r/gput 123 (Customer. "Albert" "Einstein" 12334324) customer-region)
(r/gget 123 customer-region)
;gget gets the java object. The Class should be on the server and client classpath.
;#object[org.conjode.java.Customer
;        0x4a660ff2
;        "Customer{firstName='Albert', lastName='Einstein', address='null', state='null', age=0, ssn=12334324}"]


; Some of the other functions in conjode.region namespace, not a comprehensive list
(r/empty-region? customer-region)
(r/all-keys customer-region)                      ;Returns keyset for the region
(r/remove-entry 1 customer-region)
(r/size customer-region)
(r/remove-all #{1 2 3} customer-region)           ;Removes entries for the keys
(r/remove-value 1 customer-region)                ;Leaves key intact
(r/values customer-region)
;(r/destroy-client-region "CustomerRegion" my-client)
;(r/clear-region "CustomerRegion" my-client)


(r/clear-region customer-region)
(r/gput 100 (Customer. "Albert" "Einstein" 12334324) customer-region)
(r/gput 101 (Customer. "John" "Doe" 45276675) customer-region)
(r/gput 102 (Customer. "John" "Smith" 432540012) customer-region)

(conjode.query/execute-query "select * from /CustomerRegion" my-client)
(conjode.query/execute-query "select * from /CustomerRegion where firstName=$1
    and lastName=$2" ["John" "Smith"] my-client)

(r/clear-region customer-region)
(r/gput 101 {:fname "Marie" :lname "Curie" :state "NC"} customer-region)
(r/gput 102 {:fname "John" :lname "Doe" :state "NC"} customer-region)
(r/gput 103 {:fname "John" :lname "Doe" :state "CA"} customer-region)

(q/execute-query "select * from /CustomerRegion" my-client)
(q/execute-query "select * from /CustomerRegion where :state=$1" ["NC"] my-client)



;(def default-client (harness/get-geode-client))
;(conjode.region/gput "333" "hello" "Customer" default-client)
;(conjode.region/gget "333" "Customer" default-client)


