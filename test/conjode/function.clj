(ns conjode.function
  (:import
   [org.apache.geode.cache.client ClientCache ClientCacheFactory Pool PoolManager]
   [org.apache.geode.cache.execute Execution FunctionService])
  (:require [conjode.core :refer :all :as c]
            [conjode.region :refer :all :as r]))

;; This namespace has functions corresponding to the FunctionService API
;; http://geode.apache.org/releases/latest/javadoc/org/apache/geode/cache/execute/FunctionService.html


;; Functions that can be called on Client Side
(def sample-function-map {:function-id "salary-cal-function"
                          :on-region {:region-name "Customer"}
                          :on-servers [{:host "localhost"
                                        :port 10334}
                                       {:host "localhost2"
                                        :port 10334}]})

                                        ;FunctionService.onRegion(r).execute("name")

(defn execute-function-on-region
  "Executes a data aware function on the members which host the given region"
  [function-map ^ClientCache client]
  (let [^Region region (r/get-region (get-in function-map [:on-region :region-name]))
        ^Execution execution (FunctionService/onRegion region)
        result (.execute execution (:function-id function-map))]))
