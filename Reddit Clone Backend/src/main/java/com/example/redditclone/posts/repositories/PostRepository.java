package com.example.redditclone.posts.repositories;

import com.example.redditclone.posts.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface PostRepository extends JpaRepository <Post, Long> {
}
