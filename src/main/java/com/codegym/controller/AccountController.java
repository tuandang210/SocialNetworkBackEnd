package com.codegym.controller;

import com.codegym.model.account.Account;
import com.codegym.service.account.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class AccountController {
    @Autowired
    private IAccountService accountService;

    @GetMapping("/account")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Iterable<Account>> getAll() {
        return new ResponseEntity<>(accountService.findAll(), HttpStatus.OK);
    }
}
