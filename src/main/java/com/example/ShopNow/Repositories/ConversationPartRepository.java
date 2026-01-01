package com.example.ShopNow.Repositories;
import com.example.ShopNow.Models.User;

import com.example.ShopNow.Models.Chats.ConversationParticipant;
import com.example.ShopNow.Models.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConversationPartRepository extends JpaRepository<ConversationParticipant,Integer> {

    public Optional<ConversationParticipant> findByUser(Optional<User> user);
}
