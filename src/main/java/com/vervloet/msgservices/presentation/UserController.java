package com.vervloet.msgservices.presentation;

import com.vervloet.msgservices.domain.User;
import com.vervloet.msgservices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public List<User> getAllUsers(){

        return userService.getAllUsers();

    }

    // Create a new Message
    @PostMapping()
    public User createUser(@Valid @RequestBody User user) {

        return userService.createUser(user);
    }

    @PutMapping("/{id}/change-email")
    public User updateUserEmail(@RequestBody User user,
                                @PathVariable(value = "id") Long userId){
        return userService.updateUserEmail(user, userId);
    }

    @PutMapping("/{id}/change-password")
    public User updateUserPassword(@RequestBody Map<String, String> passwords,
                                   @PathVariable(value = "id") Long userId){
        return userService.updateUserPassword(passwords, userId);
    }

}