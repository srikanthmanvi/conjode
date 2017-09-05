(ns conjode.function
  (:require [conjode.util :as u])
  (:import [org.apache.geode.cache.client ClientCache ClientCacheFactory Pool PoolManager]
           [org.apache.geode.cache.execute Execution FunctionService ResultCollector]))

(defn execute-function-on-server
  "Executes a function on one of the servers in the distributed system.
  Returns a list of results. Default pool is used to locate server."
  [function-id ^ClientCache geode-client]
  (let [^FunctionService function-service
        (FunctionService/onServer (.getDefaultPool geode-client))
        ^ResultCollector results (.execute function-service function-id)]
    (.getResult results)))

