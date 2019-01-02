package com.vervloet.msgservices.controller;

import com.vervloet.msgservices.domain.model.Message;
import com.vervloet.msgservices.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    // Get All Notes
    @GetMapping()
    public ResponseEntity<?> getAllMessages() {

        return messageService.getAllMessages();
    }

    // Get One Notes
    @GetMapping("/{messageId}")
    public ResponseEntity<?> getMessage(@PathVariable(value = "messageId") Long messageId) {

        return messageService.getMessageById(messageId);
    }

    // Create a new Message
    @PostMapping()
    public ResponseEntity<?> createMessage(@Valid @RequestBody Message message) {

        return messageService.createMessage(message);
    }

    /*@PostMapping("/{messageId}/up")
    public ResponseEntity upvoteMessage(@PathVariable(value = "messageId") Long messageId) {

        return messageService.upvoteMessage(messageId);
    }

    @PostMapping("/{messageId}/down")
    public ResponseEntity downvoteMessage(@PathVariable(value = "messageId") Long messageId) {

        return messageService.downvoteMessage(messageId);
    }

    // Delete a Note
    @DeleteMapping("/{messageId}")
    public ResponseEntity<?> deleteMessage(@PathVariable(value = "messageId") Long messageId) {

        return messageService.deleteMessage(messageId);
    }*/

    @DeleteMapping("/{messageId}")
    public ResponseEntity<?> deleteMessage(@PathVariable(value = "messageId") Long messageId) {

        return messageService.deleteMessage(messageId);
    }

}