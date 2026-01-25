package com.example.ShopNow.Models.dao;

import com.example.ShopNow.Models.Chats.Conversation;
import com.example.ShopNow.Models.Chats.Message;
import com.example.ShopNow.Models.User;

public interface MessaageDAO {
    public Message createMessage(Conversation conversation, User sender, String content, String msgType);
    public Message getMsgById(int msgId);
    public Message updateMsg(Message msg);
    public void deleteConvMessages(int convId);
}
