(ns conway.core
  (:gen-class))

;; Dimensions stored in atoms to prevent recalculation
(def m (atom nil))  ; number of rows, vertical axis, y
(def n (atom nil))  ; number of columns, horizontal axis, x

;;;; Input

;; read in args

;; read in seed file

;; generate M x N random seed with given density


;;;; Iteration

;; count living/dead neighbors
(def alive true)
(def dead  false)

(defn living? [grid x y]
  (and (<= 0 x (dec @n))
       (<= 0 y (dec @m))
       (get (get grid y) x)))

(defn live-val [grid x y]
  (if (living? grid x y) 1 0))

(defn count-neighbors [grid x y]
  (let [live-val (partial live-val grid)]
    (+ (live-val      x  (dec y))  ; above
       (live-val (inc x) (dec y))  ; above right
       (live-val (inc x)      y)   ; right
       (live-val (inc x) (inc y))  ; below right
       (live-val      x  (inc y))  ; below
       (live-val (dec x) (inc y))  ; below left
       (live-val (dec x)      y)   ; left
       (live-val (dec x) (dec y))  ; above left
       )))

;; determine iteration for point
(defn step-cell [grid x y]
  (if (living? grid x y)
    (if (<= 2 (count-neighbors grid x y) 3) alive dead)
    (if (= 3 (count-neighbors grid x y)) alive dead)))

;; perform iteration over entire grid
(defn step-grid [grid]
  (map-indexed
   (fn step-row [y _row]
     (map-indexed (fn [x _cell] (step-cell grid x y))))
   grid))

;;;; Output

;; print iteration
(def alive-ascii "X")
(def dead-ascii ".")
(defn ->string [grid]
  (->> grid
       (map (fn row->string [row]
              (->> row
                   (map (fn cell->string [cell]
                          (if (= alive cell) alive-ascii dead-ascii)))
                   (apply str))))
       (interpose "\n")
       (apply str)))

(defn console-display [grid]
  (let [bookend (str (take n (repeat "=")))]
    (print (str (interpose "\n" [bookend (->string grid) bookend])))))

;; save iteration to file

;; draw iteration?


;;;; entry

(defn -main
  (println "Starting Conway's Game of Life")
  (let [seed nil]
    ;; lazy seq of grid iterations. DO NOT REALIZE INTO MEMORY.
    (iterate step-grid seed)
    ))
