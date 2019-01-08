package com.vervloet.msgservices.controller;

import static com.vervloet.msgservices.util.UserFactory.createUser;

import com.vervloet.msgservices.domain.model.User;
import com.vervloet.msgservices.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

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
  public void whenIPostANewUserThenItShouldBeCreatedProperly() {
    User user = createUser();

  }

}
