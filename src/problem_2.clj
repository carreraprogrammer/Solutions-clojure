(ns problem-2
  (:require [clojure.spec.alpha :as s]
            [clojure.data.json :as json]))

(def invoice-str (slurp "invoice.json"))
(def data (json/read-str invoice-str :key-fn keyword))

;; I need a function that lend me extract data from a high level key, to iterate and put it in my new invoice

(defn extract-values [items keys]
  (map #(select-keys % keys) items))

(defn validate-invoice [data]
  (let [invoice (:invoice data)
        customer (:customer invoice)
        name (str (:company_name customer))
        email (str (:email customer))
        issue-date (:issue_date invoice)
        items (:items invoice)]
    {:invoice
     {
      :issue_date issue-date
      :invoice/customer {:customer/name name
                         :customer/email email}
      :invoice/items items
      }}
    ))

(validate-invoice data)