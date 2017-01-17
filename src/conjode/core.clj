(ns conjode.core
  (:require [conjode.util :as u])
  (:import (com.gemstone.gemfire.cache CacheFactory)
           (com.gemstone.gemfire.cache.client ClientCache ClientCacheFactory Pool)
           (com.gemstone.gemfire.cache.execute Execution FunctionService)))

(defn client-cache
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

(defn get
  "Gets the value associated with the given key"
  [key region-name ^ClientCache client]
  (let [region (.getRegion client region-name)
        result (.get region key)]
    result))

(defn put
  "Puts key-value into the given region"
  [key value region-name ^ClientCache client]
  (let [region (.getRegion client region-name)]
    (.put region key value)))

(defn get-region
  "Returns the region handle from the client cache"
  [region-name ^ClientCache client-cache]
  (.getRegion client-cache region-name))

(defn execute-function-on-servers
  "executes a function on all the
  servers in the given pool.  If the argument pool is nil, then the
  default pool is used"
  [function-id ^Pool pool ^ClientCache geode-client]
  (let [execution-pool (or pool (.getDefaultPool geode-client))
        ^Execution execution-obj (FunctionService/onServers pool)]
    (.execute execution-obj function-id)))


(comment (defn execute-function
           "Executes a geode function based on the input map."
           [function-map ^ClientCache client]
           (cond (:on-region function-map ())
                 (internal/execute-function-on-region function-map client)
                 (:on-server function-map)
                 (internal/execute-function-on-server function-map client))))
