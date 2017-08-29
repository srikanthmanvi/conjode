(ns playground
  (:require [conjode.core :as c]
            [conjode.region :as r]))

;Namespace to quickly run conjode functions

(def my-client (c/connect))
(def my-region (r/get-region "CustomerRegion" my-client))

;(def default-client (harness/get-geode-client))
;(conjode.region/gput "333" "hello" "Customer" default-client)
;(conjode.region/gget "333" "Customer" default-client)



