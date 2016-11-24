(ns conjode.util-test
  (:require
    [clojure.test :refer :all]
    [conjode.util :refer :all :as u])
  (:import (java.util Properties)))

(def server-properties-file "resources/geode-server.properties")

(deftest test-read-properties-file

  (testing "Test Load Properties from file"
    (let [^Properties result (u/read-properties-file server-properties-file)]
      (is (> (.size result) 1)))))
