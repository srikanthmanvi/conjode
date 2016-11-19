(ns conjode.util-test
  (:require
    [clojure.test :refer :all]
    [conjode.util :refer :all :as u]))

(def server-properties "/Users/smanvi/Workspace_clj/conjode/resources/geode-server.properties")
(deftest test-load

   (testing "Test load-properties"
        (not (nil? (u/load-properties server-properties)))))


