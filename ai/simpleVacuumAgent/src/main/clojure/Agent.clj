;(load "Sensors")
(ns Agent
  (:require Sensors Actuators))

(defn move-left []
  )
(defn move-right []
  )
(defn clean[]
  )

(defn move[]
  (if (== 0 rand-int 2)
    (move-left)
    (move-right)))

(defn is-current-location-dirty? []
  (Sensors/is-current-location-dirty?))

(defn go []
  (if (is-current-location-dirty?)
    (clean)
    (move)))
    
