(ns conjode.core
  (:require [conjode.util :as u])
  (:import [org.apache.geode.cache.client ClientCache ClientCacheFactory Pool PoolManager]))

(defn get-client-cache
  "Returns a client cache, configured using the passed cache xml file or the properties file"
  [client-cache-file]
  (cond (.endsWith client-cache-file ".properties")
        (.create (ClientCacheFactory. (u/read-properties-file client-cache-file)))
        (.endsWith client-cache-file ".xml")
        (let [factory (ClientCacheFactory.)]
          (do (.set factory "cache-xml-file" client-cache-file)
              (.create factory)))))
