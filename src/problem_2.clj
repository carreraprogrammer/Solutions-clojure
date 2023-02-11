(ns problem-2
  (:require [clojure.spec.alpha :as s]
            [clojure.data.json :as json]
            [invoice_spec :as test]
            ))

;; Import the 'clojure.instant' namespace to work with dates

(use 'clojure.instant)

;; Read the contents of the 'invoice.json' file into a string
(def invoice-str (slurp "invoice.json"))

;; Parse the JSON string into a data structure
(def data (json/read-str invoice-str :key-fn keyword))

;; Transform a date string from the format "08/03/2017" to [08 03 2017]

(defn format-date-v2 [date-vec]
  (str (nth date-vec 2) "-" (nth date-vec 1) "-" (nth date-vec 0)))

;; Transform a date string from the format [08 03 2017] to "2017-08-03"
(defn transform-date [date-str]
  (let [date-vec (mapv #(Integer/parseInt %) (clojure.string/split date-str #"/"))]
    (format-date-v2 date-vec)))

;; Validate an invoice data structure


(defn validate-invoice2 [{:keys [invoice]}]
  (let [{:keys [issue_date customer items ]} invoice
        items-data (map (fn [item]
                          {:invoice-item/price (double (:price item))
                           :invoice-item/quantity (double (:quantity item))
                           :invoice-item/sku (:sku item)
                           :invoice-item/taxes (map (fn [tax]
                                                              {:invoice-tax/tax-category (:tax_category tax)
                                                               :invoice-tax/tax-rate (double (:tax_rate tax))})
                                                               :taxes item)})
                        items)
        ]
    {:invoice/issue-date (read-instant-date (transform-date issue_date))
     :invoice/customer {:customer/name (:company_name customer)
                        :customer/email (:email customer)}
     :invoice/items (into [] items-data)}))

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
                  :invoice-item/sku (str sku)
                  :invoice-item/taxes  [{:tax/category :iva
                                         :tax/rate (double rate)}]

                  }])]

    {:invoice/issue-date (read-instant-date (transform-date issue-date))
     :invoice/customer {:customer/name name
                        :customer/email email}
     :invoice/items items}

    ))

;; Apply the validation to the data structure
(validate-invoice data)

;; Look for errors in the data structure
(s/explain ::test/invoice (validate-invoice2 data))

;; Check if the data structure is valid according to the specifications
(s/valid? ::test/invoice (validate-invoice2 data))