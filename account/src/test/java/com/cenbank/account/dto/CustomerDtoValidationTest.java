package com.cenbank.account.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;


public class CustomerDtoValidationTest {
    private static final Logger logger = LoggerFactory.getLogger(CustomerDtoValidationTest.class);
    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        logger.debug("initializing validator for customerDto tests");
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testCustomerDtoValidation_NoViolations() {
        logger.info("Testing valid customer scenario");
        CustomerDto customer = new CustomerDto();
        customer.setCountryId("ID");
        customer.setPhoneNumber("628123456789");
        customer.setName("John Doe");
        customer.setEmail("john@example.com");
        customer.setAge(25);

        var violations = validator.validate(customer);
        assertTrue(violations.isEmpty());
        logger.info("Validation passed successfully");
    }

    @Test
    public void whenInvalidPhoneForCountry_thenViolation() {
        logger.info("Testing invalid phone to country scenario");
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
        logger.warn("Validation failed: {}", violation.getMessage());
    }

    @Test
    public void whenInvalidCountryID_thenViolation() {
        logger.info("Testing invalid country Id scenario");
        CustomerDto customer = new CustomerDto();
        customer.setCountryId("NZ");
        customer.setPhoneNumber("628123456789"); // Indonesian number with US country
        customer.setName("John Doe");
        customer.setEmail("john@example.com");
        customer.setAge(25);

        var violations = validator.validate(customer);
        assertFalse(violations.isEmpty());

        ConstraintViolation<CustomerDto> violation = violations.iterator().next();
        logger.warn("Validation failed: {}", violation.getMessage());
    }
}
