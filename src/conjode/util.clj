(ns conjode.util
  (:import [java.util Properties]
           [java.io FileInputStream]
           [org.apache.geode.cache.Region]
           (org.apache.geode.cache.client ClientRegionShortcut)))


;;; Static meta-data
(def client-region-types {:local                  ClientRegionShortcut/LOCAL
                          :proxy                  ClientRegionShortcut/PROXY
                          :caching-proxy          ClientRegionShortcut/CACHING_PROXY})

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
  "returns string if key is keyword."
  (if (keyword? key)
    (name key)
    key))
