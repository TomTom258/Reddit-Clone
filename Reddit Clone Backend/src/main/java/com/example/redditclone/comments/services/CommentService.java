package com.example.redditclone.comments.services;

import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    public boolean upvoteComment(long id);

    public boolean downvoteComment(long id);

    public boolean deleteComment(long id);
}
