# conjode

[![Build Status](https://travis-ci.org/srikanthmanvi/conjode.svg?branch=master)](https://travis-ci.org/srikanthmanvi/conjode)
[![Clojars Project](https://img.shields.io/clojars/v/geode/conjode.svg)](https://clojars.org/geode/conjode)

A minimalist clojure client for [Apache Geode](http://geode.apache.org/).

## Features

- conjode should be used on the client side of the Geode distributed system.
- Since clojure has a [repl](https://en.wikipedia.org/wiki/Read%E2%80%93eval%E2%80%93print_loop), conjode can be used for exploratory  analysis of data in Apache Geode. 
- Broadly speaking conjode supports
   - Region operations (Region is where Geode stores data. Region implements java.util.concurrent.ConcurrentMap)
        - Creating/Deleting client regions.
        - Data insertion/removal. Supports clojure data types and Java objects.
        
    - Querying
        - Execute query with/without params.

    - Function execution
        - Execute Apache Geode functions (Geode functions are executed on the server side).
   


## Artifacts

conjode artifacts are released to [conjode](https://clojars.org/geode/conjode)

##### Leiningen/Boot

```clojure
[geode/conjode "0.1.0-SNAPSHOT"]
```
##### Gradle

```gradle
compile "geode:conjode:0.1.0-SNAPSHOT"
```

##### Maven

```maven
<dependency>
  <groupId>geode</groupId>
  <artifactId>conjode</artifactId>
  <version>0.1.0-SNAPSHOT</version>
</dependency>
```

Alternatively, you can `git clone` conjode repo.

## Usage

#### Connecting to a Geode Distributed System

```clojure
(ns playground
  (:require [conjode.core :as c]
            [conjode.region :as r]))

; connect to locator running on localhost[10334]
(def my-client (c/connect))

; alternatively, connect using a geode.properties file
(def my-client (c/connect "<PATH_TO_/geode.properties"))

```

#### Creating client side region

```clojure
; creates a proxy/local/caching proxy region on the client
(def customer-region
  (r/create-client-region "CustomerRegion" :proxy my-client))

; OR get hold of a existing region which was loaded through client-cache.xml via
; geode.properties
(def customer-region (r/get-region "CustomerRegion" my-client))

```

#### put/get entries using functions in conjode.region namespace.

```clojure
; put key-values in a region. ggput takes key, value, region as arguments.
(r/gput 1 "AA" customer-region)
(r/gput "fName" "John" customer-region)
(r/gput :lname "Doe" customer-region)

; gput-all puts a map in a region
(def customer-map
  {1 "John"
   2 "Sam"
   3 "Sri"})

(r/gput-all customer-map customer-region)

; get values from a region.
(r/gget 1 customer-region)                        ; "AA"
(r/gget :lname customer-region)                   ; "Doe"

; ggetAll takes a set of keys and region
(r/ggetAll #{1 2 3} customer-region)              ;{1 "John", 2 "Sam", 3 "Sri"}


; Some of the other functions in conjode.region namespace, not a comprehensive list
(r/empty-region? customer-region)
(r/all-keys customer-region)                      ;Returns keyset for the region
(r/remove-entry 1 customer-region)
(r/size customer-region)
(r/remove-all #{1 2 3} customer-region)           ;Removes entries for the keys
(r/remove-value 1 customer-region)                ;Leaves key intact
(r/values customer-region)
(r/destroy-client-region "CustomerRegion" my-client)
(r/clear-region "CustomerRegion" my-client)

```

#### Working with Java Objects

```clojure
; put a java object in customerRegion, "Customer." invokes the constructor of Customer
(r/gput 123 (Customer. "Albert" "Einstein" 12334324) customer-region)

; get the object from region
(r/gget 123 customer-region)

```

#### Executing Queries

Functions related to queries are in the namespace `conjode.query`.

```clojure
(ns playground
  (:require [conjode.core :as c]
            [conjode.region :as r]
            [conjode.query :as q))
            
(r/clear-region customer-region)
(r/gput 100 (Customer. "Albert" "Einstein" 12334324) customer-region)
(r/gput 101 (Customer. "John" "Doe" 45276675) customer-region)
(r/gput 102 (Customer. "John" "Smith" 432540012) customer-region)

(q/execute-query "select * from /CustomerRegion" my-client)

; execute query returns list of results (list of Customer objects in the above case)

;[#object[org.conjode.java.Customer
;         0x4ef2a446
;         "Customer{firstName='John', lastName='Doe', address='null', state='null', age=0, ssn=45276675}"]
; #object[org.conjode.java.Customer
;         0x54668b90
;         "Customer{firstName='Albert', lastName='Einstein', address='null', state='null', age=0, ssn=12334324}"]
; #object[org.conjode.java.Customer
;         0x5ead8aec
;         "Customer{firstName='John', lastName='Smith', address='null', state='null', age=0, ssn=432540012}"]]

```

List of params can be passed to the queries as shown below.

```clojure
(q/execute-query "select * from /CustomerRegion where firstName=$1 and lastName=$2" ["John" "Smith"] my-client)

; The above returns 1 entry
;[#object[org.conjode.java.Customer
;         0x3706ff36
;         "Customer{firstName='John', lastName='Smith', address='null', state='null', age=0, ssn=432540012}"]]


```

Clojure maps as query results.

```clojure
(r/clear-region customer-region)
(r/gput 101 {:fname "Marie" :lname "Curie" :state "NC"} customer-region)
(r/gput 102 {:fname "John" :lname "Doe" :state "NC"} customer-region)
(r/gput 103 {:fname "John" :lname "Doe" :state "CA"} customer-region)

(q/execute-query "select * from /CustomerRegion" my-client)

;[{:fname "Marie", :lname "Curie", :state "NC"}
; {:fname "John", :lname "Doe", :state "CA"}
; {:fname "John", :lname "Doe", :state "NC"}]
```

#### Executing Functions

If you have functions deployed on the server side, you can invoke the functions from the client side as below.
In the example below "TestFunction" is a function deployed on the server which returns list of strings.
```clojure
(ns playground
  (:require [conjode.core :as c]
            [conjode.region :as r]
            [conjode.function :as f :refer :all])
            
(f/execute-function-on-server "TestFunction" my-client)
;; ["Hello" " world." " Have a good day!" "true"]
```


#### Note: 
If you want clojure keywords to be stored in geode then clojure.jar should be on the Geode server class path, start geode server as below

```shell
gfsh>start locator --name=locator1 --classpath=/Users/smanvi/Software/clojure-1.8.0/clojure-1.8.0.jar
```

#### Running Tests
As conjode is a wrapper around Apache Geode java client, and as clojure makes code concise, the tests in conjode are more of integration tests than units tests. To run the tests for each namespace you should have a distributed system up and running. A distributed stystem can be started by executing the script `conjode/run/startServer.sh`. This script brings up a server, locator, creates some regiongs, deploys a jar file which has java domain classes needed for testing and a simple function.
Tests in each names can be run by executing the below

```clojure
(in-ns 'conjode.region-test)
(run-tests)

```


## License

Copyright © 2016 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
