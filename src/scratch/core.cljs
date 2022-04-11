(ns ^:figwheel-hooks scratch.core
  (:require [reagent.core :as r]
            [reagent.dom :as d]
            [re-frame.core :as rf]
            [ajax.core :refer [GET POST]]
            [re-com.core :as re-com :refer [at]]
            [scratch.dash :refer [main-panel]]))

(defn buy-botton [item-id]
  [:button
   {:on-click (fn [e]
                (.preventDefault e)
                (rf/dispatch [:buy 1]))}
   "Buy"])

(defn clean-botton []
  [:button
   {:on-click (fn [e]
                (.preventDefault e)
                (rf/dispatch [:clean]))}
   "Clean"])

(defn cart []
  (let [items (rf/subscribe [:cart-items])]
    (fn []
      [buy-botton]
      [:ul
       [:li "enens"]
       (doall
        (for [item @items]
          [:li {:key (:id item)} (:id item)]))])))

(defn cart-icon []
  (let [items (rf/subscribe [:cart-items])]
    (fn []
      [:span "total: " (count @items)])))

(defn home-page []
  [:div
   [buy-botton]
   [clean-botton]
   [cart-icon]
   [cart]])





(defn counting-button [txt]
  (let [state (r/atom 0)]
    (fn [txt]
      [:button.green
       {:on-click #(swap! state inc)}
       (str txt " " @state)])))

(rf/reg-event-fx
 :clean
 (fn [cofx _]
   (js/alert (:db cofx))
   {:db (update-in (:db cofx) [:items] nil)}))

(rf/reg-event-fx
 :buy
 [(rf/inject-cofx :temp-id)]
 (fn [cofx [_ item-id]]
   {:http-xhrio {:uri "http://app.clj.com/hi"
                 :method :get
                 :params {:tid (:temp-id cofx)}}
    :do-co {:params {:tid (:temp-id cofx)}}
    :db (update-in (:db cofx) [:items] conj {:id (:temp-id cofx)})}))



(rf/reg-fx
 :http-xhrio
 (fn [request]
   (case (:method request :get)
     :get (GET (:uri request)
               :params (:params request)
               :handler (fn [response]
                          (.log js/console (str response) (:params request))))))
 )

(rf/reg-fx
 :do-co
 (fn [params]
   (.log js/console (str "do-co " (:params params)))))

(rf/reg-cofx
 :now
 (fn [cofx _data]
   (assoc cofx :now (js/Date.))))

(defonce last-temp-id (atom 0))

(rf/reg-cofx
 :temp-id
 (fn [cofx _]
   (assoc cofx :temp-id (swap! last-temp-id inc))))


(rf/reg-sub
 :cart-items
 (fn [db _]
   (:items db)))


(defn mount []
  (rf/clear-subscription-cache!)
  (let [root (.getElementById js/document "app")]
    (rf/dispatch-sync [::scratch.dash/initialize-db])
    (d/unmount-component-at-node root)
    (d/render [main-panel] root)))


(defn ^:after-load re-render []
  (mount))

(defonce start-up (do (mount) true))
