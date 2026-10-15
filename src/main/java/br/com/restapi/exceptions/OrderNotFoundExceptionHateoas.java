package br.com.restapi.exceptions;

/**
 *
 * @author marcos
 */
public class OrderNotFoundExceptionHateoas extends RuntimeException {

    public OrderNotFoundExceptionHateoas(long id) {
        super("Could not found the order id: " + id);
    }
}
