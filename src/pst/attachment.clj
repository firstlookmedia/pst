(ns pst.attachment
  (:require [pst.util :as pu]
            [clojure.java.io :refer [copy output-stream]]))

(defrecord Attachment [java-object]
  pu/PSTMessage
  (to-dict [this] (dissoc this :java-object)))

(defn attachment [a]
  (merge
   (->Attachment a)
   (pu/obj->map a
                :size                 .getSize
                :creation-time        .getCreationTime
                :modification-time    .getModificationTime
                :embedded-pst-message .getEmbeddedPSTMessage
                :filesize             .getFilesize
                :filename             .getFilename
                :attach-method        .getAttachMethod ;; 0 => None (No attachment) 1 => By value 2 => By reference 3 => By reference resolve 4 => By reference only 5 => Embedded message 6 => OLE
                :attach-size          .getAttachSize
                :attach-num           .getAttachNum
                :long-filename        .getLongFilename
                :pathname             .getPathname
                :rendering-position   .getRenderingPosition
                :long-pathname        .getLongPathname
                :mime-tag             .getMimeTag
                :mime-sequence        .getMimeSequence
                :content-id           .getContentId
                :invisible-in-html    .isAttachmentInvisibleInHtml
                :invisible-in-rtf     .isAttachmentInvisibleInRTF
                :is-mhtml-ref         .isAttachmentMhtmlRef
                :content-disposition  .getAttachmentContentDisposition)))

(defn save
  "Given an attachment and a filename, save attached file contents to disk."
  [a fn]
  (copy (.getFileInputStream (:java-object a)) (output-stream fn)))
