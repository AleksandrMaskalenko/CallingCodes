# Introduction #
"Calling code application" - microservice to determine the country by phone number. User enters a phone number and system validates it and displays a country or error message. 

# Requirements #

* Maven (tested with version 3.6.2)
* JDK 8 (tested with openjdk version "1.8.0_222")

# Libraries used #

* Spring Boot
* Maven
* Lombok
* JUnit 5

# Installation #
## Clone the repository: ##

```
git clone git://github.com/AleksandrMaskalenko/CallingCodes.git
```
or just download .zip file and unpack them


## Build project: ##

```
mvn clean package
```

## For application start up: ##

```
java -jar target/callingcodes-0.0.1-SNAPSHOT.jar
```

Test in the browser by link:

http://localhost:8080