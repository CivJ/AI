(ns simpleVacuumAgent.Agent)
(require '(simpleVacuumAgent [Environment :as env]))

(defn done?[environment]
  (and (env/visited-left-room? environment)
       (env/visited-right-room? environment)))

(defn traveling? 
  "Compare current location to last location with f. f is <,>,=
Returns (f current previous)"
  [environment f]
  (f (env/location? environment) (env/previous-location? environment)))

(defn not-traveling?
  [environment]
  (traveling? environment =))

(defn traveling-backward?
  [environment]
  (traveling? environment <))

(defn first-move?
  [environment]
  (nil? (env/previous-location? environment)))

(defn direction
  "If we are not moving, check which direction we should go.
If we are moving, keep going untill we 'hit a wall'."
  [environment]
  (cond (first-move? environment) env/backward
        (traveling-backward? environment) env/backward
        :else env/forward))

(defn go
  [environment]
  (cond (done? environment) environment
        (env/dirty? environment) (env/clean environment)
        :else (env/move environment (direction environment))))
