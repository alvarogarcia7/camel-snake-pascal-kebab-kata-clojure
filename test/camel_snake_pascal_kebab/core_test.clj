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
  :hello_koko)

(defn format
  [input _ format-to]
  (let [words (clojure.string/split (name input) #"-")]
    (keyword
      (cond
        (= format-to :camel-case) (to-camel-case words)
        (= format-to :pascal-case) (to-pascal-case words)
        (= format-to :snake-case) (to-snake-case words)
        )))
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
