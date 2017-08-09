(ns camel-snake-pascal-kebab.core-test
  (:require [clojure.test :refer :all]
            [camel-snake-pascal-kebab.core :refer :all]))
(defn format-word [word]
  (str (Character/toUpperCase (first word))
               (apply str (rest word))))

(defn to-camel-case [text]
  (let [words (clojure.string/split (name text) #"-")
        first-word (first words)
        rest-words (rest words)
        rest-words-formatted (apply str (map format-word rest-words))]
    (keyword (str
               first-word
               rest-words-formatted))))
(defn to-pascal-case [text]
  (let [words (clojure.string/split (name text) #"-")
        words-formatted (apply str (map format-word words))]
    (keyword words-formatted))
  )

(defn format
  [input _ format-to]
  (cond
    (= format-to :camel-case) (to-camel-case input)
    (= format-to :pascal-case) (to-pascal-case input)
    :else :hello_koko
    )
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
    "from kebab to pascal case"
    (format :hello-koko :using :pascal-case) => :HelloKoko))
