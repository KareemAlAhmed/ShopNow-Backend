package com.example.ShopNow.contollers;

import com.example.ShopNow.models.User;
import com.example.ShopNow.models.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    UserDAO userDB;
    @Autowired
    public  UserController(UserDAO userDB){
        this.userDB=userDB;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getUser(@PathVariable int id) {
        return    userDB.getUser("id",id);

    }
    @PostMapping("/create")
    public ResponseEntity<?> addUser(@RequestBody User user){
        try{
            System.out.println("the user data"+user);

           return userDB.save(user);

        }catch (Exception e) {
            System.out.println("Error "+e.getMessage());
            // Handle any unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of("error", "Internal server error: " + e.getMessage())
            );
        }


    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        return userDB.login(user);
    }

    @GetMapping("/getAll")
    public List<User> getUsers() {
        return userDB.getAllUser();
    }
}
