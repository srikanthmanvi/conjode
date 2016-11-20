(ns conjode.core
  (:import
    (com.gemstone.gemfire.cache.client ClientCache ClientCacheFactory))
  (:require [conjode.helper :as helper]
            [conjode.util :as u]))

(defn client-cache
  "Returns a client cache configured using the passed cache xml file or the properties file"
  [client-cache-file]
  (cond (.endsWith client-cache-file ".properties")
    (.create (ClientCacheFactory. (u/read-properties-file client-cache-file)))
    (.endsWith client-cache-file ".xml")
    (let [factory (ClientCacheFactory.)]
      (do (.set "cache-xml-file" client-cache-file factory)
          (.create factory)))))

;(defn get-cache
;  "Returns com.gemstone.gemfire.cache.Cache, argument can be a cache.xml or a properties file"
;  [^String cache-config-file]
;  (cond (.endsWith cache-config-file ".properties")
;        (helper/cache-from-properties cache-config-file)
;        (.endsWith cache-config-file ".xml")
;        (helper/cache-from-xml cache-config-file)))

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
