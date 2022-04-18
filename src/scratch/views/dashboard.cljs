(ns scratch.views.dashboard
  (:require
   [reagent-mui.material.container :refer [container]]
   [reagent-mui.material.grid :refer [grid]]
   [reagent-mui.material.paper :refer [paper]]
   [reagent-mui.material.typography :refer [typography]]
   [reagent-mui.material.table :refer [table]]
   [reagent-mui.material.table-row :refer [table-row]]
   [reagent-mui.material.table-head :refer [table-head]]
   [reagent-mui.material.table-cell :refer [table-cell]]
   [reagent-mui.material.table-body :refer [table-body]]
   [reagent-mui.material.box :refer [box]]
   [scratch.components.copyright :refer [copyright]]))

(defn orders [{:keys [classes]}]
  (let [items [{:name "Nedved" :id 1}]]
    [:<>
     [typography {:component "h2" :variant "h6" :color "primary" :gutter-botton true}
      "Your Items"]
     [table {:size "small"}
      [table-head
       [table-row
        [table-cell "名称"]]]
      [table-body
       (for [item items]
         ^{:key (:id item)} [table-row
                             [table-cell (:name item)]])]]]))


(defn main [{:keys [classes]}]
  [container {:max-width "lg" :class (:container classes)}
   [grid {:container true :spacing 3}
    [grid {:item true :xs 12}
     [paper {:class (:paper classes)}
      [orders {:classes classes}]]]]
   [grid {:container true :spacing 3}]

   [box {:pt 4}
    [copyright]]])
