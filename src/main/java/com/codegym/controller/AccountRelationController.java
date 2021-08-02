package com.codegym.controller;

import com.codegym.model.account.Account;
import com.codegym.model.enumeration.EFriendStatus;
import com.codegym.model.friend.AccountRelation;
import com.codegym.model.friend.FriendStatus;
import com.codegym.service.account.IAccountService;
import com.codegym.service.accountRelation.IAccountRelationService;
import com.codegym.service.friendStatus.IFriendStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/relations")
public class AccountRelationController {
    @Autowired
    private IAccountService accountService;

    @Autowired
    private IAccountRelationService accountRelationService;

    @Autowired
    private IFriendStatusService friendStatusService;

    @GetMapping
    public ResponseEntity<Iterable<AccountRelation>> findAll() {
        Iterable<AccountRelation> relations = accountRelationService.findAll();
        if (!relations.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(relations, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Iterable<AccountRelation>> findAllByAccountId(@PathVariable("id") Long id) {
        if (!checkAccountIsExistedById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Iterable<AccountRelation> relations = accountRelationService.findAllByAccountId(id);
        if (!relations.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(relations, HttpStatus.OK);
    }


    @GetMapping("/{id}/friends")
    public ResponseEntity<Iterable<Account>> findFriendsOfAnAccount(@PathVariable("id") Long id) {
        if (!checkAccountIsExistedById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Iterable<Account> friends = accountRelationService.findAllByAccountIdAndStatus(id, EFriendStatus.FRIEND);
        if (!friends.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(friends, HttpStatus.OK);
    }


    @GetMapping("/{id}/pending")
    public ResponseEntity<Iterable<Account>> findPendingRequestOfAnAccount(@PathVariable("id") Long id) {
        if (!checkAccountIsExistedById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Iterable<Account> pending = accountRelationService.findAllByAccountIdAndStatus(id, EFriendStatus.PENDING);
        if (!pending.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pending, HttpStatus.OK);
    }


    @GetMapping("/{id}/guests")
    public ResponseEntity<Iterable<Account>> findGuestsOfAnAccount(@PathVariable("id") Long id) {
        if (!checkAccountIsExistedById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Iterable<Account> guests = accountRelationService.findAllByAccountIdAndStatus(id, EFriendStatus.GUEST);
        if (!guests.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(guests, HttpStatus.OK);
    }


    @GetMapping("/{id1}/{id2}")
    public ResponseEntity<AccountRelation> findEachRelation(@PathVariable("id1") Long id1,
                                                            @PathVariable("id2") Long id2) {
        if (!checkAccountIsExistedById(id1) | !checkAccountIsExistedById(id2)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<AccountRelation> accountRelation = accountRelationService.findByTwoAccountIds(id1, id2);
        if (accountRelation.isPresent()) {
            return new ResponseEntity<>(accountRelation.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping
    public ResponseEntity<AccountRelation> saveNewRelation(@RequestBody AccountRelation relation) {
        Long id1 = relation.getAccount1().getId();
        Long id2 = relation.getAccount2().getId();

        if (checkAccountIsExistedById(id1) && checkAccountIsExistedById(id2) && !checkRelationPairIsExisted(relation)) {
            accountRelationService.save(relation);
            return new ResponseEntity<>(relation, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @PutMapping("/{id1}/sendRequest/{id2}")
    public ResponseEntity<AccountRelation> sendFriendRequest(
            @PathVariable("id1") Long id1,
            @PathVariable("id2") Long id2
    ) {
        if (!checkAccountIsExistedById(id1) | !checkAccountIsExistedById(id2) ){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AccountRelation relation = accountRelationService.findByTwoAccountIds(id1, id2).get();
        if (relation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!relation.getFriendStatus().getStatus().equals(EFriendStatus.GUEST)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        FriendStatus pending = friendStatusService.findByStatus(EFriendStatus.PENDING).get();
        relation.setFriendStatus(pending);
        return new ResponseEntity<>(accountRelationService.save(relation), HttpStatus.OK);
    }


    @PutMapping("/{id1}/acceptRequest/{id2}")
    public ResponseEntity<AccountRelation> acceptFriendRequest(
            @PathVariable("id1") Long id1,
            @PathVariable("id2") Long id2
    ) {
        if (!checkAccountIsExistedById(id1) | !checkAccountIsExistedById(id2) ){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AccountRelation relation = accountRelationService.findByTwoAccountIds(id1, id2).get();
        if (relation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!relation.getFriendStatus().getStatus().equals(EFriendStatus.PENDING)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        FriendStatus friend = friendStatusService.findByStatus(EFriendStatus.FRIEND).get();
        relation.setFriendStatus(friend);
        return new ResponseEntity<>(accountRelationService.save(relation), HttpStatus.OK);
    }


    @PutMapping("/{id1}/deleteRequest/{id2}")
    public ResponseEntity<AccountRelation> deleteFriendRequest(
            @PathVariable("id1") Long id1,
            @PathVariable("id2") Long id2
    ) {
        if (!checkAccountIsExistedById(id1) | !checkAccountIsExistedById(id2) ){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AccountRelation relation = accountRelationService.findByTwoAccountIds(id1, id2).get();
        if (relation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!relation.getFriendStatus().getStatus().equals(EFriendStatus.PENDING)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        FriendStatus guest = friendStatusService.findByStatus(EFriendStatus.GUEST).get();
        relation.setFriendStatus(guest);
        return new ResponseEntity<>(accountRelationService.save(relation), HttpStatus.OK);
    }


    @PutMapping("/{id1}/unfriend/{id2}")
    public ResponseEntity<AccountRelation> unfriend(
            @PathVariable("id1") Long id1,
            @PathVariable("id2") Long id2
    ) {
        if (!checkAccountIsExistedById(id1) | !checkAccountIsExistedById(id2) ){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AccountRelation relation = accountRelationService.findByTwoAccountIds(id1, id2).get();
        if (relation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!relation.getFriendStatus().getStatus().equals(EFriendStatus.FRIEND)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        FriendStatus guest = friendStatusService.findByStatus(EFriendStatus.GUEST).get();
        relation.setFriendStatus(guest);
        return new ResponseEntity<>(accountRelationService.save(relation), HttpStatus.OK);
    }



    private boolean checkRelationPairIsExisted(AccountRelation relation) {
        Long id1 = relation.getAccount1().getId();
        Long id2 = relation.getAccount2().getId();
        if (checkAccountIsExistedById(id1) && checkAccountIsExistedById(id2)) {
            Optional<AccountRelation> accountRelation = accountRelationService.findByTwoAccountIds(id1, id2);
            if(accountRelation.isPresent()){
                return true;
            }
        }
        return false;
    }

    private boolean checkAccountIsExistedById(Long id) {
        Optional<Account> accountOptional = accountService.findById(id);
        if (accountOptional.isPresent()){
            return true;
        }
        return false;
    }
}
