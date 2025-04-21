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
<img width="450" alt="Screenshot 2025-04-21 at 18 44 09" src="https://github.com/user-attachments/assets/e5bb857a-9ea3-4f2a-9690-f809a7eb3df6" /><br/>



