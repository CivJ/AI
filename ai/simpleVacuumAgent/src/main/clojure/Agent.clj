(ns Agent
  (:require Environment))

(defn done?[environment]
  (and (Environment/visited-left-room? environment)
       (Environment/visited-right-room? environment)))

(defn traveling? 
  "Compare current location to last location with f. f is <,>,=
Returns (f current previous)"
  [environment f]
  (f (Environment/location? environment) (Environment/previous-location? environment)))

(defn not-traveling?
  [environment]
  (traveling? environment =))

(defn traveling-backward?
  [environment]
  (traveling? environment <))

(defn first-move?
  [environment]
  (nil? (Environment/previous-location? environment)))

(defn direction
  "If we are not moving, check which direction we should go.
If we are moving, keep going untill we 'hit a wall'."
  [environment]
  (cond (first-move? environment) Environment/backward
        (traveling-backward? environment) Environment/backward
        :else Environment/forward))

(defn go
  [environment]
  (cond (done? environment) environment
        (Environment/dirty? environment) (Environment/clean environment)
        :else (Environment/move environment (direction environment))))
