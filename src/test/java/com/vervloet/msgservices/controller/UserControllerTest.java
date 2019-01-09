package com.vervloet.msgservices.controller;

import static com.vervloet.msgservices.util.UserFactory.createUser;
import static com.vervloet.msgservices.util.UserFactory.createUserVo;
import com.vervloet.msgservices.domain.model.User;
import com.vervloet.msgservices.domain.vo.UserVo;
import com.vervloet.msgservices.domain.vo.UserWithCommentsVo;
import com.vervloet.msgservices.domain.vo.UserWithPostsVo;
import com.vervloet.msgservices.service.UserService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.vervloet.msgservices.util.UserFactory.createUserWithCommentsVo;
import static com.vervloet.msgservices.util.UserFactory.createUserWithPostsVo;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertNotNull;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

  private static final Long USER_ID = 0L;
  private static final String EMAIL = "email@email.com";
  private static final String PASSWORD = "password";
  private static final String RESPONSE = "response";

  @InjectMocks
  private UserController userController;
  @Mock
  private UserService userService;
  private MockMvc mockMvc;

  @Before
  public void before() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(userController)
        .build();
    MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockHttpServletRequest));
  }

  @Test
  public void whenICreateANewUserThenItShouldCallServices() {
    User user = createUser();
    UserVo userVo = createUserVo();
    ResponseEntity response = new ResponseEntity<>(userVo, HttpStatus.CREATED);
    when(userService.create(any(User.class))).thenReturn(response);
    userController.createUser(user);
    verify(userService).create(any(User.class));
  }

  @Test
  public void whenIGetUserPostsItShouldCallServices() {
    UserWithPostsVo userWithPostsVo = createUserWithPostsVo();
    ResponseEntity response = new ResponseEntity<>(userWithPostsVo, HttpStatus.OK);
    when(userService.getPosts(anyLong())).thenReturn(response);
    userController.getUserPosts(USER_ID);
    verify(userService).getPosts(eq(USER_ID));
  }

  @Test
  public void whenIGetUserCommentsItShouldCallServices() {
    UserWithCommentsVo userWithCommentsVo = createUserWithCommentsVo();
    ResponseEntity response = new ResponseEntity<>(userWithCommentsVo, HttpStatus.OK);
    when(userService.getComments(anyLong())).thenReturn(response);
    userController.getUserComments(USER_ID);
    verify(userService).getComments(eq(USER_ID));
  }

  @Test
  public void whenIDeleteUserItShouldCallServices() {
    ResponseEntity response = new ResponseEntity<>(RESPONSE, HttpStatus.OK);
    when(userService.delete(anyLong())).thenReturn(response);
    userController.deleteUser(USER_ID);
    verify(userService).delete(eq(USER_ID));
  }

  @Test
  public void whenIUpdateUserEmailItShouldCallServices() {
    User user = createUser();
    UserVo userVo = createUserVo();
    ResponseEntity response = new ResponseEntity<>(userVo, HttpStatus.OK);
    when(userService.updateEmail(any(User.class), anyLong())).thenReturn(response);
    userController.updateUserEmail(user, USER_ID);
    verify(userService).updateEmail(eq(user), eq(USER_ID));
  }

  @Test
  public void whenIUpdateUserPasswordItShouldCallServices() {
    Map<String, String> passwords = new HashMap<>();
    UserVo userVo = createUserVo();
    ResponseEntity response = new ResponseEntity<>(userVo, HttpStatus.OK);
    when(userService.updatePassword(anyMap(), anyLong())).thenReturn(response);
    userController.updateUserPassword(passwords, USER_ID);
    verify(userService).updatePassword(eq(passwords), eq(USER_ID));
  }

}
