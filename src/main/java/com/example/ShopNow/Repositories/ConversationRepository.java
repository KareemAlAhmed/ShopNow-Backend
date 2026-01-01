package com.example.ShopNow.Repositories;

import com.example.ShopNow.Models.Chats.Conversation;
import com.example.ShopNow.Models.Chats.ConversationParticipant;
import com.example.ShopNow.Models.Discount;
import com.example.ShopNow.Models.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation,Integer> {


}
