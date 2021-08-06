package com.codegym.controller;

import com.codegym.model.comment.Comment;
import com.codegym.model.like.LikeComment;
import com.codegym.model.like.LikeStatus;
import com.codegym.model.notification.Notification;
import com.codegym.model.status.Status;
import com.codegym.service.notification.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("*")
@Controller
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    private INotificationService notificationService;


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


    @PostMapping("/commentedstatus")
    public ResponseEntity<?> saveCommentedStatusNoti (@RequestBody Comment comment) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping("/likedstatus")
    public ResponseEntity<?> saveLikedStatusNoti (@RequestBody LikeStatus like) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping("/likedcomment")
    public ResponseEntity<?> saveLikedCommentNoti (@RequestBody LikeComment like) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
