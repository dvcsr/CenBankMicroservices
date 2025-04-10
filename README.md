## About v1.0.0
This is the first version of the microservices. The services are simplified model dealing with customer personal information, their account, and their credibility, which inspired by a protocol common in financial services.<br/>
<br/>
<img align="right" width="320" alt="kyc" src="https://github.com/user-attachments/assets/7f0743f5-66b0-46fa-ac77-3e426b4728f9" />
<br/>
- The microservices built with **Domain Driven Design** approach, separating two **bounded contexts**: account and compliance.
- The application also built with **Onion architecture**, as layers divided based on domain model, repository, infrastructure.
- It follows the premise of **externalize infrastructure and write adapter code so that the infrastructure does not become tightly coupled.**
- For this version, there is no yet communication between the two services, as it will be implemented in the later version.
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
  - working with **Spring Data JPA** with repository interface and @Repository annotation and declaring query methods
- Service layer:
  - working with abstraction of business process/logic with **service interface, implementation and @Service annotation** 
- Infrastructure layer:
  - **handling exceptions** for robust REST controller: GlobalExceptionHandler, custom-specific-exceptions, its related annotations: @ControllerAdvice, @ExceptionHandler
  - working with REST-related annotations:
    - @RestController, @RequestBody, @RequestMapping and its shortcut annotation http mapping,
    - @Validated, @Valid, @RequestParam, **@Pattern with regex**
    - working with API prefix configuration and **ResponseEntity**
  - working with docker compose to create **PostgreSQL container** for the application.
 <br/>
<img width="1090" alt="apidoc" src="https://github.com/user-attachments/assets/95507e99-912a-48fc-8682-33e8190ee960" />
