(ns scratch.components.main
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [scratch.util :as util]
   [reagent-mui.material.container :refer [container]]))

(def classes
  (util/make-classes "scratch-main"
                     [:icon
                      :hero-content
                      :paper
                      :footer]))
(defn showcase* [{:keys [class-name]}]
  (let []
    [:<>
     [:div {:class (:hero-content classes)}
      [container {:max-width "sm"}
       [typography ]]]]))
