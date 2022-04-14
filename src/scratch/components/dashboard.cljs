(ns scratch.components.dashboard
  (:require
   [re-frame.core :as rf]
   [reagent.core :as reagent :refer [atom with-let]]
   [scratch.util :as util]
   [reagent-mui.styles :as styles :refer [styled]]
   [reagent-mui.material.app-bar :refer [app-bar]]
   [reagent-mui.material.toolbar :refer [toolbar]]
   [reagent-mui.material.icon-button :refer [icon-button]]
   [reagent-mui.material.typography :refer [typography]]
   [reagent-mui.material.drawer :refer [drawer]]
   [reagent-mui.material.divider :refer [divider]]
   [reagent-mui.material.list :refer [list]]
   [reagent-mui.material.list-item :refer [list-item]]
   [reagent-mui.icons.menu :refer [menu]]
   [reagent-mui.icons.chevron-left :refer [chevron-left]]
   [reagent-mui.material.css-baseline :refer [css-baseline]]))

;;; Subs
(rf/reg-sub
 :drawer/open?
 (fn [db]
   (js/console.log db)
   (:drawer/open? db)))

;;; Events

(rf/reg-event-db
 :drawer/open
 (fn [db _]
   (assoc db :drawer/open? true)))

(rf/reg-event-db
 :drawer/close
 (fn [db _]
   (assoc db :drawer/open? false)
   (js/console.log (:drawer/open? db))))

(def drawer-width 240)

(def classes
  (util/make-classes "scratch-dashboard"
                     [:root
                      :toolbar-icon
                      :app-bar
                      :app-bar-shift
                      :menu-button
                      :menu-button-hidden
                      :drawer-paper
                      :drawer-paper-close
                      :title
                      ]))

(defn styles [{:keys [theme]}]
  (let [spacing (:spacing theme)
        on-desktop (util/on-desktop theme)]
    (util/set-classes
     classes
     {:root {:display "flex"}
      :toolbar-icon (merge {:display "flex"
                            :align-items "center"
                            :justify-content "flex-end"
                            :padding "0 8px"}
                           (get-in theme [:mixins] "toobal"))
      :app-bar {:z-index (js/console.log "eee")}
      :app-bar-shift {:margin-left drawer-width
                      :width (str "calc(100% - " drawer-width "px)")}
      :menu-button {:margin-right 36}
      :menu-button-hidden {:display "none"}
      :drawer-paper {:position "relative"
                     :white-space "nowrap"
                     :width drawer-width}
      :drawer-paper-close {:overflow-x "hidden"
                           :width (spacing 7)
                           on-desktop {:width (spacing 9)}}
      :title {:flex-grow 1}})))

(defn dashboard* [{:keys [class-name]}]
  (let [open? @(rf/subscribe [:drawer/open?])]
    [:div {:class [class-name (:root classes)]}
     [css-baseline]
     [app-bar {:position "absolute"
               :class [(:app-bar classes) (when open? (:app-bar-shift classes))]}
      [toolbar
       [icon-button {:edge "start"
                     :color "inherit"
                     :aria-label "open drawer"
                     :on-click #(rf/dispatch [:drawer/open])  ; Open drawer
                     :class [(:menu-button classes) (when open? (:menu-button-hidden classes))]
                     }
        [menu]]
       [typography {:component "h1"
                    :variant "h6"
                    :color "inherit"
                    :no-wrap true
                    :class (:title classes)
                    }
        "Dashboard"]]]

     [drawer {:variant "permanent"
              :classes {:paper (str (:drawer-paper classes) " "
                                    (if open? "" (:drawer-paper-close classes)))}
              :open open?}
      (js/alert open?)
      [:div {:class (:toolbar-icon classes)}
       [icon-button {:on-click #(rf/dispatch [:drawer/close])} ; Close drawer
        [chevron-left]]]
      [divider]

      ]]))

(def dashboard (styled dashboard* styles))
