(ns conjode.util
  (:import (java.util Properties)))


(defn load-properties
  "Returns a map read from the given properties file"
  [file-name]
  (with-open [input-stream (clojure.java.io/input-stream file-name)]
    (let [properties (Properties.)]
      (do (.load properties input-stream)
          properties))))
