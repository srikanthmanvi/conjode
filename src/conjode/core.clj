(ns conjode.core
  (:import
    (com.gemstone.gemfire.cache.client ClientCache))
  (:require [msync.properties.core :as properties-reader]
            [conjode.helper :as helper]))


(defn get-client-cache
  "Returns com.gemstone.gemfire.cache.client ClientCache, argument can be a cache.xml or a properties file"
  [^String cache-config-file]
  (cond (.endsWith cache-config-file ".properties")
        (helper/client-cache-from-properties cache-config-file)
        (.endsWith cache-config-file ".xml")
        (helper/client-cache-from-xml cache-config-file)))


(defn get-cache
  "Returns com.gemstone.gemfire.cache.Cache, argument can be a cache.xml or a properties file"
  [^String cache-config-file]
  (cond (.endsWith cache-config-file ".properties")
        (helper/cache-from-properties cache-config-file)
        (.endsWith cache-config-file ".xml")
        (helper/cache-from-xml cache-config-file)))

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
