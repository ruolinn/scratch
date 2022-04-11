(ns scratch.dash
  (:require [re-frame.core :as re-frame]
            [reagent-mui.material.card :refer [card]]
            [reagent-mui.material.card-content :refer [card-content]]
            [reagent-mui.material.typography :refer [typography]]
            [reagent-mui.material.css-baseline :refer [css-baseline]]
            [reagent-mui.material.container :refer [container]]
            [reagent-mui.material.grid :refer [grid]]
            [reagent-mui.material.button :refer [button]]))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   {:clock (js/Date.)}))


(re-frame/reg-event-db
 ::timer
 (fn [db [_ _2]]
   (assoc db :clock (js/Date.))))

(defn db->date-sub
  [format-map]
  (fn [clock _]
    (.toLocaleTimeString clock [] (clj->js format-map))))

(re-frame/reg-sub
 ::clock
 (fn [db _]
   (:clock db)))

(re-frame/reg-sub
 ::day
 :<- [::clock]
 (fn [clock _]
   (.toLocaleDateString
    clock
    [] #js {:weekday "long" :month "long" :day "numeric"})))

(re-frame/reg-sub
 ::time
 :<- [::clock]
 (db->date-sub {:hour "numeric" :minute "numeric" :hour12 true}))


(defn clock []
  [card
   [card-content
    [typography {:align "center" :variant "h4"}
     @(re-frame/subscribe [::day])]
    [typography {:align "center" :variant "h1"}
     @(re-frame/subscribe [::time])]]])

(defn main-panel []
  (let [card-opts {:item true :xs 12 :sm 12 :md 6  :lg 4}]
    [css-baseline
     [container {:maxWidth false}
      [grid {:container true :spacing 1}
       ;; [grid card-opts [weather]]
       [grid card-opts [clock]]
       ;; [grid card-opts [transit]]
       ;; [grid card-opts [stocks]]
       ]]]))
