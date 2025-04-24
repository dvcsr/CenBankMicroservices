package com.cenbank.account.dto;

import com.cenbank.account.config.extensions.DurationExtension;
import com.cenbank.account.config.extensions.LoggingExtension;
import jakarta.validation.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({DurationExtension.class, LoggingExtension.class})
public class CustomerDtoValidationTest {
    private static final Logger logger = LoggerFactory.getLogger(CustomerDtoValidationTest.class);
    private static Validator validator;
    private CustomerDto customer;


    @BeforeAll
    public static void setUpValidator() {
        logger.debug("initializing customerDto tests . . . .");
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @BeforeEach
    void startTest() {
        customer = createValidCustomer();
    }

    @AfterEach
    void endTest() {
        customer = null;
    }

    @AfterAll
    static void finalCleanup() {
        logger.info("Finalizing customerDto tests . . . .");
        // final cleanup
    }

    @Test
    public void testCustomerDtoValidation_NoViolations() {
        logger.info("Testing valid customer scenario");
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
        customer.setCountryId("US");
        customer.setPhoneNumber("628123456789"); // Indonesian number with US country
        customer.setName("John Doe");
        customer.setEmail("john@example.com");
        customer.setAge(25);

        Set<ConstraintViolation<CustomerDto>> violations = validator.validate(customer);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size(), "Should have exactly one violation!");

        ConstraintViolation<CustomerDto> violation = violations.iterator().next();
        logger.warn("Validation failed: {}", violation.getMessage());
    }

    @Test
    public void whenInvalidCountryID_thenViolation() {
        logger.info("Testing invalid country Id scenario");
        customer.setCountryId("NZ");
        customer.setPhoneNumber("628123456789"); // Indonesian number with US country
        customer.setName("John Doe");
        customer.setEmail("john@example.com");
        customer.setAge(25);

        Set<ConstraintViolation<CustomerDto>>  violations = validator.validate(customer);
        assertFalse(violations.isEmpty());

        ConstraintViolation<CustomerDto> violation = violations.iterator().next();
        logger.warn("Validation failed: {}", violation.getMessage());
    }

    @Test
    void testCustomerDtoAssertAll() {
        customer.setName("John");
        customer.setAge(25);

        // Multiple assertions one test
        assertAll("Customer properties",
                () -> assertEquals("John", customer.getName()),
                () -> assertTrue(customer.getAge() > 18),
                () -> assertNotNull(customer.getName()),
                () -> assertNull(customer.getAccountsDtoList())
        );
        logger.info("multiple assertions of customer dto passed");
    }

    @Test
    void CustomerDtoTimeout() {
        assertTimeout(Duration.ofMillis(10), () -> {
            validator.validate(customer);
            logger.info("timeout assertion of customer dto passed");
        });
    }

    @Test
    void testCustomerDtoException() {
        CustomerDto customerException = new CustomerDto();
        Set<ConstraintViolation<CustomerDto>> violations = validator.validate(customerException);
        if (!violations.isEmpty()) {
            ConstraintViolationException exception = new ConstraintViolationException(violations);
            assertNotNull(exception);
            assertTrue(exception.getConstraintViolations().size() > 0);
        }
        logger.info("assert exception test passed");
    }


    @Test
    public void whenMultipleValidationErrors_thenCollectAllErrors() {
        logger.info("Testing multiple validation errors scenario");

        // Setting invalid values for multiple fields
        customer.setCountryId("NZ"); // Wrong country Id
        customer.setPhoneNumber("000023456789"); // Wrong country code to phone number
        customer.setEmail("invalid-email");      // Invalid email format
        customer.setAge(15);                     // Below minimum age
        customer.setName("");                    // Empty name

        //log
        Set<ConstraintViolation<CustomerDto>> violations = validator.validate(customer);
        violations.forEach(
                violation -> logger.error("Validation error on field '{}': {}",
                        violation.getPropertyPath(), violation.getMessage())
        );

        assertTrue(violations.size() >= 4);

        Map<String, List<String>> violationMap = collectAllViolationsGroupByFields(violations);

                        //Junit assertion
        assertTrue(violationMap.containsKey("email"), "Should have email errors");
        assertTrue(violationMap.containsKey("age"), "Should have age errors");
        assertTrue(violationMap.containsKey("name"), "Should have name errors");

        List<String> nameErrors = violationMap.get("name");
        assertTrue(nameErrors.size() >= 2, "Name should have multiple errors");
        assertTrue(nameErrors.contains("name cannot be empty"));
        assertTrue(nameErrors.contains("Name must be between 2 and 50 characters"));

        //        //adding assertJ assertions to see assertJ handling failed test caused by assertion code mistake
//        assertThat(violations)
//                .as("VALIDATION OF TOTAL VIOLATIONS:")
//                .extracting(v -> String.format("Field '%s': %s", v.getPropertyPath(), v.getMessage()))
//                .describedAs("Expected 10 violations but found:\n%s", formatViolations(violations)).hasSize(10);
//
//        assertThat(violationMap)
//                .as("VALIDATION MAP FIELD CHECK:")
//                .describedAs("Looking for field 'nonexistentField'\nAvailable fields are: %s",
//                        violationMap.keySet())
//                .containsKey("nonexistentField");
//
//        // Check name field errors with actual vs expected
//        List<String> nameErrors = violationMap.get("name");
//        assertThat(nameErrors)
//                .as("Name field validation messages")
//                .describedAs("Expected messages not found.\nActual messages:\n%s",
//                    formatMessages(nameErrors))
//                .contains(
//                        "name cannot be emptyyy",
//                        "Name must be exactly 30 characters"
//                );
//
//        // Check number of name errors with detail
//        assertThat(nameErrors)
//                .as("Number of name field errors")
//                .describedAs("Expected 5 errors but found %d:\n%s",
//                        nameErrors.size(), formatMessages(nameErrors))
//                .hasSize(5);


    }

    private Map<String, List<String>> collectAllViolationsGroupByFields(Set<ConstraintViolation<CustomerDto>> violations) {
        Map<String, List<String>> violationMap = violations.stream().collect(
                Collectors.groupingBy(
                        violation -> violation.getPropertyPath().toString(),
                        Collectors.mapping(
                                ConstraintViolation::getMessage, Collectors.toList()
                        )
                ));
        return violationMap;
    }

    private String formatViolations(Set<ConstraintViolation<CustomerDto>> violations) {
        return violations.stream()
                .map(v -> String.format("- Field '%s': %s", v.getPropertyPath(), v.getMessage()))
                .collect(Collectors.joining("\n"));
    }

    private String formatMessages(List<String> messages) {
        return messages.stream()
                .map(msg -> "- " + msg)
                .collect(Collectors.joining("\n"));
    }

    private CustomerDto createValidCustomer() {
        CustomerDto validCustomer = new CustomerDto();
        validCustomer.setName("John Doe");
        validCustomer.setEmail("john.doe@example.com");
        validCustomer.setPhoneNumber("628123456789");
        validCustomer.setCountryId("ID");
        validCustomer.setAge(25);

        return validCustomer;
    }

}

