(ns Actuators
  (:require Environment))

(defn move [f]
  (Environment/update-agent-location (f 1 (Environment/get-agent-location))))

(defn move-left []
  (move -))

(defn move-right []
  (move +))

(defn clean []
  (Environment/clean))