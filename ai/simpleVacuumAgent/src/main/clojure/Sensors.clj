(ns Sensors
  (:require Environment))

(defn current-location? []
  (Environment/get-agent-location))

(defn is-current-location-dirty? []
  (Environment/is-location-dirty? (current-location?)))
