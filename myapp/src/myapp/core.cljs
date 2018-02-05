(ns myapp.core
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [keechma.app-state :as app-state]
            [keechma.ui-component :as ui]
            [keechma.controller :as c]
            [promesa.core :as p]
            [myapp.db :as db]))

(defn get-state [app-db]
  (reaction (get-in @app-db [:kv :state])))

(defn MainView [ctx]
  (let [state-ref (ui/subscription ctx :state)
        route-ref (ui/current-route ctx)]
    (fn [] 
      [:div
       [:button 
        {:on-click (fn [e]
                     (-> (db/add-todo "hello")
                         (p/then (fn [result]
                                   (js/console.log result)))
                         (p/catch (fn [err]
                                    (println err)))))}

        "Add Todo"]]
    )))

(def MainComponent
  (ui/constructor {:renderer MainView
                   :component-deps []
                   :subscription-deps [:state]}))

(defn ping! [app-state m]
  (println "ping: " m))

(defrecord MainController [])
(defmethod c/params MainController [this params] params)
(defmethod c/start MainController [this params db] db)
(defmethod c/handler MainController [this app-db in-ch out-ch]
  (c/dispatcher app-db in-ch
                {:ping ping!}))

(def app {:routes [["/"]]
          :controllers {:main (->MainController)}
          :components {:main MainComponent}
          :subscriptions {:state get-state}
          :html-element (.getElementById js/document "app")})

(def running-app (atom nil))
(defn start-app! []
  (db/initialize!)
  (reset! running-app (app-state/start! app)))

(defn restart-app! []
  (if-let [app @running-app]
    (app-state/stop! app start-app!)
    (start-app!)))

(defn reload [])

(enable-console-print!)
(restart-app!)
