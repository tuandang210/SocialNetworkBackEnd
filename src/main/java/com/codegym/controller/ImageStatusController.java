package com.codegym.controller;

import com.codegym.model.image.ImageStatus;
import com.codegym.service.imageStatus.IStatusImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/images")
public class ImageStatusController {
    @Autowired
    private IStatusImageService statusImageService;

    @GetMapping("/status/{id}")
    public ResponseEntity<Iterable<ImageStatus>> findAllByStatus(@PathVariable Long id) {
        Iterable<ImageStatus> imageStatuses = statusImageService.findAllByStatus_Id(id);
        if(!imageStatuses.iterator().hasNext()) {
            return new ResponseEntity<>(imageStatuses, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(imageStatuses, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ImageStatus> createImage (@RequestBody ImageStatus imageStatus) {
        return new ResponseEntity<>(statusImageService.save(imageStatus), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageStatus> findById(@PathVariable Long id){
        Optional<ImageStatus> imageStatus = statusImageService.findById(id);
        if (!imageStatus.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(imageStatus.get(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ImageStatus> updateImage(@RequestBody ImageStatus imageStatus, @PathVariable Long id) {
        Optional<ImageStatus> imageStatus1 = statusImageService.findById(id);
        if (!imageStatus1.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        imageStatus.setId(imageStatus1.get().getId());
        return new ResponseEntity<>(statusImageService.save(imageStatus), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ImageStatus> deleteImage(@PathVariable Long id){
        Optional<ImageStatus> imageStatus = statusImageService.findById(id);
        if (!imageStatus.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        statusImageService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
