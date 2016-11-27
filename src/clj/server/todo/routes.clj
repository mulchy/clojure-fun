(ns server.todo.routes
  (:require [compojure.core :refer [GET PUT POST DELETE routes context]]
            [ring.util.http-response :refer [not-implemented]]))

(defn wip
  []
  (not-implemented "Sorry!"))

(defn todos
  "Routes for interacting with todo objects"
  []
  (context "/user" []
           (GET    "/"
                   []
                    "[{:name \"sleep\" :complete false}]")
           (PUT    "/:id"
                   [id]
                   (wip))
           (POST   "/"
                   []
                   (wip))
           (DELETE "/:id"
                   [id]
                   (wip))))
