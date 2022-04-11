(ns scratch.views
  (:require [re-frame.core :as re-frame]
            [reagent-mui.material.card :refer [card]]
            [reagent-mui.material.card-content :refer [card-content]]
            [reagent-mui.material.typography :refer [typography]]
            [reagent-mui.material.css-baseline :refer [css-baseline]]
            [reagent-mui.material.container :refer [container]]
            [reagent-mui.material.grid :refer [grid]]
            [reagent-mui.material.button :refer [button]]
            [scratch.clock :as clock]))

(defn clock []
  [card
   [card-content
    [typography {:align "center" :variant "h4"}
     @(re-frame/subscribe [::clock/day])]
    [typography {:align "center" :variant "h1"}
     @(re-frame/subscribe [::clock/time])]]])


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
