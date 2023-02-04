(ns problem-2
  (:require [clojure.spec.alpha :as s]
            [clojure.data.json :as json]
            [invoice_spec :as test]
            ))
(use 'clojure.instant)


(def invoice-str (slurp "invoice.json"))
(def data (json/read-str invoice-str :key-fn keyword))

(read-instant-date "2017-08-23")

;; The first function transform a time with this format "08/03/2017" into a vector [08 03 2017]

(defn format-date-v2 [date-vec]
  (str (nth date-vec 2) "-" (nth date-vec 1) "-" (nth date-vec 0)))

;; The second function uses the vector and transform the date in "2017-08-03"
(defn transform-date [date-str]
  (let [date-vec (mapv #(Integer/parseInt %) (clojure.string/split date-str #"/"))]
    (format-date-v2 date-vec)))

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
                                            {:tax/category :iva
                                             :tax/rate (double rate)}
                                            ]
                          }])]
     {:invoice
     {
      :invoice/issue-date (read-instant-date (transform-date issue-date))
      :invoice/customer {:customer/name name
                         :customer/email email}
      :invoice/items items
      }}
    ))

(validate-invoice data)

(s/valid? ::test/invoice (validate-invoice data))