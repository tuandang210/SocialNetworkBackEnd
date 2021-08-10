package com.codegym.controller;

import com.codegym.model.account.Account;
import com.codegym.model.chat.Chat;
import com.codegym.model.group.Group;
import com.codegym.service.account.IAccountService;
import com.codegym.service.chat.IChatService;
import com.codegym.service.group.IGroupService;
import com.fasterxml.jackson.databind.util.ArrayIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "/groups")
public class GroupController {
    @Autowired
    private IChatService chatService;

    @Autowired
    private IGroupService groupService;

    @Autowired
    private IAccountService accountService;

    @GetMapping("/{id}")
    public ResponseEntity<Iterable<Chat>> getAll(@PathVariable Long id, @RequestParam int size) {
        return new ResponseEntity<>(chatService.findChatsByGroup_IdOrderByDateAsc(id, 10), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Group> save(@RequestBody Group group) {
        return new ResponseEntity<>(groupService.save(group), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Group> delete(@PathVariable Long id) {
        groupService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Group> addNewAccount(@RequestBody Group group) {
        Group group1 = groupService.findById(group.getId()).get();
        group1.setAccount(group.getAccount());
        return new ResponseEntity<>(groupService.save(group1), HttpStatus.OK);
    }

    @PutMapping("/{groupId}/exit/{id}")
    public ResponseEntity<Group> exitGroup(@PathVariable Long groupId, @PathVariable Long id) {
        Group group = groupService.findById(groupId).get();
        Set<Account> accounts =  group.getAccount();
        for (Account ac: accounts) {
            if (ac.getId().equals(id)) {
                accounts.remove(ac);
                break;
            }
        }
        group.setAccount( accounts);
        return new ResponseEntity<>(groupService.save(group), HttpStatus.OK);
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<Iterable<Group>> getGroupsByAccountId(@PathVariable Long id) {
        Optional<Account> group = accountService.findById(id);
        return group.map(account -> new ResponseEntity<>(groupService.findGroupsByAccount(account), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK));
    }

    @GetMapping("/{groupId}/account")
    public ResponseEntity<Iterable<Account>> getAccountOnGroup(@PathVariable Long groupId) {
        Optional<Group> groups = groupService.findById(groupId);
        return new ResponseEntity<>(groups.get().getAccount(), HttpStatus.OK);
    }
    @GetMapping("/search/{id}")
    public ResponseEntity<Iterable<Group>> findGroupsByNameContaining(@RequestParam String name,@PathVariable Long id){
        return new ResponseEntity<>(groupService.findGroupsByNameContaining(name,accountService.findById(id).get()),HttpStatus.OK);
    }
}
