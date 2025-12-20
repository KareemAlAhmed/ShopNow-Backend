package com.example.ShopNow.Services;


import com.example.ShopNow.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService {
    UserRepository userRep;

    @Autowired
    public UserService(UserRepository userRep){
        this.userRep=userRep;
    }
}
