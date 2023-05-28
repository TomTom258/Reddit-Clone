package com.example.redditclone.posts.models;

import static org.junit.jupiter.api.Assertions.*;

import com.example.redditclone.comments.models.Comment;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PostTest {

    @Test
    void testGettersAndSetters() {
        // Arrange
        String title = "Test Post";
        String content = "This is a test post.";
        LocalDateTime createdAt = LocalDateTime.now();
        Long reputation = 100L;
        Set<Comment> comments = new HashSet<>();

        // Act
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setCreated_at(createdAt);
        post.setReputation(reputation);
        post.setComments(comments);

        // Assert
        assertEquals(null, post.getId());
        assertEquals(title, post.getTitle());
        assertEquals(content, post.getContent());
        assertEquals(createdAt, post.getCreated_at());
        assertEquals(null, post.getOwner());
        assertEquals(reputation, post.getReputation());
        assertEquals(comments, post.getComments());
    }

    @Test
    void testConstructor() {
        // Arrange
        String title = "Test Post";
        String content = "This is a test post.";
        String owner = "testuser";

        // Act
        Post post = new Post(title, content, owner);

        // Assert
        assertEquals(title, post.getTitle());
        assertEquals(content, post.getContent());
        assertEquals(owner, post.getOwner());
        assertNotNull(post.getCreated_at());
        assertEquals(0L, post.getReputation());
        assertEquals(null, post.getComments());
    }
}
