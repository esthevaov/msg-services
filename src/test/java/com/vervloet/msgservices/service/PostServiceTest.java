package com.vervloet.msgservices.service;

import static com.vervloet.msgservices.util.PostFactory.createPost;
import static com.vervloet.msgservices.util.UserFactory.createUser;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.vervloet.msgservices.domain.model.CustomUserDetails;
import com.vervloet.msgservices.domain.model.Post;
import com.vervloet.msgservices.domain.model.User;
import com.vervloet.msgservices.domain.model.Vote;
import com.vervloet.msgservices.repository.PostRepository;
import com.vervloet.msgservices.repository.UserRepository;
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
public class PostServiceTest {

  private static final Long POST_ID = 0L;
  private static final String OTHER_EMAIL = "other_email@email.com";

  @InjectMocks
  private PostService postService;
  @Mock
  private PostRepository postRepository;
  @Mock
  private UserRepository userRepository;

  @Test
  public void whenICreateAPostAndIAmAuthenticatedItShouldCreateThePostCorrectly() {
    User user = createUser();
    Post post = createPost();
    CustomUserDetails customUserDetails = new CustomUserDetails(user);

    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
    Mockito.when(securityContext.getAuthentication().getPrincipal()).thenReturn(customUserDetails);
    SecurityContextHolder.setContext(securityContext);

    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
    when(postRepository.save(any(Post.class))).thenReturn(post);
    ResponseEntity response = postService.create(post);
    verify(postRepository).save(any(Post.class));
    assertEquals(response.getStatusCode(), HttpStatus.CREATED);
  }

  @Test
  public void whenIUpvoteAPostForTheFirstTimeItShouldChangePostVoteNumber() {
    User user = createUser();
    Post post = createPost();
    Long oldVotes = post.getVotes();
    CustomUserDetails customUserDetails = new CustomUserDetails(user);

    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
    Mockito.when(securityContext.getAuthentication().getPrincipal()).thenReturn(customUserDetails);
    SecurityContextHolder.setContext(securityContext);

    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
    when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
    ResponseEntity response = postService.upvote(POST_ID);
    verify(postRepository).save(any(Post.class));
    assertEquals(response.getStatusCode(), HttpStatus.OK);
    assertEquals(1, post.getVotes() - oldVotes);
  }

  @Test
  public void whenIUpvoteAPostAndIAlreadyUpvotedItShouldNotChangeVoteCountAndReturnConflict() {
    User user = createUser();
    Post post = createPost();
    Long oldVotes = post.getVotes();
    post.getVotedList().add(new Vote(post.getId(), user.getId(), 1));
    CustomUserDetails customUserDetails = new CustomUserDetails(user);

    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
    Mockito.when(securityContext.getAuthentication().getPrincipal()).thenReturn(customUserDetails);
    SecurityContextHolder.setContext(securityContext);

    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
    when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
    ResponseEntity response = postService.upvote(POST_ID);
    assertEquals(response.getStatusCode(), HttpStatus.CONFLICT);
    assertEquals(0, post.getVotes() - oldVotes);
  }

  @Test
  public void whenIUpvoteAPostAndIAlreadyDownvotedItShouldChangeVoteCountAndReturnCorrectly() {
    User user = createUser();
    Post post = createPost();
    Long oldVotes = post.getVotes();
    post.getVotedList().add(new Vote(post.getId(), user.getId(), -1));
    CustomUserDetails customUserDetails = new CustomUserDetails(user);

    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
    Mockito.when(securityContext.getAuthentication().getPrincipal()).thenReturn(customUserDetails);
    SecurityContextHolder.setContext(securityContext);

    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
    when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
    ResponseEntity response = postService.upvote(POST_ID);
    assertEquals(response.getStatusCode(), HttpStatus.OK);
    assertEquals(2, post.getVotes() - oldVotes);
  }


  @Test
  public void whenIDownvoteAPostForTheFirstTimeItShouldChangePostVoteNumber() {
    User user = createUser();
    Post post = createPost();
    Long oldVotes = post.getVotes();
    CustomUserDetails customUserDetails = new CustomUserDetails(user);

    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
    Mockito.when(securityContext.getAuthentication().getPrincipal()).thenReturn(customUserDetails);
    SecurityContextHolder.setContext(securityContext);

    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
    when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
    ResponseEntity response = postService.downvote(POST_ID);
    verify(postRepository).save(any(Post.class));
    assertEquals(response.getStatusCode(), HttpStatus.OK);
    assertEquals(-1, post.getVotes() - oldVotes);
  }

  @Test
  public void whenIDownvoteAPostAndIAlreadyDownvotedItShouldNotChangeVoteCountAndReturnConflict() {
    User user = createUser();
    Post post = createPost();
    Long oldVotes = post.getVotes();
    post.getVotedList().add(new Vote(post.getId(), user.getId(), -1));
    CustomUserDetails customUserDetails = new CustomUserDetails(user);

    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
    Mockito.when(securityContext.getAuthentication().getPrincipal()).thenReturn(customUserDetails);
    SecurityContextHolder.setContext(securityContext);

    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
    when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
    ResponseEntity response = postService.downvote(POST_ID);
    assertEquals(response.getStatusCode(), HttpStatus.CONFLICT);
    assertEquals(0, post.getVotes() - oldVotes);
  }

  @Test
  public void whenIDownvoteAPostAndIAlreadyUpvotedItShouldChangeVoteCountAndReturnCorrectly() {
    User user = createUser();
    Post post = createPost();
    Long oldVotes = post.getVotes();
    post.getVotedList().add(new Vote(post.getId(), user.getId(), 1));
    CustomUserDetails customUserDetails = new CustomUserDetails(user);

    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
    Mockito.when(securityContext.getAuthentication().getPrincipal()).thenReturn(customUserDetails);
    SecurityContextHolder.setContext(securityContext);

    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
    when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
    ResponseEntity response = postService.downvote(POST_ID);
    assertEquals(response.getStatusCode(), HttpStatus.OK);
    assertEquals(-2, post.getVotes() - oldVotes);
  }

  @Test
  public void whenIDeleteAPostAndTheAuthenticatedUserIsTheOwnerItShouldBeDeletedCorrectly() {
    User user = createUser();
    Post post = createPost();
    post.setUser(user);
    CustomUserDetails customUserDetails = new CustomUserDetails(user);

    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    when(securityContext.getAuthentication().getPrincipal()).thenReturn(customUserDetails);
    SecurityContextHolder.setContext(securityContext);

    when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
    ResponseEntity response = postService.delete(POST_ID);
    assertEquals(response.getStatusCode(), HttpStatus.OK);
    verify(postRepository).delete(any(Post.class));
  }

  @Test
  public void whenIDeleteAPostAndTheAuthenticatedUserIsNotTheOwnerItShouldBeReturnedForbidden() {
    User user = createUser();
    User otherUser = createUser();
    otherUser.setEmail(OTHER_EMAIL);
    Post post = createPost();
    post.setUser(user);
    CustomUserDetails customUserDetails = new CustomUserDetails(otherUser);

    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    when(securityContext.getAuthentication().getPrincipal()).thenReturn(customUserDetails);
    SecurityContextHolder.setContext(securityContext);

    when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
    ResponseEntity response = postService.delete(POST_ID);
    assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
  }
}
