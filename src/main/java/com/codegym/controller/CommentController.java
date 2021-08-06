package com.codegym.controller;

import com.codegym.model.comment.Comment;
import com.codegym.service.account.AccountService;
import com.codegym.service.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @GetMapping("/{id}")
    public ResponseEntity<Iterable<Comment>> showAllComment(@PathVariable Long id){
        return new ResponseEntity<>(commentService.findAllByStatusId(id), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Comment> saveComment(@RequestBody Comment comment){
        return new ResponseEntity<>(commentService.save(comment),HttpStatus.CREATED);
    };
    @PutMapping("/{id}")
    public ResponseEntity<Comment> editComment(@PathVariable Long id,@RequestBody Comment comment){
        Optional<Comment> commentOptional=commentService.findById(id);
        if (!commentOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        comment.setId(commentOptional.get().getId());
        return new ResponseEntity<>(commentService.save(comment),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Comment> deleteComment(@PathVariable Long id){
        Optional<Comment> commentOptional=commentService.findById(id);
        if (!commentOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        commentService.delete(id);
        return new ResponseEntity<>(commentOptional.get(),HttpStatus.NO_CONTENT);
    }
}
