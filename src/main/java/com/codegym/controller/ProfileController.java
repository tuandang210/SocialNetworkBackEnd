package com.codegym.controller;

import com.codegym.service.account.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private IAccountService accountService;
    @GetMapping
    public ResponseEntity<?> getAccount(@RequestParam String username){
        return new ResponseEntity<>(accountService.findByUsername(username), HttpStatus.OK);
    }
}
