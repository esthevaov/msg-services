package com.vervloet.msgservices.domain.model;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @NotBlank
    @Email
    @Column(name = "email")
    private String email;

    @NotBlank
    @Column(name = "password")
    private String password;

    @OneToMany(cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "user")
    private List<Post> posts;

    @OneToMany(cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "user")
    private List<Comment> comments;

    public User() {
    }

    public User(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.id = user.getId();
        this.posts = user.getPosts();
        this.comments = user.getComments();
    }

    public User(Builder builder) {
        Optional.ofNullable(builder.id).ifPresent(this::setId);
        Optional.ofNullable(builder.email).ifPresent(this::setEmail);
        Optional.ofNullable(builder.password).ifPresent(this::setPassword);
        Optional.ofNullable(builder.posts).ifPresent(this::setPosts);
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

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }


    public static final class Builder {

        private Long id;
        private String email;
        private String password;
        private List<Post> posts;
        private List<Comment> comments;

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

        public Builder withPosts(List<Post> posts) {
            this.posts = posts;
            return this;
        }

        public Builder withComments(List<Comment> comments) {
            this.comments = comments;
            return this;
        }

        public User build() {
            return new User(this);
        }

    }
}