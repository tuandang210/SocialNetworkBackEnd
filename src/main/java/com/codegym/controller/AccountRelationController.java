package com.codegym.controller;

import com.codegym.model.account.Account;
import com.codegym.model.dto.ResponseMessage;
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

import java.util.ArrayList;
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
        if (!accountService.existsAccountById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Iterable<AccountRelation> relations = accountRelationService.findAllByAccountId(id);
        if (!relations.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(relations, HttpStatus.OK);
    }


    @GetMapping("/friends/{id}")
    public ResponseEntity<Iterable<Account>> findFriendsOfAnAccount(@PathVariable("id") Long id) {
        if (!accountService.existsAccountById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Iterable<Account> friends = accountRelationService.findAllByAccountIdAndStatus(id, EFriendStatus.FRIEND);
        if (!friends.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(friends, HttpStatus.OK);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<Iterable<Account>> findFriendsOfAnAccount(@PathVariable Long id,@RequestParam String username){
        return new ResponseEntity<>(accountRelationService.findAllByAccount1UsernameContainingAndAccount1IdAndFriendStatus_Status(username,id),HttpStatus.OK);
    }


    @GetMapping("/{id1}/{id2}/friends")
    public ResponseEntity<Iterable<Account>> findMutualFriends(@PathVariable("id1") Long id1,
                                                               @PathVariable("id2") Long id2) {
        if (!accountService.existsAccountById(id1) && !accountService.existsAccountById(id2)) {
            return
                    new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(id1 == id2) {
            return new  ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
        }
        Iterable<Account> mutualFriends = accountRelationService.findMutualFriends(id1, id2);
        if (!mutualFriends.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(mutualFriends, HttpStatus.OK);
    }

    // Tìm danh sách lời mời kết bạn mình đã gửi
    @GetMapping("/{id}/sent")
    public ResponseEntity<Iterable<Account>> findFriendRequestSentByAccountId(@PathVariable("id") Long id) {
        if (!accountService.existsAccountById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Iterable<Account> sent = accountRelationService.findAllFriendRequestReceiver(id);
        if (!sent.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(sent, HttpStatus.OK);
    }

    //Tìm danh sách lời mời kết bạn mình đã nhận
    @GetMapping("/{id}/received")
    public ResponseEntity<Iterable<Account>> findFriendRequestReceivedByAccountId(@PathVariable("id") Long id) {
        if (!accountService.existsAccountById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Iterable<Account> received = accountRelationService.findAllFriendRequestSender(id);
        if (!received.iterator().hasNext()) {
            return new ResponseEntity<>(received, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(received, HttpStatus.OK);
    }


    @GetMapping("/{id}/guests")
    public ResponseEntity<Iterable<Account>> findGuestsOfAnAccount(@PathVariable("id") Long id) {
        if (!accountService.existsAccountById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Iterable<Account> guests = accountRelationService.findAllByAccountIdAndStatus(id, EFriendStatus.GUEST);
        if (!guests.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(guests, HttpStatus.OK);
    }

    // Tìm danh sách n

    // Tìm mối quan hệ của 2 tài khoản
    @GetMapping("/{id1}/{id2}")
    public ResponseEntity<AccountRelation> findEachRelation(@PathVariable("id1") Long id1,
                                                            @PathVariable("id2") Long id2) {
        if (id1 == id2 | !accountService.existsAccountById(id1) | !accountService.existsAccountById(id2)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<AccountRelation> accountRelation = accountRelationService.findByTwoAccountIds(id1, id2);
        if (accountRelation.isPresent()) {
            return new ResponseEntity<>(accountRelation.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    // Lưu quan hệ chung (không sửa)
    @PostMapping
    public ResponseEntity<AccountRelation> saveNewRelation(@RequestBody AccountRelation relation) {
        Long id1 = relation.getAccount1().getId();
        Long id2 = relation.getAccount2().getId();

        if (accountService.existsAccountById(id1) &&
                accountService.existsAccountById(id2) &&
                !checkRelationPairIsExisted(relation)) {
            accountRelationService.save(relation);
            return new ResponseEntity<>(relation, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    //Gửi lời mời kết bạn (không sửa)
    @PutMapping("/{id1}/request/{id2}")
    public ResponseEntity<?> sendFriendRequest(
            @PathVariable("id1") Long id1,
            @PathVariable("id2") Long id2
    ) {
        if (!accountService.existsAccountById(id1) | !accountService.existsAccountById(id2) ){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (id1 == id2) {
            return new ResponseEntity<>(new ResponseMessage("Duplicated accounts"), HttpStatus.BAD_REQUEST);
        }
        Account sender = accountService.findById(id1).get();
        Account receiver = accountService.findById(id2).get();

        AccountRelation relation = accountRelationService.findByTwoAccountIds(id1, id2).get();
        if (relation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        EFriendStatus currentStatus = relation.getFriendStatus().getStatus();
        if (currentStatus.equals(EFriendStatus.FRIEND)) {
            return new ResponseEntity<>(new ResponseMessage("Already friends"), HttpStatus.BAD_REQUEST);
        }
        if (currentStatus.equals(EFriendStatus.PENDING)) {
            if (relation.getAccount1().equals(sender)) {
                return new ResponseEntity<>(new ResponseMessage("Already sent a friend request"), HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(new ResponseMessage("Unaccepted friend request"), HttpStatus.BAD_REQUEST);
            }
        }

        FriendStatus pending = friendStatusService.findByStatus(EFriendStatus.PENDING).get();
        relation.setAccount1(sender);
        relation.setAccount2(receiver);
        relation.setFriendStatus(pending);
        return new ResponseEntity<>(accountRelationService.save(relation), HttpStatus.OK);
    }


    //Chấp nhận lời mời kết bạn
    @PutMapping("/{id1}/accept/{id2}")
    public ResponseEntity<?> acceptFriendRequest(
            @PathVariable("id1") Long id1,
            @PathVariable("id2") Long id2
    ) {
        if (!accountService.existsAccountById(id1) | !accountService.existsAccountById(id2) ){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (id1 == id2) {
            return new ResponseEntity<>(new ResponseMessage("Duplicated accounts"), HttpStatus.BAD_REQUEST);
        }
        Account receiver = accountService.findById(id1).get();
        Account sender = accountService.findById(id2).get();

        AccountRelation relation = accountRelationService.findByTwoAccountIds(id1, id2).get();
        if (relation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        EFriendStatus currentStatus = relation.getFriendStatus().getStatus();
        if (currentStatus.equals(EFriendStatus.FRIEND)) {
            return new ResponseEntity<>(new ResponseMessage("Already friends"), HttpStatus.BAD_REQUEST);
        }
        if (currentStatus.equals(EFriendStatus.GUEST)) {
            return new ResponseEntity<>(new ResponseMessage("Unsent friend request"), HttpStatus.BAD_REQUEST);
        }
        if (relation.getAccount1().equals(receiver)) {
            return new ResponseEntity<>(new ResponseMessage("Sent an unaccepted friend request"), HttpStatus.BAD_REQUEST);
        }

        FriendStatus friend = friendStatusService.findByStatus(EFriendStatus.FRIEND).get();
        relation.setFriendStatus(friend);
        return new ResponseEntity<>(accountRelationService.save(relation), HttpStatus.OK);
    }


    //Người gửi hủy lời mời kết bạn
    @PutMapping("/{id1}/cancel/{id2}")
    public ResponseEntity<?> deleteFriendRequest(
            @PathVariable("id1") Long id1,
            @PathVariable("id2") Long id2
    ) {
        if (!accountService.existsAccountById(id1) | !accountService.existsAccountById(id2) ){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (id1 == id2) {
            return new ResponseEntity<>(new ResponseMessage("Duplicated accounts"), HttpStatus.BAD_REQUEST);
        }
        Account sender = accountService.findById(id1).get();
        Account receiver = accountService.findById(id2).get();

        AccountRelation relation = accountRelationService.findByTwoAccountIds(id1, id2).get();
        if (relation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        EFriendStatus currentStatus = relation.getFriendStatus().getStatus();
        if (currentStatus.equals(EFriendStatus.FRIEND)) {
            return new ResponseEntity<>(new ResponseMessage("Already friends"), HttpStatus.BAD_REQUEST);
        }
        if (currentStatus.equals(EFriendStatus.GUEST)) {
            return new ResponseEntity<>(new ResponseMessage("Unsent friend request"), HttpStatus.BAD_REQUEST);
        }

        if (relation.getAccount1().equals(receiver)) {
            return new ResponseEntity<>(new ResponseMessage("Do not own this friend request"), HttpStatus.BAD_REQUEST);
        }

        FriendStatus guest = friendStatusService.findByStatus(EFriendStatus.GUEST).get();
        relation.setFriendStatus(guest);
        return new ResponseEntity<>(accountRelationService.save(relation), HttpStatus.OK);
    }


    //Người nhận từ chối lời mời kết bạn
    @PutMapping("{id1}/decline/{id2}")
    public ResponseEntity<?> declineFriendRequest(
            @PathVariable("id1") Long id1,
            @PathVariable("id2") Long id2
    ) {
        if (!accountService.existsAccountById(id1) | !accountService.existsAccountById(id2) ){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (id1 == id2) {
            return new ResponseEntity<>(new ResponseMessage("Duplicated accounts"), HttpStatus.BAD_REQUEST);
        }

        Account receiver = accountService.findById(id1).get();
        Account sender = accountService.findById(id2).get();
        AccountRelation relation = accountRelationService.findByTwoAccountIds(id1, id2).get();
        if (relation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        EFriendStatus currentStatus = relation.getFriendStatus().getStatus();
        if (currentStatus.equals(EFriendStatus.FRIEND)) {
            return new ResponseEntity<>(new ResponseMessage("Already friends"), HttpStatus.BAD_REQUEST);
        }
        if (currentStatus.equals(EFriendStatus.GUEST)) {
            return new ResponseEntity<>(new ResponseMessage("Unsent friend request"), HttpStatus.BAD_REQUEST);
        }
        if (relation.getAccount1().equals(receiver)) {
            return new ResponseEntity<>(new ResponseMessage("Cannot decline owned friend request"), HttpStatus.BAD_REQUEST);
        }
        FriendStatus guest = friendStatusService.findByStatus(EFriendStatus.GUEST).get();
        relation.setFriendStatus(guest);
        return new ResponseEntity<>(accountRelationService.save(relation), HttpStatus.OK);
    }


    //Người gửi unfriend người kia
    @PutMapping("/{id1}/unfriend/{id2}")
    public ResponseEntity<?> unfriend(
            @PathVariable("id1") Long id1,
            @PathVariable("id2") Long id2
    ) {
        if (!accountService.existsAccountById(id1) | !accountService.existsAccountById(id2) ){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (id1 == id2) {
            return new ResponseEntity<>(new ResponseMessage("Duplicated accounts"), HttpStatus.BAD_REQUEST);
        }

        AccountRelation relation = accountRelationService.findByTwoAccountIds(id1, id2).get();
        if (relation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        EFriendStatus currentStatus = relation.getFriendStatus().getStatus();
        if (!currentStatus.equals(EFriendStatus.FRIEND)) {
            return new ResponseEntity<>(new ResponseMessage("Not friends"), HttpStatus.BAD_REQUEST);
        }

        FriendStatus guest = friendStatusService.findByStatus(EFriendStatus.GUEST).get();
        relation.setFriendStatus(guest);
        return new ResponseEntity<>(accountRelationService.save(relation), HttpStatus.OK);
    }



    private boolean checkRelationPairIsExisted(AccountRelation relation) {
        Long id1 = relation.getAccount1().getId();
        Long id2 = relation.getAccount2().getId();
        if (accountService.existsAccountById(id1) && accountService.existsAccountById(id2)) {
            Optional<AccountRelation> accountRelation = accountRelationService.findByTwoAccountIds(id1, id2);
            if(accountRelation.isPresent()){
                return true;
            }
        }
        return false;
    }
}
