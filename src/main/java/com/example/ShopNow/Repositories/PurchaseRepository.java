package com.example.ShopNow.Repositories;

import com.example.ShopNow.Models.Purchase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase,Integer> {
   @Query("SELECT u FROM Purchase u WHERE u.user.id = :userId")
    List<Purchase> findByUserId(@Param("userId") int user);

}
