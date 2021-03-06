(ns pst.contact
  (require [pst.util :as pu]
           [pst.attachment :as pat]
           [pst.recipient :as pr]))

(defrecord Contact []
  pu/PSTMessage
  (to-dict [this] (dissoc this :java-object)))

(defn contact [c]
  (merge
   (->Contact)
   c
   (pu/obj->map (:java-object c)
                :account                       .getAccount
                :callback-telephone            .getCallbackTelephoneNumber
                :generation                    .getGeneration
                :given-name                    .getGivenName
                :government-id                 .getGovernmentIdNumber
                :business-telephone            .getBusinessTelephoneNumber
                :home-telephone                .getHomeTelephoneNumber
                :initials                      .getInitials
                :keyword                       .getKeyword
                :language                      .getLanguage
                :location                      .getLocation
                :mhs-common-name               .getMhsCommonName
                :organization-id               .getOrganizationalIdNumber
                :surname                       .getSurname
                :original-display-name         .getOriginalDisplayName
                :postal-address                .getPostalAddress
                :company-name                  .getCompanyName
                :title                         .getTitle
                :department-name               .getDepartmentName
                :office-location               .getOfficeLocation
                :primary-telephone             .getPrimaryTelephoneNumber
                :business-telephone-2          .getBusiness2TelephoneNumber
                :mobile-telephone              .getMobileTelephoneNumber
                :radio-telephone               .getRadioTelephoneNumber
                :car-telephone                 .getCarTelephoneNumber
                :other-telephone               .getOtherTelephoneNumber
                :transmittable-display-name    .getTransmittableDisplayName
                :pager-telephone               .getPagerTelephoneNumber
                :primary-fax                   .getPrimaryFaxNumber
                :business-fax                  .getBusinessFaxNumber
                :home-fax                      .getHomeFaxNumber
                :business-address-country      .getBusinessAddressCountry
                :business-address-city         .getBusinessAddressCity
                :business-address-state        .getBusinessAddressStateOrProvince
                :business-address-street       .getBusinessAddressStreet
                :business-address-postal-code  .getBusinessPostalCode
                :business-po-box               .getBusinessPoBox
                :telex                         .getTelexNumber
                :isdn                          .getIsdnNumber
                :assistant-telephone           .getAssistantTelephoneNumber
                :home-telephone-2              .getHome2TelephoneNumber
                :assistant                     .getAssistant
                :hobbies                       .getHobbies
                :middle-name                   .getMiddleName
                :display-name-prefix           .getDisplayNamePrefix
                :profession                    .getProfession
                :preferred-by-name             .getPreferredByName
                :spouse-name                   .getSpouseName
                :computer-network-name         .getComputerNetworkName
                :customer-id                   .getCustomerId
                :tty-tdd                       .getTtytddPhoneNumber
                :ftp                           .getFtpSite
                :manager-name                  .getManagerName
                :nickname                      .getNickname
                :homepage                      .getPersonalHomePage
                :business-homepage             .getBusinessHomePage
                :note                          .getNote
                :smtp-address                  .getSMTPAddress
                :company-telephone             .getCompanyMainPhoneNumber
                :childrens-names               .getChildrensNames
                :home-address-country          .getHomeAddressCountry
                :home-address-postal-code      .getHomeAddressPostalCode
                :home-address-city             .getHomeAddressCity
                :home-address-state            .getHomeAddressStateOrProvince
                :home-address-street           .getHomeAddressStreet
                :home-address-po-box           .getHomeAddressPostOfficeBox
                :other-address-city            .getOtherAddressCity
                :other-address-country         .getOtherAddressCountry
                :other-address-postal-code     .getOtherAddressPostalCode
                :other-address-state           .getOtherAddressStateOrProvince
                :other-address-street          .getOtherAddressStreet
                :other-address-po-box          .getOtherAddressPostOfficeBox
                :file-under                    .getFileUnder
                :home-address                  .getHomeAddress
                :work-address                  .getWorkAddress
                :other-address                 .getOtherAddress
                :postal-address-id             .getPostalAddressId
                :html                          .getHtml
                :work-address-street           .getWorkAddressStreet
                :work-address-city             .getWorkAddressCity
                :work-address-state            .getWorkAddressState
                :work-address-postal-code      .getWorkAddressPostalCode
                :work-address-country          .getWorkAddressCountry
                :work-address-po-box           .getWorkAddressPostOfficeBox
                :im-address                    .getInstantMessagingAddress
                :email-display-name            .getEmail1DisplayName
                :email-address-type            .getEmail1AddressType
                :email-address                 .getEmail1EmailAddress
                :email-original-display-name   .getEmail1OriginalDisplayName
                :email-type                    .getEmail1EmailType
                :email-2-display-name          .getEmail2DisplayName
                :email-2-address-type          .getEmail2AddressType
                :email-2-address               .getEmail2EmailAddress
                :email-2-original-display-name .getEmail2OriginalDisplayName
                :email-3-display-name          .getEmail3DisplayName
                :email-3-address-type          .getEmail3AddressType
                :email-3-address               .getEmail3EmailAddress
                :email-3-original-display-name .getEmail3OriginalDisplayName
                :fax-address-type              .getFax1AddressType
                :fax-email                     .getFax1EmailAddress
                :fax-original-display-name     .getFax1OriginalDisplayName
                :fax-2-address-type            .getFax2AddressType
                :fax-2-email                   .getFax2EmailAddress
                :fax-2-original-display-name   .getFax2OriginalDisplayName
                :fax-3-address-type            .getFax3AddressType
                :fax-3-email                   .getFax3EmailAddress
                :fax-3-original-display-name   .getFax3OriginalDisplayName
                :free-busy-location            .getFreeBusyLocation
                :brithday                      .getBirthday
                :anniversary                   .getAnniversary)))
