package com.vervloet.msgservices.service;

import com.vervloet.msgservices.model.CustomUserDetails;
import com.vervloet.msgservices.model.Message;
import com.vervloet.msgservices.exception.ResourceNotFoundException;
import com.vervloet.msgservices.model.User;
import com.vervloet.msgservices.persistence.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message createMessage(Message message) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CustomUserDetails customUserDetails = ((CustomUserDetails)principal);

        message.setUsername(customUserDetails.getUsername());

        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {

        return messageRepository.findAll();

    }

    public Message getMessageById(Long messageId) {

        return messageRepository.findById(messageId)
                .orElseThrow(() -> new ResourceNotFoundException("message", "id", messageId));
    }

    public ResponseEntity<?> deleteMessage(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new ResourceNotFoundException("Message", "id", messageId));

        messageRepository.delete(message);

        return ResponseEntity.ok().build();
    }

}
