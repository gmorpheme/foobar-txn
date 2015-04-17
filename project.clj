(defproject foobar-txn "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0-alpha5"]
                 [liberator "0.12.2"]
                 [com.stuartsierra/component "0.2.2"]
                 [org.clojure/tools.logging "0.3.1"]
                 [bidi "1.18.1"]
                 [environ "1.0.0"]
                 [ring/ring-core "1.3.2"]
                 [ring/ring-jetty-adapter "1.3.2"]
                 [gorilla-repl "0.3.4"]
                 [hashobject/hashids "0.2.0"]]

  :main foobar-txn.core)
