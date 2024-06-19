package it.intesys.rookielessons.controller;

import it.intesys.rookielessons.service.Mandatory;
import it.intesys.rookielessons.service.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HospitalExceptionHandler {
    @ExceptionHandler (NotFound.class)
    public ResponseEntity<String> handleNotFound (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    @ExceptionHandler (Mandatory.class)
    public ResponseEntity<String> handleBadRequest (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}