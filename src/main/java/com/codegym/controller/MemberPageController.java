package com.codegym.controller;


import com.codegym.model.page.MemberPage;
import com.codegym.service.memberpage.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/members")
public class MemberPageController {

    @Autowired
    private IMemberService memberService;

    @PutMapping
    public ResponseEntity<MemberPage> addNewMemberAndUpdateMember(@RequestBody MemberPage memberPage) {
        return new ResponseEntity<>(memberService.save(memberPage), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<MemberPage>> findByAccountId(@PathVariable Long id) {
        return new ResponseEntity<>(memberService.findMemberPageByAccount_Id(id), HttpStatus.OK);
    }
}
