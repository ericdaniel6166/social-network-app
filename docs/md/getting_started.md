##### [Back to README](/README.md)

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

5. Account (username/password)
    - ROLE_ROOT_ADMIN 
        - rootAdmin/P@ssw0rd
    - ROLE_ADMIN 
        - admin/P@ssw0rd
        - admin1/P@ssw0rd
    - ROLE_MODERATOR
        - moderator/P@ssw0rd
        - moderator1/P@ssw0rd
    - ROLE_USER
        - user/P@ssw0rd
        - user1/P@ssw0rd
    
    
    

