package com.cenbank.account.config;

import com.cenbank.account.dto.HelpContactDto;
import jakarta.validation.Validator;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.sql.DataSource;

@ImportAutoConfiguration(exclude = {
        FeignAutoConfiguration.class,
        EurekaClientAutoConfiguration.class
})
@TestConfiguration
public class TestConfigurations {
       @Primary
        @Bean
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder()
                    .setType(EmbeddedDatabaseType.H2)
                    .build();
        }

//    @Bean
//    public HelpContactDto helpContactDto() {
//        HelpContactDto dto = new HelpContactDto();
//        // Set any test values you need
//        return dto;
//    }
//
//    @Bean
//    public String buildVersion() {
//        return "1.0.0-TEST";
//    }
//
//    @Bean
//    public Validator validator() {
//        return new LocalValidatorFactoryBean();
//    }
}


