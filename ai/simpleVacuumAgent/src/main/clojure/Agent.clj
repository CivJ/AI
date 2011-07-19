(ns Agent
  (:require Sensors Actuators))

(defn move-left []
  (Actuators/move-left))

(defn move-right []
  (Actuators/move-right))

(def actions-clean "clean")
(def actions-move "move")

(def last-action?
  nil)

(defn clean[]
  (Actuators/clean))

(defn last-move-successful? []
  (not (= (Sensors/previous-location?) (Sensors/current-location?))))

(defn keep-going []
  (if (= (Sensors/previous-direction?) Sensors/left)
    (move-left)
    (move-right)))

(defn turn-around []
  (if (= (Sensors/previous-direction?) Sensors/left)
    (move-right)
    (move-left)))
  
(defn move[]
  (if (last-move-successful?)
    (keep-going)
    (turn-around)))

(defn is-current-location-dirty? []
  (Sensors/is-current-location-dirty?))

(defn go []
  (do
    (Sensors/sense-environment)
  (if (is-current-location-dirty?)
    (do
      (clean)
      (def last-action? actions-clean))
    (do
      (move)
      (def last-action? actions-move)))))


