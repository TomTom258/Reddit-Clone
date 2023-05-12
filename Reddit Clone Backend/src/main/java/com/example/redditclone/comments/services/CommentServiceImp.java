package com.example.redditclone.comments.services;

import org.springframework.stereotype.Service;

@Service
public class CommentServiceImp implements CommentService {
    @Override
    public boolean upvoteComment(long id) {
        return false;
    }

    @Override
    public boolean downvoteComment(long id) {
        return false;
    }

    @Override
    public boolean deleteComment(long id) {
        return false;
    }
}
