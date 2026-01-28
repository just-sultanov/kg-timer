(ns datetime
  (:require
   ["dayjs" :as dayjs]
   [clojure.string :as str]))

(defn now
  []
  (dayjs/utc))

(defn tomorrow
  [date]
  (-> (dayjs date)
      (.add 1 "day")
      (.startOf "day")))

(defn diff
  [from to]
  (let [ms (.diff from to)
        hours (-> ms (/ 3600000) js/Math.floor)
        minutes (-> ms (- (* hours 3600000)) (/ 60000) js/Math.floor)
        seconds (-> ms (- (* hours 3600000) (* minutes 60000)) (/ 1000) js/Math.floor)]
    {:hours hours, :minutes minutes, :seconds seconds}))

(defn remaining-time
  [from dt]
  (->> (diff from dt)
       ((juxt :hours :minutes :seconds))
       (mapv #(.padStart (str %) 2 "0"))
       (str/join ":")))
