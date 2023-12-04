package com.abn.amro.assignment.configuration;

import com.abn.amro.assignment.exception.NotValidInput;
import com.abn.amro.assignment.exception.RecipeNotFoundException;
import org.hibernate.query.sqm.PathElementException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionConfiguration {

    @ExceptionHandler(RecipeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNotFoundException(RecipeNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ResponseEntity<String> dataUniqueViolation(DataIntegrityViolationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        return ResponseEntity.badRequest().body(getValidationErrors(result));
    }

    @ExceptionHandler({InvalidDataAccessApiUsageException.class, PathElementException.class, NotValidInput.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> generalValidationException(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }


    private Map<String, String> getValidationErrors(BindingResult result) {
        Map<String, String> validationErrors = new HashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            validationErrors.put(fieldName, errorMessage);
        }
        return validationErrors;
    }


}
