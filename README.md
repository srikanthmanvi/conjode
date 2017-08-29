# conjode

[![Build Status](https://travis-ci.org/smanvi-pivotal/conjode.svg?branch=master)](https://travis-ci.org/smanvi-pivotal/conjode)

A [work-in-progress]Clojure library to talk to Apache Geode.

## Usage

Get a geode client which connects to a Geode cluster running on defaults on localhost.

```clojure
; connect to locator running on default port
(def my-client (conjode.core/connect))                       

; alternatively, connect using a geode.properties file
(def my-client (conjode.core/connect "<PATH_TO_/geode.properties"))                

; creates a proxy region on the client
(def customer-region 
      (conjode.region/create-client-region "CustomerRegion" :proxy my-client)) 
      
; put can take java/clojure literals and clojure keywords as keys and Values
(conjode.region/gput 1 "AA" customer-region)
(conjode.region/gput "fName" "John" customer-region)
(conjode.region/gput :lname "Doe" customer-region)
(conjode.region/gput :sex :male customer-region)

; get can take java/clojure literals and clojure keywords as keys
(conjode.region/gget 1 customer-region) ;=>"AA"
(conjode.region/gget "fName" customer-region) ;=>"John"
(conjode.region/gget :lname customer-region) ;=>"Doe"
(conjode.region/gget :sex customer-region) ;=>:male

Note: If clojure keywords are to be stored in geode then clojure.jar should be on the Geode server class path.


```

## License

Copyright © 2016 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
