package com.example.ShopNow.models.dao;

import com.example.ShopNow.models.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class UserDAOImp implements UserDAO {
    EntityManager entityManager;
    @Autowired
    public UserDAOImp(EntityManager entityManager){
        this.entityManager=entityManager;
    }

    @Transactional
    public ResponseEntity<?> save(User user){
        try{
            User fountUser=(User)entityManager.createQuery("FROM User WHERE username = :name OR email = :email")
                    .setParameter("name",user.getUsername()).setParameter("email",user.getEmail()).getSingleResult();

            String error="";
            String fieldError="";
            if(fountUser.getEmail().equals(user.getEmail())){
                fieldError="email";
                error="User with this email already exists";
            } else if (fountUser.getUsername().equals(user.getUsername())) {
                fieldError="username";
                error="User with this Username already exists";
            }
            return ResponseEntity.badRequest().body(
                    Map.of("error", Map.of(fieldError,error),
                            "status","error")
            );
        }catch (Exception e){
            entityManager.persist(user);
            return ResponseEntity.ok(Map.of(
                    "message", "User created successfully",
                    "status", "success",
                    "user",user
            ));
        }

    }

    public ResponseEntity<?> getUser(String userField, Object data){
        if(userField.equals("email")){
            try{
                String email=(String)data;
              User user=  entityManager.createQuery("FROM User WHERE email = :theEmail",User.class).setParameter("theEmail",email).getSingleResult();
              return ResponseEntity.ok(Map.of(
                      "status", "success",
                      "user",user));
            }catch (Exception e){
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "error",
                        "message","User Doesnt Exist!"));
                }
        } else if (userField.equals("id")) {
            int id=(Integer)data;
            try{
                User user= entityManager.createQuery("FROM User WHERE id = :theId",User.class).setParameter("theId",id).getSingleResult();
                return ResponseEntity.ok(Map.of(
                        "status", "success",
                        "user",user));
            }catch (Exception e){
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "error",
                        "message","User Doesnt Exist!"));
            }

        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of("error", "Internal server error")
            );
        }

    }

    public ResponseEntity<?> login( User data){
        ResponseEntity<?> res = this.getUser("email", data.getEmail());
        if (res.getStatusCode().is2xxSuccessful()) {
            Object body = res.getBody();
            // Since you returned Map.of(), the body will be a Map

                Map<String, Object> responseMap = (Map<String, Object>) body;

                String status = (String) responseMap.get("status");
                User user = (User)responseMap.get("user");

                if(user.getPassword().equals(data.getPassword())){
                    return ResponseEntity.ok(Map.of(
                            "status", "success",
                                "user",user));
                }else{
                    return ResponseEntity.badRequest().body(Map.of(
                            "status", "error",
                                    "error", Map.of("password","Incorrect Password!"))
                            );
                }

        }else{
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "error", Map.of("email","Email Doesnt Exist!")));
        }

    }
    public List<User> getAllUser(){
        return entityManager.createQuery("FROM User ",User.class).getResultList();
    }
}
