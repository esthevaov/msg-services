package com.vervloet.msgservices.presentation;

import com.vervloet.msgservices.model.User;
import com.vervloet.msgservices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {

        return new ResponseEntity<>(userService.createUser(user), HttpStatus.OK);
    }

    @PutMapping("/{id}/change-email")
    public ResponseEntity<User> updateUserEmail(@RequestBody User user,
                                @PathVariable(value = "id") Long userId){
        return new ResponseEntity<>(userService.updateUserEmail(user, userId), HttpStatus.OK);
    }

    @PutMapping("/{id}/change-password")
    public ResponseEntity<User> updateUserPassword(@RequestBody Map<String, String> passwords,
                                   @PathVariable(value = "id") Long userId){
        return new ResponseEntity<>(userService.updateUserPassword(passwords, userId), HttpStatus.OK);
    }

}