(ns conjode.core-test
  (:require [clojure.test :refer :all]
            [conjode.core :refer :all :as c])
  )

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))))



(def customer-definition {:cache {:cache-attributes cache-attributes}})

(comment ((conjode.core/load-properties "/Users/smanvi/Workspace_clj/conjode/resources/geode-server.properties")))

(def geode-properties {})




;These are the attributes for the cache element
;http://gemfire.docs.pivotal.io/docs-gemfire/latest/reference/topics/cache_xml.html
(def cache-attributes {:copy-on-read          false
                       :is-server             false
                       :lock-timeout          60
                       :lock-lease            120
                       :message-sync-interval 1
                       :search-timeout        300})

