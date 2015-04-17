(ns foobar-txn.core
  (:require [bidi.bidi :as bidi]
            [bidi.ring :refer (make-handler)]
            [liberator.core :refer (defresource)]
            [liberator.dev :refer (wrap-trace)]
            [ring.adapter.jetty :refer (run-jetty)]
            [ring.middleware.cors :refer [wrap-cors]]
            [hashids.core :as hash]))

(defonce session-counter (atom 0))
(defonce transaction-counter (atom 0))
(defonce random-source (java.util.Random.))
(defonce store (atom {}))

(defn random-amount []
  (Math/abs (double (/ (.nextInt random-source) 1000000))))

(defn random-date []
  (let [ms (+
            1429276091602
            (Math/abs (mod (.nextLong random-source) (* 70 365 24 60 60 1000))))]
    (str (java.util.Date. ms))))

(defresource session-resource
  :allowed-methods [:post]
  :available-media-types ["application/json"]
  :handle-created (fn [ctx]
                    (let [id (hash/encrypt (swap! session-counter inc) "session")]
                      (swap! store assoc id {:transfers []})
                      {:id id})))

(defresource transfer-resource
  :allowed-methods [:get :post]
  :available-media-types ["application/json"]
  :handle-ok  (fn [ctx]
                (let [response {:transfers [{:amount (random-amount) :account "asdfs" :date (random-date)}
                                            {:amount (random-amount) :account "lsdfdj" :date (random-date)}
                                            {:amount (random-amount) :account "asdfsd" :date (random-date)}]}]
                  (println response)
                  response))
  :handle-created (fn [ctx]
                    {:id (hash/encrypt (swap! session-counter inc) "transfer")}))

(def routes ["/api" {"/session" {""  :session
                                 ["/" :session-id "/transfer"] :transfer}}])

(def handler
  (->
   (make-handler routes {:session session-resource
                         :transfer transfer-resource})
   (wrap-trace :header :ui)
   (wrap-cors :access-control-allow-origin [#".*"]
              :access-control-allow-methods [:get :put :post :delete])))

(defn -main []
  (run-jetty handler {:port 9000 :join? false}))
