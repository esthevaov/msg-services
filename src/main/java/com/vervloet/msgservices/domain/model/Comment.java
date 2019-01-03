package com.vervloet.msgservices.domain.model;

import java.util.Optional;
import javax.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comments")
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private long id;

    @Size(max = 1000)
    @Column(name = "content")
    private String content;

    @Column(name= "created", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date created;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Comment() {
    }

    public Comment(String content, Date created) {
        this.content = content;
        this.created = created;
    }

    public Comment(Builder builder) {
        Optional.ofNullable(builder.id).ifPresent(this::setId);
        Optional.ofNullable(builder.content).ifPresent(this::setContent);
        Optional.ofNullable(builder.created).ifPresent(this::setCreated);
        Optional.ofNullable(builder.post).ifPresent(this::setPost);
        Optional.ofNullable(builder.user).ifPresent(this::setUser);
    }

    public static Builder builder() {
        return new Builder();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static final class Builder {

        private Long id;
        private String content;
        private Date created;
        private Post post;
        private User user;

        public Builder withId(long id) {
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

        public Builder withPost(Post post) {
            this.post = post;
            return this;
        }

        public Builder withUser(User user) {
            this.user = user;
            return this;
        }

        public Comment build() {
            return new Comment(this);
        }

    }
}