(ns conjode.core-test
  (:require [clojure.test :refer :all]
            [conjode.core :refer :all :as c])
  )

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))))


(def )
(def customer-definition {:cache {:cache-attributes cache-attributes}})

(comment ((conjode.core/load-properties "/Users/smanvi/Workspace_clj/conjode/resources/geode-server.properties")))

(def sample-region-config {:region-name "TradeRegion"
                           :ref-id :caching-proxy
                           })

(c/create-region sample-region-config
                 (c/client-cache-from-xml "client-cache.xml"))



