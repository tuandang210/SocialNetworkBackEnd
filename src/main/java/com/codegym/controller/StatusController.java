package com.codegym.controller;

import com.codegym.model.status.Status;
import com.codegym.service.status.IStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/status")
public class StatusController {
    @Autowired
    private IStatusService statusService;
    @GetMapping("/list")
    public ResponseEntity<Iterable<Status>> showAllStatus(){
        return new ResponseEntity<>(statusService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Status> findByIdStatus(@PathVariable Long id){
        Optional<Status> statusOptional=statusService.findById(id);
        if (!statusOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(statusOptional.get(),HttpStatus.OK);
    }
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Status> createStatus(@RequestBody Status status){
        return new ResponseEntity<>(statusService.save(status),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Status> editStatus(@RequestBody Status status,@PathVariable Long id){
        Optional<Status> statusOptional=statusService.findById(id);
        if (!statusOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        status.setId(statusOptional.get().getId());
        return new ResponseEntity<>(statusService.save(status),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Status> deleteStatus(@PathVariable Long id){
        Optional<Status> statusOptional=statusService.findById(id);
        if (!statusOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        statusService.delete(id);
        return new ResponseEntity<>(statusOptional.get(),HttpStatus.NO_CONTENT);
    }
}
