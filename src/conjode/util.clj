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

(def client-region-shortcuts {:caching-proxy "CACHING_PROXY"
                              :caching-proxy-heap-lru "CACHING_PROXY_HEAP_LRU"
                              :caching-proxy-overflow "CACHING_PROXY_OVERFLOW"
                              :local "LOCAL"
                              :local-heap-lru "LOCAL_HEAP_LRU"
                              :local-overflow "LOCAL_OVERFLOW"
                              :local-persistent "LOCAL_PERSISTENT"
                              :local-persistent-overflow "LOCAL_PERSISTENT_OVERFLOW"
                              :proxy "PROXY"})
