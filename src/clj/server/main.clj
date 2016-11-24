(ns server.main
  (:require [com.stuartsierra.component :as component]
            [reloaded.repl :as reload]
            [server.core :refer [server]])
  (:gen-class))

(defn new-app
  "Returns a new 'System Map' which can be started and stopped with component"
  []
  (component/system-map
   :app-component {}
   :server (component/using
            (server)
            [:app-component])))

(defn setup-app-for-reload!
  "Defines how to set up the app's state when reloading"
  []
  (reload/set-init! new-app))

;;TODO does this need to call join or something?
(defn -main
  "The production entry point"
  [& args]
  (component/start (new-app)))
