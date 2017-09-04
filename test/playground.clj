(ns playground
  (:require [conjode.core :as c]
            [conjode.region :as r]))

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


; Some of the other functions in conjode.region namespace, not a comprehensive list
(r/empty-region? customer-region)
(r/all-keys customer-region)                      ;Returns keyset for the region
(r/remove-entry 1 customer-region)
(r/size customer-region)
(r/remove-all #{1 2 3} customer-region)           ;Removes entries for the keys
(r/remove-value 1 customer-region)                ;Leaves key intact
(r/values customer-region)
(r/destroy-client-region "CustomerRegion" my-client)
(r/clear-region "CustomerRegion" my-client)


(conjode.query/execute-query "select * from /CustomerRegion" my-client)

;(def default-client (harness/get-geode-client))
;(conjode.region/gput "333" "hello" "Customer" default-client)
;(conjode.region/gget "333" "Customer" default-client)


