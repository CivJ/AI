(ns PerformanceMeasure
  (:require Agent Environment))

(defn measure-performance []
    (Environment/count-clean-squares))

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
  (let [boards (map first (Environment/build-all-possible-states))
       agent-locations (map second (Environment/build-all-possible-states))
       times (repeat (count boards) 1000)]
    (prn "Average Score: " (double (reduce-test-results (map run-agent-test  boards agent-locations times ))))))
  