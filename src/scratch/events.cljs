(ns scratch.events
  (:require [re-frame.core :as re-frame]
            [day8.re-frame.tracing :refer-macros [fn-traced]]
            [scratch.db :as db]
            [reitit.frontend.easy :as rfe]
            [reitit.frontend.controllers :as rfc]))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
            db/default-db))

(re-frame/reg-event-db
 ::drawer-open
 (fn [db _]
   (assoc db :drawer-open? true)))

(re-frame/reg-event-db
 ::drawer-close
 (fn [db _]
   (assoc db :drawer-open? false)))

(re-frame/reg-event-fx
 ::navigate
 (fn [_cofx [_ & route]]
   {::navigate! route}))

(re-frame/reg-fx
 ::navigate!
 (fn [route]
   (apply rfe/push-state route)))

(re-frame/reg-event-db
 ::navigated
 (fn [db [_ new-match]]
   (let [old-match   (:current-route db)
         controllers (rfc/apply-controllers (:controllers old-match) new-match)]
     (assoc db :current-route (assoc new-match :controllers controllers)))))
