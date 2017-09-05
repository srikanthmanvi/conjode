(ns conjode.function-test
    (:require [clojure.test :refer :all])
  (:require [conjode.function :refer [execute-function-on-server]]
            [conjode.core :as core]
            [conjode.function :as f]))


(defn once-fixture
  "Setup and Tear down which run once per namespace"
  [f]
  ;(core/connect)
  (f)
  (core/close-client))


(use-fixtures :each once-fixture)


(deftest execute-function-on-server-test

  (testing "Function with results"
           (let [test-client (core/connect)
                 function-id "TestFunction"
                 expected ["Hello" " world." " Have a good day!" "true"]
                 result (f/execute-function-on-server function-id test-client)]
             (is (= result expected)))))
