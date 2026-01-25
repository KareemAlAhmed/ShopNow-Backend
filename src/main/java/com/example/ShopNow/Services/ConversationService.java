package com.example.ShopNow.Services;

import com.example.ShopNow.Exceptions.UserNotFoundException;
import com.example.ShopNow.Models.Chats.Conversation;
import com.example.ShopNow.Models.Chats.ConversationParticipant;
import com.example.ShopNow.Models.Chats.Message;
import com.example.ShopNow.Models.User;
import com.example.ShopNow.Models.dao.ConversationDAO;
import com.example.ShopNow.Models.dao.ConversationPartDAO;
import com.example.ShopNow.Models.dao.MessaageDAO;
import com.example.ShopNow.Models.dao.UserDAO;
import com.example.ShopNow.Repositories.ConversationPartRepository;
import com.example.ShopNow.Repositories.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class ConversationService {
    private ConversationDAO conversationDAO;
    private ConversationPartDAO conversationPartDAO;
    private MessaageDAO messaageDAO;
    private UserDAO userDAO;

    @Autowired
    public ConversationService(ConversationDAO conversationDAO,ConversationPartDAO conversationPartDAO,UserDAO userDAO,MessaageDAO messaageDAO){
        this.conversationDAO=conversationDAO;
        this.conversationPartDAO=conversationPartDAO;
        this.userDAO=userDAO;
        this.messaageDAO=messaageDAO;
    }
    public ResponseEntity<?> getConversation(int user1,int user2){
        Conversation conver=conversationDAO.getConversationFromUsers(user1,user2);
        if(conver == null){
            User use1=userDAO.getUserById(user1);
            User use2=userDAO.getUserById(user2);
            Conversation conv= conversationDAO.createConversation("user"+user1+"-user"+user2);
            ConversationParticipant participant1= conversationPartDAO.createConvPart(use1,conv);
            ConversationParticipant participant2= conversationPartDAO.createConvPart(use2,conv);
            Set<ConversationParticipant> participants = new HashSet<>();
            participants.add(participant1);
            participants.add(participant2);
            conv.setParticipants(participants);
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "conver",conv));
        }else{
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "conver",conver));
        }

    }

    public ResponseEntity<?> getAllConversation(int userid){
        User user=userDAO.getUserById(userid);
      List<ConversationParticipant> allParts = conversationPartDAO.getConvPart(user);
      List<Conversation> convs=new ArrayList<>();
      for (ConversationParticipant participant:allParts){
          Conversation conv=conversationDAO.getConversationById(participant.getConversation().getId());
          convs.add(conv);
      }
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "allConversation",convs));
    }

    public ResponseEntity<?> createMessage(int sellerId,int conversationId,String content, String msgType,int receiverInRoom){
        Conversation conversation= conversationDAO.getConversationById(conversationId);
        User seller=userDAO.getUserById(sellerId);

        Message msg=messaageDAO.createMessage(conversation,seller,content,msgType);
        if(receiverInRoom == 0){
            for(ConversationParticipant part : conversation.getParticipants()){
                if(part.getUser().getId() != sellerId){
                    conversationPartDAO.updateParticipant(part,"unread_msg");
                }
            }

        }


        if(msg != null){
            conversation.setLastMessage(content);
            conversation.setLastMessageAt(LocalDateTime.now());
            conversation.setUpdatedAt(LocalDateTime.now());
            conversation.addMsg(msg);
            conversationDAO.updateConv(conversation);
        }

        return ResponseEntity.ok(Map.of(
                "status", "success",
                "msg",msg,
                "conversation",conversation));
    }
    public ResponseEntity<?> updateConvPartData(int userId,int conversationId){
        Conversation conversation= conversationDAO.getConversationById(conversationId);

        if(conversation != null){
            for(ConversationParticipant part : conversation.getParticipants()){
                if(part.getUser().getId() == userId){

                    conversationPartDAO.updateParticipant(part,"read_msg");
                    break;
                }
            }
        }
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "conversation",conversation));
    }

    public ResponseEntity<?> deleteMsg(int msgId,int convId){
        Conversation conv=conversationDAO.getConversationById(convId);
        Message msg= messaageDAO.getMsgById(msgId);
        boolean isConvUpdated=false;
        if(conv.getLastMessage().equals(msg.getContent())){
            conv.setLastMessage("Message is deleted");
            conv.setLastMessageAt(LocalDateTime.now());
            conversationDAO.updateConv(conv);
            isConvUpdated=true;
        }
        msg.setContent("Message is deleted");

        return ResponseEntity.ok(Map.of(
                "status", "success",
                "msg",messaageDAO.updateMsg(msg),
                "isConvUpdated",isConvUpdated,
                "conv",conv));
    }
    public ResponseEntity<?> deleteConvMessages(int convId){

        messaageDAO.deleteConvMessages(convId);
        return ResponseEntity.ok(Map.of(
                "status", "success"));
    }

    public ResponseEntity<?> createConversation(int user1Id,int user2Id){
        User user1=userDAO.getUserById(user1Id);
        User user2=userDAO.getUserById(user2Id);
        if(user1 == null){
            throw new UserNotFoundException("User Doesnt Exist!");
        }
        if(user2 == null){
            throw new UserNotFoundException("User Doesnt Exist!");
        }
        Conversation conv= conversationDAO.createConversation("user"+user1Id+"-user"+user2Id);
        ConversationParticipant participant1= conversationPartDAO.createConvPart(user1,conv);
        ConversationParticipant participant2= conversationPartDAO.createConvPart(user2,conv);
        Set<ConversationParticipant> participants = new HashSet<>();
        participants.add(participant1);
        participants.add(participant2);
        conv.setParticipants(participants);

        return ResponseEntity.ok(Map.of(
                "status", "success",
                "conversation",conv));
    }


}
