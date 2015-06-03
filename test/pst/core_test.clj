(ns pst.core-test
  (:require [clojure.test :refer :all]
            [pst.archive :as pa]
            [pst.folder  :as pf]
            [pst.message :as pm]))

(def archive (pa/archive "test/resources/sample1.pst"))

(deftest message-store-test
  (testing "Message store display name"
    (is (= "sample1" (:display-name (:message-store archive))))))

(deftest folder-count-tests
  (testing "Counting things in a folder"
    (is (= 0 (:message-count (:root-folder archive)))
        (= 4 (:subfolder-count (:root-folder archive))))))

(deftest subfolder-names
  (testing "Subfolder names"
    (let [subs (pf/subfolders (:root-folder archive))]
      (is (= '("Top of Outlook data file" "Search Root"
               "SPAM Search Folder 2" "ItemProcSearch")
             (map :display-name subs))))))

(deftest names-and-counts-test
  (testing "Subfolder counts"
    (let [subs    (pf/subfolders (:root-folder archive))
          outlook (pf/subfolders (first subs))]
      (is (= '(["Deleted Items" 0] ["Sample1" 1])
             (map vector
                  (map :display-name outlook)
                  (map :message-count outlook)))))))

(deftest message-test
  (let [subs    (pf/subfolders (:root-folder archive))
        outlook (pf/subfolders (first subs))
        sample  (second outlook)
        sample-message (first (pf/messages sample))]
      (is (= (:is-read sample-message) true))
      (is (= (:subject sample-message) "Here is a sample message"))
      (is (= (:conversation-topic sample-message) "Here is a sample message"))
      (is (= (:is-from-me sample-message) true))
      (is (= (:rfc-message-id sample-message)
             "<B2FDDB8BE384C94794441DB4A7F3D8B804AE624B@TK5EX14MBXC114.redmond.corp.microsoft.com>"))
      (is (= (:sender-email-address sample-message)
             "/O=MICROSOFT/OU=NORTHAMERICA/CN=RECIPIENTS/CN=TERRYMAH1"))
      (is (= (:sender-name sample-message)
             "Terry Mahaffey"))
      (is (= (:attachment-count sample-message) 1))))

(deftest attachment-test
  (let [subs    (pf/subfolders (:root-folder archive))
        outlook (pf/subfolders (first subs))
        sample  (second outlook)
        sample-message (first (pf/messages sample))
        attachment (first (pm/attachments sample-message))]
    (is (= (:long-filename attachment) "leah_thumper.jpg"))))
