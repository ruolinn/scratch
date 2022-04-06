(ns scratch.core
  (:require [reagent.core :as r]
            [reagent.dom :as d]
            [re-frame.core :as rf]
            [ajax.core :refer [GET POST]]))

(defn home-page []
  [:div
   [:ul
    (for [item (range 3)]
      ^{:key item} [:li "Item " item])]])

(defn counting-button [txt]
  (let [state (r/atom 0)]
    (fn [txt]
      [:button.green
       {:on-click #(swap! state inc)}
       (str txt " " @state)])))

(defn buy-botton [item-id]
  [:button
   {:on-click (fn [e]
                (.preventDefault e)
                (rf/dispatch [:buy 1]))}
   "Buy"])

(rf/reg-event-fx
 :buy
 [(rf/inject-cofx :now)]
 (fn [cofx [_ item-id]]
   {:http-xhrio {:uri "http://app.clj.com/hi"
                 :method :get
                 :params {:time (:now cofx)}}
    :do-co {:params {:time (:now cofx)}}}))



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



(defn mount-root []
  (d/render [buy-botton 1] (.getElementById js/document "app")))

(defn ^:export init []
  (mount-root))
