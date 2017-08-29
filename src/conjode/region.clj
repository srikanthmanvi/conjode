(ns conjode.region
  (:require [conjode.util :as util :refer :all]
            [conjode.core :as c :refer :all]
            [conjode.internal :as i :refer :all])
  (:import [org.apache.geode.cache.client ClientCache ClientCacheFactory Pool PoolManager ClientRegionFactory]
           [org.apache.geode.cache Region]))

(defn size

  "Returns the number of entries present in this region. For DataPolicy.PARTITION,
  this is a distributed operation that returns the number of entries present in entire region.
  For all other types of regions, it returns the number of entries present locally, and it is not a distributed operation.
  PROXY regions will return 0 as no entries are stored locally on the client."

  ([^Region region]
   (.size region))

  ([region-name ^ClientCache geode-client]
   (let [^Region region (.getRegion geode-client region-name)]
     (if (nil? region)
       {:error (str "Region " region-name " does not exist")}
       (size region)))))

(defn gget
  "Gets the value associated with the given key. Key can be a keyword or a literal"
  ([key region-name ^ClientCache geode-client]
   ;(gget (util/unkeyword key) (.getRegion geode-client region-name)))
   (gget key (.getRegion geode-client region-name)))

  ([key ^Region region]
    (if (nil? region)
      {:error (str "Region not found")}
      ;(.get region (util/unkeyword key)))))
      (.get region key))))

(defn gput
  "Puts key-value into the given region. Key can keywords, objects or literals."
  ([key value region-name ^ClientCache geode-client]
   (gput key value (.getRegion geode-client region-name)))

  ([key value ^Region region]
   (if (nil? region)
     {:error (str "Region not found")}
     ;(.put region (util/unkeyword key) value))))
     (.put region key value))))

(defn gput-all
  "Puts a map of key-value pairs in a region"

  ([m ^Region region]
   (.putAll region m))

  ([m region-name ^ClientCache geode-client]
   (gput-all m (.getRegion geode-client region-name))))

(defn ggetAll
  "Gets the values for all the keys in the collection"
  ([keys ^Region region]
    (.getAll region keys)))

(defn get-region
  "Returns the instance of org.apache.geode.cache.Region for the
  given region-name."
  [region-name ^ClientCache geode-client]
  (.getRegion geode-client region-name))

(defn get-parent-region
  "Geode regions can be nested. This function returns the parent region
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

  ([region-name region-type ^ClientCache geode-client]
   (if (contains? util/client-region-types (keyword region-type))
     (let [^ClientRegionFactory client-region-factory
           (.createClientRegionFactory geode-client ((keyword region-type) util/client-region-types))]
       (.create client-region-factory region-name))
     {:error (str "Invalid region-type. Only :local :proxy :caching-proxy are supported")}))

  ([region-name region-type]
   (create-client-region region-name @c/connection region-type)))

(defn destroy-client-region

  "Destroys the whole region. Destroy cascades to all entries and subregions.
   After the destroy, this region object can not be used any more and any attempt
   to use this region object will get RegionDestroyedException.
   The region destroy will be distributed to other caches if the scope is not Scope.LOCAL.
   Applicable to both server and client side regions."

  [region-name ^ClientCache geode-client]
  (let [^Region region (i/region-from-name region-name geode-client)]
    (.destroyRegion region)))

(defn clear-region
  "Removes all entries from this region.
  Clear will be distributed to other caches if the scope is not Scope.LOCAL.
  Cannot be called to clear server side regions."
  [region-name ^ClientCache geode-client]
  (let [^Region region (i/region-from-name region-name geode-client)]
    (if (nil? region)
      {:error (str "Region not found")}
      (.clear region))))

(defn empty-region?
  "returns true if the region is empty, false otherwise"
  ([region-name ^ClientCache geode-client]
   (if-let [^Region region (i/region-from-name region-name geode-client)]
     (.isEmpty region)
     {:error (str "Invalid region " region-name)}))

  ([^Region region]
    (if (nil? region)
      {:error (str "Region is nil")}
      (.isEmpty region))))

(defn values
  "values in the client side region. Returns no records for :proxy region"
  ([^Region region]
    (.values region))

  ([region-name ^ClientCache geode-client]
    (values (get-region region-name geode-client))))

