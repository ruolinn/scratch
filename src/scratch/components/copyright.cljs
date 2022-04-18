(ns scratch.components.copyright
  (:require
   [reagent-mui.material.typography :refer [typography]]
   [reagent-mui.material.link :refer [link]]))

(defn copyright []
  [typography {:variant "body2" :color "text-secondary" :align "center"}
   "Copyright ©"
   [link {:color "inherit" :href "https://material-ui.com"}
    "Your Website"]
   (.getFullYear (js/Date.))])
