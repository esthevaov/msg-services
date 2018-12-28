package com.vervloet.msgservices.controller;

import com.vervloet.msgservices.model.User;
import com.vervloet.msgservices.repository.UserRepository;
import com.vervloet.msgservices.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/user")
public class MessageController {

    @Autowired
    UserRepository userRepository;

    // Create a new User
    @PostMapping("/signup")
    public User createUser(@Valid @RequestBody User user) {

        return userRepository.save(user);
    }

    // Log In in User
    @PostMapping("/login")
    public User loginUser(@Valid @RequestBody User user) {

        return userRepository.save(user);
    }

    // Get All Notes
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}