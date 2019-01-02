package com.vervloet.msgservices.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.vervloet.msgservices.domain.vo.jsonapi.BaseJsonApiModel;
import java.util.List;
import java.util.Optional;

@JsonPropertyOrder(value = {"user_id", "email", "password", "message_id"})
public class UserVo extends BaseJsonApiModel {

  @JsonProperty(value = "user_id", access = Access.READ_ONLY)
  private Long id;
  @JsonProperty(value = "email", access = JsonProperty.Access.WRITE_ONLY)
  private String email;
  @JsonProperty(value = "password", access = JsonProperty.Access.WRITE_ONLY)
  private String password;
  @JsonProperty(value = "messages_ids")
  private List<Long> messagesIds;

  public UserVo() {
  }

  public UserVo(Builder builder) {
    Optional.ofNullable(builder.id).ifPresent(this::setId);
    Optional.ofNullable(builder.email).ifPresent(this::setEmail);
    Optional.ofNullable(builder.password).ifPresent(this::setPassword);
    Optional.ofNullable(builder.messagesIds).ifPresent(this::setMessagesIds);
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

  public List<Long> getMessagesIds() {
    return messagesIds;
  }

  public void setMessagesIds(List<Long> messagesIds) {
    this.messagesIds = messagesIds;
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
    private List<Long> messagesIds;

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

    public Builder withMessagesIds(List<Long> messagesIds) {
      this.messagesIds = messagesIds;
      return this;
    }

    public UserVo build() {
      return new UserVo(this);
    }

  }


}
