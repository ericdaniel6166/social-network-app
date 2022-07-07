# social-network-app

# Getting started

1. Download and import Postman collections and environment: 

Link: https://github.com/ericdaniel6166/social-network-app/tree/main/docs/postman

Postman Collections with all apis, environment and example response for certain scenarios

![Preview Postman_Collections](https://github.com/ericdaniel6166/social-network-app/blob/main/docs/images/Preview_Postman_collections.png)

2. Download and install Microsoft SQL Server 2019 database and SQL Server Management Studio: [Link](https://youtu.be/QsXWszvjMBM)
3. Create a database with name "social_network_app" 

![Database](https://github.com/ericdaniel6166/social-network-app/blob/main/docs/images/database.png)

4. Config your database username and password 

Config database username and password in src/main/resources/application-local.yml

![Database_config](https://github.com/ericdaniel6166/social-network-app/blob/main/docs/images/database_config.png)

5. Running project: 

Please choose active profile = local

- Running with IntelliJ

![Run_Intellij](https://github.com/ericdaniel6166/social-network-app/blob/main/docs/images/run_intellij.png)

- Running with Maven cmd 

mvnw spring-boot:run -Dspring-boot.run.profiles=local

![Run_Maven_cmd](https://github.com/ericdaniel6166/social-network-app/blob/main/docs/images/run_maven_cmd.png)

# Framework/Library

- Spring Boot 2.6.2
- Spring Cloud Sleuth
- AuditorAware 
    - auditing
    - tracking every insert, update, and delete operation and storing it
- REST Query Language with RSQL
- JWT
- Swagger 2
- ModelMapper
- Jackson 
- Junit 5



# Screenshots
1. Preview Swagger

http://localhost:8080/swagger-ui/
default port: 8080

![Preview Postman_Collections](https://github.com/ericdaniel6166/social-network-app/blob/main/docs/images/Preview_Swagger.png)