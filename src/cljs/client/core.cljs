(ns client.core
  (:require
                                        ;[reagent.core :as r]
                                        ;[ajax.core :refer [GET]]
   [cljs.core.async :as async]
   ;om next
   [om.dom :as dom]
   [goog.dom :as gdom]
   [om.next :as om :refer-macros [defui]])
  (:require-macros [cljs.core.async.macros :as async-macros]))

(enable-console-print!)

(def init-data
  {:todos [{:id 1 :name "Learn Om.next" :complete false}
           {:id 2 :name "Sleep more" :complete false}]})

;; parsing
(defmulti read om/dispatch)
(defmulti mutate om/dispatch)

;; unsure why I need this
(defn get-todo [state key]
  (let [st @state]
    (into [] (map #(get-in st %)) (get st key))))

(defmethod read :todos
  [{:keys [state]} key _]
  {:value (get-todo state key)})


(defmethod mutate 'todo/complete
  [{:keys [state] :as env} key {:keys [id] :as params}]
  {:action
   (fn []
     (swap! state update-in
            [:todo/by-id id :complete]
            not))})


(defmethod mutate 'todo/edit
  [{:keys [state] :as env} key {:keys [id name]}]
  {:action
   (fn []
     (swap! state update-in
            [:todo/by-id id :name]
            (constantly name)))})


;; rendering
(defui Todo
  static om/Ident
  (ident [this {:keys [id]}]
         [:todo/by-id id])
  static om/IQuery
  (query [this]
         '[:id :name :complete])
  Object
  (render [this]
          (println "Render Todo" (-> this om/props :name))
          (let [{:keys [name complete id] :as props} (om/props this)]
            (dom/li nil
                    (dom/input #js {:type "text"
                                    :value name
                                    :onChange (fn [e]
                                                (print "Updating name...")
                                                (let [text (.. e -target -value)]
                                                  (println "new text" text)
                                                  (om/transact! this `[(todo/edit {:name ~text :id ~id})])))
                                    })
                    (dom/input #js {:type    "checkbox"
                                    :checked complete
                                    :onChange (fn [_]
                                                (om/transact! this `[(todo/complete {:id ~id})]))
                                    })))))

(def todo (om/factory Todo {:keyfn :id}))

(defui ListView
  Object
  (render [this]
    (println "Render ListView" (-> this om/path first))
    (let [list (om/props this)]
      (apply dom/ul nil
        (map todo list)))))

(def list-view (om/factory ListView))

(defui RootView
  static om/IQuery
  (query [this]
         (let [subquery (om/get-query Todo)]
           `[{:todos ~subquery}]))
  Object
  (render [this]
    (println "Render RootView")
    (let [{:keys [todos]} (om/props this)]
      (list-view todos))))

(def reconciler
  (om/reconciler
    {:state  init-data
     :parser (om/parser {:read read :mutate mutate})}))

(om/add-root! reconciler
  RootView (gdom/getElement "app"))
