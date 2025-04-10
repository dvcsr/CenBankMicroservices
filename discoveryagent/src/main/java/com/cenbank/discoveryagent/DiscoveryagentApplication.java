package com.cenbank.discoveryagent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryagentApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryagentApplication.class, args);
    }

}
