(ns server.boot
  {:boot/export-tasks true}
  (:require [boot.core :as b]
            [reloaded.repl :refer [go]]
            [server.lifecycle.main :refer [setup-app-for-reload!]]
            [clojure.tools.namespace.repl :refer [disable-reload!]]))

(disable-reload!)

(b/deftask reload-app
  []
  (let [x (atom nil)]
    (b/with-pre-wrap fileset
      (swap! x (fn [x]
                  (if x
                    x
                    (setup-app-for-reload!)
                    )))
      (go)
      fileset)))
