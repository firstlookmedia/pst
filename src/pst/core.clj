(ns pst.core
  (:require [pst.message       :as pm]
            [pst.message-store :as ms]
            [pst.folder        :as pf]
            [pst.archive       :as pa]
            [pst.attachment    :as pat]
            [pst.recipient     :as pr]
            [pst.util          :as pu]
            [clojure.pprint    :as pprint]))

(defn- pad
  ([d] (pad d " "))
  ([d c]
   (apply str (repeat d c))))

(defn process-message [m depth noisy]
  (do
    (println (str (pad (inc depth)) "  Subject:        " (:subject m)))
    (println (str (pad (inc depth)) "  Class:        " (:message-class m)))
    (case (:message-class m)
      "IPM.Contact"  (println (str (apply str (repeat (inc depth) " " )) "  Contact: "
                               (:given-name m) " "
                               (:surname m) " "
                               (:smtp-address m)))
      "IPM.Appointment" (do
                          (println (str (pad (inc depth)) "  Start: " (:start-time m)))
                          (println (str (pad (inc depth)) "  Location: " (:location m)))
                          (println (str (pad (inc depth)) "  Attendees: " (:all-attendees m))))
      (if noisy (pprint/pprint (pu/to-dict m))))

    nil))


(defn process-folder
  ([f noisy] (process-folder f 0 noisy))
  ([f depth noisy]
   (do
     (println (str (pad (inc depth) "*") " Folder:        " (:display-name f)))
     (println (str (pad (inc depth) " ") " Message Count: " (:message-count f)))
     (doall (map (fn [m] (process-message m depth noisy)) (pf/messages f)))
     (doall (map (fn [ff] (process-folder ff (inc depth) noisy)) (pf/subfolders f))))))

(defn process-archive [a noisy]
  (let [store       (:message-store a)
        store-name  (:display-name store)
        root-folder (:root-folder a)]
    (do
      (println (str "Message store: " store-name))
      (process-folder root-folder noisy))))

(defn -main [& args]
  (do (println "PST demo using " (first args))
      (let [archive (pa/archive (first args))
            noisy   (second args)]
        (process-archive archive noisy))))
