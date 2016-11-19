(ns conjode.reference)


(if true
  (do (println "Success!")
      "By Zeus's hammer!")
  (do (println "Failure!")
      "By Aquaman's trident!"))

(when (= 1 1)
  (println "FirstOne")
  (println "Second One")
  true)

;nil and false are considered logic false
(nil? false)
(= false nil)

;or  - returns the first truthy or the last values
;and - returns the first falsey value, if no falsey value then the last truthy value
(or (= 1 2) (= 2 2) false)
(and (= 1 1) (= 1 2) true)

;maps
(hash-map :a 1 :b 2)
(get {:a {"name" "hello" "age" 123}} :a)
(get {:a {"name" "hello" "age" 123}} :b "defaultValue")
(get-in {:a {"name" "hello" "age" 123}} [:a "age"] "NotFound")
;Use map as a function, and the searchKey as the argument
({:name "myself" "age" 123} "age")
;or use keyword to lookup from map
(:addressed {:name "myself" :age 123 :address {:city "Cary" :zip 27519}} "NOTFOUND")

;Vector is arrays, its a o based index collection
[1 2 3]
(get [1 2 3] 0)                                             ;gets 0th element
(get [:a {:name "myself" :city "Cary"}] 1)
(get (vector :a {:name "myself" :city "Cary"}) 1)
(conj [1 2 3] 4)

;List is a sequence which adds elements to the begining, and uses nth instead of get
'(1 2 3)
(nth '(1 2 3 4) 0)
(nth (list [0 1 2] [:a :b :c] ["a" "b" "c"]) 1)
(conj '(2 3 4) 1)

;Sets can be created from existing lists and vectors using set function
#{:a 1 "abc"}
(hash-set 1 2 4 1)
(contains? #{1 2 4} 1)                                      ;returns bool
(get #{:a :b :c} :a)                                        ;Lookup Sets, returns element
(:a #{:a :b})                                               ;Lookup Sets, returns element

;map creates a list by applying a function to each member of a collection
(map inc #{1 2 3})


(defn multi-arity-fun
  "Does different things based on number of args"
  ([] (multi-arity-fun "FirstName" "LastName"))
  ([a b] (println "Called with 2 args : " a b))
  ([a b c] (println "Called with 3 args :" a b c))
  ([a b c & etc] (println "Called with more than 3" a b c etc)))

;Destruction allows to destructure the given collection and to bind each of those to given symbols
;Destructure vectors by specifying binding symbols within a vecotor of args
(defn multiple-choices
  [[first-choice second-choice & unimportant-choices]]
  (println "Your First Choice is " first-choice)
  (println "Your Second Choice is " second-choice)
  (println "Your Rest of the Choices are " (clojure.string/join "," unimportant-choices)))

;Destructure maps by specifying binding symbols within a vecotor of args
;associate the name arg1 with the value corresponding to the key :name
(defn multiple-choices-map-dest
  [{first-choice :fchoice second-choice :schoice :as unimportant-choices}]
  (println "Your First Choice is " first-choice)
  (println "Your Second Choice is " second-choice)
  (println "Your All Choices are " (clojure.string/join "," unimportant-choices)))

;(conjode.reference/multiple-choices-map-dest {:fchoice "fname" :schoice "second name" :tchoice "thirdName" :fname "fourthName"})

;Just to destructure by using keywords out of a map; the map should have keywords as keys
(defn multiple-choices-map-dest-shortcut
  [{:keys [fchoice schoice] :as unimportant-choices}]
  (println "Your First Choice is " fchoice)
  (println "Your Second Choice is " schoice)
  (println "Your All Choices are " (clojure.string/join "," unimportant-choices)))
;(conjode.reference/multiple-choices-map-dest {:fchoice "fname" :schoice "second name" :tchoice "thirdName" :fname "fourthName"})

;Anonymous function
(#(* % 3) 4)
(map #(* % %) [1 2 3])

(let [a1 123 b 456]
  (println a1 b))

(clojure.set/rename-keys {:a 1 :b 2} (map #{} ) )


