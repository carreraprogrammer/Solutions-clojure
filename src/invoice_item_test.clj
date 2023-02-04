(ns invoice-item-test
  (:require [clojure.test :refer [deftest is]]
            [invoice-item :refer [subtotal]]))

(deftest test-without-discount
  (let [item {:invoice-item/precise-quantity 5
              :invoice-item/precise-price 15}]
    (is (= 75.0 (subtotal item)))))

(deftest test-without-price
  (let [item {:invoice-item/precise-quantity 10
              :discount-rate 10}]
    (is (= nil (subtotal item) ))))



