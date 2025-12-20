package com.example.ShopNow.Contollers;

import com.example.ShopNow.Models.User;
import com.example.ShopNow.Models.dao.UserDAO;
import com.example.ShopNow.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Controller
public class WebController {
    Map<Integer, List<String>> userRolesMap = new HashMap<>();
    List<User> allUsers= new ArrayList<>();
    @Autowired
    private JdbcTemplate jdbcTemplate;
    UserRepository userRepository;
    @Autowired
    public WebController(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @GetMapping("/")
    public String start(Model themodel){
        allUsers = userRepository.findAll();

        for (User user : allUsers) {
            // Query authorities table for each user's roles
            String sql = "SELECT authority FROM authorities WHERE username = ?";
            List<String> roles = jdbcTemplate.queryForList(
                    sql,
                    String.class,
                    user.getUsername()
            );
            userRolesMap.put(user.getId(), roles);
        }
        themodel.addAttribute("userRoles", userRolesMap);
        themodel.addAttribute("users",allUsers);
        return "dashboard";
    }

    @GetMapping("/user/update/{id}")
    public String updateUserForm(@PathVariable int id,Model themodel){
        User user=userRepository.findById(id).get();
        List<String> roles=userRolesMap.get(id);
        themodel.addAttribute("user",user);
        themodel.addAttribute("userRoles",roles);

        return "updateUser";
    }

    @PostMapping("/user/update/{id}")
    @Transactional
    public String updateUser(@PathVariable int id,@ModelAttribute("user")User userToUpdate,@RequestParam("selectedRoles") String selectedRoles,Model themodel){
        User user=userRepository.findById(id).get();
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setEnabled(user.getEnabled());
        userToUpdate.setPassword(user.getPassword());

        userRepository.save(userToUpdate);
        if(selectedRoles.isEmpty()){
            selectedRoles="ROLE_USER";
        }
        List<String> newRoles = Arrays.asList(selectedRoles.split(","));

        // Delete existing roles
        String deleteSql = "DELETE FROM authorities WHERE username = ?";
        jdbcTemplate.update(deleteSql, user.getUsername());

        // Insert new roles
        for (String role : newRoles) {
            if (!role.trim().isEmpty()) {
                String insertSql = "INSERT INTO authorities (username, authority) VALUES (?, ?)";
                jdbcTemplate.update(insertSql, user.getUsername(), role.trim());
            }
        }
        allUsers = userRepository.findAll();
        userRolesMap.clear();
        for (User user2 : allUsers) {
            // Query authorities table for each user's roles
            String sql = "SELECT authority FROM authorities WHERE username = ?";
            List<String> roles = jdbcTemplate.queryForList(
                    sql,
                    String.class,
                    user2.getUsername()
            );
            userRolesMap.put(user2.getId(), roles);
        }
        themodel.addAttribute("userRoles", userRolesMap);

        themodel.addAttribute("userName","Karim");
        themodel.addAttribute("users",allUsers);
        return "dashboard";
    }
    @PostMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable int id,Model themodel){
        User userToDelete=userRepository.findById(id).get();
        String username = userToDelete.getUsername();

        // Delete from authorities table first
        String deleteAuthoritiesSql = "DELETE FROM authorities WHERE username = ?";
        jdbcTemplate.update(deleteAuthoritiesSql, username);

        // Then delete the user
        userRepository.deleteById(id);

        allUsers = userRepository.findAll();
        userRolesMap.clear();

        for (User user : allUsers) {
            // Query authorities table for each user's roles
            String sql = "SELECT authority FROM authorities WHERE username = ?";
            List<String> roles = jdbcTemplate.queryForList(
                    sql,
                    String.class,
                    user.getUsername()
            );
            userRolesMap.put(user.getId(), roles);
        }
        themodel.addAttribute("userRoles", userRolesMap);
        themodel.addAttribute("userName","Karim");
        themodel.addAttribute("users",allUsers);
        return "dashboard";
    }



}

