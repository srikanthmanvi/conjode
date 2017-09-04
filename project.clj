(defproject geode/conjode "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.apache.geode/geode-core "1.2.0"]
                 ;[com.gemstone.gemfire/gemfire "8.0.0"]
                 ]
  :test-selectors {:needs-server  :needs-server
                   :starts-server :starts-server
                   ;:default (complement [:needs-server :starts-server])}
                   :default       (fn [m] (not (or (:needs-server m) (:starts-server m))))}
  :java-source-paths ["test/org/conjode/java"]
  :plugins [[quickie "0.4.2"]])
