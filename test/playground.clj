(ns playground
  (:require [conjode.core :as c]
            [conjode.region :as r]))

;Namespace to quickly run conjode functions

(def my-client (c/connect))
(def my-region (r/get-region "CustomerRegion" my-client))

(r/gput 101 "Hello" my-region)
(r/gput 102 "Hello World" my-region)
(r/remove-entry 101 my-region)

;remove-all
(r/gput :one "One" my-region)
(r/gput :two "two" my-region)
(r/gget :one my-region)
(r/gget :two my-region)
(r/remove-all #{:one :two} my-region)

;(def default-client (harness/get-geode-client))
;(conjode.region/gput "333" "hello" "Customer" default-client)
;(conjode.region/gget "333" "Customer" default-client)



