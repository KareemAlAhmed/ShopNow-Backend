package com.example.ShopNow.Models.dao;

import com.example.ShopNow.Models.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class UserDAOImp implements UserDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    EntityManager entityManager;
    @Autowired
    public UserDAOImp(EntityManager entityManager){
        this.entityManager=entityManager;
    }

    @Transactional
    public ResponseEntity<?> save(User user){
        user.setPassword("{noop}"+user.getPassword());
        user.setFunds(10000.0);
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
            user.setEnabled(1);
            user.setCreatedAt(LocalDateTime.now());
            entityManager.persist(user);
            entityManager.createNativeQuery(
                            "INSERT INTO authorities (username, authority) VALUES (:username, :authority)")
                    .setParameter("username", user.getUsername())
                    .setParameter("authority", "ROLE_USER")
                    .executeUpdate();
        if(user.isVendor()){
            entityManager.createNativeQuery(
                            "INSERT INTO authorities (username, authority) VALUES (:username, :authority)")
                    .setParameter("username", user.getUsername())
                    .setParameter("authority", "ROLE_VENDOR")
                    .executeUpdate();
        }
            return ResponseEntity.ok(Map.of(
                    "message", "User created successfully",
                    "status", "success",
                    "user",user
            ));
        }

    }


    public User getUserByField(String userField, Object data){
        try{
            if(userField.equals("email")){
                String email=(String)data;
                return   entityManager.createQuery("FROM User WHERE email = :theEmail",User.class).setParameter("theEmail",email).getSingleResult();
            }else if (userField.equals("id")) {
                int id=(Integer)data;
                return entityManager.createQuery("FROM User WHERE id = :theId",User.class).setParameter("theId",id).getSingleResult();
            }else if (userField.equals("username")) {
                String username=(String)data;
                return entityManager.createQuery("FROM User WHERE username = :theUsername",User.class).setParameter("theUsername",username).getSingleResult();
            }else if (userField.equals("phone")) {
                String phone=(String)data;
                return entityManager.createQuery("FROM User WHERE phone = :thePhone",User.class).setParameter("thePhone",phone).getSingleResult();
            }
            return null;
        }catch (Exception e){
            return null;
        }

    }

    public List<User> getAllUser(){
        return entityManager.createQuery("FROM User ",User.class).getResultList();
    }
    public List<UserResponseDTO> getUsersSecured(){
        List<User> users= entityManager.createQuery("FROM User ",User.class).getResultList();
        return users.stream()
                .map(UserResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateCartWish( User data){
        entityManager.merge(data);
    }

    public User getUserById(int id){
        return entityManager.find(User.class,id);
    }

    @Transactional
    public User updateUser(User user){
        return entityManager.merge(user);
    }

    @Transactional
    public void updateUserAuthorities(String oldData ,String field,String newData){
        if(field.equals("username")){
            String updateSqlUSer = "UPDATE users SET username = ? WHERE username = ?";
            jdbcTemplate.update(updateSqlUSer, newData,oldData);
            String updateSql = "UPDATE authorities SET username = ? WHERE username = ?";
            jdbcTemplate.update(updateSql, newData,oldData);
        }else{
           // String updateSql = "UPDATE authorities SET username = ? WHERE username = ?";
          //  jdbcTemplate.update(updateSql, newData, user.getUsername());
        }

    }

}
