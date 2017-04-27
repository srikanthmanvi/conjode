(ns conjode.region
  (:require [conjode.util :as util :refer :all]
            [conjode.util :as u])
  (:import [org.apache.geode.cache.client ClientCache ClientCacheFactory Pool PoolManager ClientRegionFactory]
           [org.apache.geode.cache Region]))

(defn gget
  "Gets the value associated with the given key"
  [key region-name ^ClientCache geode-client]
  (let [region (.getRegion geode-client region-name)
        result (.get region key)]
    result))

(defn gput
  "Puts key-value into the given region"
  [key value region-name ^ClientCache geode-client]
  (let [region (.getRegion geode-client region-name)]
    (.put region key value)))

(defn gput-all
  "Puts a map of key-value pairs in a region"
  [m region-name ^ClientCache geode-client]
  (let [region (.getRegion geode-client region-name)]
    (.putAll region m)))

(defn get-region
  "Returns the instance of org.apache.geode.cache.Region for the
  given region-name."
  [region-name ^ClientCache geode-client]
  (.getRegion geode-client region-name))

(defn get-parent-region
  "Geode regions can be nested. This funtion returns the parent region
  for the given region. Return type is org.apache.geode.cache.Region.
  Argument can be a string or org.apache.geode.cache.Region"

  [region ^ClientCache geode-client]
  (cond (instance? org.apache.geode.cache.Region region)
        (.getParentRegion region)
        (string? region)
        (.getParentRegion (get-region region geode-client))
        :else
        (throw (IllegalArgumentException.))))

(defn create-client-region
  "Creates a region on the client side. The region exists in the client JVM
  and not on the members of the distributed system. The region-types can be
  one of
  :local :local-heap-lru :proxy :caching-proxy :caching-proxy-heap-lru"
  [region-name ^ClientCache geode-client client-region-type]
  (if (contains? (keys (u/client-region-types)) client-region-type)
    (let [^ClientRegionFactory client-region-factory
          (.createClientRegionFactory geode-client (get (u/client-region-types) client-region-type))]
      (.create client-region-factory region-name))
    (do (str "Invalid inputs to create region")
        :error)))



(defn get-full-path
  "Returns the full path of the given region. Ex: /Customer/USCustomer."
  [^Region region ^ClientCache geode-client]
  (.getFullPath region))

(defn get-region-attributes
  "Returns the region attributes for the given region"
  [^Region region ^ClientCache geode-client]
  (util/ensure-region region)
  (.getAttributes region))
