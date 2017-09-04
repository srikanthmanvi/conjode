(ns conjode.query
  (:require [conjode.util :as util :refer :all]
            [conjode.core :as c :refer :all]
            [conjode.internal :as i :refer :all])
  (:import [org.apache.geode.cache.client ClientCache ClientCacheFactory]
           [org.apache.geode.cache Region]
           (org.apache.geode.cache.query QueryService Query)))


(defn execute-query
  "Executes the given query, returns a list of results."
  ([query-str ^ClientCache geode-client]
   (let [^QueryService query-service (.getQueryService geode-client)
         ^Query query (.newQuery query-service query-str)]
     (.asList (.execute query))))

  ([query-str query-params ^ClientCache geode-client]
   (let [^QueryService query-service (.getQueryService geode-client)
         ^Query query (.newQuery query-service query-str)]
     (.asList (.execute query (object-array query-params))))))
