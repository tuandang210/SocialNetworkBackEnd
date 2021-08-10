package com.codegym.controller;

import com.codegym.model.like.LikeStatus;
import com.codegym.service.likeStatus.ILikeStatusService;
import com.codegym.service.notification.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/likestatus")
public class LikeStatusController {
    @Autowired
    private ILikeStatusService likeStatusService;

    @Autowired
    private INotificationService notificationService;

    @GetMapping("/{id}")
    public ResponseEntity<Iterable<LikeStatus>> showAllLikeStatus(@PathVariable Long id){
        return new ResponseEntity<>(likeStatusService.findAllByStatusId(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LikeStatus> createLikeStatus(@RequestBody LikeStatus likeStatus) {
        LikeStatus saved = likeStatusService.save(likeStatus);
        notificationService.saveLikeNotification(saved);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LikeStatus> update(@PathVariable Long id, @RequestBody LikeStatus likeStatus) {
        Optional<LikeStatus> likeStatusOptional = likeStatusService.findById(id);
        if(!likeStatusOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        likeStatus.setId(likeStatusOptional.get().getId());
        return new ResponseEntity<>(likeStatus, HttpStatus.OK);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<LikeStatus> deleteLikeStatus(@PathVariable Long accountId, @RequestParam Long statusId){
        Optional<LikeStatus> optionalLikeStatus=likeStatusService.findByAccountIdAndStatusId(accountId, statusId);
        if (!optionalLikeStatus.isPresent()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        notificationService.deleteLikeNotification(optionalLikeStatus.get());
        likeStatusService.delete(optionalLikeStatus.get().getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
