package com.vervloet.msgservices.presentation;

import com.vervloet.msgservices.domain.User;
import com.vervloet.msgservices.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Create a new Message
    @PostMapping("")
    public User createUser(@Valid @RequestBody User user) {

        return userRepository.save(user);
    }

}