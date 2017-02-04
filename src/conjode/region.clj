(ns conjode.region
  (:require [conjode.util :as util :refer :all])
  (:import [org.apache.geode.cache.client ClientCache ClientCacheFactory Pool PoolManager]
           [org.apache.geode.cache.execute Execution FunctionService]
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
  (cond (instance? ^Region region)
        (.getParentRegion region)
        (string? region)
        (.getParentRegion (get-region region geode-client))
        :else
        (throw (IllegalArgumentException.))))

(defn get-full-path
  "Returns the full path of the given region. Ex: /Customer/USCustomer."
  [^Region region ^ClientCache geode-client]
  (.getFullPath region))

(defn get-region-attributes
  "Returns the region attributes for the given region"
  [^Region region ^ClientCache geode-client]
  (util/ensure-region region)
  (.getAttributes region))
