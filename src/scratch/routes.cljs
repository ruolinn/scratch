(ns scratch.routes
  (:require
   [re-frame.core :as re-frame]
   [reitit.coercion.spec]
   [reitit.frontend]
   [reitit.frontend.easy :as rfe]
   [reitit.frontend.controllers :as rfc]
   [scratch.events :as events]))


(def routes
  ["/"
   [""
    {:name :routes/home
     :view dashboard/main
     :link-text "Home"
     :icon dashboard/drawer-icon
     :controllers
     [{:start (log-fn "Entering home page")
       :stop (log-fn "Leaving home page")}]}]])

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
