package com.vervloet.msgservices.controller;

import com.vervloet.msgservices.domain.model.Comment;
import com.vervloet.msgservices.service.CommentService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post/{postId}/comment/")
public class CommentController {

  @Autowired
  private CommentService commentService;

  // Get All Notes
  @GetMapping()
  public ResponseEntity<?> getAllComments() {

    return commentService.getAllComments();
  }

  // Get One Notes
  @GetMapping("/{commentId}")
  public ResponseEntity<?> getCommentById(@PathVariable(value = "postId") Long postId,
                                          @PathVariable(value = "commentId") Long commentId) {

    return commentService.getCommentById(commentId);
  }

  // Create a new Post
  @PostMapping()
  public ResponseEntity<?> createComment(@PathVariable(value = "postId") Long postId,
                                         @Valid @RequestBody Comment comment) {

    return commentService.createComment(postId, comment);
  }

  @DeleteMapping("/{postId}")
  public ResponseEntity<?> deleteComment(@PathVariable(value = "postId") Long postId,
                                         @PathVariable(value = "commentId") Long commentId) {

    return commentService.deleteComment(commentId);
  }
}


