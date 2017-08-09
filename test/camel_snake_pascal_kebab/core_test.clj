(ns camel-snake-pascal-kebab.core-test
  (:require [clojure.test :refer :all]
            [camel-snake-pascal-kebab.core :refer :all]))
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
  words
  [input]
  (clojure.string/split (name input) #"-"))

(defn format
  [input _ format-to]
  (let [words (words input)
        output-function-name (symbol (str "to-" (name format-to)))
        output-function (get (ns-publics 'camel-snake-pascal-kebab.core-test)
                   output-function-name)]
    (keyword
      (output-function words)))
  )

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
    (format :hello-koko :using :pascal-case) => :HelloKoko))
