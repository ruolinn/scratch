(ns scratch.events
  (:require [re-frame.core :as re-frame]
            [day8.re-frame.tracing :refer-macros [fn-traced]]
            [scratch.db :as db]))

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
