package com.codegym.controller;

import com.codegym.model.account.Account;
import com.codegym.model.account.Role;
import com.codegym.service.account.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private IAccountService accountService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Iterable<Account>> getAll() {
        return new ResponseEntity<>(accountService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<Iterable<Account>> getAllUser() {
        return new ResponseEntity<>(accountService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Optional<Account>> findById(@PathVariable Long id) {
        Optional<Account> accountOptional = accountService.findById(id);
        if (!accountOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accountOptional, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Account> createNew(@RequestBody Account account) {
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(new Role(2L,"ROLE_USER"));
        account.setRoles(roleSet);
        return new ResponseEntity<>(accountService.save(account), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Account> updateAccount(@RequestBody Account account) {
        Account account1 = accountService.findById(account.getId()).get();
        account1.setFullName(account.getFullName());
        account1.setAddress(account.getAddress());
        account1.setPhone(account.getPhone());
        account1.setFavorite(account.getFavorite());
        account1.setAvatar(account.getAvatar());
        return new ResponseEntity<>(accountService.saveVer(account1), HttpStatus.OK);
    }

    @PutMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Account> blockAccount(@RequestBody Account account) {
        Account account2 = accountService.findById(account.getId()).get();
        account2.setUsername(account.getUsername());
        account2.setPassword(account.getPassword());
        account2.setEmail(account.getEmail());
        account2.setBirthday(account.getBirthday());
        account2.setFullName(account.getFullName());
        account2.setAddress(account.getAddress());
        account2.setPhone(account.getPhone());
        account2.setFavorite(account.getFavorite());
        account2.setAvatar(account.getAvatar());
        return new ResponseEntity<>(accountService.save(account), HttpStatus.OK);
    }
}
