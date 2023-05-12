package com.example.redditclone.comments.repositories;

import com.example.redditclone.comments.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
