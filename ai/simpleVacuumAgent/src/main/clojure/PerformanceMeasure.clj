(ns PerformanceMeasure
  (:require Agent Environment))

(defn get-score []
  (Environment/count-clean-squares))

(defn agent-moved? []
  (= Agent/last-action? Agent/actions-move))

(defn get-penalties []
  "The agent loses 1 point for each movement or movement attempt."
  (if (agent-moved?) -1 0))

(defn measure-performance []
  (+ (get-score) (get-penalties)))

(defn initialize-environment
  "Define the initial board state and agent location"
  [board agent-location]
  (Environment/update-state board agent-location))

(defn run-agent-test
  "Return a collection of test result data."
  [board agent-location stop-time]
  (initialize-environment board agent-location)
    (loop [time 0
           score 0]
      (if (< time stop-time)
        (do
          (Agent/go)
          (recur (inc time)
                 (+ score (measure-performance))))
        (list board agent-location score))))

(defn reduce-test-results
  "Takes the results collection and returns the average score."
  [results]
  (quot (reduce + (map #(second (rest %1)) results))
        (count results)))

(defn run-all-tests []
  (let [time 1000
        boards (map first (Environment/build-all-possible-states))
       agent-locations (map second (Environment/build-all-possible-states))
       times (repeat (count boards) time)]
    (prn "Average Score for " time "loops : "
         (double (reduce-test-results (map run-agent-test  boards agent-locations times ))))))



  