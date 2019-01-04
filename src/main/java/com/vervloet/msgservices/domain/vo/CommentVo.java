package com.vervloet.msgservices.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.vervloet.msgservices.domain.vo.jsonapi.BaseJsonApiModel;
import java.util.Optional;
import java.util.Date;

@JsonPropertyOrder(value = {"id", "content", "created", "post_path", "user_path"})
public class CommentVo extends BaseJsonApiModel {

  @JsonProperty(value = "id")
  private Long id;

  @JsonProperty(value = "content")
  private String content;

  @JsonProperty(value = "created")
  private Date created;

  @JsonProperty(value = "post_path")
  private String postPath;

  @JsonProperty(value = "user_path")
  private String userPath;


  public CommentVo() {
  }

  public CommentVo(Builder builder) {
    Optional.ofNullable(builder.id).ifPresent(this::setId);
    Optional.ofNullable(builder.content).ifPresent(this::setContent);
    Optional.ofNullable(builder.created).ifPresent(this::setCreated);
    Optional.ofNullable(builder.postPath).ifPresent(this::setPostPath);
    Optional.ofNullable(builder.userPath).ifPresent(this::setUserPath);
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

  public String getPostPath() {
    return postPath;
  }

  public void setPostPath(String postPath) {
    this.postPath = postPath;
  }

  public String getUserPath() {
    return userPath;
  }

  public void setUserPath(String userPath) {
    this.userPath = userPath;
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
    private String postPath;
    private String userPath;

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

    public Builder withPostPath(String postPath) {
      this.postPath = postPath;
      return this;
    }

    public Builder withUserPath(String userPath) {
      this.userPath = userPath;
      return this;
    }

    public CommentVo build() {
      return new CommentVo(this);
    }

  }
}