(ns conway.output.terminal
  (:require [conway.game :as game]))

;; print iteration
(def alive-ascii "X")
(def dead-ascii ".")
(defn- cell->string [cell]
  (if (= game/alive cell) alive-ascii dead-ascii))

(def spacing 2)
(defn- ->string [grid]
  (->> grid
       (map (fn row->string [row]
              (->> row
                   (map cell->string)
                   (interpose (apply str (repeat spacing " ")))
                   (apply str))))
       (interpose "\n")
       (apply str)))

(defn- total-chars-per-line
  "Calculate total characters per line required to display grid of dimension `n`.

  Calculation assumes each line is the dimension with `spacing` characters
  interposed."
  [n]
  (+ n (* spacing (- n 1))))

(defn- make-bookend
  "Create string for preceding/following grid display for visual separation on
  repeated prints."
  [n]
  (->> (repeat "=")
       (take (total-chars-per-line n))
       (vec)
       (apply str)))

(defn clear! []
  (print (str (char 27) "[2J")) ; clear screen
  (print (str (char 27) "[;H"))) ; move cursor to the top left corner of the screen

(defn display!
  " Prints to the console the given grid with `=` top/bottom, `X` alive and `.`
  dead cells.

  =======
  X  .  .
  X  .  X
  .  .  X
  ======="
  [_m n grid]
  (let [bookend (make-bookend n)]
    (println (apply str [bookend "\n" (->string grid) "\n" bookend]))))
