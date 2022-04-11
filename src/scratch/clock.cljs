(ns scratch.clock
  (:require [re-frame.core :as re-frame]))

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




