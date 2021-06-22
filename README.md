## alexandria-Inventory
> Spring Inventory Management (Case Study)

A RESTFul micro-service for the management of the Inventory of Alexandria Online Shop.


## Motivation
This project is aimed on enforcing the knowledge of Spring Framework, REST services
and related technologies with emphasis on testability.


## Technologies
Project is created with:
* Java 11
* Maven
* Spring Boot 2.5.1
* Spring Web
* Spring Data
* Spring Security
* Spring Test DBUnit 1.3.0
* Jackson 2.11.1
* Swagger 3.0.0
* Lombok 1.18.20


## Features
- [x] Inventory management
- [x] Stock management
- [x] Security management (JSON authentication)


## Setup
alexandria-Inventory is a [Spring Boot](https://spring.io/guides/gs/spring-boot) application built using [Maven](https://spring.io/guides/gs/maven/). You can build a jar file and run it from the command line:


```
git clone https://github.com/niolikon/alexandria-Inventory.git
cd alexandria-Inventory
./mvnw package
java -jar target/*.jar
```

## Documentation
The exported RESTFul APIs are documented using [Swagger framework](https://swagger.io/), you can access the provided documentation by running alexandria-Inventory and opening [Swing UI](http://localhost:8080/inventory/swagger-ui/) on your browser.

<img src="alexandria-Inventory_Swagger-Capture.jpg">

Please use the <b>Authorize</b> button to test the CRUD operations with different user profiles.


# License

The alexandria-Inventory (Case Study) application is released under [MIT License](LICENSE).
