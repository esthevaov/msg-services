package com.vervloet.msgservices.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.vervloet.msgservices.domain.vo.jsonapi.BaseJsonApiModel;
import java.util.Optional;
import java.util.Date;

@JsonPropertyOrder(value = {"id", "content", "created", "post_id", "user_id"})
public class CommentVo extends BaseJsonApiModel {

  @JsonProperty(value = "id")
  private Long id;

  @JsonProperty(value = "content")
  private String content;

  @JsonProperty(value = "created")
  private Date created;

  @JsonProperty(value = "post_id")
  private Long postId;

  @JsonProperty(value = "user_id")
  private Long userId;


  public CommentVo() {
  }

  public CommentVo(Builder builder) {
    Optional.ofNullable(builder.id).ifPresent(this::setId);
    Optional.ofNullable(builder.content).ifPresent(this::setContent);
    Optional.ofNullable(builder.created).ifPresent(this::setCreated);
    Optional.ofNullable(builder.postId).ifPresent(this::setPostId);
    Optional.ofNullable(builder.userId).ifPresent(this::setUserId);
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

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Long getPostId() {
    return postId;
  }

  public void setPostId(Long postId) {
    this.postId = postId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  @Override
  public String getTypeName() {
    return "comment";
  }

  @Override
  public Optional<String> baseUrl() {
    return Optional.of("/api/comment/" + getId());
  }


  public static final class Builder {

    private Long id;
    private String content;
    private Date created;
    private Long postId;
    private Long userId;

    public Builder withId(Long id) {
      this.id = id;
      return this;
    }

    public Builder withContent(String content) {
      this.content = content;
      return this;
    }

    public Builder withCreated(Date created) {
      this.created = created;
      return this;
    }

    public Builder withPostId(Long postId) {
      this.postId = postId;
      return this;
    }

    public Builder withUserId(Long userId) {
      this.userId = userId;
      return this;
    }

    public CommentVo build() {
      return new CommentVo(this);
    }

  }
}
