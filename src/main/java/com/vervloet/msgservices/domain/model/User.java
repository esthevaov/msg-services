package com.vervloet.msgservices.domain.model;

import java.util.HashSet;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

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
    private Set<Message> messages = new HashSet<>();


    public User() {
    }

    public User(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.id = user.getId();
        this.messages = user.getMessages();
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }
}