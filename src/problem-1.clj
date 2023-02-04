
(defn conditions
  [item]
  "Generate the conditions that satisfy the requirements of the problem"
  (let [taxes (:taxable/taxes item)
        retentions (:retentionable/retentions item)]
    (or (and (some (fn [tax] (= 19 (:tax/rate tax))) taxes) (not (some (fn [ret] (= 1 (:retention/rate ret))) retentions)))
        (and (not (some (fn [tax] (= 19 (:tax/rate tax))) taxes)) (some (fn [ret] (= 1 (:retention/rate ret))) retentions)))))

(defn filter-invoice
  "Filter the invoice items that satisfy the conditions provided"
  [invoice]
  (filter conditions (:invoice/items invoice)))             ;; (filter CONDITIONS COLLECTION)


;; 3. What are the conditions?
;;; - The first condition is that the item should have at least one iva = 19 % OR :ret-fuente 1%
;;; - The second condition is that the item should have EXACTLY one of the two conditions
