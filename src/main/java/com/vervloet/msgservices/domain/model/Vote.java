package com.vervloet.msgservices.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "votes")
@EntityListeners(AuditingEntityListener.class)
public class Vote {

  @Column(name = "message_id")
  private Long messageId;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "type_vote")
  private Integer type_vote;

  public Vote() {
  }

  public Long getMessageId() {
    return messageId;
  }

  public void setMessageId(Long messageId) {
    this.messageId = messageId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Integer getType_vote() {
    return type_vote;
  }

  public void setType_vote(Integer type_vote) {
    this.type_vote = type_vote;
  }
}
