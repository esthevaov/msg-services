package com.vervloet.msgservices.service;

import com.vervloet.msgservices.domain.model.CustomUserDetails;
import com.vervloet.msgservices.domain.model.Message;
import com.vervloet.msgservices.domain.exceptions.ResourceNotFoundException;
import com.vervloet.msgservices.domain.model.User;
import com.vervloet.msgservices.repository.MessageRepository;
import com.vervloet.msgservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    public Message createMessage(Message message) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CustomUserDetails customUserDetails = ((CustomUserDetails)principal);

        User user = userRepository.findByEmail(customUserDetails.getUsername());

        message.setUser(user);

        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {

        return messageRepository.findAll();

    }

    public Message getMessageById(Long messageId) {

        return messageRepository.findById(messageId)
                .orElseThrow(() -> new ResourceNotFoundException("message", "id", messageId));
    }

    /*public ResponseEntity<?> upvoteMessage(Long messageId) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CustomUserDetails customUserDetails = ((CustomUserDetails)principal);

        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new ResourceNotFoundException("message", "id", messageId));

        if(message.checkVoted(customUserDetails.getUsername())){

            return new ResponseEntity<>("Ja votado", HttpStatus.FORBIDDEN);

        } else {

            message.setVotes(message.getVotes() + 1L);
            message.addVoted(customUserDetails.getUsername());

            return ResponseEntity.ok().build();
        }
    }

    public ResponseEntity<?> downvoteMessage(Long messageId) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CustomUserDetails customUserDetails = ((CustomUserDetails)principal);

        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new ResourceNotFoundException("message", "id", messageId));

        if(message.checkVoted(customUserDetails.getUsername())){

            return new ResponseEntity<>("Ja votado", HttpStatus.FORBIDDEN);

        } else {

            message.setVotes(message.getVotes() - 1L);
            message.addVoted(customUserDetails.getUsername());

            return ResponseEntity.ok().build();
        }
    }*/

    public ResponseEntity<?> deleteMessage(Long messageId) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CustomUserDetails customUserDetails = ((CustomUserDetails)principal);

        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new ResourceNotFoundException("Message", "id", messageId));

        messageRepository.delete(message);

        return ResponseEntity.ok().build();
    }
/*        if( message.getUsername().equals(customUserDetails.getUsername())){
            messageRepository.delete(message);
        } else {
            return new ResponseEntity<>("Nao Autorizado", HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok().build();
    }*/

}
