package com.vervloet.msgservices.service;

import com.vervloet.msgservices.domain.model.User;
import com.vervloet.msgservices.domain.exceptions.ResourceNotFoundException;
import com.vervloet.msgservices.domain.vo.UserVo;
import com.vervloet.msgservices.mapper.UserMapper;
import com.vervloet.msgservices.repository.UserRepository;
import java.util.Optional;
import java.util.stream.Collectors;
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

    public ResponseEntity<?> getAllUsers(){

        List<User> allUsers = userRepository.findAll();

        List<UserVo> allUsersVo = allUsers.stream().map(n -> UserMapper.mapDomainToVo(n))
            .collect(Collectors.toList());

        return new ResponseEntity<>(allUsersVo, HttpStatus.OK);
    }

    public ResponseEntity<?> createUser(User user) {

        if(!Optional.ofNullable(userRepository.findByEmail(user.getEmail())).isPresent()) {

            User savedUser = userRepository.save(user);

            return new ResponseEntity<>(UserMapper.mapDomainToVo(savedUser), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("E-mail ja cadastrado", HttpStatus.CONFLICT);
        }
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
