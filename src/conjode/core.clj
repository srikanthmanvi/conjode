(ns conjode.core
  (:import
    (com.gemstone.gemfire.cache.client ClientCache)
    (com.gemstone.gemfire.cache.client ClientCacheFactory)
    (com.gemstone.gemfire.cache CacheFactory))
  (:require [msync.properties.core :as properties-reader]))

(defn client-cache-from-xml
  "Returns a client cache configured using the passed cache xml file"
  [client-cache-xml]
  (let [factory (doto (ClientCacheFactory.) (.set "cache-xml-file" client-cache-xml))
        geode-client (.create factory)]
    geode-client))

(defn cache-from-xml
  "Returns a new com.gemstone.gemfire.cache.Cache created using the passed cache.xml."
  [server-cache-xml]
  (let [factory (doto (CacheFactory.) (.set "cache-xml-file" server-cache-xml))
        geode-cache (.create factory)] geode-cache))

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
  "Returns the region from the distributed system"
  [region-name ^ClientCache client-cache]
  (.getRegion client-cache region-name))


(comment
  (defn create-region
    "Creates a region based on the map provided"
    [region-config ^ClientCache client-cache]
    (let [{:keys [region-name ref-id]} region-config])))
