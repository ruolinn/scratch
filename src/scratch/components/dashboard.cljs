(ns scratch.components.dashboard
  (:require
   [re-frame.core :as re-frame]
   [reitit.frontend.easy :as rfe]
   [reitit.core :as reitit]
   [reagent.core :as reagent :refer [atom with-let]]
   [scratch.util :as util]
   [scratch.events :as events]
   [scratch.subs :as subs]
   [reagent-mui.styles :as styles :refer [styled]]
   [reagent-mui.material.app-bar :refer [app-bar]]
   [reagent-mui.material.toolbar :refer [toolbar]]
   [reagent-mui.material.icon-button :refer [icon-button]]
   [reagent-mui.material.typography :refer [typography]]
   [reagent-mui.material.drawer :refer [drawer]]
   [reagent-mui.material.divider :refer [divider]]
   [reagent-mui.material.list :refer [list]]
   [reagent-mui.material.list-item :refer [list-item]]
   [reagent-mui.material.list-item-icon :refer [list-item-icon]]
   [reagent-mui.material.list-item-text :refer [list-item-text]]
   [reagent-mui.icons.menu :refer [menu]]
   [reagent-mui.icons.chevron-left :refer [chevron-left]]
   [reagent-mui.material.css-baseline :refer [css-baseline]]))


(def drawer-width 240)

(def classes
  (util/make-classes "scratch-dashboard"
                     [:root
                      :toolbar
                      :toolbar-icon
                      :app-bar
                      :app-bar-shift
                      :menu-button
                      :menu-button-hidden
                      :title
                      :drawer-paper
                      :drawer-paper-close
                      :app-bar-spacer
                      :container
                      :content
                      :paper
                      :fixed-height
                      ]))

(defn styles [{:keys [theme]}]
  (let [spacing (:spacing theme)
        on-desktop (util/on-desktop theme)
        on-app-bar-leaving (util/on-app-bar theme :leaving-screen)
        on-app-bar-entering (util/on-app-bar theme :entering-screen)
        on-drawer-paper-entering (util/on-drawer-paper theme :entering-screen)
        on-drawer-paper-leaving (util/on-drawer-paper theme :leaving-screen)]
    (util/set-classes
     classes
     {:root {:display "flex"}
      :toolbar {:padding-right 24}
      :toolbar-icon (merge {:display "flex"
                            :align-items "center"
                            :justify-content "flex-end"
                            :padding "0 8px"}
                           (get-in theme [:mixins :toolbar]))

      :app-bar {:z-index (+ (get-in theme [:z-index :drawer]) 1)
                :transition on-app-bar-leaving}
      :app-bar-shift {:margin-left drawer-width
                      :width (str "calc(100% - " drawer-width "px)")
                      :transition on-app-bar-entering}

      :menu-button {:margin-right 36}
      :menu-button-hidden {:display "none"}
      :drawer-paper {:position "relative"
                     :white-space "nowrap"
                     :width drawer-width
                     :transition on-drawer-paper-entering}
      :drawer-paper-close {:overflow-x "hidden"
                           :transition on-drawer-paper-leaving
                           :width (spacing 7)
                           on-desktop {:width (spacing 9)}}
      :app-bar-spacer (get-in theme [:mixins :toobar])
      :content {:flex-grow 1
                :height "100vh"
                :overflow "auto"}
      :container {:padding-top (spacing 4)
                  :padding-bottom (spacing 4)}
      :paper {:padding (spacing 4)
              :display "flex"
              :overflow "auto"
              :flex-direction "column"}
      :title {:flex-grow 1}
      :fixed-height {:height 240}})))

(defn dashboard* [{:keys [class-name router current-route]}]
  (let [open? @(re-frame/subscribe [::subs/drawer-open?])]
    [:div {:class [class-name (:root classes)]}
     [css-baseline]
     [app-bar {:position "absolute"
               :class [(:app-bar classes) (when open? (:app-bar-shift classes))]}
      [toolbar {:class (:toolbar classes)}
       [icon-button {:edge "start"
                     :color "inherit"
                     :aria-label "open drawer"
                     :on-click #(re-frame/dispatch [::events/drawer-open]) ; Open drawer
                     :class [(:menu-button classes) (when open? (:menu-button-hidden classes))]}
        [menu]]
       [typography {:component "h1"
                    :variant "h6"
                    :color "inherit"
                    :no-wrap true
                    :class (:title classes)}
        "Dashboard"]]]

     [drawer {:variant "permanent"
              :classes {:paper (str (:drawer-paper classes) " "
                                    (if open? "" (:drawer-paper-close classes)))}
              :open open?}
      [:div {:class (:toolbar-icon classes)}
       [icon-button {:on-click #(re-frame/dispatch [::events/drawer-close])} ; Close drawer
        [chevron-left]]]
      [divider]

      [list
       (for [route-name (reitit/route-names router)
             :let [route (reitit/match-by-name router route-name)
                   text (-> route :data :link-text)
                   icon (-> route :data :icon)
                   selected? (= route-name (-> current-route :data :name))]]

         ^{:key route-name} [list-item {:button true :on-click #(rfe/push-state route-name) :selected selected?}
                             [list-item-icon [icon]]
                             [list-item-text {:primary text}]])]

      #_[divider]]
     [:main {:class (:content classes)}
      [:div {:class (:app-bar-spacer classes)}]
      (when current-route
        [(-> current-route :data :view) {:classes classes}])]]))

(def dashboard (styled dashboard* styles))
