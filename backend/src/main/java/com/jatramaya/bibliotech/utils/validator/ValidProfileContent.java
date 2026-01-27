package com.jatramaya.bibliotech.utils.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = ProfileValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidProfileContent {
    String message() default "Either avatarUrl or bio must be provided";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
