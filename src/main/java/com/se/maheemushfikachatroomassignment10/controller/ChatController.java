package com.se.maheemushfikachatroomassignment10.controller;

import com.se.maheemushfikachatroomassignment10.model.ChatUser;
import com.se.maheemushfikachatroomassignment10.model.Message;
import com.se.maheemushfikachatroomassignment10.repository.ChatUserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ChatUserRepository chatUserRepository;

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message receiveMessage(@Payload Message message) {
        return message;
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<ChatUser>> getUsers() {
        return new ResponseEntity<>(chatUserRepository.findAll(), HttpStatus.OK);
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/chatroom/greetings")
    public List<ChatUser> addUser(@Payload Message chatMessage,
                                  SimpMessageHeaderAccessor headerAccessor) {
        //Add username in web socket session
        ChatUser chatUser = chatUserRepository.findByUsername(chatMessage.getSenderName());
        if (chatUser == null) {
            chatUser = new ChatUser(chatMessage.getSenderName());
        }
        chatUser.setActive(true);
        chatUserRepository.save(chatUser);

        return chatUserRepository.findAll();
    }

    @MessageMapping("/chat.setActiveUser")
    @SendTo("/chatroom/greetings")
    public List<ChatUser> activeUser(@Payload Message chatMessage,
                                     SimpMessageHeaderAccessor headerAccessor) {
        ChatUser chatUser = chatUserRepository.findByUsername(chatMessage.getSenderName());
        if (chatUser == null) {
            chatUser = new ChatUser(chatMessage.getSenderName());
        }
        chatUser.setActive(true);
        chatUserRepository.save(chatUser);

        return chatUserRepository.findAll();
    }

    @MessageMapping("/chat.setInActiveUser")
    @SendTo("/chatroom/greetings")
    public List<ChatUser> inactiveUser(@Payload Message chatMessage,
                                       SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        ChatUser chatUser = chatUserRepository.findByUsername(chatMessage.getSenderName());
        if (chatUser == null) {
            chatUser = new ChatUser(chatMessage.getSenderName());
        }
        chatUser.setActive(false);
        chatUserRepository.save(chatUser);

        return chatUserRepository.findAll();
    }

}
