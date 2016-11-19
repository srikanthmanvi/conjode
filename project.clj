(defproject conjode "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [com.gemstone.gemfire/gemfire "8.2.0"]
                 [com.sm/common "1.0-SNAPSHOT"]
                 [org.msync/properties-clj "0.3.0"]]
  :repositories  {"gemstone-releases" "http://repo.spring.io/gemstone-release-cache/"}
  :plugins [[quickie "0.4.2"]])
