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
        issue-date (:issue_date invoice)
        items (let [price (:price (nth (:items invoice) 0))
                    quantity (:quantity (nth (:items invoice) 0))
                    sku (:sku (nth (:items invoice) 0))
                    taxes (:taxes (nth (:items invoice) 0))
                    rate (:tax_rate (first taxes))
                    ]
                    [{:invoice-item/price (double price)
                      :invoice-item/quantity (double quantity)
                      :invoice-item/:sku (str sku)
                      :invoice-item/:taxes [
                                            :tax/category :iva
                                            :tax/rate (double rate)
                                            ]
                          }])]
    {:invoice
     {
      :issue_date issue-date
      :invoice/customer {:customer/name name
                         :customer/email email}
      :invoice/items items
      }}
    ))

(validate-invoice data)