# pst

A little library which wraps [java-libpst](https://github.com/rjohnsondev/java-libpst) - provides a read-only interface to ["PFF"](http://forensicswiki.org/wiki/Personal_Folder_File_%28PAB,_PST,_OST%29) files. 

## Usage

An example of recursively walking a PFF database is given in `pst.core`. It can be run with

    $ lein run <path-to-file.pst> <verbose>

`verbose` is optional and will dump full message metadata and contents if set.

More example usage:

``` clojure
(:require
   [pst.message       :as pm]
   [pst.message-store :as ms]
   [pst.folder        :as pf]
   [pst.archive       :as pa]
   [pst.attachment    :as pat]
   [pst.recipient     :as pr]))

(def archive (pa/archive "test/resources/sample1.pst"))

(:display-name (:message-store archive))  ;; -> "sample1"

(:message-count (:root-folder archive))   ;; -> 0

(:subfolder-count (:root-folder archive)) ;; -> 4

(def subfs (pf/subfolders (:root-folder archive)))
(map :display-name subfs)
;; -> ("Top of Outlook data file" "Search Root" "SPAM Search Folder 2" "ItemProcSearch")

(def outlook (first subfs))
(def outlooks (pf/subfolders outlook))
(map vector
     (map :display-name outlooks)
     (map :message-count outlooks))
;; -> (["Deleted Items" 0] ["Sample1" 1])

(def sample (second outlooks))
(:message-count sample)     ;; -> 1

(def sample-message (first (pf/messages sample)))
(:is-read sample-message)   ;; -> true
(:subject sample-message)   ;; "Here is a sample message"

(:attachment-count sample-message) ;; -> 1
(pat/save (first (pm/attachments sample-message)) "~/sample.jpg")

(def sample-recipient (first (pm/recipients sample-message)))
(:smtp-address sample-recipient) ;; -> "terrymah@microsoft.com"
```

Contacts, appointments, messages, and attachments are supported. See `pst.core`.

## License

Copyright © 2015 Joshua Thayer <joshua@firstlook.org>

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
