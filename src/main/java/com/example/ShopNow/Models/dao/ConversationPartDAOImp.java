package com.example.ShopNow.Models.dao;

import com.example.ShopNow.Models.Chats.ConversationParticipant;
import com.example.ShopNow.Models.Chats.Conversation;
import com.example.ShopNow.Models.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public class ConversationPartDAOImp implements ConversationPartDAO{
    EntityManager entityManager;
    @Autowired
    public ConversationPartDAOImp( EntityManager entityManager){
        this.entityManager=entityManager;
    }
    public List<ConversationParticipant> getConvPart(User User){
        return entityManager.createQuery("FROM ConversationParticipant WHERE user = :userid").setParameter("userid",User).getResultList();
    }
    @Transactional
    public ConversationParticipant createConvPart(User user,Conversation conversation){
        ConversationParticipant participant=new ConversationParticipant();
        participant.setConversation(conversation);
        participant.setUser(user);
        participant.setJoinedAt(LocalDateTime.now());
        participant.setMuted(false);
        participant.setUnreadCount(0);
        participant.setLastReadAt(LocalDateTime.now());
        entityManager.persist(participant);
        return participant;
    }

    @Transactional
    public void updateParticipant(ConversationParticipant part,String field){
        if(field.equals("unread_msg")){
            part.setUnreadCount(part.getUnreadCount() + 1);
            entityManager.merge(part);
        } else if (field.equals("read_msg")) {
            part.setUnreadCount(0);
            part.setLastReadAt(LocalDateTime.now());
            entityManager.merge(part);
        }
    }
}
