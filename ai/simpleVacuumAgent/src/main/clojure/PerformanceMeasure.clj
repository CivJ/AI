(ns PerformanceMeasure
  (:require Agent Environment))

(defn update-environment [board agent-location]
  (Environment/update-state board agent-location))

(defn measure-performance [board agent-location]
  (do
    (update-environment board agent-location)
    (Environment/count-clean-squares)))

(defn run-agent-test [board agent-location stop-time]
  (update-environment board agent-location)
    (loop [time 0
           score 0]
      (if (< time stop-time)
        (do
          (Agent/go)
          (recur (inc time)
                 (+ score (measure-performance (Environment/get-board) (Environment/get-agent-location)))))
        (prn "Starting location: " agent-location " Initial board: " board "    Score: " score))))

(defn run-all-tests []
  (let [boards (map first (Environment/build-all-possible-states))
       agent-locations (map second (Environment/build-all-possible-states))
       times (repeat (count boards) 10)]
    (map run-agent-test  boards agent-locations times )))
  