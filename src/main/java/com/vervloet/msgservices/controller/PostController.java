package com.vervloet.msgservices.controller;

import com.vervloet.msgservices.domain.model.Post;
import com.vervloet.msgservices.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostService postService;

    // Get All Notes
    @GetMapping()
    public ResponseEntity<?> getAllPosts() {

        return postService.getAllPosts();
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

    @PostMapping("/{messageId}/up")
    public ResponseEntity upvoteMessage(@PathVariable(value = "messageId") Long messageId) {

        return postService.upvoteMessage(messageId);
    }

    @PostMapping("/{messageId}/down")
    public ResponseEntity downvoteMessage(@PathVariable(value = "messageId") Long messageId) {

        return postService.downvoteMessage(messageId);
    }


    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable(value = "postId") Long postId) {

        return postService.deletePost(postId);
    }

}