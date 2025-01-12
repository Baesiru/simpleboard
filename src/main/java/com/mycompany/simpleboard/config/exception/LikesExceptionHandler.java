package com.mycompany.simpleboard.config.exception;

import com.mycompany.simpleboard.config.exception.likes.LikesAlreadyFoundException;
import com.mycompany.simpleboard.config.exception.likes.LikesNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LikesExceptionHandler {
    @ExceptionHandler(LikesNotFoundException.class)
    public ResponseEntity<Object> likeNotFoundException(LikesNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(LikesAlreadyFoundException.class)
    public ResponseEntity<Object> likesAlreadyFoundException(LikesAlreadyFoundException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
