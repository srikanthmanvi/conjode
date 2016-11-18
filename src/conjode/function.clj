(ns conjode.function
  (:import (com.gemstone.gemfire.cache.client ClientCache)))

;Execution execution1 = FunctionService.onRegion(clientCache.getRegion(""));

(comment
  (defn run-function-on-region
    "Runs the given function on the given region using the given geode client"
    [function-id region-name ^geode-client]))
