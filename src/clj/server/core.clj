(ns server.core
  (:require
            [compojure.core :refer [GET defroutes]]
            [compojure.route :refer [resources]]
            ;[compojure.handler :refer [api]]
            ;[ring.util.response :refer [redirect]]
            [ring.util.http-response :refer :all]
            ;[ring.middleware.reload :refer [wrap-reload]]
            [aleph.http :as http]
            [aleph.netty :as netty])
  (:gen-class))

(defroutes routes
  (resources "/js" {:root "js"})
; (resources "/css" {:root "css"})

  (GET "/" []
       (-> (ok "Hello world"))))

(defn -main
  [& args]
  (println "starting")
  (netty/wait-for-close (http/start-server routes {:port 3000}))
  (println "now what?"))
