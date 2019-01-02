package com.vervloet.msgservices.controller;

import com.vervloet.msgservices.domain.model.User;
import com.vervloet.msgservices.domain.vo.UserVo;
import com.vervloet.msgservices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public List<User> getAllUsers(){

        return userService.getAllUsers();
    }

    // Create a new Message
    @PostMapping("/create-user")
    public ResponseEntity<UserVo> createUser(@Valid @RequestBody User user) {

        return userService.createUser(user);
    }

    @PutMapping("/{id}/change-email")
    public ResponseEntity<?> updateUserEmail(@RequestBody User user,
                                @PathVariable(value = "id") Long userId){
        return userService.updateUserEmail(user, userId);
    }

    @PutMapping("/{id}/change-password")
    public ResponseEntity<?> updateUserPassword(@RequestBody Map<String, String> passwords,
                                   @PathVariable(value = "id") Long userId){
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