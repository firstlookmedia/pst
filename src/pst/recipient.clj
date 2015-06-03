(ns pst.recipient
  (require [pst.util :as pu]))

(defrecord Recipient [java-object])

(defn recipient [r]
  (merge
   (->Recipient r)
   (pu/obj->map r
                :display-name       .getDisplayName
                :type               .getRecipientType
                :email-address-type .getEmailAddressType
                :email-address      .getEmailAddress
                :flags              .getRecipientFlags
                :order              .getRecipientOrder
                :smtp-address       .getSmtpAddress)))
