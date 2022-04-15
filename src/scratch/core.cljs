(ns ^:figwheel-hooks scratch.core
  (:require [reagent.core :as r]
            [reagent.dom :as d]
            [re-frame.core :as rf]
            ;;[ajax.core :refer [GET POST]]
            ;;[re-com.core :as re-com :refer [at]]
            ;;[re-pollsive.core :as poll]
            [scratch.events :as events]
            ;;[scratch.polling :as poll-config]
            ;;[scratch.views :as views]
            [scratch.app :refer [app]]))

(defn dev-setup []
  (when goog.DEBUG
    (enable-console-print!)
    (println "dev mode")))

(defn mount []
  (rf/clear-subscription-cache!)
  (let [root (.getElementById js/document "app")]
    (rf/dispatch-sync [::events/initialize-db])
    ;; (rf/dispatch-sync [::poll/init])
    ;; (rf/dispatch [::poll/set-rules poll-config/rules])
    (dev-setup)
    (d/unmount-component-at-node root)
    (d/render [app] root)))


(defn ^:after-load re-render []
  (mount))

(defonce start-up (do (mount) true))
