(ns scratch.subs
  (:require [re-frame.core :as re-frame]
            [clojure.set :refer [intersection]]
            [clojure.string :as str]))

(re-frame/reg-sub
 ::drawer-open?
 (fn [db _]
   (js/console.log "fdsf")
   (:drawer-open? db)))
