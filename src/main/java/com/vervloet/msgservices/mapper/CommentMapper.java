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
        .withUserPath(buildUserPath(comment.getUser().getId()))
        .withPostPath(buildCommentPath(comment.getPost().getId()))
        .build();
  }

  public static String buildUserPath(Long userId){
    return "localhost:8080/api/user/"+userId;
  }

  public static String buildCommentPath(Long postId){
    return "localhost:8080/api/posts/"+postId;
  }
}
