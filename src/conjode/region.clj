(ns conjode.region
  (:require [conjode.util :as util :refer :all])
  (:import [org.apache.geode.cache.client ClientCache ClientCacheFactory Pool PoolManager ClientRegionFactory]
           [org.apache.geode.cache Region]))

(defn size

  "Returns the number of entries present in this region. For DataPolicy.PARTITION,
  this is a distributed operation that returns the number of entries present in entire region.
  For all other types of regions, it returns the number of entries present locally, and it is not a distributed operation.
  PROXY regions will return 0."
  [region-name ^ClientCache geode-client]
  (let [^Region region (.getRegion geode-client region-name)]
    (.size region)))


(defn gget
  "Gets the value associated with the given key. Key can be a keyword or a literal"
  [key region-name ^ClientCache geode-client]
  (let [region (.getRegion geode-client region-name)
        result (.get region (util/unkeyword key))]
    result))

(defn gput
  "Puts key-value into the given region. Key can be a keyword or a literal."
  [key value region-name ^ClientCache geode-client]
  (let [region (.getRegion geode-client region-name)]
    (.put region (util/unkeyword key) value)))

;todo: Handle keywords
(defn gput-all
  "Puts a map of key-value pairs in a region"
  [m region-name ^ClientCache geode-client]
  (let [region (.getRegion geode-client region-name)]
    (.putAll region m)))

;todo:
(defn ggetAll
  "Gets the values for all the keys in the collection"
  [keys]
  true)

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
  (cond (instance? Region region)
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
  (if (contains? (keys (util/client-region-types)) client-region-type)
    (let [^ClientRegionFactory client-region-factory
          (.createClientRegionFactory geode-client (get (util/client-region-types) client-region-type))]
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
