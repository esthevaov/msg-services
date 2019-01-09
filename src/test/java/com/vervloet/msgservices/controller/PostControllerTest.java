package com.vervloet.msgservices.controller;

import static com.vervloet.msgservices.util.PostFactory.createPost;
import static com.vervloet.msgservices.util.PostFactory.createPostVo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.vervloet.msgservices.domain.model.Post;
import com.vervloet.msgservices.domain.vo.PostVo;
import com.vervloet.msgservices.service.PostService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RunWith(MockitoJUnitRunner.class)
public class PostControllerTest {

  private static final Long POST_ID = 0L;
  private static final String RESPONSE = "response";

  @InjectMocks
  private PostController postController;
  @Mock
  private PostService postService;
  private MockMvc mockMvc;

  @Before
  public void before() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(postController)
        .build();
    MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockHttpServletRequest));
  }

  @Test
  public void whenIGetAllPostsItShouldCallServices() {
    PostVo postVo = createPostVo();
    List<PostVo> listPostsVo = new ArrayList<>();
    listPostsVo.add(postVo);
    ResponseEntity response = new ResponseEntity<>(listPostsVo, HttpStatus.OK);
    when(postService.getAll()).thenReturn(response);
    postController.getAllPosts();
    verify(postService).getAll();
  }

  @Test
  public void whenIGetAllPostsByVoteNumberAscItShouldCallServices() {
    PostVo postVo = createPostVo();
    List<PostVo> listPostVo = new ArrayList<PostVo>();
    listPostVo.add(postVo);
    ResponseEntity response = new ResponseEntity<>(listPostVo, HttpStatus.OK);
    when(postService.getAllByVoteNumberAsc()).thenReturn(response);
    postController.getAllPostsByVoteNumberAsc();
    verify(postService).getAllByVoteNumberAsc();
  }

  @Test
  public void whenIGetAllPostsByVoteNumberDescItShouldCallServices() {
    PostVo postVo = createPostVo();
    List<PostVo> listPostVo = new ArrayList<PostVo>();
    listPostVo.add(postVo);
    ResponseEntity response = new ResponseEntity<>(listPostVo, HttpStatus.OK);
    when(postService.getAllByVoteNumberDesc()).thenReturn(response);
    postController.getAllPostsByVoteNumberDesc();
    verify(postService).getAllByVoteNumberDesc();
  }

  @Test
  public void whenIGetAllPostsByCommentNumberItShouldCallServices() {
    PostVo postVo = createPostVo();
    List<PostVo> listPostVo = new ArrayList<PostVo>();
    listPostVo.add(postVo);
    ResponseEntity response = new ResponseEntity<>(listPostVo, HttpStatus.OK);
    when(postService.getAllByCommentNumber()).thenReturn(response);
    postController.getAllPostsByCommentNumber();
    verify(postService).getAllByCommentNumber();
  }

  @Test
  public void whenIGetAllPostsByDateRecentItShouldCallServices() {
    PostVo postVo = createPostVo();
    List<PostVo> listPostVo = new ArrayList<>();
    listPostVo.add(postVo);
    ResponseEntity response = new ResponseEntity<>(listPostVo, HttpStatus.OK);
    when(postService.getAllByDateRecent()).thenReturn(response);
    postController.getAllPostsByDateRecent();
    verify(postService).getAllByDateRecent();
  }

  @Test
  public void whenIGetPostByIdItShouldCallServices() {
    PostVo postVo = createPostVo();
    ResponseEntity response = new ResponseEntity<>(postVo, HttpStatus.OK);
    when(postService.getById(anyLong())).thenReturn(response);
    postController.getPostById(POST_ID);
    verify(postService).getById(eq(POST_ID));
  }

  @Test
  public void whenICreateANewPostItShouldCallServices() {
    Post post = createPost();
    PostVo postVo = createPostVo();
    ResponseEntity response = new ResponseEntity<>(postVo, HttpStatus.CREATED);
    when(postService.create(any(Post.class))).thenReturn(response);
    postController.createPost(post);
    verify(postService).create(any(Post.class));

  }

  @Test
  public void whenIUpvoteAPostItShouldCallServices() {
    ResponseEntity response = new ResponseEntity<>(RESPONSE, HttpStatus.OK);
    when(postService.upvote(anyLong())).thenReturn(response);
    postController.upvotePost(POST_ID);
    verify(postService).upvote(eq(POST_ID));
  }

  @Test
  public void whenIDownvoteAPostItShouldCallServices() {
    ResponseEntity response = new ResponseEntity<>(RESPONSE, HttpStatus.OK);
    when(postService.downvote(anyLong())).thenReturn(response);
    postController.downvotePost(POST_ID);
    verify(postService).downvote(eq(POST_ID));
  }

  @Test
  public void whenIDeleteAPostItShouldCallServices() {
    ResponseEntity response = new ResponseEntity<>(RESPONSE, HttpStatus.OK);
    when(postService.delete(anyLong())).thenReturn(response);
    postController.deletePost(POST_ID);
    verify(postService).delete(eq(POST_ID));
  }
}
