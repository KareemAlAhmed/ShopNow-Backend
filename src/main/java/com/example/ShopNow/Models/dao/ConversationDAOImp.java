package com.example.ShopNow.Models.dao;

import com.example.ShopNow.Models.Chats.Conversation;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.example.ShopNow.Models.User;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ConversationDAOImp implements ConversationDAO{
    EntityManager entityManager;
    @Autowired
    public ConversationDAOImp( EntityManager entityManager){
        this.entityManager=entityManager;
    }
    @Transactional
    public Conversation createConversation(String name){
        Conversation conversation=new Conversation();
        conversation.setCreatedAt(LocalDateTime.now());
        conversation.setType("DIRECT");
        entityManager.persist(conversation);
        return conversation;
    }
    public Conversation getConversationFromUsers(int user1, int user2){
        String jpql = "SELECT cp.conversation.id FROM ConversationParticipant cp " +
                "WHERE cp.user.id IN (:user1, :user2) " +
                "GROUP BY cp.conversation.id " +
                "HAVING COUNT(DISTINCT cp.user.id) = 2";

        List<Integer> results = entityManager
                .createQuery(jpql, Integer.class)
                .setParameter("user1", user1)
                .setParameter("user2", user2)
                .getResultList();

        // If query returns: [15] (one conversation with ID 15)
        // Then:
        // results.get(0) returns 15 (the conversation ID)
        // NOT 0!

        return results.isEmpty() ? null : entityManager.find(Conversation.class,results.get(0));
    }
    public Conversation getConversationById(int convId){
        return entityManager.find(Conversation.class,convId);
    }
    @Transactional
    public Conversation updateConv(Conversation conv){
        return entityManager.merge(conv);
    }
}
