package com.example.ShopNow.Exceptions;

public class DiscountErrorException extends RuntimeException{
    public DiscountErrorException(String msg){
        super(msg);
    }
}
