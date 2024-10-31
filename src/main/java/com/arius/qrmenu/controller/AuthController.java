package com.arius.qrmenu.controller;

import java.util.List;

import com.arius.qrmenu.model.AuthRequest;
import com.arius.qrmenu.model.User;
import com.arius.qrmenu.model.UserInfoDetails;
import com.arius.qrmenu.service.JwtService;
import com.arius.qrmenu.service.UserInfoService;
import com.arius.qrmenu.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/auth")
@CrossOrigin
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping ("/user")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping ("/login")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        final UserInfoDetails userDetails = (UserInfoDetails) service.loadUserByUsername(authRequest.getUsername());
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                return new ResponseEntity<String>(jwtService.generateToken(userDetails), HttpStatus.OK);
            } else {
                throw new UsernameNotFoundException("invalid user request !");
            }
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // @PostMapping("/addNewUser")
    // public ResponseEntity<?> createUser(@RequestBody User user) {
    //
    // user.setPassword(passwordEncoder.encode(user.getPassword()));
    // userService.createUser(user);
    // return new ResponseEntity<>(HttpStatus.OK);
    //
    // }
}
