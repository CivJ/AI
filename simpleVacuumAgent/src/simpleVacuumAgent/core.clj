(ns simpleVacuumAgent.core)
(require '(simpleVacuumAgent [PerformanceMeasure :as performance-measure]))

(defn -main [& args]
  (performance-measure/run-all-tests 10))

