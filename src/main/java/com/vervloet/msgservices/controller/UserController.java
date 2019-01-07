package com.vervloet.msgservices.controller;

import com.vervloet.msgservices.domain.model.User;
import com.vervloet.msgservices.repository.UserRepository;
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

@Api(value = "Pseudo Reddit API")
@RequestMapping("/api/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @ApiOperation(
        value = "Create a new user based on 'email' and 'password' defined on the body of the " +
        "request.",
        tags = {"Pseudo Reddit API - Users"}
    )
    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {

        return userService.createUser(user);
    }

    /*@ApiOperation(
        value = "Get user information.",
        tags = {"Pseudo Reddit API - Users"}
    )
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable(value = "userId") Long userId) {

        return userService.getUser(userId);
    }

    @GetMapping()
    public ResponseEntity<?> getAllUsers(){

        return userService.getAllUsers();
    }*/

    @ApiOperation(
        value = "Get user's list of posts, the user is defined by the userId on the url.",
        tags = {"Pseudo Reddit API - Users"}
    )
    @GetMapping("/{userId}/posts")
    public ResponseEntity<?> getUserPosts(@PathVariable(value = "userId") Long userId) {

        return userService.getUserPosts(userId);
    }

    @ApiOperation(
        value = "Get user's list of comments, the user is defined by the userId on the url.",
        tags = {"Pseudo Reddit API - Users"}
    )
    @GetMapping("/{userId}/comments")
    public ResponseEntity<?> getUserComments(@PathVariable(value = "userId") Long userId) {

        return userService.getUserComments(userId);
    }

    @ApiOperation(
        value = "Delete user, the user is defined by the userId on the url.",
        tags = {"Pseudo Reddit API - Users"}
    )
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "userId") Long userId) {

        return userService.deleteUser(userId);
    }

    @ApiOperation(
        value = "Change user's email to the 'email' tag passed on the body of the request, only " +
        "if 'password' matches with the user's password. The user is defined by the userId on " +
        "the url.",
        tags = {"Pseudo Reddit API - Users"}
    )
    @PutMapping("/{userId}/change-email")
    public ResponseEntity<?> updateUserEmail(@RequestBody User user,
                                @PathVariable(value = "userId") Long userId){
        return userService.updateUserEmail(user, userId);
    }

    @ApiOperation(
        value = "Change user's password to the 'new-password' tag passed on the body of the " +
            "request, only if tag 'old-password' matches with the user's actual password. " +
            "The user is defined by the userId on the url.",
        tags = {"Pseudo Reddit API - Users"}
    )
    @PutMapping("/{userId}/change-password")
    public ResponseEntity<?> updateUserPassword(@RequestBody Map<String, String> passwords,
                                   @PathVariable(value = "userId") Long userId){
        return userService.updateUserPassword(passwords, userId);
    }
}