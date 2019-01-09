package com.vervloet.msgservices.util;

import com.vervloet.msgservices.domain.model.Post;
import com.vervloet.msgservices.domain.vo.PostVo;

public class PostFactory {

  private static final Long POST_ID = 0L;
  private static final Long USER_ID = 0L;
  private static final String TITLE = "title";
  private static final String CONTENT = "content";
  private static final Long VOTES = 0L;

  public static Post createPost() {
    return Post.builder()
        .withId(POST_ID)
        .withTitle(TITLE)
        .withContent(CONTENT)
        .withVotes(VOTES)
        .build();
  }

  public static PostVo createPostVo() {
    return PostVo.builder()
        .withId(POST_ID)
        .withTitle(TITLE)
        .withContent(CONTENT)
        .withVotes(VOTES)
        .withUserId(USER_ID)
        .build();
  }
}
