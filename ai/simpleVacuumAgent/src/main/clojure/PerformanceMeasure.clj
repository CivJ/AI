(ns PerformanceMeasure
  (:require Agent Environment))

(defn update-environment [board agent-location]
  (Environment/update-state board agent-location))

(defn measure-performance [board agent-location]
  (do
    (update-environment board agent-location)
    (Environment/count-clean-squares)))

(defn run-agent-test [board agent-location stop-time]
    (prn "Evaluation beginning...")
    (loop [time 0
           score 0]
      (if (< time stop-time)
        (do
          (Agent/go)
          (recur (inc time)
                 (+ score (measure-performance board agent-location))))
        (prn "SCORE: " score))))

(defn run-all-tests []
  (let [boards (map first (Environment/build-all-possible-states))
       agent-locations (map second (Environment/build-all-possible-states))
       times (repeat (count boards) 2)]
    (map run-agent-test  boards agent-locations times )))
    ;   (prn "boards: " boards " locations: " agent-locations " times: " times)))
  