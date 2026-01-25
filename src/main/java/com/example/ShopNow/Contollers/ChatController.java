package com.example.ShopNow.Contollers;

import com.example.ShopNow.Services.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat/")
public class ChatController {

    public ConversationService conversationService;

    @Autowired
    public ChatController(ConversationService conversationService){
        this.conversationService=conversationService;
    }

    @GetMapping("/getConversation")
    public ResponseEntity<?> getConversation(@RequestParam("user1_id")int user1,@RequestParam("user2_id")int user2){
        return conversationService.getConversation(user1,user2);
    }
    @GetMapping("/getAllConversation")
    public ResponseEntity<?> getAllConversation(@RequestParam("user_id")int userId){
        return conversationService.getAllConversation(userId);
    }

    @PostMapping("/createMessage")
    public ResponseEntity<?> createMessage(@RequestParam("user_id")int userId,@RequestParam("conversation_id")int convId,@RequestParam("content")String content,@RequestParam("msgType")String msgType,@RequestParam("receiverInRoom")int receiverInRoom){
        return conversationService.createMessage(userId,convId,content,msgType,receiverInRoom);
    }
    @PostMapping("/updateConv")
    public ResponseEntity<?> updateConvPartData(@RequestParam("user_id")int userId,@RequestParam("conversation_id")int convId){
        return conversationService.updateConvPartData(userId,convId);
    }
    @PostMapping("/createConv")
    public ResponseEntity<?> createConversation(@RequestParam("user1_id")int user1Id,@RequestParam("user2_id")int user2Id){
        return conversationService.createConversation(user1Id,user2Id);
    }
    @PutMapping("/deleteMsg")
    public ResponseEntity<?> deleteConv(@RequestParam("msg_id")int msgId,@RequestParam("conv_id")int convId){
        return conversationService.deleteMsg(msgId,convId);
    }
    @DeleteMapping("/deleteAllMsg")
    public ResponseEntity<?> deleteConvMessages(@RequestParam("conv_id")int convId){
        return conversationService.deleteConvMessages(convId);
    }
}
