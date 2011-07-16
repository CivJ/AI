(ns Agent
  (:require Sensors Actuators))

(defn move-left []
  (Actuators/move-left))

(defn move-right []
  (Actuators/move-right))

(defn clean[]
  (Actuators/clean))

(defn move[]
  (if (== 0 (rand-int 2))
    (move-left)
    (move-right)))

(defn is-current-location-dirty? []
  (Sensors/is-current-location-dirty?))

(defn go []
  "Performs the agent's sensing and actions."
  (if (is-current-location-dirty?)
    (do
      (clean)
      (defn moved? [] false))
    (do
      (move)
      (defn moved? [] true))))
  
    
