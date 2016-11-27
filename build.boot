(set-env!
  :source-paths #{"src/cljs"}
  :resource-paths #{
                    "src/clj"
                    ;"src/cljc"
                    }
  :asset-paths #{"assets"}
  :dependencies '[[org.clojure/clojure        "1.9.0-alpha14"]
                  [org.clojure/clojurescript  "1.9.293"]
                  [org.clojure/core.async     "0.2.395"]

                  ;; dev
                  [adzerk/boot-cljs           "1.7.228-2"      :scope "test"]
                  [adzerk/boot-cljs-repl      "0.3.3"          :scope "test"]
                  [com.cemerick/piggieback    "0.2.1"          :scope "test"]
                  [weasel                     "0.7.0"          :scope "test"]
                  [org.clojure/tools.nrepl    "0.2.12"         :scope "test"]
                  [adzerk/boot-reload         "0.4.13"         :scope "test"]
                  [reloaded.repl              "0.2.3"          :scope "test"]

                  ;; server
                  [compojure                  "1.5.1"]
                  [ring                       "1.5.0"] ;; TODO ring core?
                  [metosin/ring-http-response "0.8.0"]
                  [aleph                      "0.4.2-alpha10"]
                  [hiccup                     "1.0.5"]
                  [com.stuartsierra/component "0.3.1"]

                  ;; client
                  [reagent "0.6.0"]
                  [cljs-ajax "0.5.8"]
                  ;[binaryage/devtools "0.8.2"]
                  ])

(require
 '[adzerk.boot-cljs      :refer [cljs]]
 '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl repl-env]]
 '[adzerk.boot-reload    :refer [reload]]
; '[metosin.boot-alt-test :refer [alt-test]]
; '[deraen.boot-less      :refer [less]]
; '[deraen.boot-sass      :refer [sass]]
                                        ; '[crisptrutski.boot-cljs-test :refer [test-cljs prep-cljs-tests run-cljs-tests]] TODO make this work
 '[server.boot          :refer [reload-app]]
; '[reloaded.repl         :refer [go reset start stop system]]

 ;'[pandeiro.boot-http    :refer [serve]]
 ;'[server.core           :refer [routes]]
 )

(task-options!
  pom {:project 'something
       :version "0.1.0-SNAPSHOT"
       :description "Trying to compile something"
       :license {"The MIT License (MIT)" "http://opensource.org/licenses/mit-license.php"} ;TODO
       }
  aot {:namespace #{'server.lifecycle.main}}
  jar {:main 'server.lifecycle.main}
  cljs {:source-map true}
  ;repl {:init-ns 'server.core}
  )

(deftask dev []
  (comp
   (watch)
   (cljs-repl)
   (reload)
   (cljs)
   (reload-app)))

(deftask package
  "Build the package"
  []
  (comp
    (cljs :optimizations :advanced)
    (aot)
    (pom)
    (uber)
    (jar)
    (sift :include #{#".*\.jar"})
    (target)))
