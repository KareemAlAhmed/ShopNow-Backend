package com.example.ShopNow.Models.dao;

import com.example.ShopNow.Models.Chats.Message;
import com.example.ShopNow.Models.Chats.Conversation;
import com.example.ShopNow.Models.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
@Repository
public class MessaageDAOImp implements MessaageDAO{
    EntityManager entityManager;
    @Autowired
    public MessaageDAOImp( EntityManager entityManager){
        this.entityManager=entityManager;
    }
    @Transactional
    public Message createMessage(Conversation conversation, User sender,String content,String msgType){
        Message msg=new Message();
        msg.setContent(content);
        msg.setConversation(conversation);
        msg.setSender(sender);
        msg.setContent(content);
        LocalDateTime currentDate=LocalDateTime.now();
        msg.setCreatedAt(currentDate);
        msg.setDeliveredAt(currentDate);
        msg.setType(msgType);
        entityManager.persist(msg);
        return msg;
    }
}
