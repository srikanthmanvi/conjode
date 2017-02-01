(ns conjode.util
  (:import (java.util Properties)
           (java.io FileInputStream)))

;This namespace has functions that have nothing to do with Geode classes

(defn read-properties-file
  "Returns java.util.Properties by reading a properties file"
  [path-to-file]
  (with-open [input-stream (FileInputStream. path-to-file)]
    (let [^Properties properties (Properties.)]
      (.load properties input-stream)
      properties)))
