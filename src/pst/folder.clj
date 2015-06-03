(ns pst.folder
  (:import [com.pff PSTFile])
  (:require [pst.message :as pm]
            [pst.contact :as pcon]
            [pst.appointment :as papp]))

(defrecord Folder
    [display-name
     message-count
     subfolder-count
     java-object])

(defn folder
  "Given a com.pff.PSTFolder, return a Folder record."
  [^com.pff.PSTFolder f]
  (->Folder (.getDisplayName    f)
            (.getContentCount   f)
            (.getSubFolderCount f)
            f))

(defn nth-message
  "Get the nth message from a folder. Returns one of a Message,
  Contact, or Activity record."
  [^Folder f n] (let [_         (.moveChildCursorTo        (:java-object f) n)
                      child-obj (.getNextChild (:java-object f))
                      child     (if (nil? child-obj)
                                  nil
                                  (pm/message child-obj))]
                      (case (:message-class child)
                        nil               nil
                        "IPM.Note"        child
                        "IPM.Contact"     (pcon/contact child)
                        "IPM.Appointment" (papp/appointment child)
                        child))) ;; better handle this...

(defn messages
  "Given a folder, return a lazy seq of Messages."
  ([^Folder f]   (messages f 0))
  ([^Folder f n] (let [current-message (nth-message f n)]
                   (if (nil? current-message)
                     nil
                     (lazy-seq (cons current-message
                                     (messages f (inc n))))))))

(defn subfolders
  "Given a folder, return a seq of subfolders."
  [^Folder f] (map folder (.getSubFolders (:java-object f))))
