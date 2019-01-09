package com.vervloet.msgservices.util;

import com.vervloet.msgservices.domain.model.User;
import com.vervloet.msgservices.domain.vo.UserVo;
import com.vervloet.msgservices.domain.vo.UserWithCommentsVo;
import com.vervloet.msgservices.domain.vo.UserWithPostsVo;

public class UserFactory {

  private static final Long USER_ID = 0L;
  private static final String EMAIL = "email@email.com";
  private static final String PASSWORD = "password";

  public UserFactory() {
  }

  public static User createUser() {
    return User.builder()
        .withId(USER_ID)
        .withEmail(EMAIL)
        .withPassword(PASSWORD)
        .build();
  }

  public static UserVo createUserVo() {
    return UserVo.builder()
        .withId(USER_ID)
        .withEmail(EMAIL)
        .withPassword(PASSWORD)
        .build();
  }

  public static UserWithPostsVo createUserWithPostsVo(){
    return UserWithPostsVo.builder()
        .withId(USER_ID)
        .withEmail(EMAIL)
        .withPassword(PASSWORD)
        .build();
  }

  public static UserWithCommentsVo createUserWithCommentsVo(){
    return UserWithCommentsVo.builder()
        .withId(USER_ID)
        .withEmail(EMAIL)
        .withPassword(PASSWORD)
        .build();
  }

}
