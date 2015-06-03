(ns pst.core
  (:require [pst.message       :as pm]
            [pst.message-store :as ms]
            [pst.folder        :as pf]
            [pst.archive       :as pa]
            [pst.attachment    :as pat]
            [pst.recipient     :as pr]))


(defn process-message [m depth]
  (do
    (prn (str (apply str (repeat (inc depth) " ")) "  Subject:        " (:subject m)))
    (prn (str (apply str (repeat (inc depth) " ")) "  Class:        " (:message-class m)))
    (case (:message-class m)
      "IPM.Contact"  (prn (str (apply str (repeat (inc depth) " " )) "  Contact: "
                               (:given-name m) " "
                               (:surname m) " "
                               (:smtp-address m)))
      "IPM.Appointment" (do
                          (prn (str (apply str (repeat (inc depth) " " )) "  Start: " (:start-time m)))
                          (prn (str (apply str (repeat (inc depth) " " )) "  Location: " (:location m)))
                          (prn (str (apply str (repeat (inc depth) " " )) "  Attendees: " (:all-attendees m))))
      nil)))


(defn process-folder
  ([f] (process-folder f 0))
  ([f depth]
   (do
     (prn (str (apply str (repeat (inc depth) "*")) " Folder:        " (:display-name f)))
     (prn (str (apply str (repeat (inc depth) " ")) " Message Count: " (:message-count f)))
     ;; (prn (str (apply str (repeat (inc depth) " ")) " Subfolder Count: " (:subfolder-count f)))
     (doall (map (fn [m] (process-message m depth)) (pf/messages f)))
     (doall (map (fn [ff] (process-folder ff (inc depth))) (pf/subfolders f))))))

(defn process-archive [a]
  (let [store       (:message-store a)
        store-name  (:display-name store)
        root-folder (:root-folder a)]
    (do
      (prn (str "Message store: " store-name))
      (process-folder root-folder))))

(defn -main [& args]
  (do (prn "PST demo using " (first args))
      (let [archive (pa/archive (first args))]
        (process-archive archive))))
