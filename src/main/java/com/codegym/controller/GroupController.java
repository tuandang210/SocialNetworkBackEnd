package com.codegym.controller;

import com.codegym.model.chat.Chat;
import com.codegym.model.group.Group;
import com.codegym.service.chat.IChatService;
import com.codegym.service.group.IGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "/groups")
public class GroupController {
    @Autowired
    private IChatService chatService;

    @Autowired
    private IGroupService groupService;

    @GetMapping("/{id}")
    public ResponseEntity<Iterable<Chat>> getAll(@PathVariable Long id,@RequestParam int size){
        return new ResponseEntity<>(chatService.findChatsByGroup_IdOrderByDateAsc(id,size), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Group> save(@RequestBody Group group) {
        return new ResponseEntity<>(groupService.save(group),HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Group> delete(@PathVariable Long id) {
        groupService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<Group> addNewAccount(@RequestBody Group group){
        return new ResponseEntity<>(groupService.save(group),HttpStatus.OK);
    }
}
