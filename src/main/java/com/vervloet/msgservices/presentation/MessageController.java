package com.vervloet.msgservices.presentation;

import com.vervloet.msgservices.domain.Message;
import com.vervloet.msgservices.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    // Get All Notes
    @GetMapping()
    public List<Message> getAllMessages() {

        return messageService.getAllMessages();
    }

    // Get All Notes
    @GetMapping("/{id}")
    public Message getMessage(@PathVariable(value = "id") Long messageId) {

        return messageService.getMessageById(messageId);
    }

    // Create a new Message
    @PostMapping()
    public Message createMessage(@Valid @RequestBody Message message) {

        return messageService.createMessage(message);
    }

    // Delete a Note
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMessage(@PathVariable(value = "id") Long noteId) {

        return messageService.deleteMessage(noteId);
    }

}