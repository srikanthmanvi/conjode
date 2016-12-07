(ns conjode.core
  (:import
    (com.gemstone.gemfire.cache.client ClientCache ClientCacheFactory)
    (com.gemstone.gemfire.cache CacheFactory))
  (:require [conjode.util :as u]))

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
  "Returns the region from the distributed system"
  [region-name ^ClientCache client-cache]
  (.getRegion client-cache region-name))

(comment
  (defn create-region
    "Creates a region based on the map provided"
    [region-config ^ClientCache client-cache]
    (let [{:keys [region-name ref-id]} region-config])))
