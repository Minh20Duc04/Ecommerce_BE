package com.Ecommerce_BE.Exception;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(String msg)
    {
        super(msg);
    }
}
