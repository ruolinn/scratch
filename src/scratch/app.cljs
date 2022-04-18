(ns scratch.app
  (:require
   [re-frame.core :as re-frame]
   [reagent.core :as reagent :refer [atom]]
   [reagent-mui.colors :as colors]
   [reagent-mui.styles :as styles :refer [styled]]
   [scratch.util :as util]
   [scratch.components.dashboard :refer [dashboard]]
   [scratch.routes :as routes]
   [scratch.subs :as subs]))

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

(defn app* [{:keys [class-name]}]
  [styles/theme-provider theme
   [dashboard {:router routes/router :current-route @(re-frame/subscribe [::subs/current-route])}]])

(def app (styled app* styles))
