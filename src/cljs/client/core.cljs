(ns client.core
  (:require [reagent.core :as r]
            [cljs.core.async :as async]
            [ajax.core :refer [GET]])
  (:require-macros [cljs.core.async.macros :as async-macros]))

(enable-console-print!)
(println "Hello World")

(r/render-component [:p "hi from reagent"]
                    (js/document.getElementById "app"))

(GET "/todos"
     :handler       (fn [r] (print r))
     :error-handler (fn [r] (print r)))
