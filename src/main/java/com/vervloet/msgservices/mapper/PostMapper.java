package com.vervloet.msgservices.mapper;

import com.vervloet.msgservices.domain.model.Comment;
import com.vervloet.msgservices.domain.model.Post;
import com.vervloet.msgservices.domain.vo.CommentVo;
import com.vervloet.msgservices.domain.vo.PostVo;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PostMapper {

  public PostMapper() {
  }

  public static Post mapVoToDomain(PostVo postVo) {
    return Post.builder()
        .withId(postVo.getId())
        .withTitle(postVo.getTitle())
        .withContent(postVo.getContent())
        .withCreated(postVo.getCreated())
        .withVotes(postVo.getVotes())
        .build();
  }

  public static PostVo mapDomainToVo(Post post) {
    return PostVo.builder()
        .withId(post.getId())
        .withTitle(post.getTitle())
        .withContent(post.getContent())
        .withCreated(post.getCreated())
        .withVotes(post.getVotes())
        .withUserId(post.getUser().getId())
        .withCommentsVos(mapCommentsToCommentsVos(post.getComments()))
        .build();
  }

  public  static List<CommentVo> mapCommentsToCommentsVos(List<Comment> comments) {
    if (Optional.ofNullable(comments).isPresent()) {
      return comments.stream().map(comment -> CommentMapper.mapDomainToVo(comment)).collect(Collectors.toList());
    } else {
      return Collections.emptyList();
    }
  }
}

