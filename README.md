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
