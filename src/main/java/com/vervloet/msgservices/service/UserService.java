package com.vervloet.msgservices.service;

import com.vervloet.msgservices.domain.exceptions.ResourceNotFoundException;
import com.vervloet.msgservices.domain.model.CustomUserDetails;
import com.vervloet.msgservices.domain.model.User;
import com.vervloet.msgservices.mapper.UserMapper;
import com.vervloet.msgservices.repository.UserRepository;
import com.vervloet.msgservices.utils.ResponseBuilder;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public UserService( UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<Map<String, Object>> getComments(Long userId) {

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        return  ResponseBuilder.createDataResponse(UserMapper.mapDomainToWithCommentsVo(user), HttpStatus.OK);
    }

    public ResponseEntity<Map<String, Object>> getPosts(Long userId) {

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        return  ResponseBuilder.createDataResponse(UserMapper.mapDomainToWithPostsVo(user), HttpStatus.OK);
    }

    public ResponseEntity<Map<String, Object>> create(User user) {

        if(!userRepository.findByEmail(user.getEmail()).isPresent()) {

            User savedUser = userRepository.save(user);

            return ResponseBuilder.createDataResponse(UserMapper.mapDomainToVo(savedUser), HttpStatus.CREATED);
        } else {

            return ResponseBuilder.createErrorResponse("E-mail already registered", HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<Map<String, Object>> delete(Long userId) {

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        if(user.getEmail().equals(customUserDetails.getUsername())) {

            userRepository.delete(user);

            return  ResponseBuilder.createErrorResponse("User Deleted", HttpStatus.OK);
        } else {

            return  ResponseBuilder.createErrorResponse("Action not authorized for this user", HttpStatus.FORBIDDEN);
        }
    }

    public ResponseEntity<Map<String, Object>> updateEmail(User userChange, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        if (user.getPassword().equals(userChange.getPassword())){
            user.setEmail(userChange.getEmail());
            User savedUser = userRepository.save(user);

            return ResponseBuilder.createDataResponse(UserMapper.mapDomainToVo(savedUser), HttpStatus.OK);
        } else {

            return ResponseBuilder.createErrorResponse("Action not authorized for this user", HttpStatus.FORBIDDEN);
        }
    }

    public ResponseEntity<Map<String, Object>> updatePassword(Map<String, String> passwords, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        if (user.getPassword().equals(passwords.get("old-password"))){

            user.setPassword(passwords.get("new-password"));
            User savedUser = userRepository.save(user);

            return ResponseBuilder.createDataResponse(UserMapper.mapDomainToVo(savedUser), HttpStatus.OK);
        } else {

            return ResponseBuilder.createErrorResponse("Action not authorized for this user", HttpStatus.FORBIDDEN);
        }
    }

}
