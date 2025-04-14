kindly check the branches. this readme is collections from each branch's readme.

## About v1.0.0
This is the first version of the microservices. The services are simplified model dealing with customer personal information, their account, and their credibility, which inspired by a protocol common in financial services.<br/>
<br/>
<img align="right" width="320" alt="kyc" src="https://github.com/user-attachments/assets/7f0743f5-66b0-46fa-ac77-3e426b4728f9" />
<br/>
- The microservices built with **Domain Driven Design** approach, separating two **bounded contexts**: account and compliance.
- The application also built with **Onion architecture**, as layers divided based on domain model, repository, infrastructure.
- It follows the premise of **externalize infrastructure and write adapter code so that the infrastructure does not become tightly coupled.**
- For this version, there is no yet communication between the two services, as it will be implemented in the later version.
- Final containerization of the microservices will be in a later version.
<br/><br/>
## What I've demonstrated through this project in v1.0.0:
- Built the project with **Gradle** and to be compatible with **GraalVM**
- Created a **Spring Boot web application REST API** leveraging on **springdoc openapi for documentation** and **docker compose for PostgreSQL database** setup.
- Leveraging on **Spring Data JPA Auditing** to transparently keep track of who created or changed an entity and when the change happened.
- Domain layer:
    - working with @Entity, @MappedSuperclass for base entity, Lombok annotations, Hibernate annotations,
    - working with DTO, constraint annotations, custom entity-DTO mapper,
    - using @EnableJpaAuditing to enable **Spring Data JPA Auditing** and use @EntityListeners in base entity for persistence data logs (when and who change the data). 
- Repository layer:
  - working with **Spring Data JPA** with repository interface, declaring query methods, and using @Repository, @Modifying, @Transactional annotations
- Service layer:
  - working with abstraction of business process/logic with **service interface, implementation and @Service annotation** 
- Infrastructure layer:
  - **handling exceptions** for robust REST controller: GlobalExceptionHandler, custom-specific-exceptions, its related annotations: @ControllerAdvice, @ExceptionHandler
  - working with REST-related annotations:
    - @RestController, @RequestBody, @RequestMapping and its shortcut annotation http mapping,
    - @Validated, @Valid, @RequestParam, **@Pattern with regex**
    - working with API prefix configuration and **ResponseEntity**
  - working with docker compose to create **PostgreSQL container** for the application.
  - documentation of API testing in **Postman collection.**
 <br/>
<img width="1090" alt="apidoc" src="https://github.com/user-attachments/assets/95507e99-912a-48fc-8682-33e8190ee960" />
<img width="755" alt="v100" src="https://github.com/user-attachments/assets/51a12adf-3c71-4aed-8c61-d02200c92dc0" />
<br/><br/>


## About v1.1.0
Major feature added in this version is the implementation of externalized configurations by creating **Spring Cloud Config Server** and adding **Config Client** dependency to the existing microservices<br/>
<br/>
<img width="467" align="left" alt="diagv110" src="https://github.com/user-attachments/assets/23762b44-db22-4fa3-aaaa-a806c6a0d8ac" />
<br/>
In this version each microservices has **Spring Profile** configuration
  - spring.profiles.active=*value*
  - {in this demo available values: "qa", "prod" and the default value of "default"}
<br/><br/><br/><br/>The application will connect to config server during startup to find the properties value related to the activated profile above.<br/>
<br/><br/>Config server has the value available because config server connect to central repo -> check https://github.com/dvcsr/config-CenBankMicroservices<br/><br/><br/>
<br/><br/>**EXAMPLE**: The concrete example shown in new REST API endpoint in this version. Take an example for account microservice (port 8080):
- activate prod profile by set spring.profiles.active=prod in application configuration properties.
- http://localhost:8080/api/help GET result will correspond with what is inside account-prod.yml
- http://localhost:8080/api/build GET result will correspond with what is inside account-prod.yml
<br/><br/>It shows that
- spring.profiles.active=qa -> result correspond with account-qa.yml
- spring.profiles.active=prod -> result correspond with account-prod.yml
- spring.profiles.active=default -> result correspond with account.yml
<br/><br/>It is possible with the help of **Spring Cloud Config**
<br/><br/>*NOTE*:
- **the config server must be running first** before we can run the microservices **in order for the microservices to works properly** because it will try to connect to config server at startup
- whenever we are making new changes in config files, each time the config server invoked by microservices, it will fetch from the central location, so **config server always give the latest version of config files BUT** in this version, microservices must restart so that it can fire config server API during startup and get the updated value
- in later version, there will be a feature where the microservices will be able to update its value inside the application at runtime whenever there are new changes in central config files, without having to restart or stop the service.
<br/><br/>
## What I've demonstrated through this project in v1.1.0:
- Build config server using Spring Cloud Config Server, work with @EnableConfigServer annotation.
- Setup config server configuration to link with remote central repo (in this demo using Github)
- To microservices: add Spring Profile configuration, add config client dependency, setup necessary configuration to link microservices with the config server with spring.config.import=
- Work with @ConfigurationProperties to bind values from external config files to a class (in this demo a DTO), use @EnableConfigurationProperties to specify the class and enable the feature.
- Created mock config files in central repo with **key-value matches the DTO** and **name the file to follow naming convention protocol that enable this feature to work properly.**
- Create new controller for this demo:
  - shows the feature by using the previously mentioned new DTO in REST API endpoint inside controller
  - demonstrated **other approach of reading config files value to the application using @Value annotation in field and use it in REST API endpoint inside controller.**
- Demonstrated a **feature inside config server to encrypt and decrypt** by using encrypt.key=*value* in config server properties. check:
  - https://github.com/dvcsr/config-CenBankMicroservices/blob/main/compliance-qa.yaml
  - https://github.com/dvcsr/config-CenBankMicroservices/blob/main/account-qa.yml
  - value saved is encrypted, but if we set above files "qa" to be active with spring profile.active=qa, in microservice endpoint it is decrypted and shows a humanly comprehensible data.
- Update API testing documentation in **Postman collection**
<br/><br/>


## About v1.2.0
- add new feature Spring Cloud Netflix: Eureka service registry and client side Service Discovery
- add Spring Cloud Open Feign: in this version there will be communication between microservices powered by Spring Cloud Open Feign.
- work with @FeignClient also enables Spring Cloud LoadBalancer client.
- Update API testing documentation in Postman collection.
<br/><br/>more complete readme To Be Updated




