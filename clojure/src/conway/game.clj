(ns conway.game
  "Conway's Game of Life implementation.

  Intended usage includes:
    - `play` to receive a LazySeq of game iterations
    - `alive` to abstract representation
    - `dead` for the same reason
    - `set-dimensions!` to prepare before playing the game")

;; Dimensions stored in atoms to prevent recalculation
(def m (atom nil))  ; number of rows, vertical axis, y
(def n (atom nil))  ; number of columns, horizontal axis, x

;; count living/dead neighbors
(def alive 1)
(def dead  0)

(defn set-dimensions! [new-m new-n]
  (reset! m new-m)
  (reset! n new-n))

(defn get-dimensions
  ([]
   {:m @m :n @n})
  ([grid]
   {:m (count grid)
    :n (count (first grid))}))

(defn- alive? [grid x y]
  (and (<= 0 x (dec @n))
       (<= 0 y (dec @m))
       (= alive (nth (nth grid y) x))))

(defn- neighbor-coords
  "Get the value of each neighbor in a clockwise-from-12 vec"
  [x y]
  [[     x  (dec y)]  ; above
   [(inc x) (dec y)]  ; above right
   [(inc x)      y]   ; right
   [(inc x) (inc y)]  ; below right
   [     x  (inc y)]  ; below
   [(dec x) (inc y)]  ; below left
   [(dec x)      y]   ; left
   [(dec x) (dec y)]  ; above left
   ])

(defn count-neighbors [grid x y]
  (->> (neighbor-coords x y)
       (filter (fn [[nx ny]] (alive? grid nx ny)))
       (count)))

;; determine iteration for point
(defn- step-cell [grid x y]
  (if (alive? grid x y)
    (if (<= 2 (count-neighbors grid x y) 3) alive dead)
    (if ( = 3 (count-neighbors grid x y))   alive dead)))

(defn- step-row [grid y row]
  (vec (map-indexed (fn [x _cell] (step-cell grid x y))
                    row)))

;; perform iteration over entire grid
(defn- step-grid
  "Expects a `grid` whose cells are all either `alive` or `dead`, and whose
  dimensions match those in `m` and `n` via `set-dimensions!`."
  [grid]
  (vec (map-indexed (partial step-row grid) grid)))

(defn play [seed]
  (iterate step-grid seed))
