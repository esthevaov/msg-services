package com.vervloet.msgservices.mapper;

import com.vervloet.msgservices.domain.model.Comment;
import com.vervloet.msgservices.domain.model.Post;
import com.vervloet.msgservices.domain.model.User;
import com.vervloet.msgservices.domain.vo.UserVo;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserMapper {

  public UserMapper() {
  }

  public static User mapVoToDomain(UserVo userVo){
    return User.builder()
        .withId(userVo.getId())
        .withEmail(userVo.getEmail())
        .withPassword(userVo.getPassword())
        .build();
  }

  public static UserVo mapDomainToVo(User user){
    return UserVo.builder()
        .withId(user.getId())
        .withEmail(user.getEmail())
        .withPassword(user.getPassword())
        .withPostsIds(mapPostsToPostsIds(user.getPosts()))
        .withCommentsIds(mapCommentsToCommentsIds(user.getComments()))
        .build();
  }

  public  static List<Long> mapPostsToPostsIds(List<Post> posts) {
    if (Optional.ofNullable(posts).isPresent()) {
      return posts.stream().map(Post::getId).collect(Collectors.toList());
    } else {
      return Collections.emptyList();
    }
  }

  public  static List<Long> mapCommentsToCommentsIds(List<Comment> comments) {
    if (Optional.ofNullable(comments).isPresent()) {
      return comments.stream().map(Comment::getId).collect(Collectors.toList());
    } else {
      return Collections.emptyList();
    }
  }
}
