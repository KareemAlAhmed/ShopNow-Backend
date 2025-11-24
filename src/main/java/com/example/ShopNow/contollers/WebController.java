package com.example.ShopNow.contollers;

import com.example.ShopNow.models.Product;
import com.example.ShopNow.models.User;
import com.example.ShopNow.models.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WebController {
    @Autowired
    ProductController prodController;
    UserDAO userDB;
    @Autowired
    public  WebController(UserDAO userDB){
        this.userDB=userDB;
    }

    @GetMapping("/")
    public List<User> start(){
        System.out.println("the user data");

        return userDB.getAllUser();
    }


}
