package com.example.nesstest.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(value = {CustomRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(CustomRequestException e){
        //1. Create payload containing exception details
        CustomException apiException = new CustomException(
                e.getMessage(),
                e,
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        //2. Return response entity
        return new ResponseEntity<>(apiException,HttpStatus.BAD_REQUEST);
    }
}
