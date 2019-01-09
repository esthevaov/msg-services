package com.vervloet.msgservices.controller;

import com.vervloet.msgservices.domain.model.Comment;
import com.vervloet.msgservices.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

@Api(value = "Pseudo Reddit API", tags = {"Pseudo Reddit API - Comments"}, description = "Comment API")
@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

  @Autowired
  private CommentService commentService;

  @Autowired
  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }

  @ApiOperation(
      value = "Get list of all comments.",
      tags = {"Pseudo Reddit API - Comments"}
  )
  @GetMapping()
  public ResponseEntity<?> getAllComments() {

    return commentService.getAll();
  }

  @ApiOperation(
      value = "Get comment.",
      notes = "The comment is defined by the commentId on the url.",
      tags = {"Pseudo Reddit API - Comments"}
  )
  @GetMapping("/{commentId}")
  public ResponseEntity<?> getCommentById(@PathVariable(value = "postId") Long postId,
                                          @PathVariable(value = "commentId") Long commentId) {

    return commentService.getById(commentId);
  }

  @ApiOperation(
      value = "Create a new comment based on 'content' defined on the body of the " +
          "request.",
      tags = {"Pseudo Reddit API - Comments"}
  )
  @PostMapping()
  public ResponseEntity<?> createComment(@PathVariable(value = "postId") Long postId,
                                         @Valid @RequestBody Comment comment) {

    return commentService.create(postId, comment);
  }

  @ApiOperation(
      value = "Delete comment.",
      notes = "The comment is defined by the commentId on the url.",
      tags = {"Pseudo Reddit API - Comments"}
  )
  @DeleteMapping("/{commentId}")
  public ResponseEntity<?> deleteComment(@PathVariable(value = "postId") Long postId,
                                         @PathVariable(value = "commentId") Long commentId) {

    return commentService.delete(commentId);
  }
}


