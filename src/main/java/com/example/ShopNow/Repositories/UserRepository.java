package com.example.ShopNow.Repositories;

import com.example.ShopNow.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserRepository extends JpaRepository<User,Integer> {

}