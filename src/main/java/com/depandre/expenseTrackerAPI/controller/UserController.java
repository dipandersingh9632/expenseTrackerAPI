package com.depandre.expenseTrackerAPI.controller;


import com.depandre.expenseTrackerAPI.entity.User;
import com.depandre.expenseTrackerAPI.entity.UserModel;
import com.depandre.expenseTrackerAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> save(@RequestBody UserModel userModel){
        return new ResponseEntity<>(userService.createUser(userModel), HttpStatus.CREATED);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> get(@PathVariable Long id){
        return new ResponseEntity<>(userService.read(id), HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> update(@RequestBody UserModel updatedUser, @PathVariable Long id){
        return new ResponseEntity<>(userService.update(updatedUser, id), HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> delete(@PathVariable Long id){
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
