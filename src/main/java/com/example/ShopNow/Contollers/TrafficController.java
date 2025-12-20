package com.example.ShopNow.Contollers;

import com.example.ShopNow.ExceptiomResponses.*;
import com.example.ShopNow.Exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class TrafficController {
    @ExceptionHandler
    public ResponseEntity<ProductNotFoundResponse> productNotFound(ProductNotFoundException exc){
        ProductNotFoundResponse response=new ProductNotFoundResponse();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMsg(exc.getMessage());
        response.setTimestamps(LocalDateTime.now());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler
    public ResponseEntity<InsufficientFundsResponse> insufficientFunds(InsufficientFundsException exc){
        InsufficientFundsResponse response=new InsufficientFundsResponse();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMsg(exc.getMessage());
        response.setTimestamps(LocalDateTime.now());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<UserNotFoundResponse> userNotFound(UserNotFoundException exc){
        UserNotFoundResponse response=new UserNotFoundResponse();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMsg(exc.getMessage());
        response.setTimestamps(LocalDateTime.now());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<CredentialsErrorResponse> credentialError(CredentialsErrorException exc){
        CredentialsErrorResponse response=new CredentialsErrorResponse();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMsg(exc.getMessage());
        response.setTimestamps(LocalDateTime.now());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<DiscountErrorResponse> discountError(DiscountErrorException exc){
        DiscountErrorResponse response=new DiscountErrorResponse();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMsg(exc.getMessage());
        response.setTimestamps(LocalDateTime.now());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }
}
