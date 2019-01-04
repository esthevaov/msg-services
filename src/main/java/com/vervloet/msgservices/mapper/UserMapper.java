package com.vervloet.msgservices.mapper;

import com.vervloet.msgservices.domain.model.Comment;
import com.vervloet.msgservices.domain.model.Post;
import com.vervloet.msgservices.domain.model.User;
import com.vervloet.msgservices.domain.vo.CommentVo;
import com.vervloet.msgservices.domain.vo.PostVo;
import com.vervloet.msgservices.domain.vo.UserVo;
import com.vervloet.msgservices.domain.vo.UserWithCommentsVo;
import com.vervloet.msgservices.domain.vo.UserWithPostsVo;
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

  public static UserWithCommentsVo mapDomainToWithCommentsVo(User user){
    return UserWithCommentsVo.builder()
        .withId(user.getId())
        .withEmail(user.getEmail())
        .withPassword(user.getPassword())
        .withPostsIds(mapPostsToPostsIds(user.getPosts()))
        .withCommentsVos(mapCommentsToCommentsVos(user.getComments()))
        .build();
  }

  public static UserWithPostsVo mapDomainToWithPostsVo(User user){
    return UserWithPostsVo.builder()
        .withId(user.getId())
        .withEmail(user.getEmail())
        .withPassword(user.getPassword())
        .withPostsVos(mapPostsToPostsVos(user.getPosts()))
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

  public  static List<CommentVo> mapCommentsToCommentsVos(List<Comment> comments) {
    if (Optional.ofNullable(comments).isPresent()) {
      return comments.stream().map(comment -> CommentMapper.mapDomainToVo(comment)).collect(Collectors.toList());
    } else {
      return Collections.emptyList();
    }
  }

  public  static List<PostVo> mapPostsToPostsVos(List<Post> posts) {
    if (Optional.ofNullable(posts).isPresent()) {
      return posts.stream().map(post -> PostMapper.mapDomainToVo(post)).collect(Collectors.toList());
    } else {
      return Collections.emptyList();
    }
  }
}
