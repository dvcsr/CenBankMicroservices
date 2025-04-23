package com.cenbank.account.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CustomerDtoValidationTest {
private static Validator validator;
    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testCustomerDtoValidation_NoViolations() {
        CustomerDto customer = new CustomerDto();
        customer.setCountryId("ID");
        customer.setPhoneNumber("628123456789");
        customer.setName("John Doe");
        customer.setEmail("john@example.com");
        customer.setAge(25);

        var violations = validator.validate(customer);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void whenInvalidPhoneForCountry_thenViolation() {
        CustomerDto customer = new CustomerDto();
        customer.setCountryId("US");
        customer.setPhoneNumber("628123456789"); // Indonesian number with US country
        customer.setName("John Doe");
        customer.setEmail("john@example.com");
        customer.setAge(25);

        var violations = validator.validate(customer);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void whenInvalidCountryID_thenViolation() {
        CustomerDto customer = new CustomerDto();
        customer.setCountryId("NZ");
        customer.setPhoneNumber("628123456789"); // Indonesian number with US country
        customer.setName("John Doe");
        customer.setEmail("john@example.com");
        customer.setAge(25);

        var violations = validator.validate(customer);
        assertFalse(violations.isEmpty());
    }
}
