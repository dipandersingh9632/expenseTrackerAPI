package com.depandre.expenseTrackerAPI.controller;

import com.depandre.expenseTrackerAPI.entity.LoginModel;
import com.depandre.expenseTrackerAPI.entity.User;
import com.depandre.expenseTrackerAPI.entity.UserModel;
import com.depandre.expenseTrackerAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/login")
    public ResponseEntity<HttpStatus> login(@RequestBody LoginModel loginDetails){
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                                        loginDetails.getEmail(), loginDetails.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> save(@RequestBody UserModel userModel){
        return new ResponseEntity<>(userService.createUser(userModel), HttpStatus.CREATED);
    }
}
