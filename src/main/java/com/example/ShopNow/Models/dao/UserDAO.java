package com.example.ShopNow.Models.dao;

import com.example.ShopNow.Models.User;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface UserDAO {
    public ResponseEntity<?> save(User user);
    public List<User> getAllUser();
    public void updateCartWish( User data);
    public User getUserById(int id);
    public User getUserByField(String userField, Object data);
    public User updateUser(User user);
    public void updateUserAuthorities(String oldData,String field,String newData);
}
