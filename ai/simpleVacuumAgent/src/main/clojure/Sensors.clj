(ns Sensors
  (:require Environment))

(def state
  {:current-location nil
   :last-location nil
   :last-direction nil})

(def left "left")
(def right "right")
(def location-current :current-location)
(def location-previous :last-location)
(def direction-previous :last-direction)

(defn sense-last-location []
  (assoc state :last-location (state :current-location)))

(defn sense-current-location []
  (assoc state :current-location (Environment/get-agent-location)))

(defn sense-last-direction []
   (assoc state :last-direction
          (let [current (state :current-location)
                previous (state :last-location)]
            (cond
             (some nil? [current previous]) nil
             (> current previous) right
             ;If we didn't move and we're on the left, then we tried to go left...
             (== current previous) (if (= 0 current)
                                     left
                                     right)
             (< current previous) left))))

(def sensors
  [sense-last-location sense-current-location sense-last-direction])

(defn update-state [f]
  "Update state by calling a function which returns the new state."
  (def state (f)))
  
(defn current-location? []
  (state location-current))

(defn previous-location? []
  (state location-previous))

(defn previous-direction? []
  (state direction-previous))

(defn is-current-location-dirty? []
  (Environment/is-location-dirty? (current-location?)))

(defn sense-environment []
  "Sensors will update the internal environment state."
  (do
    (update-state sense-last-location)
    (update-state sense-current-location)
    (update-state sense-last-direction)))
