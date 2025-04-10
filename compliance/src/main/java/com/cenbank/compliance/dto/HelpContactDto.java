package com.cenbank.compliance.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "compliance")
public record HelpContactDto(String detail, Map<String, String> contactDetails,
                             List<String> availability) {
}
