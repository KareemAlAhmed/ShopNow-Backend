package com.example.ShopNow.Exceptions;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String msg){
        super(msg);
    }
}
