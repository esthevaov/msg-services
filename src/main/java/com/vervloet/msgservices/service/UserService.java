package com.vervloet.msgservices.service;

import com.vervloet.msgservices.domain.model.User;
import com.vervloet.msgservices.domain.exceptions.ResourceNotFoundException;
import com.vervloet.msgservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){

        return userRepository.findAll();
    }

    public User createUser(User user) {

        return userRepository.save(user);
    }

    public User updateUserEmail(User userChange, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        if (user.getPassword().equals(userChange.getPassword())){
            user.setEmail(userChange.getEmail());
        }

        return userRepository.save(user);
    }

    public User updateUserPassword(Map<String, String> passwords, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        if (user.getPassword().equals(passwords.get("old-password"))){
            user.setPassword(passwords.get("new-password"));
        }

        return userRepository.save(user);
    }

}
