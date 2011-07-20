(ns Environment
  (:require clojure.contrib.combinatorics))

(defn get-board-size []
  "Gets the size of the board."
  10)

;The "board" will be a seq of 0's and 1's representing clean and dirty.
(def *state*
  {:agent-location 0
   :environment [0]})

(defn build-all-possible-boards []
  "Returns a sequence of vectors Each vector represents a unique state of the environment.
All possible environment states are in the sequence.We are Using vectors so we can update by index position."
  (map vec (clojure.contrib.combinatorics/selections [0 1] (get-board-size))))

(defn build-all-possible-states []
  (clojure.contrib.combinatorics/cartesian-product
   (Environment/build-all-possible-boards)
   (range 0 (get-board-size))))

(defn get-board []
  (*state* :environment))

(defn get-agent-location []
  (*state* :agent-location))

;Redefining this seems wrong...
(defn update-state [board agent-location]
  (def *state*
    {:agent-location agent-location
     :environment board}))

(defn update-agent-location [location]
  "The agent may not move off the board."
  (let [min 0
        max (- (count (get-board)) 1)]
    (cond
     (< location min) (update-state (get-board) min)
     (> location max) (update-state (get-board) max)
     true (update-state (get-board) location))))
    
;assoc only works on maps and vectors.
(defn get-cleaned-environment [location]
  (assoc (*state* :environment) location 1))

;update our state with the new cleaned location
(defn clean []
  (let [location (get-agent-location)]
  (update-state (get-cleaned-environment location) location)))

(defn is-location-dirty? [location]
  (if (== 0 (nth (get-board) location))
    true
    false))

(defn count-clean-squares []
  "Returns the number of clean squares in the environment."
  (reduce + (get-board)))


