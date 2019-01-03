package com.vervloet.msgservices.service;

import com.vervloet.msgservices.domain.model.User;
import com.vervloet.msgservices.domain.exceptions.ResourceNotFoundException;
import com.vervloet.msgservices.domain.vo.UserVo;
import com.vervloet.msgservices.mapper.UserMapper;
import com.vervloet.msgservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<UserVo> createUser(User user) {

        User savedUser = userRepository.save(user);

        return new ResponseEntity<>(UserMapper.mapDomainToVo(savedUser), HttpStatus.OK);
    }

    public ResponseEntity<?> updateUserEmail(User userChange, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        if (user.getPassword().equals(userChange.getPassword())){
            user.setEmail(userChange.getEmail());
            User savedUser = userRepository.save(user);

            return new ResponseEntity<>(UserMapper.mapDomainToVo(savedUser), HttpStatus.OK);
        } else {

            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    public ResponseEntity<?> updateUserPassword(Map<String, String> passwords, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        if (user.getPassword().equals(passwords.get("old-password"))){

            user.setPassword(passwords.get("new-password"));
            User savedUser = userRepository.save(user);

            return new ResponseEntity<>(UserMapper.mapDomainToVo(savedUser), HttpStatus.OK);
        } else {

            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }


    }

}
