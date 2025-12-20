package com.example.ShopNow.Exceptions;

public class CredentialsErrorException extends RuntimeException{
    public CredentialsErrorException(String msg){
        super(msg);
    }
}
