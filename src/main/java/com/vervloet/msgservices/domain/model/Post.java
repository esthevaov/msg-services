package com.vervloet.msgservices.domain.model;

import java.util.List;
import java.util.Optional;
import javax.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "posts")
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 200)
    @NotBlank
    private String title;

    @Size(max = 1000)
    private String content;

    private Long votes = 0L;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date created;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "post")
    private List<Comment> comments;


    public Post() {
    }

    public Post(Builder builder) {
        Optional.ofNullable(builder.id).ifPresent(this::setId);
        Optional.ofNullable(builder.title).ifPresent(this::setTitle);
        Optional.ofNullable(builder.content).ifPresent(this::setContent);
        Optional.ofNullable(builder.votes).ifPresent(this::setVotes);
        Optional.ofNullable(builder.created).ifPresent(this::setCreated);
        Optional.ofNullable(builder.user).ifPresent(this::setUser);
        Optional.ofNullable(builder.comments).ifPresent(this::setComments);
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

    public void setVotes(Long votes) {
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public static final class Builder {

        private Long id;
        private String title;
        private String content;
        private Long votes;
        private Date created;
        private User user;
        private List<Comment> comments;

        public Builder withId(Long id) {
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

        public Builder withComments(List<Comment> comments) {
            this.comments = comments;
            return this;
        }

        public Post build() {
            return new Post(this);
        }

    }

}
