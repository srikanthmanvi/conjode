(ns conjode.core
  (:require [conjode.util :as u])
  (:import com.gemstone.gemfire.cache.CacheFactory
           [com.gemstone.gemfire.cache.client ClientCache ClientCacheFactory Pool PoolManager]
           [com.gemstone.gemfire.cache.execute Execution FunctionService]))

(defn get-client-cache
  "Returns a client cache, configured using the passed cache xml file or the properties file"
  [client-cache-file]
  (cond (.endsWith client-cache-file ".properties")
        (.create (ClientCacheFactory. (u/read-properties-file client-cache-file)))
        (.endsWith client-cache-file ".xml")
        (let [factory (ClientCacheFactory.)]
          (do (.set factory "cache-xml-file" client-cache-file)
              (.create factory)))))

(defn cache
  "Returns a Cache created using the passed cache.xml or geode.properties file"
  [cache-config-file]
  (cond (.endsWith cache-config-file ".properties")
        (.create (CacheFactory. (u/read-properties-file cache-config-file)))
        (.endsWith cache-config-file ".xml")
        (let [factory (CacheFactory.)]
          (do (.set factory "cache-xml-file" cache-config-file)
              (.create factory)))))

(defn gget
  "Gets the value associated with the given key"
  [key region-name ^ClientCache client]
  (let [region (.getRegion client region-name)
        result (.get region key)]
    result))

(defn gput
  "Puts key-value into the given region"
  [key value region-name ^ClientCache client]
  (let [region (.getRegion client region-name)]
    (.put region key value)))

(defn get-region
  "Returns the region handle from the client cache"
  [region-name ^ClientCache client-cache]
  (.getRegion client-cache region-name))


(defn- get-pool-by-name [pool-name]
  (PoolManager/find pool-name))

(defn execute-function-on-server
  "Executes a function on one of the servers in the given pool. The provided
pool can a a pool name which is defined in the cache.xml or can an instance
  of org.apache.geode.cache.client.Pool. If a pool is not provided, then the default
 pool is used."

  ([function-id pool ^ClientCache geode-client]
   (let [execution-pool (or (when (instance? Pool pool) pool) (get-pool-by-name pool))
         ^Execution execution-obj (FunctionService/onServers pool)]
         (.execute execution-obj function-id)))

  ([function-id ^ClientCache geode-client]
   (execute-function-on-server function-id (.getDefaultPool geode-client) geode-client)))


(comment (defn execute-function
           "Executes a geode function based on the input map."
           [function-map ^ClientCache client]
           (cond (:on-region function-map ())
                 (internal/execute-function-on-region function-map client)
                 (:on-server function-map)
                 (internal/execute-function-on-server function-map client))))
