(ns pst.core
  (:import [com.pff PSTFile]
           [java.io File InputStream
            ByteArrayInputStream])
  (:require [pst.message       :as message]
            [pst.message-store :as ms]
            [pst.folder        :as pf]
            [clojure.java.io :refer [input-stream copy]]))

;; (defn pst-file [path]
;;   (PSTFile. path))
(defn root-folder [m]
  (.getRootFolder m))

(defn display-name [folder]
  (.getDisplayName folder))

(defn content-count [folder]
  (.getContentCount folder))

(defn has-subfolders [folder]
  (.hasSubfolders folder))

(defn subfolders [folder]
  (.getSubFolders folder))

(defmacro obj->map [o & bindings]
  (let [s (gensym "local")]
    `(let [~s ~o]
       ~(->> (partition 2 bindings)
             (map (fn [[k v]]
                    (if (vector? v)
                      [k (list (last v) (list (first v) s))]
                      [k (list v s)])))
             (into {})))))


(defn messages
  "Return a lazy seq of the messages in a folder."
  [folder]
  (lazy-seq (cons (.getNextChild folder) (messages folder))))
   

(defn process-folder [folder]
  (prn (str "Folder: "          (display-name folder)
            ", content count: " (content-count folder)))
  (do
    (doall (map #(prn (str "Subject: " (message/subject %)))
                (take-while some? (messages folder))))
    (if (has-subfolders folder)
      (doseq [subf (subfolders folder)]
        (process-folder subf)))))

(defprotocol FileOps
  (pst-file [input] "Return a file record"))

(defrecord PSTArchive
    [message-store root-folder encryption-type pst-file-type])

(defn pst-archive [filename]
  (let [pst (PSTFile. filename)]
    (->PSTArchive (ms/message-store (.getMessageStore   pst))                  
                  (pf/folder        (.getRootFolder     pst))
                  (.getEncryptionType pst)
                  (.getFileHandle     pst))))
        
(def archive (pst-archive "test/resources/sample1.pst"))
;; "sample1"
(:display-name (:message-store archive))
;; 0
(:message-count (:root-folder archive))
;; 4
(:subfolder-count (:root-folder archive))
(def subfs (pf/subfolders (:root-folder archive)))

;; ("Top of Outlook data file" "Search Root" "SPAM Search Folder 2" "ItemProcSearch")
(map :display-name subfs)

(def outlook (first subfs))
;; ("Deleted Items" "Inbox" "Outbox" "Sent Items" "Calendar" "Contacts" "Journal" "Notes" "Tasks" "Drafts" "RSS Feeds" "Junk E-mail")
(def outlooks (pf/subfolders outlook))
;; (["Deleted Items" 0] ["Sample1" 1])
(map vector
     (map :display-name outlooks)
     (map :message-count outlooks))

(def sample (second outlooks))

;; 1
(:message-count sample)

(def sample-message (first (pf/messages sample)))

;; true
(:is-read sample-message)
;; "Here is a sample message"
(:subject sample-message)
(:conversation-topic sample-message)

;; true
(:is-from-me sample-message)
;; 1
(:attachment-count sample-message)

