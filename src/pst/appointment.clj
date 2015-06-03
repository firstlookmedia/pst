(ns pst.appointment
  (require [pst.util :as pu]))

(defrecord Appointment []
  pu/PSTMessage
  (to-dict [this] (dissoc this :java-object)))

(defn appointment [a]
  (merge
   (->Appointment)
   a
   (pu/obj->map (:java-object a)
                :send-as-ical                      .getSendAsICAL
                :busy-status                       .getBusyStatus
                :show-as-busy                      .getShowAsBusy
                :location                          .getLocation
                :start-time                        .getStartTime
                :start-time-zone                   .getStartTimeZone
                :end-time                          .getEndTime
                :end-time-zone                     .getEndTimeZone
                :recurrence-time-zone              .getRecurrenceTimeZone
                :duration                          .getDuration
                :color                             .getColor
                :sub-type                          .getSubType
                :meeting-status                    .getMeetingStatus
                :response-status                   .getResponseStatus
                :is-recurring                      .isRecurring
                :recurrence-base                   .getRecurrenceBase
                :recurrence-type                   .getRecurrenceType
                :recurrence-pattern                .getRecurrencePattern
                :recurrence-structure              .getRecurrenceStructure
                :timezone                          .getTimezone
                :all-attendees                     .getAllAttendees
                :to-attendees                      .getToAttendees
                :cc-attendees                      .getCCAttendees
                :appointment-sequence              .getAppointmentSequence
                :is-online                         .isOnlineMeeting
                :net-meeting-type                  .getNetMeetingType
                :net-meeting-server                .getNetMeetingServer
                :net-meeting-organizer-alias       .getNetMeetingOrganizerAlias
                :net-meeting-autostart             .getNetMeetingAutostart
                :conference-server-allow-external  .getConferenceServerAllowExternal
                :net-meeting-document-path         .getNetMeetingDocumentPathName
                :net-show-url                      .getNetShowURL
                :attendee-critical-change          .getAttendeeCriticalChange
                :owner-critial-change              .getOwnerCriticalChange
                :conference-server-password        .getConferenceServerPassword
                :appointment-counter-proposal      .getAppointmentCounterProposal
                :is-slient                         .isSilent
                :required-attendees                .getRequiredAttendees
                :locale-id                         .getLocaleId)))

                ;; these are in git, but not in a tagged release of libpst
                ;; :global-object-id                  .getGlobalObjectId
                ;; :clean-global-object-id            .getCleanGlobalObjectId)))
