package com.pow.ebank.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.hibernate.JDBCException;
import org.hibernate.exception.ConstraintViolationException;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<String> handleAllException(InvalidParameterException e){
        log.error("Required input fields are empty {}", e);
        return new ResponseEntity<String>("Required input fields are empty", HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(ValueNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(ValueNotFoundException e){
        log.error("Required input fields are empty {}", e);
        return new ResponseEntity<String>("Value not found", HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleDuplicateRecodeException(ConstraintViolationException e){
        log.error("Recode already exist {}", e);
        return new ResponseEntity<String>("Recode already exist", HttpStatus.ALREADY_REPORTED);
    }

    @ExceptionHandler(JDBCException.class)
    public ResponseEntity<String> handleJdbcException(JDBCException e){
        log.error("Error processing data with database {}", e);
        return new ResponseEntity<String>("Data processing error", HttpStatus.ALREADY_REPORTED);
    }
}
