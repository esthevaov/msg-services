package com.vervloet.msgservices.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.vervloet.msgservices.domain.vo.jsonapi.BaseJsonApiModel;
import java.util.List;
import java.util.Optional;

@JsonPropertyOrder(value = {"user_id", "email", "password", "posts_vos", "comments_ids"})
public class UserWithPostsVo extends BaseJsonApiModel {

  @JsonProperty(value = "user_id", access = Access.READ_ONLY)
  private Long id;

  @JsonProperty(value = "email")
  private String email;

  @JsonProperty(value = "password")
  private String password;

  @JsonProperty(value = "posts_vos")
  private List<PostVo> postsVos;

  @JsonProperty(value = "comments_ids")
  private List<Long> commentsIds;

  public UserWithPostsVo() {
  }

  public UserWithPostsVo(Builder builder) {
    Optional.ofNullable(builder.id).ifPresent(this::setId);
    Optional.ofNullable(builder.email).ifPresent(this::setEmail);
    Optional.ofNullable(builder.password).ifPresent(this::setPassword);
    Optional.ofNullable(builder.postsVos).ifPresent(this::setPostsVos);
    Optional.ofNullable(builder.commentsIds).ifPresent(this::setCommentsIds);
  }

  public static Builder builder() {
    return new Builder();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<PostVo> getPostsVos() {
    return postsVos;
  }

  public void setPostsVos(List<PostVo> postsVos) {
    this.postsVos = postsVos;
  }

  public List<Long> getCommentsIds() {
    return commentsIds;
  }

  public void setCommentsIds(List<Long> commentsIds) {
    this.commentsIds = commentsIds;
  }

  @Override
  public String getTypeName() {
    return "user";
  }

  @Override
  public Optional<String> baseUrl() {
    return Optional.of("/api/user/" + getId());
  }

  public static final class Builder {

    private Long id;
    private String email;
    private String password;
    private List<PostVo> postsVos;
    private List<Long> commentsIds;

    public Builder withId(Long id) {
      this.id = id;
      return this;
    }

    public Builder withEmail(String email) {
      this.email = email;
      return this;
    }

    public Builder withPassword(String password) {
      this.password = password;
      return this;
    }

    public Builder withPostsVos(List<PostVo> postsVos) {
      this.postsVos = postsVos;
      return this;
    }

    public Builder withCommentsIds(List<Long> commentsIds) {
      this.commentsIds = commentsIds;
      return this;
    }

    public UserWithPostsVo build() {
      return new UserWithPostsVo(this);
    }

  }


}
