(ns cbats-health-check.core
  (:require [clojure.string]
            [bunraku.core :as bunraku]
            [clj-http.client :as client]))

(def chrome (bunraku/chrome-driver))


(defn sso-enabled?
  [driver client-system-url]
  (-> driver
      (bunraku/get-url (format "https://%s" client-system-url))
      (bunraku/get-current-url)
      (clojure.string/includes? "accounts.careerbuilder.com")))
