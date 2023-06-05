package com.example.redditclone.comments.services;

import com.example.redditclone.dtos.CommentDto;
import org.springframework.stereotype.Service;

@Service
public interface CommentValidator {
    public boolean addComment(CommentDto commentDto, long id, String username);
    public boolean editTheComment(CommentDto commentDto, Long commentId, String username);
}
