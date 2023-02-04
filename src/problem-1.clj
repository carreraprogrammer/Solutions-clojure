
; Find the items that satisfy the next conditions

;; 1. At least have one item that has :iva 19%
;; 2. At least one item has retention :ret_fuente 1%
;; 3. Every item must satisfy EXACTLY one of the above two conditions. This means that an item cannot have BOTH :iva 19% and retention :ret_fuente 1%.

;; How can I find the function that filter the items that satisfy that requirements?
;; 1. The core function is a FILTER that return only the items that satisfy those conditions:
;;; - (filter CONDITIONS COLLECTION)
;; 2. The COLECTION argument is going to be the Invoice, so the filter function should be inside another function that takes as argument a COLLECTION in this case Invoice
;;; - (defn filter-invoice [Invoice] (filter CONDITIONS [Invoice]))
;; 3. What are the conditions?
;;; - The first condition is that the item should have at least one iva = 19 % OR :ret-fuente 1%
;;; - The second condition is that the item should have EXACTLY one of the two conditions
