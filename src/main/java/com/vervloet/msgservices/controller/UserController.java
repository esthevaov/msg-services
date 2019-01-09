package com.vervloet.msgservices.controller;

import com.vervloet.msgservices.domain.model.User;
import com.vervloet.msgservices.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Pseudo Reddit API", tags = {"Pseudo Reddit API - Users"}, description = "User API")
@RequestMapping("/api/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(
        value = "Create a new user based on 'email' and 'password' defined on the body of the " +
        "request.",
        tags = {"Pseudo Reddit API - Users"}
    )
    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {

        return userService.create(user);
    }

    @ApiOperation(
        value = "Get user's list of posts.",
        notes = "The user is defined by the userId on the url.",
        tags = {"Pseudo Reddit API - Users"}
    )
    @GetMapping("/{userId}/posts")
    public ResponseEntity<?> getUserPosts(@PathVariable(value = "userId") Long userId) {

        return userService.getPosts(userId);
    }

    @ApiOperation(
        value = "Get user's list of comments.",
        notes = "The user is defined by the userId on the url.",
        tags = {"Pseudo Reddit API - Users"}
    )
    @GetMapping("/{userId}/comments")
    public ResponseEntity<?> getUserComments(@PathVariable(value = "userId") Long userId) {

        return userService.getComments(userId);
    }

    @ApiOperation(
        value = "Delete user.",
        notes = "The user is defined by the userId on the url.",
        tags = {"Pseudo Reddit API - Users"}
    )
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "userId") Long userId) {

        return userService.delete(userId);
    }

    @ApiOperation(
        value = "Change user's email.",
        notes = "Change user's email to the 'email' tag passed on the body of the request, only " +
        "if 'password' matches with the user's password. The user is defined by the userId on " +
        "the url.",
        tags = {"Pseudo Reddit API - Users"}
    )
    @PutMapping("/{userId}/change-email")
    public ResponseEntity<?> updateUserEmail(@RequestBody User user,
                                @PathVariable(value = "userId") Long userId){
        return userService.updateEmail(user, userId);
    }

    @ApiOperation(
        value = "Change user's password.",
        notes = "Change user's password to the 'new-password' tag passed on the body of the " +
            "request, only if tag 'old-password' matches with the user's actual password. " +
            "The user is defined by the userId on the url.",
        tags = {"Pseudo Reddit API - Users"}
    )
    @PutMapping("/{userId}/change-password")
    public ResponseEntity<?> updateUserPassword(@RequestBody Map<String, String> passwords,
                                   @PathVariable(value = "userId") Long userId){
        return userService.updatePassword(passwords, userId);
    }
}