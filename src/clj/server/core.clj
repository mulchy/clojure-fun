(ns server.core
  (:require [aleph.http :as http]
            [aleph.netty :as netty]
            [com.stuartsierra.component :as component]
            [server.routes :refer [app-routes]]))

;; internal, Defines how to start and stop the server, as well as any other components it depends on
(defrecord WebServer [port server app-component]
  component/Lifecycle

  (start [component]
    (if server
      (do
        (println "server already started")
        component)
      (let [port (or port 3000)]
        (println "starting webserver component")
        (-> component
            (assoc :server (http/start-server (app-routes app-component) {:port port}))
            (assoc :port   port)))))

  (stop  [component]
    (if (not server)
      (do
        (println "server already stopped")
        component)
      (do
        (println "stopping webserver component")
        (.close (:server component))
        (-> component
            (assoc :server nil)
            (assoc :port   nil))))))

(defn server
  "Creates a new instance of a 'WebServer' which will probably be used as a component in a larger app system"
  []
  (map->WebServer {}))
