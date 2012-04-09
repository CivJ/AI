(ns simpleVacuumAgent.PerformanceMeasure)
(require '(simpleVacuumAgent  [Agent :as agent]))
(require '(simpleVacuumAgent  [Environment :as env]))

(defn get-points
  [environment]
  (env/count-clean-rooms environment))

(defn agent-moved?
  [environment]
  (if (agent/first-move? environment) false
      (not (=
            (env/location? environment)
            (env/previous-location? environment)))))

(defn get-penalties
  [environment]
  "The agent loses 1 point for each movement or movement attempt."
  (if (agent-moved? environment) -1 0))

(defn get-score
  [environment]
    (+ (get-points environment) (get-penalties environment)))

(defn measure-performance
  [test]
  (let [environment (agent/go(test :environment))
        score (get-score environment)]
    (-> test
        (assoc :environment environment)
        (assoc :score score))))

(defn run-test
  [time test]
  (if (< time (test :stop-time))
    (run-test (inc time) (measure-performance test))
    test))

(defn begin-test
  "Entry point for the test. Returns the given test with an updated score and environment."
  [test]
  (let [stop-time 0]
    (run-test stop-time test)))

(defn reduce-test-results
  "Takes the results collection and returns the average score."
  [results]
  (quot (reduce + (map #(second (rest %1)) results))
        (count results)))

(defn initialize-environment
  "Define the initial board state and agent location"
  [board agent-location]
  (-> (env/make)
      (env/add-board board)
      (env/add-agent-location agent-location)))

(defn build-all-environments []
  "Builds all possible environments based on the board size. This will take a long even for
smallish boards."
  (let [environment-states (env/build-all-possible-states)
        boards (map first environment-states)
        agent-locations (map second environment-states)]
    (map initialize-environment boards agent-locations)))

(defn build-test
  [environment stop-time]
  {:environment environment
   :stop-time stop-time})

(defn build-all-tests
  [stop-time]
  (let [environments (build-all-environments)
        times (repeat (count environments) stop-time)]
    (map build-test environments times)))

(defn get-scores
  [tests]
  (map get tests (repeat (count tests) :score)))

(defn reduce-scores
  [scores]
  (double (/ (reduce + scores) (count scores))))

(defn run-all-tests
  [stop-time]
  (prn "Average Score: " (reduce-scores (get-scores (map begin-test (build-all-tests stop-time))))))

