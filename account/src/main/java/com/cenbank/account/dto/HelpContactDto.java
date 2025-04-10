package com.cenbank.account.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "account")
@Getter
@Setter
public class HelpContactDto{
    private String detail;
    private Map<String, String> contactDetails;
    private List<String> availability;
}
