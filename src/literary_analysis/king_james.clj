(ns literary-analysis.king-james
  (:require [clojure.java.io :as io]))

(def bible (slurp "https://www.gutenberg.org/files/10/10-0.txt"))

(defn load-res [res]
  (-> res
      io/resource
      slurp))

(def words (re-seq #"[\w|']+" bible))

(count words)

(take 30 words)

;; 50 most frequently used words
;;------------------------------
(def common-words 
  (-> "common-words.txt" 
      io/resource 
      io/reader
      line-seq
      set))

(->> words
     (map clojure.string/lower-case)
     (remove common-words)
     frequencies
     (sort-by val)
     (take-last 50))

;; 20 longest words
;------------------
(->> words
     distinct
     (sort-by count)
     (take-last 30))

;; longest palindrome
;;-------------------
(defn palindrome? [coll]
  (= (seq coll) (reverse coll)))

(palindrome? "racecar")

(->> words
     distinct
     (filter palindrome?)
     (sort-by count)
     (take-last 20))