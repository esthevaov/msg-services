package com.vervloet.msgservices.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Optional;
import javax.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

// @JsonIgnoreProperties(value = {"created", "votes"}, allowGetters = true)
@Entity
@Table(name = "messages")
@EntityListeners(AuditingEntityListener.class)
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @NotBlank
    private String title;

    @Size(max = 500)
    private String content;

    private Long votes;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date created;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public Message() {
    }

    public Message(@NotBlank String title, String content) {
        this.title = title;
        this.content = content;
        this.votes = 0L;
    }

    public Message(Builder builder) {
        Optional.ofNullable(builder.id).ifPresent(this::setId);
        Optional.ofNullable(builder.title).ifPresent(this::setTitle);
        Optional.ofNullable(builder.content).ifPresent(this::setContent);
        Optional.ofNullable(builder.votes).ifPresent(this::setVotes);
        Optional.ofNullable(builder.created).ifPresent(this::setCreated);
        Optional.ofNullable(builder.user).ifPresent(this::setUser);
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /*public List<String> getVoted() {

        return voted;
    }

    public void setVoted(List<String> voted) {

        this.voted = voted;
    }

    public void addVoted(String voted) {

        this.voted.add(voted);
    }

    public boolean checkVoted(String voted) {

        return this.voted.contains(voted);
    }*/

    public static final class Builder {

        private Long id;
        private String title;
        private String content;
        private Long votes;
        private Date created;
        private User user;

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

        public Builder withUser(User user) {
            this.user = user;
            return this;
        }

        public Message build() {
            return new Message(this);
        }

    }

}
