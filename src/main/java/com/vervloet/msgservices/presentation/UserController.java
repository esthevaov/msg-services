package com.vervloet.msgservices.presentation;

import com.vervloet.msgservices.model.CustomUserDetails;
import com.vervloet.msgservices.model.User;
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


@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public List<User> getAllUsers(){

        return userService.getAllUsers();
    }

    @GetMapping("/user")
    public List<User> getAllUsers2(){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails) {
            String username = ((CustomUserDetails)principal).getUsername();
            System.out.println("Username: "+ username);
        } else {
            String username = principal.toString();
            System.out.println("Username: "+ username);
        }

        return userService.getAllUsers();
    }

    // Create a new Message
    @PostMapping("/create-user")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {

        return new ResponseEntity<>(userService.createUser(user), HttpStatus.OK);
    }

    @PutMapping("/user/{id}/change-email")
    public ResponseEntity<User> updateUserEmail(@RequestBody User user,
                                @PathVariable(value = "id") Long userId){
        return new ResponseEntity<>(userService.updateUserEmail(user, userId), HttpStatus.OK);
    }

    @PutMapping("/user/{id}/change-password")
    public ResponseEntity<User> updateUserPassword(@RequestBody Map<String, String> passwords,
                                   @PathVariable(value = "id") Long userId){
        return new ResponseEntity<>(userService.updateUserPassword(passwords, userId), HttpStatus.OK);
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