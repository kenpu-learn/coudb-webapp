(defproject myapp "0.1.0"
  :description "Frontend for learning pouchdb"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
                 [org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.946"]
                 [keechma "0.3.1"]
                 [reagent "0.7.0"]
                 [funcool/promesa "1.5.0"]
                ]
  :plugins [[lein-cljsbuild "1.1.7"]
            [lein-less "1.7.5"]
            [lein-figwheel "0.5.14"]]

  :min-lein-version "2.5.0"

  :clean-targets [[:cljsbuild :builds 0 :compiler :output-dir]
                  [:cljsbuild :builds 0 :compiler :output-to]
                  [:less :target-path]]

  :resource-paths ["resources" "public"]
  :less {:source-paths ["less"]
         :target-path "public/css"}
  :figwheel {:http-server-root "."
             :server-port 8000
             :nrepl-port 7002
             :nrepl-middleware ["cemerick.piggieback/wrap-cljs-repl"]
             :css-dirs ["public/css"]}
  :cljsbuild {:builds [{:id "app"
                        :source-paths ["src"]
                        :compiler {:main "myapp.core"
                                   :output-to "public/js/app.js"
                                   :output-dir "public/js/out"
                                   :asset-path "js/out"
                                   :source-map true
                                   :optimizations :none
                                   :pretty-print true}
                        :figwheel {:on-jsload "myapp.core/reload"}
                        }]}
  :profiles {:dev {:dependencies [[org.clojure/tools.nrepl "0.2.13"]
                                  [com.cemerick/piggieback "0.2.2"]]}}

  )
