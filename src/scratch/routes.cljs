(ns scratch.routes
  (:require
   [re-frame.core :as re-frame]
   [reitit.coercion.spec]
   [reitit.frontend]
   [reitit.frontend.easy :as rfe]
   [reitit.frontend.controllers :as rfc]
   [scratch.events :as events]
   [reagent-mui.icons.home :refer [home]]
   [reagent-mui.icons.art-track :refer [art-track]]
   [scratch.views.dashboard :as dashboard]))

(defn main []
  [:div
   [:h1 "main"]])

(defn drawer-icon []
  [home])

(defn component-demo []
  [:div
   [:h1 "components"]])
(defn component-demo-icon []
  [art-track])

(defn log-fn [& args]
  (fn [& _] (apply js/console.log args)))

(def routes
  ["/"
   [""
    {:name "home"
     :view dashboard/main
     :link-text "Home"
     :icon drawer-icon
     :controllers
     [{:start (log-fn "Entering home page")
       :stop (log-fn "Leaving home page")}]}]
   ["components"
    {:name "components"
     :view component-demo
     :link-text "Components"
     :icon component-demo-icon
     :controllers
     [{:start (log-fn "Entering componments")
       :stop (log-fn "Leaving components")}]
     }]])

(def router
  (reitit.frontend/router
   routes
   {:data {:coercion reitit.coercion.spec/coercion}}))

(defn on-navigate [new-match]
  (when new-match
    (re-frame/dispatch [::events/navigated new-match])))

(defn init-routes! []
  (js/console.log "initializing routes")
  (rfe/start!
   router
   on-navigate
   {:user-fragment true}))
