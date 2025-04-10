package com.cenbank.account.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "account")
public record HelpContactDto(String detail, Map<String, String> contactDetails,
                             List<String> availability) {
}
