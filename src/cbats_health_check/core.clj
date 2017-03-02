(ns cbats-health-check.core
  (:require [clojure.string]
            [bunraku.core :as bunraku]
            [cheshire.core :refer [parse-string]]
            [clj-http.client :as client]))

(def chrome (bunraku/chrome-driver))

(defn sso-enabled?
  [driver client-system-url]
  (-> driver
      (bunraku/get-url (format "https://%s" client-system-url))
      (bunraku/get-current-url)
      (clojure.string/includes? "accounts.careerbuilder.com")))

(defn get-hal-customers
  "Makes a request to HAL and gets the customers"
  []
  (try
    (let [uri "https://hal.cb1tools.com/customer-systems"
          response (client/get uri)
          json (parse-string (:body response) true)]
      (get-in json [:data 0 :id]))
    (catch Exception e 
      e)))
