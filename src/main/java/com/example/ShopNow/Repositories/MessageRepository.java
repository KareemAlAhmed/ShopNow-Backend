package com.example.ShopNow.Repositories;

import com.example.ShopNow.Models.Chats.Message;
import com.example.ShopNow.Models.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message,Integer> {
}
