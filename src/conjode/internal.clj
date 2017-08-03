(ns conjode.internal
  (:import (org.apache.geode.cache.client ClientCache)))

(defn region-from-name
  "Gives a org.apache.geode.cache for a given region-name"
  [region-name ^ClientCache geode-client]
  (.getRegion geode-client region-name))
