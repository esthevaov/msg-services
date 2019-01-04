package com.vervloet.msgservices.controller;

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
  public ResponseEntity<?> getAllPosts() {

    return postService.getAllPosts();
  }

  @GetMapping("/vote-asc")
  public ResponseEntity<?> getAllPostsByVoteNumberAsc() {

    return postService.getAllPostsByVoteNumberAsc();
  }

  @GetMapping("/vote-desc")
  public ResponseEntity<?> getAllPostsByVoteNumberDesc() {

    return postService.getAllPostsByVoteNumberDesc();
  }

  @GetMapping("/comment-num")
  public ResponseEntity<?> getAllPostsByCommentNumber() {

    return postService.getAllPostsByCommentNumber();
  }

  @GetMapping("/date-recent")
  public ResponseEntity<?> getAllPostsByDateRecent() {

    return postService.getAllPostsByDateRecent();
  }

  // Get One Notes
  @GetMapping("/{postId}")
  public ResponseEntity<?> getPost(@PathVariable(value = "postId") Long postId) {

    return postService.getPostById(postId);
  }

  // Create a new Post
  @PostMapping()
  public ResponseEntity<?> createPost(@Valid @RequestBody Post post) {

    return postService.createPost(post);
  }

  @PostMapping("/{postId}/up")
  public ResponseEntity upvoteMessage(@PathVariable(value = "postId") Long messageId) {

    return postService.upvotePost(messageId);
  }

  @PostMapping("/{postId}/down")
  public ResponseEntity downvoteMessage(@PathVariable(value = "postId") Long messageId) {

    return postService.downvotePost(messageId);
  }


  @DeleteMapping("/{postId}")
  public ResponseEntity<?> deletePost(@PathVariable(value = "postId") Long postId) {

    return postService.deletePost(postId);
  }


