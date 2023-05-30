package com.simplesystem.challenge.simpletodo.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.simplesystem.challenge.simpletodo.exception.TodoException;

@RestControllerAdvice
public class ErrorController {

	private final Logger LOGGER = LogManager.getLogger(getClass());

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TodoException.class)
    public String handleNotFound(TodoException e) {
    	LOGGER.error(e.getMessage());
        return e.getMessage();
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String handleInternalError(Exception e) {
    	LOGGER.error("Unhandled Exception in Controller", e);
        return "Internal error";
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationError(MethodArgumentNotValidException e) {
    	return "vaidation errror";
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({UnrecognizedPropertyException.class, HttpMessageNotReadableException.class})
    public String handleUnrecognizedPropertyException(UnrecognizedPropertyException e) {
    	return "Unrecognized Property";
    }
}
