package br.com.restapi.exceptions;

public class UserServiceLogicException extends Exception {

    private static final long serialVersionUID = 1L;

    public UserServiceLogicException() {
        super("Something went wrong. Please try again later!");
    }
}
