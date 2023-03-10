package com.depandre.expenseTrackerAPI.controller;

import com.depandre.expenseTrackerAPI.entity.JwtResponse;
import com.depandre.expenseTrackerAPI.entity.LoginModel;
import com.depandre.expenseTrackerAPI.entity.User;
import com.depandre.expenseTrackerAPI.entity.UserModel;
import com.depandre.expenseTrackerAPI.security.CustomUserDetailsService;
import com.depandre.expenseTrackerAPI.service.UserService;
import com.depandre.expenseTrackerAPI.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;




    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginModel loginDetails) throws Exception {
        authenticate(loginDetails.getEmail(), loginDetails.getPassword());

        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginDetails.getEmail());

        //we need to generate the jwt token
        final String token = jwtTokenUtil.generateToken(userDetails);

        return new ResponseEntity<JwtResponse>(new JwtResponse(token), HttpStatus.OK);
    }

    private void authenticate(String email, String password) throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("User disabled");
        } catch (BadCredentialsException e) {
            throw new Exception("Bad Credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> save(@RequestBody UserModel userModel){
        return new ResponseEntity<>(userService.createUser(userModel), HttpStatus.CREATED);
    }
}
