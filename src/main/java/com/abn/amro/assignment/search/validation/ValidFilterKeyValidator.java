package com.abn.amro.assignment.search.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ValidFilterKeyValidator implements ConstraintValidator<ValidFilterKey, String> {
    private Class<?> entityClass;
    private List<String> predefinedKeys;

    @Override
    public void initialize(ValidFilterKey constraintAnnotation) {
        this.entityClass = constraintAnnotation.entity();
        this.predefinedKeys = Arrays.asList(constraintAnnotation.predefinedKeys());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        List<String> allowedFilterKeys;
        if (entityClass != Void.class) {
            allowedFilterKeys = getFieldNames(entityClass);
        } else {
            allowedFilterKeys = predefinedKeys;
        }
        return allowedFilterKeys.contains(value);
    }

    private List<String> getFieldNames(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .map(Field::getName)
                .collect(Collectors.toList());
    }
}
