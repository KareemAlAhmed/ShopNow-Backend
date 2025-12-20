package com.example.ShopNow.Contollers;

import com.example.ShopNow.Exceptions.UserNotFoundException;
import com.example.ShopNow.Models.Review;
import com.example.ShopNow.Models.User;
import com.example.ShopNow.Models.dao.UserDAO;
import com.example.ShopNow.Services.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    WebService webService;
    @Autowired
    public  UserController(WebService webService){
        this.webService=webService;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getUser(@PathVariable int id) {
        return webService.getUser("id",id);

    }
    @PostMapping("/create")
    public ResponseEntity<?> addUser(@RequestBody User user){
        return webService.save(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        return webService.login(user);
    }

    @GetMapping("/getAll")
    public List<User> getUsers() {
        return webService.getAllUser();
    }

    @PostMapping("/saveCartWish")
    public ResponseEntity<?> saveCartWish(@RequestBody User user){
        return webService.updateCartWish(user);
    }

    @PostMapping("/checkOut")
    public ResponseEntity<?> checkOut(@RequestBody User user,@RequestParam("discountCode") String discountCode ){
        return webService.checkOut(user,discountCode);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateUserData(@RequestBody User user,@RequestParam("pass") String pass ){
        return webService.updateUseData(user,pass);
    }
    @PostMapping("/createReview")
    public ResponseEntity<?> createrReview(@RequestBody Review review,@RequestParam("userId") int userId,@RequestParam("prodId") int prodId){
        return webService.createReview(review,userId,prodId);
    }
}
