package com.mycompany.simpleboard.config.exception;

import com.mycompany.simpleboard.config.exception.board.BoardNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BoardExceptionHandler {
    @ExceptionHandler(BoardNotFoundException.class)
    public ResponseEntity<Object> boardNotFoundException(BoardNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
