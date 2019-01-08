package com.vervloet.msgservices.controller;

import com.vervloet.msgservices.domain.model.Post;
import com.vervloet.msgservices.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Api(value = "Pseudo Reddit API", tags = {"Pseudo Reddit API - Posts"}, description = "Post API")
@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ApiOperation(
        value = "Get list of all posts.",
        tags = {"Pseudo Reddit API - Posts"}
    )
    @GetMapping()
    public ResponseEntity<?> getAllPosts() {

        return postService.getAllPosts();
    }

    @ApiOperation(
        value = "Get list of all posts sorted by number of votes in ascending order.",
        tags = {"Pseudo Reddit API - Posts"}
    )
    @GetMapping("/?sort=vote-asc")
    public ResponseEntity<?> getAllPostsByVoteNumberAsc() {

        return postService.getAllPostsByVoteNumberAsc();
    }

    @ApiOperation(
        value = "Get list of all posts sorted by number of votes in descending order.",
        tags = {"Pseudo Reddit API - Posts"}
    )
    @GetMapping("/?sort=vote-desc")
    public ResponseEntity<?> getAllPostsByVoteNumberDesc() {

        return postService.getAllPostsByVoteNumberDesc();
    }

    @ApiOperation(
        value = "Get list of all posts sorted by higher number of comments.",
        tags = {"Pseudo Reddit API - Posts"}
    )
    @GetMapping("/?sort=comment-num")
    public ResponseEntity<?> getAllPostsByCommentNumber() {

        return postService.getAllPostsByCommentNumber();
    }

    @ApiOperation(
        value = "Get list of all posts sorted by most recent post.",
        tags = {"Pseudo Reddit API - Posts"}
    )
    @GetMapping("/?sort=date-recent")
    public ResponseEntity<?> getAllPostsByDateRecent() {

        return postService.getAllPostsByDateRecent();
    }

    @ApiOperation(
        value = "Get post, the post is defined by the postId on the url.",
        tags = {"Pseudo Reddit API - Posts"}
    )
    @GetMapping("/{postId}")
    public ResponseEntity<?> getPost(@PathVariable(value = "postId") Long postId) {

        return postService.getPostById(postId);
    }

    @ApiOperation(
        value = "Create a new post based on 'title' and 'content' defined on the body of the " +
            "request.",
        tags = {"Pseudo Reddit API - Posts"}
    )
    @PostMapping()
    public ResponseEntity<?> createPost(@Valid @RequestBody Post post) {

        return postService.createPost(post);
    }

    @ApiOperation(
        value = "Upvote post.",
        notes = "The post is defined by the postId on the url.",
        tags = {"Pseudo Reddit API - Posts"}
    )
    @PostMapping("/{postId}/up")
    public ResponseEntity upvoteMessage(@PathVariable(value = "postId") Long messageId) {

        return postService.upvotePost(messageId);
    }

    @ApiOperation(
        value = "Downvote post.",
        notes = "The post is defined by the postId on the url.",
        tags = {"Pseudo Reddit API - Posts"}
    )
    @PostMapping("/{postId}/down")
    public ResponseEntity downvoteMessage(@PathVariable(value = "postId") Long messageId) {

        return postService.downvotePost(messageId);
    }

    @ApiOperation(
        value = "Delete post.",
        notes = "The post is defined by the postId on the url.",
        tags = {"Pseudo Reddit API - Posts"}
    )
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable(value = "postId") Long postId) {

        return postService.deletePost(postId);
    }

}