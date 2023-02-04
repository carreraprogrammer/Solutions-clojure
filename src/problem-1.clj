
;; First condition for the item: The item should have a taxe equal to determinated rate

(defn has-tax-with-rate [taxes rate]
  (some (fn [tax] (= rate (:tax/rate tax))) taxes))

;; Second condition for the item: The item should have a retention equal to determinated rate

(defn has-retention-with-rate [retentions rate]
  (some (fn [ret] (= rate (:retention/rate ret))) retentions))

;; The three conditions together for this particular problem:
;;; 1- The item should have at least a tax with IVA = 19
;;; 1- The item should have at least a tax with ret = 1
;;; 3- The item should not have both at the same time

(defn conditions [item]
  (let [taxes (:taxable/taxes item)
        retentions (:retentionable/retentions item)]
    (or (and (has-tax-with-rate taxes 19) (not (has-retention-with-rate retentions 1)))
        (and (not (has-tax-with-rate taxes 19)) (has-retention-with-rate retentions 1)))))

;; The function that returned me the filtered invoice that satisfy all the arguments

(defn filter-invoice
  "Filter the invoice items that satisfy the conditions provided"
  [invoice]
  (if (empty? (:invoice/items invoice))
    (println "Error: The invoice has no items.")
    (->> (:invoice/items invoice)
         (filter conditions))))

;; This part of the code transform "invoice.edn" and save the map in a variable called invoice

(def invoice (clojure.edn/read-string (slurp "invoice.edn")))

;; Here you can verify the filtered list

(filter-invoice invoice)