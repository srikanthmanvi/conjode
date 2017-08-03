(ns conjode.core
  (:require [conjode.util :as u])
  (:import (org.apache.geode.cache.client ClientCacheFactory ClientCache)))


;connection atom will hold a geode connection. call the function connect to initiate a connection. The connection will be
;stored in connection.
(def connection (atom nil))

(defn get-client-cache
  "Returns a client cache, configured using the passed cache xml file or the properties file"
  [client-cache-file]
  (cond (.endsWith client-cache-file ".properties")
        (.create (ClientCacheFactory. (u/read-properties-file client-cache-file)))
        (.endsWith client-cache-file ".xml")
        (let [factory (ClientCacheFactory.)]
          (do (.set factory "cache-xml-file" client-cache-file)
              (.create factory)))))

(defn connect
  "Connects to a geode cluster. Connection can be retrieved by referring to the atom conjode.core/connection
  until conjode.core/close-client is called.
  client-cache-file can a geode cache.xml file or geode.properties file.
  If no parameter is passed, then the clients connects to the locator running on default localhost[10344]"

  ([client-cache-file]
   (reset! connection (get-client-cache client-cache-file)))

  ([] (connect "resources/geode-client.properties")))

(defn close-client
  "close the given client cache. when none provided, closes the default client cache"
  ([^ClientCache connection]
   (.close connection))
  ([] (.close @connection)))
