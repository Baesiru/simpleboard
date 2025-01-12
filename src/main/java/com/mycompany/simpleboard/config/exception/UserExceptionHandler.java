package com.mycompany.simpleboard.config.exception;

import com.mycompany.simpleboard.config.exception.user.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(ExistsUsernameException.class)
    public ResponseEntity<Object> existsUsernameException(ExistsUsernameException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    @ExceptionHandler(ExistsEmailException.class)
    public ResponseEntity<Object> existsEmailException(ExistsEmailException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    @ExceptionHandler(LoginFailException.class)
    public ResponseEntity<Object> loginFailException(LoginFailException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> usernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(SamePasswordException.class)
    public ResponseEntity<Object> samePasswordException(SamePasswordException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(CodeNotEqualsException.class)
    public ResponseEntity<Object> codeNotEqualsException(CodeNotEqualsException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
