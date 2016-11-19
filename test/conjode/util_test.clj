(ns conjode.util-test
  (:require
    [clojure.test :refer :all]
    [conjode.util :refer :all :as u]))

(deftest util_test

   (testing "Test load-properties"
        (not (nil? (u/load-properties "/sers/smanvi/Workspace_clj/conjode/resources/geode-server.properties")))))


