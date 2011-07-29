(ns Environment
  (:require clojure.contrib.combinatorics))

(def forward +)
(def backward -)
(def key-board :board)
(def key-location :agent-location)
(def key-previous-location :previous-agent-location)
(def visited-left-room :left-room)
(def visited-right-room :right-room)

;The "board" will be a seq of 0's and 1's representing clean and dirty.
(defn make[]
  {visited-right-room false
   visited-left-room false})

(defn- add-to-environment
  [environment k v]
  (assoc environment k v))

(defn add-board
  [environment board]
  (add-to-environment environment key-board board))

(defn add-agent-location
  [environment agent-location]
  (add-to-environment environment key-location agent-location))

(defn add-previous-location
  [environment previous-location]
  (add-to-environment environment key-previous-location previous-location))

(defn- visit-left-room
  [environment]
  (assoc environment visited-left-room true))

(defn- visit-right-room
  [environment]
  (assoc environment visited-right-room true))

(defn last-room-number?
  [environment]
  (- (count (environment key-board)) 1))

(defn dirty?
  [environment]
  (= 0 (get (environment key-board) (environment key-location))))

(defn clean?
  [environment]
  (not (dirty? environment)))

(defn location?
  [environment]
  (environment key-location))

(defn previous-location?
  [environment]
  (environment key-previous-location))

(defn visited-left-room?
  [environment]
  (environment visited-left-room))

(defn visited-right-room?
  [environment]
  (environment visited-right-room))

(defn- agent-in-left-room?
  [environment]
  (= 0 (location? environment)))

(defn- agent-in-right-room?
  [environment]
  (= (last-room-number? environment) (location? environment)))

(defn- will-bump-left-wall?
  [environment forward-or-backward]
  (and (= 0 (location? environment))
       (= backward forward-or-backward)))

(defn- will-bump-right-wall?
  [environment forward-or-backward]
  (and (= (last-room-number? environment) (location? environment))
       (= forward forward-or-backward)))

(defn- will-bump-a-wall?
  [environment forward-or-backward]
  (or (will-bump-left-wall? environment forward-or-backward)
      (will-bump-right-wall? environment forward-or-backward)))

(defn count-clean-rooms
  [environment]
  (reduce + (environment key-board)))

(defn- update-location
  [environment forward-or-backward]
  (let [location (location? environment)]
  (-> environment
    (add-previous-location location)
    (add-agent-location (forward-or-backward location 1)))))

(defn update-visited-rooms 
  "Updates the first or last rooms as visited if the agent is currently there."
  [environment]
  (cond (agent-in-left-room? environment) (visit-left-room environment)
        (agent-in-right-room? environment) (visit-right-room environment)
        :else environment))

(defn clean
  [environment]
  (let [board (environment key-board)
        location (environment key-location)]
    (-> environment
        (add-board (assoc board location 1))
        (update-visited-rooms))))

(defn bump-wall
  [environment]
  (add-previous-location environment (location? environment)))

(defn move
  "Move the Agent left or right or nowhere."
  [environment forward-or-backward]
  (if (will-bump-a-wall? environment forward-or-backward) (bump-wall environment)
      (update-location environment forward-or-backward)))

(defn- get-board-size []
  "Gets the size of the board. Use for generating environments."
  4)

(defn build-all-possible-boards []
  "Returns a sequence of vectors Each vector represents a unique state of the environment.
All possible environment states are in the sequence.We are Using vectors so we can update by index position."
  (map vec (clojure.contrib.combinatorics/selections [0 1] (get-board-size))))

(defn build-all-possible-states []
  (clojure.contrib.combinatorics/cartesian-product
   (Environment/build-all-possible-boards)
   (range 0 (get-board-size))))


