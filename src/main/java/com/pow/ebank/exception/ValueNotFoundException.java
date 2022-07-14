package com.pow.ebank.exception;

public class ValueNotFoundException extends RuntimeException{
    public ValueNotFoundException(String message){
        super(message);
    }
}
