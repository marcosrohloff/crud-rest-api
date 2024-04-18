package br.com.restapi.exceptions;

/**
 *
 * @author marcos
 */
public class EmployeeNotFoundExceptionHateoas extends RuntimeException {

    public EmployeeNotFoundExceptionHateoas(long id) {
        super("Could not found the employee: " + id);
    }
}
