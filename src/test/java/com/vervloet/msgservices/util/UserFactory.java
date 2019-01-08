package com.vervloet.msgservices.util;

import com.vervloet.msgservices.domain.model.User;

public class UserFactory {

  private static final Long ID = 0L;
  private static final String EMAIL = "email@email.com";
  private static final String PASSWORD = "password";

  public UserFactory() {
  }

  public static User createUser() {
    return User.builder()
        .withId(ID)
        .withEmail(EMAIL)
        .withPassword(PASSWORD)
        .build();
  }

  public static User createUserVo() {
    return User.builder()
        .withId(ID)
        .withEmail(EMAIL)
        .withPassword(PASSWORD)
        .build();
  }
}
