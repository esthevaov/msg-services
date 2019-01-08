package com.vervloet.msgservices.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.vervloet.msgservices.domain.vo.jsonapi.BaseJsonApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.Optional;

@JsonPropertyOrder(value = {"user_id", "email", "password", "posts_id", "comments_vos"})
public class UserWithCommentsVo extends BaseJsonApiModel {

  @ApiModelProperty(hidden = true)
  @JsonProperty(value = "user_id", access = Access.READ_ONLY)
  private Long id;

  @JsonProperty(value = "email")
  private String email;

  @JsonProperty(value = "password")
  private String password;

  @JsonProperty(value = "posts_ids")
  private List<Long> postsIds;

  @JsonProperty(value = "comments_vo")
  private List<CommentVo> commentsVos;

  public UserWithCommentsVo() {
  }

  public UserWithCommentsVo(Builder builder) {
    Optional.ofNullable(builder.id).ifPresent(this::setId);
    Optional.ofNullable(builder.email).ifPresent(this::setEmail);
    Optional.ofNullable(builder.password).ifPresent(this::setPassword);
    Optional.ofNullable(builder.postsIds).ifPresent(this::setPostsIds);
    Optional.ofNullable(builder.commentsVos).ifPresent(this::setCommentsVos);
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

  public List<Long> getPostsIds() {
    return postsIds;
  }

  public void setPostsIds(List<Long> postsIds) {
    this.postsIds = postsIds;
  }

  public List<CommentVo> getCommentsVos() {
    return commentsVos;
  }

  public void setCommentsVos(List<CommentVo> commentsVos) {
    this.commentsVos = commentsVos;
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
    private List<Long> postsIds;
    private List<CommentVo> commentsVos;

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

    public Builder withPostsIds(List<Long> postsIds) {
      this.postsIds = postsIds;
      return this;
    }

    public Builder withCommentsVos(List<CommentVo> commentsVos) {
      this.commentsVos = commentsVos;
      return this;
    }

    public UserWithCommentsVo build() {
      return new UserWithCommentsVo(this);
    }

  }


}
