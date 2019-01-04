package com.vervloet.msgservices.controller;

import com.vervloet.msgservices.domain.model.User;
import com.vervloet.msgservices.repository.UserRepository;
import com.vervloet.msgservices.service.UserService;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public ResponseEntity<?> getAllUsers(){

        return userService.getAllUsers();
    }

    // Create a new Post
    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {

        return userService.createUser(user);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable(value = "userId") Long userId) {

        return userService.getUser(userId);
    }

    @GetMapping("/{userId}/comments")
    public ResponseEntity<?> getUserComments(@PathVariable(value = "userId") Long userId) {

        return userService.getUserComments(userId);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "userId") Long userId) {

        return userService.deleteUser(userId);
    }

    @PutMapping("/{userId}/change-email")
    public ResponseEntity<?> updateUserEmail(@RequestBody User user,
                                @PathVariable(value = "userId") Long userId){
        return userService.updateUserEmail(user, userId);
    }

    @PutMapping("/{userId}/change-password")
    public ResponseEntity<?> updateUserPassword(@RequestBody Map<String, String> passwords,
                                   @PathVariable(value = "userId") Long userId){
        return userService.updateUserPassword(passwords, userId);
    }

    @PostMapping("/logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }

}