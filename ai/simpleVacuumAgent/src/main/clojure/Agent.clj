(ns Agent
  (:require Sensors Actuators))

(defn move-left []
  (Actuators/move-left))

(defn move-right []
  (Actuators/move-right))

(def actions-clean "clean")
(def actions-move "move")
(def actions-noop "noop")
(def last-action? nil)
(def turn-around-count 0)

(defn clean[]
  (Actuators/clean))

(defn last-move-successful? []
  (not (= (Sensors/previous-location?) (Sensors/current-location?))))

(defn keep-going []
  (if (= (Sensors/previous-direction?) Sensors/left)
    (move-left)
    (move-right)))

(defn turn-around []
  (do
    (if (= (Sensors/previous-direction?) Sensors/left)
      (move-right)
      (move-left))
    (def turn-around-count (inc turn-around-count))))
  
(defn move[]
  (if (last-move-successful?)
    (keep-going)
    (turn-around)))

(defn done-with-patrol? []
  (> turn-around-count 1))

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
      (if (not (done-with-patrol?))
        (do
          (move)
          (def last-action? actions-move))
        (def last-action? actions-noop))))))


