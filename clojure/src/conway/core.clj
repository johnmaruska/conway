(ns conway.core
  (:require [conway.output.terminal :as terminal]
            [conway.game :as game])
  (:gen-class))

;;;; Input

;; read in args

;; read in seed file

;; generate M x N random seed with given density


;;;; Output

;; save iteration to file

;; draw iteration?

(defn binary->abstracted [grid]
  (map (fn [row]
         (map (fn [cell]
                (if (zero? cell) game/dead game/alive))
              row))
       grid))

(def seed-grid
  (binary->abstracted
   [[0 0 0 0 0 0]
    [0 0 0 1 0 0]
    [0 1 0 0 1 0]
    [0 1 0 0 1 0]
    [0 0 1 0 0 0]
    [0 0 0 0 0 0]]))
(def m (count seed-grid))
(def n (count (first seed-grid)))

(defn -main [& args]
  (println "Starting Conway's Game of Life")

  (let [seed     seed-grid
        display! (partial terminal/display! m n)
        clear!   terminal/clear!]
    (game/set-dimensions! m n)
    ;; lazy seq of grid iterations. DO NOT REALIZE INTO MEMORY.
    (->> (game/play seed)
         (map (fn [grid]
                (clear!)
                (display! grid)
                (Thread/sleep 500)))
         (take 5)
         (dorun))))
