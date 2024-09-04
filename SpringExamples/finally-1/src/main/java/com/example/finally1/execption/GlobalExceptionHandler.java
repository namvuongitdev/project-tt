package com.example.finally1.execption;

import com.example.finally1.execption.custom.FileNotFound;
import com.example.finally1.execption.custom.ListIsEmptyExecption;
import com.example.finally1.execption.custom.UserNotFoundExection;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    Map<String, String> errors = new HashMap<>();

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        if (!errors.isEmpty()) {
            errors.clear();
        }
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = messageSource.getMessage(error.getCode(), null, LocaleContextHolder.getLocale());
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        if (!errors.isEmpty()) {
            errors.clear();
        }
        ex.getConstraintViolations().forEach(violation ->
                {
                    String fieldName = violation.getPropertyPath().toString();
                    String errorMessage = messageSource.getMessage(violation.getMessage(), null, LocaleContextHolder.getLocale());
                    errors.put(fieldName, errorMessage);
                }
        );
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleTypeInputExceptions(MethodArgumentTypeMismatchException ex) {
        if (!errors.isEmpty()) {
            errors.clear();
        }
        String errorMessage = messageSource.getMessage("checkType", null, LocaleContextHolder.getLocale());
        errors.put(ex.getName(), errorMessage);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, String>> handleRequestParameterException(MissingServletRequestParameterException ex) {
        if (!errors.isEmpty()) {
            errors.clear();
        }
        String errorMessage = messageSource.getMessage("checkRequestParameter", null, LocaleContextHolder.getLocale());
        errors.put(ex.getParameterName(), errorMessage);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundExection.class)
    // xử lý không tìm thấy student
    public ResponseEntity<ErrorResponse> handlerNotFound(UserNotFoundExection ex) {
        String message = messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale());
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND, message), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    // xử lý mã sutdent không được trùng
    public ResponseEntity<ErrorResponse> handleUnique(DataIntegrityViolationException ex) {
        String message = messageSource.getMessage("Unique", null, LocaleContextHolder.getLocale());
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.CONFLICT, message), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MismatchedInputException.class)
    public ResponseEntity<ErrorResponse> handleInputNotEmptyExecption() {
        String message = messageSource.getMessage("InputExecption", null, LocaleContextHolder.getLocale());
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<ErrorResponse> handleJsonParseException() {
        String message = messageSource.getMessage("JsonParseException", null, LocaleContextHolder.getLocale());
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ListIsEmptyExecption.class)
    public ResponseEntity<ErrorResponse> handleListIsEmptyExecption(ListIsEmptyExecption ex){
        String message = messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale());
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST , message) , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileNotFound.class)
    public ResponseEntity<ErrorResponse> handleFileIsEmpty(FileNotFound ex){
        String message = messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale());
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST , message) , HttpStatus.BAD_REQUEST);
    }
}
