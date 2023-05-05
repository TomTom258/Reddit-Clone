package com.example.redditclone.users.repositories;

import com.example.redditclone.users.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface PostRepository extends JpaRepository <Post, Long> {
}
