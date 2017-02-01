(defproject conjode "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.apache.geode/geode-core "1.0.0-incubating"]
                 ;; [com.gemstone.gemfire/gemfire "8.2.0"]
                 [org.msync/properties-clj "0.3.0"]
                 [org.eclipse.jetty/jetty-server "9.2.13.v20150730"]
                 [org.eclipse.jetty/jetty-webapp "9.2.13.v20150730"]]
  :repositories {"gemstone-releases" "http://repo.spring.io/gemstone-release-cache/"}
  :test-selectors {:needs-server  :needs-server
                   :starts-server :starts-server
                                        ;:default (complement [:needs-server :starts-server])}
                   :default       (fn [m] (not (or (:needs-server m) (:starts-server m))))}
  :java-source-paths ["test/org/conjode/java"]
  :plugins [[quickie "0.4.2"]
            [cider/cider-nrepl "0.15.0-snapshot"]])
