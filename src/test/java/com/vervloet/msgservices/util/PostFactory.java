package com.vervloet.msgservices.util;

import com.vervloet.msgservices.domain.model.Comment;
import com.vervloet.msgservices.domain.model.Post;
import com.vervloet.msgservices.domain.model.Vote;
import com.vervloet.msgservices.domain.vo.CommentVo;
import com.vervloet.msgservices.domain.vo.PostVo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        .withComments(new ArrayList<>())
        .withVotedList(new ArrayList<>())
        .build();
  }

  public static PostVo createPostVo() {
    return PostVo.builder()
        .withId(POST_ID)
        .withTitle(TITLE)
        .withContent(CONTENT)
        .withVotes(VOTES)
        .withUserId(USER_ID)
        .withCommentsVos(new ArrayList<>())
        .build();
  }
}
