package com.vervloet.msgservices.service;

import com.vervloet.msgservices.domain.exceptions.ResourceNotFoundException;
import com.vervloet.msgservices.domain.model.Comment;
import com.vervloet.msgservices.domain.model.CustomUserDetails;
import com.vervloet.msgservices.domain.model.Post;
import com.vervloet.msgservices.domain.model.User;
import com.vervloet.msgservices.domain.vo.CommentVo;
import com.vervloet.msgservices.mapper.CommentMapper;
import com.vervloet.msgservices.repository.CommentRepository;
import com.vervloet.msgservices.repository.PostRepository;
import com.vervloet.msgservices.repository.UserRepository;
import com.vervloet.msgservices.utils.ResponseBuilder;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CommentRepository commentRepository;

  public CommentService(PostRepository postRepository,
      UserRepository userRepository,
      CommentRepository commentRepository) {
    this.postRepository = postRepository;
    this.userRepository = userRepository;
    this.commentRepository = commentRepository;
  }

  public ResponseEntity<Map<String, Object>> getAll() {
    List<Comment> allComments = commentRepository.findAll();
    List<CommentVo> allCommentsVo = allComments.stream().map(n -> CommentMapper.mapDomainToVo(n))
        .collect(Collectors.toList());

    return ResponseBuilder.createDataResponse(allCommentsVo, HttpStatus.OK);
  }

  public ResponseEntity<Map<String, Object>> getById(Long commentId) {
    Comment comment = commentRepository.findById(commentId)
        .orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));

    return ResponseBuilder.createDataResponse(CommentMapper.mapDomainToVo(comment), HttpStatus.OK);
  }

  public ResponseEntity<Map<String, Object>> create(Long postId, Comment comment) {

    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    CustomUserDetails customUserDetails = ((CustomUserDetails)principal);

    User user = userRepository.findByEmail(customUserDetails.getUsername())
        .orElseThrow(() -> new ResourceNotFoundException("user", "email", customUserDetails.getUsername()));
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));;

    comment.setUser(user);
    comment.setPost(post);
    Comment savedComment = commentRepository.save(comment);

    return ResponseBuilder.createDataResponse(CommentMapper.mapDomainToVo(savedComment), HttpStatus.CREATED);
  }

  public ResponseEntity<Map<String, Object>> delete(Long commentId) {

    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    CustomUserDetails customUserDetails = ((CustomUserDetails)principal);
    Comment comment = commentRepository.findById(commentId)
        .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

    if( comment.getUser().getEmail().equals(customUserDetails.getUsername())){
      commentRepository.delete(comment);

      return ResponseBuilder.createDataResponse("Comment Deleted", HttpStatus.OK);
    } else {

      return ResponseBuilder.createErrorResponse("Action not authorized for this user", HttpStatus.FORBIDDEN);
    }
  }

}
