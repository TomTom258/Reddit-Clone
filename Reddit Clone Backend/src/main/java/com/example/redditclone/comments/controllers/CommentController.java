package com.example.redditclone.comments.controllers;

import com.example.redditclone.comments.services.CommentValidator;
import com.example.redditclone.dtos.CommentDto;
import com.example.redditclone.dtos.ErrorResponseDto;
import com.example.redditclone.dtos.OkResponseDto;
import com.example.redditclone.dtos.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private CommentValidator commentValidator;

    public CommentController(CommentValidator commentValidator) {
        this.commentValidator = commentValidator;
    }

    @PostMapping("add/{id}")
    public ResponseEntity<ResponseDto> addComment(@PathVariable long id, @RequestBody CommentDto commentDto) {
        try {
            commentValidator.addComment(commentDto, id);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponseDto(e.getReason()));
        }
        return new ResponseEntity<>(new OkResponseDto(201, "Comment was successfully added"), HttpStatus.CREATED);
    }
}
