(ns scratch.polling
  (:require [scratch.clock :as clock]))

(def rules
  (vec
   (concat
    [{:interval 1
      :event [::clock/timer]
      :dipatch-event-on-start? true
      }])))
