package com.example.redditclone.comments.models;

import com.example.redditclone.posts.models.Post;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "comment")
@Table(name = "Comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    Long id;

    @Column(name = "content", length = 1337)
    String content;

    @Column(name = "created_at")
    LocalDateTime created_at;

    @Column(name = "owner")
    String owner;

    @Column(name = "reputation")
    Long reputation;

    String ownerProfilePicture;

    @ManyToOne
    @JoinColumn(name = "post_id", insertable = false)
    private Post post;

    public Comment() {
        this.created_at = LocalDateTime.now();
        this.reputation = 0L;
    }

    public Comment(String content, String owner) {
        this.content = content;
        this.owner = owner;
        this.created_at = LocalDateTime.now();
        this.reputation = 0L;
        this.ownerProfilePicture = "";
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

    @JsonBackReference
    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Long getPostId(){
        return post.getId();
    }

    public String getOwnerProfilePicture() {
        return ownerProfilePicture;
    }

    public void setOwnerProfilePicture(String ownerProfilePicture) {
        this.ownerProfilePicture = ownerProfilePicture;
    }
}
