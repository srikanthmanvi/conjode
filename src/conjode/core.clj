(ns conjode.core
  (:require [conjode.util :as u])
  (:import [org.apache.geode.cache.client ClientCache ClientCacheFactory Pool PoolManager]
           [org.apache.geode.cache.execute Execution FunctionService]))

(defn get-client-cache
  "Returns a client cache, configured using the passed cache xml file or the properties file"
  [client-cache-file]
  (cond (.endsWith client-cache-file ".properties")
        (.create (ClientCacheFactory. (u/read-properties-file client-cache-file)))
        (.endsWith client-cache-file ".xml")
        (let [factory (ClientCacheFactory.)]
          (do (.set factory "cache-xml-file" client-cache-file)
              (.create factory)))))

(defn- get-pool-by-name [pool-name]
  (PoolManager/find pool-name))

(defn execute-function-on-server
  "Executes a function on one of the servers in the given pool. The provided
  pool can be the pool-name of a predefined pool name or an instance
  of org.apache.geode.cache.client.Pool. When a pool is not provided default pool used"

  ([function-id pool ^ClientCache geode-client]
   (let [execution-pool (or (when (instance? Pool pool) pool) (get-pool-by-name pool))
         ^Execution execution-obj (FunctionService/onServers pool)
         results (.execute execution-obj function-id)]
     (cond
       (instance? org.apache.geode.internal.cache.execute.NoResult results)
       true)))

  ([function-id ^ClientCache geode-client]
   (execute-function-on-server function-id (.getDefaultPool geode-client) geode-client)))

(comment (defn execute-function
           "Executes a geode function based on the input map."
           [function-map ^ClientCache client]
           (cond (:on-region function-map ())
                 (internal/execute-function-on-region function-map client)
                 (:on-server function-map)
                 (internal/execute-function-on-server function-map client))))
