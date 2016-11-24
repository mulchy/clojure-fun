(ns server.index
  (:require [hiccup.core :refer [html]]
            [hiccup.page :refer [html5 include-js include-css]]))

(def page
  (html
    (html5
      [:head
       [:title "Example"]
       [:meta {:charset "utf-8"}]
       [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge"}]
       [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
       (include-css "css/main.css")]
      [:body
       [:div.container [:div#app]]
       [:h1 "goodbye world"]
       (include-js "js/main.js")])))
