(ns conjode.util
  (:import [java.util Properties]
           [java.io FileInputStream]
           [org.apache.geode.cache.Region]))

;;Holds utility functions used in other namespaces

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
