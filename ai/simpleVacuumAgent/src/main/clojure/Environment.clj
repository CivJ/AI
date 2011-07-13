(ns Environment
  (:require clojure.contrib.combinatorics))

(def *board-size* 2)

;The "board" will be a seq of 0's and 1's representing clean and dirty.
(def *state*
  {:agent-location 0
   :environment [0]})

;For running the agent in all possible states of the environment.
(defn build-all-possible-boards []
  (clojure.contrib.combinatorics/selections [0 1] *board-size*))

(defn build-all-possible-states []
  (clojure.contrib.combinatorics/cartesian-product
   (Environment/build-all-possible-boards)
   (range 0 Environment/*board-size*)))

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
  (update-state (*state* :environment) location))

;assoc only works on maps and vectors.
(defn get-cleaned-environment [location]
  (assoc (*state* :environment) location 1))

;update our state with the new cleaned location
(defn clean []
  (let [location (*state* :agent-location)]
  (update-state location (get-cleaned-environment location))))

(defn is-location-dirty? [location]
  (if (== 0 (nth (get-board) location))
    true
    false))

(defn count-clean-squares[]
  "Returns the number of clean squares in the environment."
  (reduce + (get-board)))


