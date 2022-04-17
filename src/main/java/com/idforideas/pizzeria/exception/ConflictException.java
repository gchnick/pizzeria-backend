package com.idforideas.pizzeria.exception;

public class ConflictException extends RuntimeException {

    private static final String DESCRIPTION = "Conflict Exception (409)";

    public ConflictException(String datail) {
        super(DESCRIPTION + ". " + datail);
    }

    
    
}
