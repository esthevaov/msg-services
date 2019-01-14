package com.vervloet.msgservices.service;

import static com.vervloet.msgservices.util.CommentFactory.createComment;
import static com.vervloet.msgservices.util.PostFactory.createPost;
import static com.vervloet.msgservices.util.UserFactory.createUser;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.vervloet.msgservices.domain.model.Comment;
import com.vervloet.msgservices.domain.model.CustomUserDetails;
import com.vervloet.msgservices.domain.model.Post;
import com.vervloet.msgservices.domain.model.User;
import com.vervloet.msgservices.repository.CommentRepository;
import com.vervloet.msgservices.repository.PostRepository;
import com.vervloet.msgservices.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@RunWith(MockitoJUnitRunner.class)
public class CommentServiceTest {

  private static final Long COMMENT_ID = 0L;
  private static final String OTHER_EMAIL = "other_email@email.com";

  @InjectMocks
  private CommentService commentService;
  @Mock
  private UserRepository userRepository;
  @Mock
  private PostRepository postRepository;
  @Mock
  private CommentRepository commentRepository;

  @Test
  public void whenIGetAllCommentsItShouldCallRepositoryCorrectly() {
    List<Comment> allComments = new ArrayList<>();
    when(commentRepository.findAll()).thenReturn(allComments);
    ResponseEntity response = commentService.getAll();
    verify(commentRepository).findAll();
    assertEquals(response.getStatusCode(), HttpStatus.OK);
  }

  @Test
  public void whenICreateACommentItShouldCallRepositoryCorrectly() {
    Comment comment = createComment();
    User user = createUser();
    Post post = createPost();
    CustomUserDetails customUserDetails = new CustomUserDetails(user);

    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
    Mockito.when(securityContext.getAuthentication().getPrincipal()).thenReturn(customUserDetails);
    SecurityContextHolder.setContext(securityContext);

    when(commentRepository.save(any(Comment.class))).thenReturn(comment);
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
    when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
    ResponseEntity response = commentService.create(COMMENT_ID, comment);
    verify(commentRepository).save(any(Comment.class));
    assertEquals(response.getStatusCode(), HttpStatus.CREATED);
  }

  @Test
  public void whenIDeleteACommentAndTheAuthenticatedUserIsTheOwnerItShouldBeDeletedCorrectly() {
    User user = createUser();
    Comment comment = createComment();
    comment.setUser(user);
    CustomUserDetails customUserDetails = new CustomUserDetails(user);

    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    when(securityContext.getAuthentication().getPrincipal()).thenReturn(customUserDetails);
    SecurityContextHolder.setContext(securityContext);

    when(commentRepository.findById(anyLong())).thenReturn(Optional.of(comment));
    ResponseEntity response = commentService.delete(COMMENT_ID);
    assertEquals(response.getStatusCode(), HttpStatus.OK);
    verify(commentRepository).delete(any(Comment.class));
  }

  @Test
  public void whenIDeleteAPostAndTheAuthenticatedUserIsNotTheOwnerItShouldBeReturnedForbidden() {
    User user = createUser();
    User otherUser = createUser();
    otherUser.setEmail(OTHER_EMAIL);
    Comment comment = createComment();
    comment.setUser(user);
    CustomUserDetails customUserDetails = new CustomUserDetails(otherUser);

    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    when(securityContext.getAuthentication().getPrincipal()).thenReturn(customUserDetails);
    SecurityContextHolder.setContext(securityContext);

    when(commentRepository.findById(anyLong())).thenReturn(Optional.of(comment));
    ResponseEntity response = commentService.delete(COMMENT_ID);
    assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
  }

}
