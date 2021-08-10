package com.codegym.controller;

import com.codegym.model.account.Account;
import com.codegym.model.comment.Comment;
import com.codegym.model.dto.ResponseMessage;
import com.codegym.model.enumeration.EFriendStatus;
import com.codegym.model.friend.AccountRelation;
import com.codegym.model.like.LikeStatus;
import com.codegym.model.notification.Notification;
import com.codegym.model.status.Status;
import com.codegym.service.account.IAccountService;
import com.codegym.service.accountRelation.IAccountRelationService;
import com.codegym.service.notification.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    private INotificationService notificationService;

    @Autowired
    private IAccountRelationService accountRelationService;

    @Autowired
    private IAccountService accountService;

    @GetMapping("/{id}")
    public ResponseEntity<Notification> findById (@PathVariable("id") Long id) {
        Optional<Notification> noti = notificationService.findById(id);
        if (!noti.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(noti.get(), HttpStatus.OK);
    }


    @GetMapping("/account/unread/{id}")
    public ResponseEntity<Iterable<Notification>> findUnreadByAccountId (@PathVariable("id") Long id) {
        Iterable<Notification> noti = notificationService.findAllUnreadByAccountId(id);
        if (!noti.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(noti, HttpStatus.OK);
    }


    @GetMapping("/account/read/{id}")
    public ResponseEntity<Iterable<Notification>> findAllReadByAccountId(@PathVariable("id") Long id) {
        Iterable<Notification> noti = notificationService.findAllReadByAccountId(id);
        if (!noti.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(noti, HttpStatus.OK);
    }


    @GetMapping("/account/{id}")
    public ResponseEntity<Iterable<Notification>> findAllByAccountId(@PathVariable("id") Long id) {
        Iterable<Notification> noti = notificationService.findAllByAccountId(id);
        if (!noti.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(noti, HttpStatus.OK);
    }


    // save comment notification
    @PostMapping("/comment")
    public ResponseEntity<?> saveCommentNoti (@RequestBody Comment comment) {
        Account author = comment.getStatus().getAccount();
        Account commenter = comment.getAccount();
        if (author.equals(commenter)) {
            return new ResponseEntity<>(new ResponseMessage("Should not receive notification about myself"), HttpStatus.BAD_REQUEST);
        }

        AccountRelation relation = accountRelationService.findByTwoAccountIds(author.getId(), commenter.getId()).get();
        if (!relation.getFriendStatus().getStatus().equals(EFriendStatus.FRIEND)) {
            return new ResponseEntity<>(new ResponseMessage("Not friends"), HttpStatus.BAD_REQUEST);
        }

        Notification commentNoti = new Notification();
        commentNoti.setAccount(author);
        commentNoti.setComment(comment);
        commentNoti.setContent(commenter.getUsername() + " đã bình luận về bài viết của bạn");
        return new ResponseEntity<>(notificationService.save(commentNoti), HttpStatus.CREATED);
    }


    // save like notification
    @PostMapping("/like")
    public ResponseEntity<?> saveLikeNoti (@RequestBody LikeStatus like) {
        Account author = like.getStatus().getAccount();
        Account liker = like.getAccount();
        if (author.equals(liker)) {
            return new ResponseEntity<>(new ResponseMessage("Should not receive notification about myself"), HttpStatus.BAD_REQUEST);
        }

        AccountRelation relation = accountRelationService.findByTwoAccountIds(author.getId(), liker.getId()).get();
        if (!relation.getFriendStatus().getStatus().equals(EFriendStatus.FRIEND)) {
            return new ResponseEntity<>(new ResponseMessage("Not friends"), HttpStatus.BAD_REQUEST);
        }

        Notification likeNoti = new Notification();
        likeNoti.setAccount(author);
        likeNoti.setLike(like);
        likeNoti.setContent(liker.getUsername() + " đã thích bài viết của bạn");
        return new ResponseEntity<>(notificationService.save(likeNoti), HttpStatus.CREATED);
    }


    // save status notification
    @PostMapping("/status/{id}")
    public ResponseEntity<?> saveStatusNoti (@RequestBody Status status,
                                             @PathVariable("id") Long id) {
        Account author = status.getAccount();
        if (!accountService.existsAccountById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (author.getId() == id) {
            return new ResponseEntity<>(new ResponseMessage("Should not receive notification about myself"), HttpStatus.BAD_REQUEST);
        }

        AccountRelation relation = accountRelationService.findByTwoAccountIds(author.getId(), id).get();
        if (!relation.getFriendStatus().getStatus().equals(EFriendStatus.FRIEND)) {
            return new ResponseEntity<>(new ResponseMessage("Not friends"), HttpStatus.BAD_REQUEST);
        }
        if (status.getPrivacy().getName().equals("only-me")) {
            return new ResponseEntity<>(new ResponseMessage("Private status"), HttpStatus.BAD_REQUEST);
        }

        Notification statusNoti = new Notification();
        statusNoti.setAccount(accountService.findById(id).get());
        statusNoti.setStatus(status);
        statusNoti.setContent(author.getUsername() + " đã đăng một bài viết mới");
        return new ResponseEntity<>(notificationService.save(statusNoti), HttpStatus.CREATED);
    }

    // đánh dấu chưa đọc
    @PutMapping("/unread/{id}")
    public ResponseEntity<?> markAsUnread (@PathVariable("id") Long id) {
        Notification noti = notificationService.findById(id).get();
        if (noti == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (noti.getIsRead() == false){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        noti.setIsRead(false);
        return new ResponseEntity<>(notificationService.save(noti), HttpStatus.OK);
    }

    // đánh dấu đã đọc
    @PutMapping("/read/{id}")
    public ResponseEntity<?> markAsRead (@PathVariable("id") Long id) {
        Notification noti = notificationService.findById(id).get();
        if (noti == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (noti.getIsRead() == true){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        noti.setIsRead(true);
        return new ResponseEntity<>(notificationService.save(noti), HttpStatus.OK);
    }


    // đánh dấu đã đọc tất cả
    @PutMapping("/read/all")
    public ResponseEntity<?> markAllAsRead(@PathVariable("id") Long id) {
        Iterable<Notification> unreadNoti = notificationService.findAllUnreadByAccountId(id);
        if (!unreadNoti.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        for (Notification noti: unreadNoti) {
            noti.setIsRead(true);
            notificationService.save(noti);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
