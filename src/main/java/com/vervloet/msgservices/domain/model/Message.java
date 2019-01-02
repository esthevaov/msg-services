package com.vervloet.msgservices.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(max = 100)
    @NotBlank
    private String title;

    @Size(max = 500)
    private String content;

    private long votes;

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
        this.votes = 0;
    }

    public long getId() {
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

    public long getVotes() {
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

}
