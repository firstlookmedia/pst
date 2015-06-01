(ns pst.archive
  (:import [com.pff PSTFile]
           [java.io File InputStream
            ByteArrayInputStream])
  (:require [pst.message-store :as ms]
            [pst.folder        :as pf]))

(defrecord Archive
    [message-store root-folder encryption-type pst-file-type])

(defn archive
  "Given a filename as a string, return an Archive record."
  [filename]
  (let [pst (PSTFile. filename)]
    (->Archive (ms/message-store   (.getMessageStore   pst))
               (pf/folder          (.getRootFolder pst))
               (.getEncryptionType pst)
               (.getFileHandle     pst))))
