package com.vervloet.msgservices.controller;

import com.vervloet.msgservices.domain.model.Post;
import com.vervloet.msgservices.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    // Get All Notes
    @GetMapping()
    public ResponseEntity<?> getAllPosts() {

        return postService.getAllPosts();
    }

    @GetMapping("/?sort=vote-asc")
    public ResponseEntity<?> getAllPostsByVoteNumberAsc() {

        return postService.getAllPostsByVoteNumberAsc();
    }

    @GetMapping("/?sort=vote-desc")
    public ResponseEntity<?> getAllPostsByVoteNumberDesc() {

        return postService.getAllPostsByVoteNumberDesc();
    }

    @GetMapping("/?sort=comment-num")
    public ResponseEntity<?> getAllPostsByCommentNumber() {

        return postService.getAllPostsByCommentNumber();
    }

    @GetMapping("/?sort=date-recent")
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

}