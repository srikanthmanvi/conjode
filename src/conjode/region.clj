(ns conjode.region
  (:require [conjode.util :as util :refer :all]
            [conjode.core :as c :refer :all])
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
  and not on the servers of the distributed system, unless the same region exists on the server side and
  the type of the client region being created is PROXY or CACHING_PROXY. The region-types can be
  one of below

  :local, only has local state and never sends operations to a server.
  :proxy, has no local state and forwards all operations to a server.
  :caching-proxy, has local state but can also send operations to a server. "

  ([region-name ^ClientCache geode-client region-type]
   (if (contains? util/client-region-types (keyword region-type))
     (let [^ClientRegionFactory client-region-factory
           (.createClientRegionFactory geode-client ((keyword region-type) util/client-region-types))]
       (.create client-region-factory region-name))
     (do (str "Invalid region-type. Only :local :proxy :caching-proxy are supported")
         :error)))

  ([region-name region-type]
   (create-client-region region-name (c/connection) region-type)))


(defn get-region-attributes
  "Returns the region attributes for the given region"
  [^Region region ^ClientCache geode-client]
  (util/ensure-region region)
  (.getAttributes region))
