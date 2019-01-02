package com.vervloet.msgservices.domain.vo;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.Date;
import java.util.Optional;

@JsonPropertyOrder(value = {"id", "title", "content", "votes", "created", "user_id"})
public class MessageVo {

  private Long id;

  private String title;

  private String content;

  private Long votes;

  private Date created;

  private Long userId;


  public MessageVo() {
  }

  public MessageVo(Builder builder) {
    Optional.ofNullable(builder.id).ifPresent(this::setId);
    Optional.ofNullable(builder.title).ifPresent(this::setTitle);
    Optional.ofNullable(builder.content).ifPresent(this::setContent);
    Optional.ofNullable(builder.votes).ifPresent(this::setVotes);
    Optional.ofNullable(builder.created).ifPresent(this::setCreated);
    Optional.ofNullable(builder.userId).ifPresent(this::setUserId);
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


  public static final class Builder {

    private Long id;
    private String title;
    private String content;
    private Long votes;
    private Date created;
    private Long userId;

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

    public MessageVo build() {
      return new MessageVo(this);
    }
  }
}
