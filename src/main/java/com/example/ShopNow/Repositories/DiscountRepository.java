package com.example.ShopNow.Repositories;

import com.example.ShopNow.Models.Discount;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiscountRepository extends JpaRepository<Discount,Integer> {

    Optional<Discount> findByCode(String code);
    @Transactional
    void deleteByCode(String code);
}
