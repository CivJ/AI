(ns Actuators
  (:require Environment))

(defn move [plus-or-minus]
  (Environment/update-agent-location (plus-or-minus (Environment/get-agent-location) 1)))

(defn move-left []
  (move -))

(defn move-right []
  (move +))

(defn clean []
  (Environment/clean))