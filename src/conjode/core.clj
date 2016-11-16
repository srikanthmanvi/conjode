(ns conjode.core
  (:import
    (com.gemstone.gemfire.cache.client ClientCache)
    (com.gemstone.gemfire.cache.client ClientCacheFactory))
  (:require [msync.properties.core :as properties-reader]))

(defn client-cache-from-xml
  "Returns a client cache configured using the passed cache xml file"
  [client-cache-xml]
  (let [factory (doto (ClientCacheFactory.) (.set "cache-xml-file" client-cache-xml))
        geode-client (.create factory)]
    geode-client))

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

(comment
  (defn create-region
    "Creates a region based on the map provided"
    [region-config]
    (let [region-name (:region-name region-config)])))
