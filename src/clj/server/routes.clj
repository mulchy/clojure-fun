(ns server.routes
  (:require [compojure.core :refer [GET routes]]
            [compojure.route :refer [resources not-found]]
            ;[ring.util.http-response :refer :all]
            [server.index :refer [page]]))

(defn app-routes
  "Defines the routes this server responds to."
  [app]
  (routes
   (resources "/js" {:root "js"})
   (resources "/css" {:root "css"})
   (GET "/" [] page)
   (GET "/todos" []
        "[{:name \"sleep\"
          :complete false}]")
   (not-found "Sorry!")))
