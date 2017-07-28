(ns playground
  (:require [conjode.core :as core]
            [conjode.region :as region]
            [conjode.harness :as harness]))

;Namespace to quickly run conjode functions

;(def default-client (harness/get-geode-client))
;(conjode.region/gput "333" "hello" "Customer" default-client)
;(conjode.region/gget "333" "Customer" default-client)



(def details {:fname "srikanth" :lname "manvi"})

;(defn look-up
;  "docstring"
;  [look-up-key]
;  ((keyword look-up-key) details))

(defn look-up
  "docstring"
  [look-up-key]
  (look-up-key details))


(look-up :fname)