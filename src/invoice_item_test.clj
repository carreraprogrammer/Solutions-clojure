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

(deftest test-with-negative-quantity
  (let [item {:invoice-item/precise-quantity -20
              :invoice-item/precise-price 220}]
    (is (= -4400.0 (subtotal item)))))

(deftest test-discount-greater-than-100
  (let [item {:invoice-item/precise-quantity 2
              :invoice-item/precise-price 20
              :invoice-item/discount-rate 200}]
    (is (= -40.0 (subtotal item)))))

(deftest test-with-positive-discount
  (let [item {:invoice-item/precise-quantity 2
              :invoice-item/precise-price 20
              :invoice-item/discount-rate 10}]
    (is (= 36.0 (subtotal item)))))

(deftest test-with-negative-discount
  (let [item {:invoice-item/precise-quantity 2
              :invoice-item/precise-price 20
              :invoice-item/discount-rate -10}]
    (is (= 44.0 (subtotal item)))))