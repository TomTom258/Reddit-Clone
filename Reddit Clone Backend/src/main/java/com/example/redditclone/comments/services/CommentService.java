package com.example.redditclone.comments.services;

import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    public boolean checkEmailVerifiedAt(String username);
    public boolean upvoteComment(long id, String upvotedByUsername);

    public boolean downvoteComment(long id, String downvotedByUsername);

    public boolean deleteComment(long id);
}
