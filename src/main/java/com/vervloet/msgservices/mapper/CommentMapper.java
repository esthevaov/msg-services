package com.vervloet.msgservices.mapper;

import com.vervloet.msgservices.domain.model.Comment;
import com.vervloet.msgservices.domain.vo.CommentVo;

public class CommentMapper {

  public CommentMapper() {
  }

  public static Comment mapVoToDomain(CommentVo commentVo) {
    return Comment.builder()
        .withId(commentVo.getId())
        .withContent(commentVo.getContent())
        .withCreated(commentVo.getCreated())
        .build();
  }

  public static CommentVo mapDomainToVo(Comment comment) {
    return CommentVo.builder()
        .withId(comment.getId())
        .withContent(comment.getContent())
        .withCreated(comment.getCreated())
        .withUserId(comment.getUser().getId())
        .withPostId(comment.getPost().getId())
        .build();
  }
}
