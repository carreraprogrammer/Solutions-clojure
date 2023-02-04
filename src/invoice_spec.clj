(ns invoice_spec
  (:require
    [clojure.spec.alpha :as s] ))

(s/def :customer/name string?)                              ;; :customer/name should be a string
(s/def :customer/email string?)                             ;; :customer/email should be a string
(s/def :invoice/customer (s/keys :req [:customer/name       ;; :invoice/customer should have two keys, :customer/name and :customer/email
                                       :customer/email]))

(s/def :customer/name string?) ;; :customer/name should be a string
(s/def :customer/email string?) ;; :customer/email should be a string
(s/def :invoice/customer (s/keys :req [:customer/name ;; :invoice/customer should have two keys, :customer/name and :customer/email
                                       :customer/email]))

(s/def :tax/rate double?) ;; :tax/rate should be a double
(s/def :tax/category #{:iva}) ;; :tax/category should be the keyword :iva
(s/def ::tax (s/keys :req [:tax/category ;; :tax should have two keys, :tax/category and :tax/rate
                           :tax/rate]))
(s/def :invoice-item/taxes (s/coll-of ::tax :kind vector? :min-count 1)) ;; :invoice-item/taxes should be a vector of at least one ::tax map

(s/def :invoice-item/price double?) ;; :invoice-item/price should be a double
(s/def :invoice-item/quantity double?) ;; :invoice-item/quantity should be a double
(s/def :invoice-item/sku string?) ;; :invoice-item/sku should be a string

(s/def ::invoice-item
  (s/keys :req [:invoice-item/price ;; :invoice-item should have four keys, :invoice-item/price, :invoice-item/quantity, :invoice-item/sku, and :invoice-item/taxes
                :invoice-item/quantity
                :invoice-item/sku
                :invoice-item/taxes]))

(s/def :invoice/issue-date inst?) ;; :invoice/issue-date should be a date or time
(s/def :invoice/items (s/coll-of ::invoice-item :kind vector? :min-count 1)) ;; :invoice/items should be a vector of at least one ::invoice-item map

(s/def ::invoice
  (s/keys :req [:invoice/issue-date ;; :invoice should have three keys, :invoice/issue-date, :invoice/customer, and :invoice/items
                :invoice/customer
                :invoice/items]))

