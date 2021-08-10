package com.codegym.controller;

import com.codegym.model.like.LikeComment;
import com.codegym.service.likeComment.ILikeCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/likeComment")
public class LikeCommentController {
    @Autowired
    private ILikeCommentService likeCommentService;
    @GetMapping("/{id}")
    public ResponseEntity<Iterable<LikeComment>> showLikeComment(@PathVariable Long id){
        return new ResponseEntity<>(likeCommentService.findAllByCommentId(id), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<LikeComment> createLikeComment(@RequestBody LikeComment likeComment){
        return new ResponseEntity<>(likeCommentService.save(likeComment),HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<LikeComment> editLikeComment(@RequestBody LikeComment likeComment,@PathVariable Long id){
        Optional<LikeComment>optionalLikeComment=likeCommentService.findById(id);
        if (!optionalLikeComment.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        likeComment.setId(optionalLikeComment.get().getId());
        return new ResponseEntity<>(likeCommentService.save(likeComment),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<LikeComment> disLikeComment(@PathVariable Long id){
        Optional<LikeComment>optionalLikeComment=likeCommentService.findById(id);
        if (!optionalLikeComment.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        likeCommentService.delete(id);
        return new ResponseEntity<>(optionalLikeComment.get(),HttpStatus.NO_CONTENT);
    }
}
