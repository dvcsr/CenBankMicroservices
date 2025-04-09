package com.cenbank.compliance;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "REST API: Compliance Microservices",
                description = "Attention: FOR INTERNAL USE ONLY",
                version = "v1.1",
                contact = @Contact(
                        name = "David",
                        email = "dputracaesars@gmail.com",
                        url = "https://www.github.com/dvcsr"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.arc.dev"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description =  "External resources: What is microservices?",
                url = "https://www.google.com/search?q=what+is+microservices"
        )
)
public class ComplianceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComplianceApplication.class, args);
    }

}
