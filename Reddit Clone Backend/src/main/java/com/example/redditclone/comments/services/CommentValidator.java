package com.example.redditclone.comments.services;

import com.example.redditclone.dtos.CommentDto;
import org.springframework.stereotype.Service;

@Service
public interface CommentValidator {
    public boolean validateId(Long id);
    public boolean validateContent(String content);
    public boolean addComment(CommentDto commentDto, long id);
    public boolean editTheComment(CommentDto commentDto, Long commentId);
}
