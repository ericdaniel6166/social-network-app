# social-network-app

![Coverage](.github/badges/jacoco.svg)

# [Getting started](docs/md/getting_started.md)

# Framework/Library

- Spring Boot 2.6.2
- Junit 5
- Jacoco 
    - Code coverage reports generator for Java projects.
- CI (Using GitHub Actions)
    - jacoco-badge-generator
- Spring Cloud Sleuth
    - provides Spring Boot auto-configuration for distributed tracing.
    - enhancing logs, especially in a system built up of multiple services
    - integrates effortlessly with logging frameworks like Logback and SLF4J
    - this project uses Logback
- Flyway
    - perform database migrations
- AuditorAware 
    - auditing
    - tracking every insert, update, and delete operation and storing it
- REST Query Language with RSQL
    - for complex – searching/filtering resources
- JWT
    - JSON Web Token or JWT
    - The tokens contain claims that are encoded as a JSON object and are digitally signed using a private secret or a public key/private key pair.
    - This project uses a public key/private key pair
- Swagger 2
    - generate the REST API documents for RESTful web services
- ModelMapper
    - map objects to each other
- Jackson 
    - ObjectMapper to serialize Java objects into JSON and deserialize JSON string into Java objects.





# Screenshots
1. Preview Swagger

http://localhost:8080/swagger-ui/

default port: 8080

![Preview Postman_Collections](https://github.com/ericdaniel6166/social-network-app/blob/main/docs/images/Preview_Swagger.png)

2. Preview Jacoco report

- Run maven clean test

![Jacoco_Step1](https://github.com/ericdaniel6166/social-network-app/blob/main/docs/images/jacoco_step1.png)

- Open report by a browser, destination: target/site/jacoco/index.html

![Jacoco_Step2](https://github.com/ericdaniel6166/social-network-app/blob/main/docs/images/jacoco_step2.png)

- Preview Jacoco report

![Jacoco_preview](docs/images/jacoco_preview.png)