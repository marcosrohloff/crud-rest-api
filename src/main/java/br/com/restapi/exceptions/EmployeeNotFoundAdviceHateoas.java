package br.com.restapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author marcos
 */
@ControllerAdvice
public class EmployeeNotFoundAdviceHateoas {

    @ResponseBody
    @ExceptionHandler(EmployeeNotFoundExceptionHateoas.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(EmployeeNotFoundExceptionHateoas ex) {
        final String message = ex.getMessage();
        return message;
    }

}
