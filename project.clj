(defproject scratch "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :aliases {"fig" ["trampoline" "run" "-m" "figwheel.main" "-b" "dev"]}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [reagent "1.1.1"]
                 [cljsjs/react "17.0.2-0"]
                 [cljsjs/react-dom "17.0.2-0"]
                 [re-frame "1.3.0-rc3"]
                 [day8.re-frame/http-fx "v0.2.0"]
                 [day8.re-frame/tracing "0.5.5"]
                 [cljs-ajax "0.8.4"]
                 [re-com "2.13.2"]
                 [hiccup "1.0.5"]
                 [re-pollsive "0.1.0"]
                 [arttuka/reagent-material-ui "5.5.0-0"]]
  :source-paths ["src"]
  :plugins []
  :profiles {:dev {
                   :dependencies [[org.clojure/clojurescript "1.10.773"]
                                  [com.bhauman/figwheel-main "0.2.16"]
                                  ;; optional but recommended
                                  [com.bhauman/rebel-readline-cljs "0.1.4"]
                                  [binaryage/devtools "1.0.3"]]
                   :resource-paths ["target"]
                   :clean-targets ^{:protect false} ["target"]
                   :compiler {:preloads [devtools.preload]
                              :external-config {:devtools/config {:feature-to-install [:fomatters :hits]
                                                                  :fn-symbol "F"
                                                                  :print-config-overrides true
                                                                  }}
                              }
                   }})
