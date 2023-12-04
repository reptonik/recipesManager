package com.abn.amro.assignment.search.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ValidFilterKeyValidator.class)
public @interface ValidFilterKey {
    String message() default "Invalid filter key";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?> entity() default Void.class;

    String[] predefinedKeys() default {};
}