(ns problem-2
  (:require [clojure.spec.alpha :as s]
            [clojure.data.json :as json]))

(def invoice-str (slurp "invoice.json"))
(def data (json/read-str invoice-str :key-fn keyword))

(defn validate-invoice [data]
  (let [invoice (:invoice data)
        customer (:customer invoice)
        name (str (:company_name customer))
        email (str (:email customer))
        issue-date (:issue_date invoice)]
    {:invoice
     {
      :issue_date issue-date
      :invoice/customer {:customer/name name
                         :customer/email email}
      :invoice/items [{
                       :invoice-item/price ""
                       :invoice-item/quantity ""
                       :invoice-item/sku ""
                       :invoice-item/taxes []
                       }
                      {
                       :invoice-item/price ""
                       :invoice-item/quantity ""
                       :invoice-item/sku ""
                       :invoice-item/taxes []
                       }
                      {
                       :invoice-item/price ""
                       :invoice-item/quantity ""
                       :invoice-item/sku ""
                       :invoice-item/taxes []
                       }
                      ]
      }}
    ))

(validate-invoice data)