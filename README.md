# conjode

[![Build Status](https://travis-ci.org/srikanthmanvi/conjode.svg?branch=master)](https://travis-ci.org/srikanthmanvi/conjode)

A [work-in-progress]Clojure library to talk to Apache Geode.

## Usage

#### Create a geode client which connects to a distributed system and does get/put operations.

```clojure
(ns playground
  (:require [conjode.core :as c]
            [conjode.region :as r]))

; connect to locator running on localhost[10334]
(def my-client (c/connect))                       

; alternatively, connect using a geode.properties file
(def my-client (c/connect "<PATH_TO_/geode.properties"))                

; creates a proxy region on the client
(def customer-region 
      (r/create-client-region "CustomerRegion" :proxy my-client)) 
      
; put can take java/clojure literals and clojure keywords as keys and Values
(r/gput 1 "AA" customer-region)
(r/gput "fName" "John" customer-region)
(r/gput :lname "Doe" customer-region)
(r/gput :sex :male customer-region)

; get can take java/clojure literals and clojure keywords as keys
(r/gget 1 customer-region) ;=>"AA"
(r/gget "fName" customer-region) ;=>"John"
(r/gget :lname customer-region) ;=>"Doe"
(r/gget :sex customer-region) ;=>:male

Note: If you want clojure keywords to be stored in geode then clojure.jar should be on the Geode server class path.


```

## License

Copyright © 2016 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
