(ns invoice-item-test
  (:require [clojure.test :refer [deftest is]]
            [invoice-item :refer [subtotal]]))

(deftest test-without-discount
  (let [item {:invoice-item/precise-quantity 5
              :invoice-item/precise-price 15}]
    (is (= 75.0 (subtotal item)))))

(deftest test-with-negative-price
  (let [item {:invoice-item/precise-quantity 20
              :invoice-item/precise-price -20}]
    (is (= -400.0 (subtotal item)))))
