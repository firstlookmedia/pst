(ns pst.hacking
  (:require
   [pst.core          :as pst]
   [pst.message       :as pm]
   [pst.message-store :as ms]
   [pst.folder        :as pf]
   [pst.archive       :as pa]
   [pst.attachment    :as pat]
   [pst.recipient     :as pr]))


(def archive (pa/archive "test/resources/sample1.pst"))
;; (def archive2 (pa/archive "/mnt/archives/sony_hack_data/mlynton/mlynton.ost"))

(pst/process-archive archive)
;; (process-archive archive2)

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

;; i think "messages" might be things that are not actually
;; messages... like contacts, appointments, the like.

;; true
(:is-read sample-message)
;; "Here is a sample message"
(:subject sample-message)
(:conversation-topic sample-message)

;; true
(:is-from-me sample-message)
;; 1
(:attachment-count sample-message)
;; "<B2FDDB8BE384C94794441DB4A7F3D8B804AE624B@TK5EX14MBXC114.redmond.corp.microsoft.com>"
(:rfc-message-id sample-message)
;; "/O=MICROSOFT/OU=NORTHAMERICA/CN=RECIPIENTS/CN=TERRYMAH1"
(:sender-email-address sample-message)
;; "Terry Mahaffey"
(:sender-name sample-message)

(:internet-article-number sample-message)
(pat/save (first (pm/attachments sample-message)) "/home/joshua/uh.jpg")

(def sample-recipient (first (pm/recipients sample-message)))
;; "terrymah@microsoft.com"
(:smtp-address sample-recipient)
