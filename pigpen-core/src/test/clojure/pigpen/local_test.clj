(ns pigpen.local-test
  (:require [clojure.test :refer [run-tests]]
            [pigpen.functional-test :as t :refer [TestHarness]]
            [pigpen.functional-suite :refer [def-functional-tests]]
            [pigpen.core :as pig]
            [pigpen.local :as local]))

(def prefix "build/functional/local/")

(.mkdirs (java.io.File. prefix))

(def-functional-tests "local"
  (reify TestHarness
    (data [this data]
      (pig/return data))
    (dump [this command]
      (local/dump command))
    (file [this]
      (str prefix (gensym)))
    (read [this file]
      (clojure.string/split-lines
        (slurp file)))
    (write [this lines]
      (let [file (t/file this)]
        (spit file (clojure.string/join "\n" lines))
        file))))

