kindly check the branches. this readme is collections from each branch's readme.

## About v1.2.1
What I've demonstrated in v1.2.1 :
<br/><br/>Add changes in Account Microservices:
- Implement custom validation in customerDto
- Implement testing with Junit for customerDto Validation:
    - lifecycle Methods Implementation: @BeforeEach, @AfterEach, @BeforeAll, @AfterAll
    - Implement JUnit extensions:
        - extensions: DurationExtension class, LoggingExtension class,  for test lifecycle duration measurement and logging, use with @ExtendWith
    - Assertion:
        - assertTrue(), assertFalse(), assertEquals(), assertAll(), assertTimeout(), assertThrows(), assertNotNull(), assertNull()<br/><br/>

 ## About v1.2.0
- added new feature with **Spring Cloud Netflix** for internal traffic:
  - since microservices and cloud native applications are dynamic in nature, with service containers are starting and stopping dynamically, the IP adresses are ephemeral, and **Service Discovery** features will automatically maintains routing seamlessly.
  - Essential components are **Eureka server** and **Eureka clients**:
    - quoted from latest Spring Cloud doc (Spring Cloud Netflix 4.2.1): **"When a client registers with Eureka, it provides meta-data about itself — such as host, port, health indicator URL, home page, and other details. Eureka receives heartbeat messages from each instance belonging to a service. If the heartbeat fails over a configurable timetable, the instance is normally removed from the registry."**
    - the above explained that **there is service registry that tracking and storing information about all running instances and when new eureka client started, at first it will register itself to eureka server and when running will send a heartbeat message periodically to the server.**<br/><br/>
- added communication between microservices with **Spring Cloud Open Feign**:
  - It makes writing web service clients easier. Open feign client allow us to just write declarative code -> the approach is similar to JPA repository, **we only need to create interface and abstract method and behind the scene it will already handled the implementations.** one of primary rule to follow is that the abstract method must match exactly with what’s written in the target controller.
  - there is included **implementation of client side load balancing:**
    - as in this project, feign client from account microservice will try to connect to compliance microservices to fetch data, but **first what feign client does is that it will connect to eureka server to fetch the service registry.**
    - if there are three compliance microservices containers are UP and running, it will fetch 3 different IP addresses to compliance service.
    - **how feign client will choose one to connect out of three available IPs are based on the client side load balancing algorithm implemented in Spring Cloud Open Feign.** <br/><br/>
- **containerization:**
  - All microservices in this project except Eureka Server, were **containerized in native image leveraging on GraalVM and Spring Cloud support** (except Eureka Server, which stated in official doc: "Spring Cloud Netflix Eureka Server does not support Spring AOT transformations or native images").
  - images are available in https://hub.docker.com/u/davecaesar
  - project can be run using docker compose I have provided in this version's repository with sequence as follows: database -> configserver -> eurekaserver -> microservices<br/><br/>
- Updated Postman Collection to include new REST API in account microservice which will included fetched data from compliance microservice. read more on http://localhost:8080/swagger-ui/index.html after all services are running.
<img width="1030" alt="Screenshot 2025-04-21 at 22 02 42" src="https://github.com/user-attachments/assets/2a785a6d-e305-4a31-a6ee-ea84f08b02b8" />

<br/><br/>
## Discussions on v1.2.0
In this section I put interesting things I encountered while I work with the project. Any engagement would be greatly appreciated!
1. failed healthcheck test docker compose
  - In above I put the sequence of docker compose to follow because of dependency flow that some containers need other containers to be ready in order to work properly:<br/>
    - database -> configserver -> eurekaserver -> microservices
<br/> This can be configured with 'depends_on' attributes which will be used along with healthcheck test attributes. in this project, I tried the attributes:<br/>
  a. eurekaserver depends on configserver, service must be healthy, then<br/>
  b. microservices (account and compliance) depends on configserver and eurekaserver, service must be healthy<br/>
  c. I exposed configserver actuator health endpoint and use the address for healthcheck test value in docker compose<br/>
  d. It did not work (result configserver is unhealthy and other container never started).
- Some similar problems I found on the web caused by the container image that does not include curl, but as this project's container built using paketo jammy tiny, the default for GraalVM, it includes curl: https://github.com/paketo-buildpacks/jammy-tiny-stack/blob/main/stack/stack.toml <br/> so I end up in creating a separated docker compose.<br/><br/>

2. Spring Cloud Config Server AOT and Native Image Support was very tricky, it needs to add several options that initially I have no idea how to add it to the project that I build. 
<img width="423" alt="Screenshot 2025-04-21 at 18 38 13" src="https://github.com/user-attachments/assets/beca66ae-7da5-4e88-bcc2-6b22549acd6d" />
<br/>after hours of searching, I found that in my case I was using CLI command './gradlew bootBuildImage' to create the docker image, the options were working if passed through this syntax inside configserver's build.gradle file:<br/><br/>
<img width="450" alt="Screenshot 2025-04-21 at 18 44 09" src="https://github.com/user-attachments/assets/e5bb857a-9ea3-4f2a-9690-f809a7eb3df6" /><br/><br/>

3. If you try to see if service discovery and load balancing is working by stopping one of compliance containers, or restarting the containers, it may take a while before it back to work (>30s) and in the meantime you might get either one of this message exceptions which I haven't implement the fallback mechanism: "timeout", "No route to host executing", "feign client instance not found".
This is discussed in official docs and I didn't change the default.
<img width="500" alt="Screenshot 2025-04-21 at 22 30 35" src="https://github.com/user-attachments/assets/e3e253b6-9457-446c-bc55-4e308aa39ed6" />

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
- Update API testing documentation in **Postman collection**<br/><br/>

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




