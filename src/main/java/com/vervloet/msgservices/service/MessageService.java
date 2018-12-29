package com.vervloet.msgservices.service;

import com.vervloet.msgservices.domain.Message;
import com.vervloet.msgservices.exception.ResourceNotFoundException;
import com.vervloet.msgservices.persistence.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message createMessage(@Valid @RequestBody Message message) {

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
