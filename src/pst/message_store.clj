(ns pst.message-store)

(defrecord MessageStore
    [display-name tag-record-key])

(defn message-store [ms]
  (->MessageStore (.getDisplayName        ms)
                  (.getTagRecordKeyAsUUID ms)))
                  
