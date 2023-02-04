(ns problem-2
  (:require [clojure.spec.alpha :as s]
            [clojure.data.json :as json]))

(def invoice-str (slurp "invoice.json"))
(def data (json/read-str invoice-str :key-fn keyword))

(println data)