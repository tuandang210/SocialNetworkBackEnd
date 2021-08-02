package com.codegym.controller;

import com.codegym.model.account.Privacy;
import com.codegym.service.privacy.IPrivacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/privacy")
public class PrivacyController {
    @Autowired
    private IPrivacyService privacyService;
    @GetMapping
    public ResponseEntity<Iterable<Privacy>> showPrivacy(){
        return new ResponseEntity<>(privacyService.findAll(), HttpStatus.OK);
    }
}
