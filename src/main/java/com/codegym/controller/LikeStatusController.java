package com.codegym.controller;

import com.codegym.model.like.LikeStatus;
import com.codegym.service.likeStatus.ILikeStatusService;
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
    @GetMapping("/{id}")
    public ResponseEntity<Iterable<LikeStatus>> showAllLikeStatus(@PathVariable Long id){
        return new ResponseEntity<>(likeStatusService.findAllByStatusIdAndIsLikeTrue(id), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<LikeStatus> createLikeStatus(@RequestBody LikeStatus likeStatus){
        return new ResponseEntity<>(likeStatusService.save(likeStatus),HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<LikeStatus> deleteLikeStatus(@PathVariable Long id){
        Optional<LikeStatus> optionalLikeStatus=likeStatusService.findById(id);
        if (!optionalLikeStatus.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        likeStatusService.delete(id);
        return new ResponseEntity<>(optionalLikeStatus.get(),HttpStatus.OK);
    }
}
