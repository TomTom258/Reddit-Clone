package com.example.redditclone.posts.services;

import com.example.redditclone.posts.models.Post;
import org.springframework.stereotype.Service;

@Service
public interface PostValidator {
    public boolean validateTitle(Post post);

    public boolean validateContent(Post post);

    public boolean postThePost(Post post);

    public boolean editThePost(Post post, Long postId);

}
