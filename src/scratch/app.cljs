(ns scratch.app
  (:require
   [reagent.core :as reagent :refer [atom]]
   [reagent-mui.colors :as colors]
   [reagent-mui.styles :as styles :refer [styled]]
   [scratch.util :as util]
   [scratch.components.dashboard :refer [dashboard]]))

(def classes
  (util/make-classes "scratch-app"
                     [:root
                      :welcome]))

(defn styles [{{:keys [spacing]} :theme}]
  (util/set-classes
   classes
   {}))

(def theme (styles/create-theme
            {:palette {:primary   colors/blue
                       :secondary {:main (:A700 colors/red)}
                       :spotify   "#1db954"}}))

(defn app* [{:keys [class-name router current-route]}]
  [styles/theme-provider theme
   [dashboard {:router router :current-route current-route}]])

(def app (styled app* styles))
