package com.example.redditclone.posts.models;

import com.example.redditclone.comments.models.Comment;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity(name = "post")
@Table(name = "Posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    Long id;

    @Column(name = "title")
    String title;

    @Column(name = "content", length = 1337)
    String content;

    @Column(name = "created_at")
    LocalDateTime created_at;

    @Column(name = "owner")
    String owner;

    @Column(name = "reputation")
    Long reputation;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private Set<Comment> comments;

    public Post() {
        this.created_at = LocalDateTime.now();
        this.reputation = 0L;
    }

    public Post(String title, String content, String owner) {
        this.title = title;
        this.content = content;
        this.owner = owner;
        this.created_at = LocalDateTime.now();
        this.reputation = 0L;
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

    public Long getReputation() {
        return reputation;
    }

    public void setReputation(Long reputation) {
        this.reputation = reputation;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public String getOwner() {
        return owner;
    }

    @JsonManagedReference
    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
