package com.jatramaya.bibliotech.utils.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CredentialStrengthValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongCredential {
    String message() default "Username must contain at least one number and one special character";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}