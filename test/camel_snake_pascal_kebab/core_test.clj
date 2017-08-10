(ns camel-snake-pascal-kebab.core-test
  (:require [clojure.test :refer :all]
            [camel-snake-pascal-kebab.core :refer :all]
            [midje.sweet :refer :all]
            [clojure.test.check.generators :as gen]
            [clojure.test.check :as tc]
            [clojure.test.check.properties :as prop]
            ))
(defn capitalize [word]
  (str (Character/toUpperCase (first word))
       (apply str (rest word))))

(defn to-camel-case [words]
  (let [rest-words-formatted (apply str (map capitalize (rest words)))]
    (str
      (first words)
      rest-words-formatted)))
(defn to-pascal-case [words]
  (let [words-formatted (apply str (map capitalize words))]
    words-formatted))

(defn
  to-snake-case
  [words]
  (->>
    words
    (map clojure.string/lower-case)
    (clojure.string/join "_")))

(defn
  to-kebab-case
  [words]
  (->>
    words
    (map clojure.string/lower-case)
    (clojure.string/join "-")))

(defn
  words
  [input]
  (re-seq #"[A-Z]?[a-z]+" (name input)))


(defn format-words
  [words format-to]
  (let [function-name (symbol (str "to-" (name format-to)))
        output-function (get (ns-publics *ns*) function-name)]
    (keyword
      (output-function words))))

(defn
  format
  [input _ format-to]
  (format-words (words input) format-to))


(def kebab-properties
  (prop/for-all
    [names (gen/not-empty (gen/vector (gen/not-empty gen/string-alphanumeric)))]
    (let [output (format-words names :kebab-case)
          length-of-words (apply + (map count names))
          n-minus-1-words (dec (count names))
          expected-length (+ n-minus-1-words length-of-words)]
      ;(println (str names " got converted into " output))
      (symbol? output) => true                              ; is a symbol
      (re-matches #"(\\S+-?)+" (name output)) => true       ; follows the regex
      (= (count (name output)) expected-length))            ; has the correct length
    ))

(facts
  "converting in different cases"
  (fact
    "from kebab case"
    (format :hello-koko :using :camel-case) => :helloKoko
    (format :hello-koko-pepe :using :camel-case) => :helloKokoPepe
    (format :hello-pepe :using :camel-case) => :helloPepe
    )
  (fact
    "from kebab to snake case"
    (format :hello-koko :using :snake-case) => :hello_koko)
  (fact
    "from pascal case to snake case"
    (format :Hello-Koko :using :snake-case) => :hello_koko)
  (fact
    "from kebab to pascal case"
    (format :hello-koko :using :pascal-case) => :HelloKoko)
  (fact
    "to kebab case"
    (format :hello-koko :using :kebab-case) => :hello-koko
    (format-words ["hello" "kokO"] :kebab-case) => :hello-koko
    (format-words ["HELLO" "KOKO"] :kebab-case) => :hello-koko
    (:result (tc/quick-check 100 kebab-properties)) => true
    )

  (facts
    "detect words in any input format"
    (fact
      "in camel case"
      (words :helloWorld) => ["hello" "World"])
    (fact
      "in snake case"
      (words :hello_world) => ["hello" "world"])
    (fact
      "in pascal case"
      (words :HelloWorld) => ["Hello" "World"])
    (fact
      "in kebab case"
      (words :hello-world) => ["hello" "world"])))
