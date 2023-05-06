package com.example.redditclone.users.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "post")
@Table(name = "Posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    Long post_id;

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

    public Long getPost_id() {
        return post_id;
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
}
