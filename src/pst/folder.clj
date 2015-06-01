(ns pst.folder
  (:require [pst.message :as message]))

(defrecord Folder
    [display-name
     message-count
     subfolder-count
     java-object])

(defn folder
  "Given a com.pff.PSTFolder, return a nice clojure record"
  [f]
  (->Folder (.getDisplayName    f)
            (.getContentCount   f)
            (.getSubFolderCount f)
            f))

(defn nth-message
  [^Folder f n] (do (.moveChildCursorTo (:java-object f) n)

                    ;; this will return nil if n is beyond the
                    ;; last message, which works for our lazy-seq
                    ;; below
                    (.getNextChild      (:java-object f))))
                          
(defn messages
  "Given a folder, return a lazy seq of messages"
  ([^Folder f]   (messages f 0))
  ([^Folder f n] (let [current-message (nth-message f n)]
                   (if (nil? current-message)
                     nil
                     (lazy-seq (cons (message/message current-message)
                                     (messages f (inc n))))))))

(defn subfolders
  "Given a folder, return a seq of subfolders"
  [^Folder f] (map folder (.getSubFolders (:java-object f))))
  