package com.cenbank.account.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


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
        assertEquals(1, violations.size(), "Should have exactly one violation!");

        ConstraintViolation<CustomerDto> violation = violations.iterator().next();
        System.out.println("Validation failed: " + violation.getMessage());
        assertEquals("Phone number doesn't match the country code", violation.getMessage());
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

        ConstraintViolation<CustomerDto> violation = violations.iterator().next();
        System.out.println("Validation failed: " + violation.getMessage());
    }
}
