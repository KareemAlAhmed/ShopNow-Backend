package com.example.ShopNow.Models.dao;

import com.example.ShopNow.Models.Chats.ConversationParticipant;

import java.util.List;
import com.example.ShopNow.Models.Chats.Conversation;
import com.example.ShopNow.Models.User;
public interface ConversationPartDAO {
    public List<ConversationParticipant> getConvPart(User userid);
    public ConversationParticipant createConvPart(User user,Conversation conversation);
    public void updateParticipant(ConversationParticipant part,String field);

}
