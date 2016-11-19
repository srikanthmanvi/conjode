(ns conjode.helper
  (:import (com.gemstone.gemfire.cache CacheFactory)
           (com.gemstone.gemfire.cache.client ClientCacheFactory)))


(defn cache-from-xml
  "Returns a new com.gemstone.gemfire.cache.Cache created using the passed cache.xml."
  [server-cache-xml]
  (let [factory     (doto (CacheFactory.) (.set "cache-xml-file" server-cache-xml))
        geode-cache (.create factory)] geode-cache))

(defn client-cache-from-xml
  "Returns a client cache configured using the passed cache xml file"
  [client-cache-xml]
  (let [factory      (doto (ClientCacheFactory.) (.set "cache-xml-file" client-cache-xml))
        geode-client (.create factory)]
    geode-client))

(defn cache-from-properties [config-file] :to-do)
(defn client-cache-from-properties [config-file] :to-do)