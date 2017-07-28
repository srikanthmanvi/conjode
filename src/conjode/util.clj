(ns conjode.util
  (:import [java.util Properties]
           [java.io FileInputStream]
           [org.apache.geode.cache.Region]
           (org.apache.geode.cache.client ClientRegionShortcut)))

;;Holds utility functions used in other namespaces
;
(def client-region-types {:local                  ClientRegionShortcut/LOCAL
                          :local-heap-lru         ClientRegionShortcut/LOCAL_HEAP_LRU
                          :proxy                  ClientRegionShortcut/PROXY
                          :caching-proxy          ClientRegionShortcut/CACHING_PROXY
                          :caching-proxy-heap-lru ClientRegionShortcut/CACHING_PROXY_HEAP_LRU})

(defn read-properties-file
  "Returns java.util.Properties by reading a properties file"
  [path-to-file]
  (with-open [input-stream (FileInputStream. path-to-file)]
    (let [^Properties properties (Properties.)]
      (.load properties input-stream)
      properties)))

(defn ensure-region
  "Checks if the given argument is a Region. If not throws Illegalargumentexception"
  [region]
  (if (instance? org.apache.geode.cache.Region region)
    true
    (throw (IllegalArgumentException.
             (str "Passed argument " region " is not of type org.apache.geode.cache.Region")))))

(defn unkeyword [key]
  "If the key is a keyword, returns a string otherwise returns key"
  (if (keyword? key)
    (name key)
    key))
