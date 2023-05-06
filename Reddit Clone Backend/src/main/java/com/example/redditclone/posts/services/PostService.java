package com.example.redditclone.posts.services;

import org.springframework.stereotype.Service;

@Service
public interface PostService {
    public boolean upvotePost(long id);

    public boolean downvotePost(long id);

    public boolean deletePost(long id);
}
