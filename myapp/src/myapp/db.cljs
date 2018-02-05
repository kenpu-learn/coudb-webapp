(ns myapp.db
  (:require [promesa.core :as p]))

(def db* (atom nil))
(def id-counter* (atom 0))

(defn get-id [] 
  (str "id-" (swap! id-counter* inc)))

(defn initialize! []
  (reset! db* (js/PouchDB. "todos")))

(defn add-todo [text]
  (let [todo #js {:_id (get-id)
                  :title text
                  :completed false}]
    (p/promise (fn [resolve reject] 
                 (.put @db* 
                       todo 
                       (fn [err result] (if err (reject err) (resolve result))))))))
