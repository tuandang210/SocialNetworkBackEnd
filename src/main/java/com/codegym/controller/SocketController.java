package com.codegym.controller;

import com.codegym.model.chat.Chat;
import com.codegym.service.chat.IChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin("*")
@RestController
public class SocketController {
    @Autowired
    private IChatService chatService;

    @MessageMapping("/message")
    @SendTo("/topic/message")
    public Chat messageUsiSocket(Chat chat) {
        return chatService.save(chat);
    }
}
