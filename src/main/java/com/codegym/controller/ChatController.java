package com.codegym.controller;

import com.codegym.model.chat.Chat;
import com.codegym.service.chat.IChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/chats")
public class ChatController {

    @Autowired
    private IChatService chatService;

    @GetMapping
    public ResponseEntity<Iterable<Chat>> findAll() {
        return new ResponseEntity<>(chatService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/pagination")
    public ResponseEntity<List<Chat>> findAllPage(@RequestParam int page, @RequestParam int size) {
        return new ResponseEntity<>(chatService.findAll(page, size), HttpStatus.OK);
    }

    @GetMapping("/send")
    public ResponseEntity<Optional<Chat>> findAllPage(@RequestParam Long id1, @RequestParam Long id2) {
        return new ResponseEntity<>(chatService.findChatByAccount1_IdAndAccount2_Id(id1, id2), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Chat> save(@RequestBody Chat chat) {
        return new ResponseEntity<>(chatService.save(chat), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id1}/delete/{id2}")
    public ResponseEntity<?> delete(@PathVariable Long id1, @PathVariable Long id2) {
        Optional<Chat> chat = chatService.findChatByAccount1_IdAndAccount2_Id(id1, id2);
        if (chat.isPresent()) {
            chatService.delete(chat.get().getId());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id1}/message/{id2}")
    public ResponseEntity<Iterable<Chat>> getAllChatAccount(@PathVariable Long id1, @PathVariable Long id2) {
        return new ResponseEntity<>(chatService.findChatsByAccount1_IdAndAccount2_Id(id1, id2),HttpStatus.OK);
    }


}
