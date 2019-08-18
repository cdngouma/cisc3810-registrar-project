package com.ngouma.brooklyn.cisc3810.registrarservices.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

/*
 * TODO: Implement exceptions handler
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleError(HttpServletRequest req, EntityNotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND, "Entity not found");
    }

/*  @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
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
