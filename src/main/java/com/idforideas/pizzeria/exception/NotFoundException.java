package com.idforideas.pizzeria.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String detail) {
        super(detail);
    }  
}
