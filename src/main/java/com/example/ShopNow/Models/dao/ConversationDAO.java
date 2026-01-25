package com.example.ShopNow.Models.dao;

import com.example.ShopNow.Models.Chats.Conversation;
import com.example.ShopNow.Models.User;
import java.util.List;

public interface ConversationDAO {
    public Conversation createConversation(String name);
    public Conversation getConversationById(int convId);
    public Conversation getConversationFromUsers(int user1,int user2);
    public Conversation updateConv(Conversation conv);


}
