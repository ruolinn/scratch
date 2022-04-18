(ns scratch.subs
  (:require [re-frame.core :as re-frame]
            [clojure.set :refer [intersection]]
            [clojure.string :as str]))

(re-frame/reg-sub
 ::drawer-open?
 (fn [db _]
   (:drawer-open? db)))

(re-frame/reg-sub
 ::current-route
 (fn [db]
   (:current-route db)))
