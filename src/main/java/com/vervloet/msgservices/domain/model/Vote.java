package com.vervloet.msgservices.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "votes")
@EntityListeners(AuditingEntityListener.class)
public class Vote {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "post_id")
  private Long postId;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "type_vote")
  private Integer typeVote;


  public Vote() {
  }

  public Vote(Long messageId, Long userId, Integer typeVote) {
    this.postId = messageId;
    this.userId = userId;
    this.typeVote = typeVote;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public Integer getTypeVote() {
    return typeVote;
  }

  public void setTypeVote(Integer typeVote) {
    this.typeVote = typeVote;
  }

  public Vote setTypeVoteAndReturn(Integer typeVote) {
    this.typeVote = typeVote;
    return this;
  }
}
