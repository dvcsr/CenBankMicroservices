package com.cenbank.account.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneCountryCodeValidator.class)
public @interface ValidPhoneCountryCode {
    String message() default "Phone number doesn't match country code";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
