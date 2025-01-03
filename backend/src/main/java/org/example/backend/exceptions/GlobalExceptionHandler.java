package org.example.backend.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<String> handleUsernameAlreadyExistsException(
            UsernameAlreadyExistsException usernameAlreadyExistsException) {
        return new ResponseEntity<>(usernameAlreadyExistsException.getMessage(),
                HttpStatus.CONFLICT);
    }
}
