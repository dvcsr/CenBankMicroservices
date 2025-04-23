package com.cenbank.account.validation;

import com.cenbank.account.dto.CustomerDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashMap;
import java.util.Map;

public class PhoneCountryCodeValidator implements ConstraintValidator<ValidPhoneCountryCode, CustomerDto> {
    private static final Map<String, String> COUNTRY_CODES = new HashMap<>();

    static {
        COUNTRY_CODES.put("US", "1");
        COUNTRY_CODES.put("UK", "44");
        COUNTRY_CODES.put("ID", "62");
    }


    @Override
    public boolean isValid(CustomerDto customerDto, ConstraintValidatorContext constraintValidatorContext) {
        if (customerDto.getCountryId() == null || customerDto.getPhoneNumber() == null) {
            return false;
        }
        String countryCode = COUNTRY_CODES.get(customerDto.getCountryId());
        return countryCode != null && customerDto.getPhoneNumber().startsWith(countryCode);
    }
}
