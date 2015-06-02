(ns pst.core
  (:require [pst.message       :as pm]
            [pst.message-store :as ms]
            [pst.folder        :as pf]
            [pst.archive       :as pa]
            [pst.attachment    :as pat]))
            ;; [clojure.java.io :refer [input-stream copy]]))


;; (defn process-folder [folder]
;;   (prn (str "Folder: "          (display-name folder)
;;             ", content count: " (content-count folder)))
;;   (do
;;     (doall (map #(prn (str "Subject: " (message/subject %)))
;;                 (take-while some? (messages folder))))
;;     (if (has-subfolders folder)
;;       (doseq [subf (subfolders folder)]
;;         (process-folder subf)))))

;; (defrecord PSTArchive
;;     [message-store root-folder encryption-type pst-file-type])

;; (defn pst-archive [filename]
;;   (let [pst (PSTFile. filename)]
;;     (->PSTArchive (ms/message-store (.getMessageStore   pst))
;;                   (pf/folder        (.getRootFolder     pst))
;;                   (.getEncryptionType pst)
;;                   (.getFileHandle     pst))))

(def archive (pa/archive "test/resources/sample1.pst"))
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
(:rfc-message-id sample-message)
(:sender-email-address sample-message)
(:sender-name sample-message)

(:internet-article-number sample-message)
(pat/save (first (pm/attachments sample-message)) "/home/joshua/uh.jpg")
