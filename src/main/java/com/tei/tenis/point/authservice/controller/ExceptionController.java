package com.tei.tenis.point.authservice.controller;


import com.tei.tenis.point.authservice.controller.exceptions.UserAlreadyExistsException;
import com.tei.tenis.point.authservice.controller.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> handleAlreadyExist(Exception e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new ErrorDTO("User already exists!"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleNotFound(Exception e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new ErrorDTO(e.getMessage()), HttpStatus.NOT_FOUND);
    }

}
