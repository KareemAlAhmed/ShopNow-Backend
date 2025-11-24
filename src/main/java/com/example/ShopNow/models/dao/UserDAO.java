package com.example.ShopNow.models.dao;

import com.example.ShopNow.models.User;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface UserDAO {
    public ResponseEntity<?> getUser(String userField,Object id);
    public ResponseEntity<?> save(User user);
    public List<User> getAllUser();
    public ResponseEntity<?> login(User user);
}
