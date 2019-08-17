package com.ngouma.brooklyn.cisc3810.registrarservices.api.helper;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.http.HttpServletRequest;

/*
 * TODO: Implement exceptions handler
 */
//@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

 /*   @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public CustomResponseEntity handleError(HttpServletRequest req, EmptyResultDataAccessException ex) {
        return new CustomResponseEntity("The data requested could not be found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public CustomResponseEntity handleError(HttpServletRequest req, SQLIntegrityConstraintViolationException ex) {
        return new CustomResponseEntity("Data do not meet required format", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public CustomResponseEntity handleError(HttpServletRequest req, Exception ex) {
        return new CustomResponseEntity("Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
   */
}
