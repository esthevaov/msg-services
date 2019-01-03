package com.vervloet.msgservices.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.vervloet.msgservices.domain.vo.jsonapi.BaseJsonApiModel;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@JsonPropertyOrder(value = {"id", "title", "content", "votes", "created", "user_id", "comments_ids"})
public class PostVo extends BaseJsonApiModel {

  @JsonProperty(value = "id")
  private Long id;

  @JsonProperty(value = "title")
  private String title;

  @JsonProperty(value = "content")
  private String content;

  @JsonProperty(value = "votes")
  private Long votes;

  @JsonProperty(value = "created")
  private Date created;

  @JsonProperty(value = "user_id")
  private Long userId;

  @JsonProperty(value = "comments_ids")
  private List<Long> commentsIds;


  public PostVo() {
  }

  public PostVo(Builder builder) {
    Optional.ofNullable(builder.id).ifPresent(this::setId);
    Optional.ofNullable(builder.title).ifPresent(this::setTitle);
    Optional.ofNullable(builder.content).ifPresent(this::setContent);
    Optional.ofNullable(builder.votes).ifPresent(this::setVotes);
    Optional.ofNullable(builder.created).ifPresent(this::setCreated);
    Optional.ofNullable(builder.userId).ifPresent(this::setUserId);
    Optional.ofNullable(builder.commentsIds).ifPresent(this::setCommentsIds);
  }

  public static Builder builder() {
    return new Builder();
  }

  public Long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Long getVotes() {
    return votes;
  }

  public void setVotes(long votes) {
    this.votes = votes;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public List<Long> getCommentsIds() {
    return commentsIds;
  }

  public void setCommentsIds(List<Long> commentsIds) {
    this.commentsIds = commentsIds;
  }

  @Override
  public String getTypeName() {
    return "post";
  }

  @Override
  public Optional<String> baseUrl() {
    return Optional.of("/api/post/" + getId());
  }

  public static final class Builder {

    private Long id;
    private String title;
    private String content;
    private Long votes;
    private Date created;
    private Long userId;
    private List<Long> commentsIds;

    public Builder withId(long id) {
      this.id = id;
      return this;
    }

    public Builder withTitle(String title) {
      this.title = title;
      return this;
    }

    public Builder withContent(String content) {
      this.content = content;
      return this;
    }

    public Builder withVotes(Long votes) {
      this.votes = votes;
      return this;
    }

    public Builder withCreated(Date created) {
      this.created = created;
      return this;
    }

    public Builder withUserId(Long userId) {
      this.userId = userId;
      return this;
    }

    public Builder withCommentsIds(List<Long> commentsIds) {
      this.commentsIds = commentsIds;
      return this;
    }

    public PostVo build() {
      return new PostVo(this);
    }
  }
}