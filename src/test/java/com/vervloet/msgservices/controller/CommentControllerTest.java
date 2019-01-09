package com.vervloet.msgservices.controller;

import static com.vervloet.msgservices.util.CommentFactory.createComment;
import static com.vervloet.msgservices.util.CommentFactory.createCommentVo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.vervloet.msgservices.domain.model.Comment;
import com.vervloet.msgservices.domain.vo.CommentVo;
import com.vervloet.msgservices.service.CommentService;
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
public class CommentControllerTest {

  private static final Long COMMENT_ID = 0L;
  private static final Long POST_ID = 0L;
  private static final String RESPONSE = "response";

  @InjectMocks
  private CommentController commentController;
  @Mock
  private CommentService commentService;
  private MockMvc mockMvc;

  @Before
  public void before() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(commentController)
        .build();
    MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockHttpServletRequest));
  }

  @Test
  public void whenIGetAllPostsItShouldCallServices() {
    CommentVo commentVo = createCommentVo();
    List<CommentVo> listCommentsVo = new ArrayList<>();
    listCommentsVo.add(commentVo);
    ResponseEntity response = new ResponseEntity<>(listCommentsVo, HttpStatus.OK);
    when(commentService.getAll()).thenReturn(response);
    commentController.getAllComments();
    verify(commentService).getAll();
  }

  @Test
  public void whenIGetPostByIdItShouldCallServices() {
    CommentVo commentVo = createCommentVo();
    ResponseEntity response = new ResponseEntity<>(commentVo, HttpStatus.OK);
    when(commentService.getById(anyLong())).thenReturn(response);
    commentController.getCommentById(POST_ID, COMMENT_ID);
    verify(commentService).getById(eq(COMMENT_ID));
  }

  @Test
  public void whenICreateANewPostItShouldCallServices() {
    Comment comment = createComment();
    CommentVo commentVo = createCommentVo();
    ResponseEntity response = new ResponseEntity<>(commentVo, HttpStatus.CREATED);
    when(commentService.create(anyLong(), any(Comment.class))).thenReturn(response);
    commentController.createComment(POST_ID, comment);
    verify(commentService).create(eq(POST_ID), any(Comment.class));

  }

  @Test
  public void whenIDeleteAPostItShouldCallServices() {
    ResponseEntity response = new ResponseEntity<>(RESPONSE, HttpStatus.OK);
    when(commentService.delete(anyLong())).thenReturn(response);
    commentController.deleteComment(POST_ID, COMMENT_ID);
    verify(commentService).delete(eq(COMMENT_ID));
  }
}
