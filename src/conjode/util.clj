(ns conjode.util
  (:import (java.util Properties)))


(defn load-properties
  "Returns a map read from the given properties file"
  [file-name]
  (with-open [input-stream (clojure.java.io/input-stream file-name)]
    (let [properties (Properties.)]
      (do (.load properties input-stream)
          properties))))

(def client-region-shortcuts {:caching-proxy "CACHING_PROXY"
                              :caching-proxy-heap-lru "CACHING_PROXY_HEAP_LRU"
                              :caching-proxy-overflow "CACHING_PROXY_OVERFLOW"
                              :local "LOCAL"
                              :local-heap-lru "LOCAL_HEAP_LRU"
                              :local-overflow "LOCAL_OVERFLOW"
                              :local-persistent "LOCAL_PERSISTENT"
                              :local-persistent-overflow "LOCAL_PERSISTENT_OVERFLOW"
                              :proxy "PROXY"})