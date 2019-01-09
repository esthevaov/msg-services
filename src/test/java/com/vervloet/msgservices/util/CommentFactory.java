package com.vervloet.msgservices.util;

import com.vervloet.msgservices.domain.model.Comment;
import com.vervloet.msgservices.domain.vo.CommentVo;

public class CommentFactory {

  private static final Long COMMENT_ID = 0L;
  private static final String CONTENT = "content";

  public static Comment createComment() {
    return Comment.builder()
        .withId(COMMENT_ID)
        .withContent(CONTENT)
        .build();
  }

  public static CommentVo createCommentVo() {
    return CommentVo.builder()
        .withId(COMMENT_ID)
        .withContent(CONTENT)
        .build();
  }

}
