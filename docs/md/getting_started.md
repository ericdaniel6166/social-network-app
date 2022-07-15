##### [Back to index](/README.md)

##### Getting started

1. Download and import Postman collections and environment: 

- Link: [/docs/postman](/docs/postman)

- Postman Collections with all apis, environment and example response for certain scenarios

![Preview Postman_Collections](/docs/images/Preview_Postman_collections.png)

2. Setup database

- Download and install Microsoft SQL Server 2019 database and SQL Server Management Studio: [Link](https://youtu.be/QsXWszvjMBM)

- Create a database with name "social_network_app" 

![Database](/docs/images/database.png)

- Config database username and password in src/main/resources/application-local.yml

![Database_config](/docs/images/database_config.png)

3. Setup mail

- Use mailtrap: https://mailtrap.io/

- Config mail username and password

![Config_mailtrap](/docs/images/config_mailtrap.png)

4. Running project: 

Please choose active profile = local

- Running with IntelliJ

![Run_Intellij](/docs/images/run_intellij.png)

- Running with Maven cmd 

mvnw spring-boot:run -Dspring-boot.run.profiles=local

![Run_Maven_cmd](/docs/images/run_maven_cmd.png)

5. Account with a ROLE_ADMIN (username/password)

admin

P@ssw0rd