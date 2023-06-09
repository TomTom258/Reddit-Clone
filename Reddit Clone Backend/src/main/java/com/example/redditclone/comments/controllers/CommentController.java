package com.example.redditclone.comments.controllers;

import com.example.redditclone.comments.services.CommentService;
import com.example.redditclone.comments.services.CommentValidator;
import com.example.redditclone.dtos.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private CommentValidator commentValidator;
    private CommentService commentService;

    public CommentController(CommentValidator commentValidator, CommentService commentService) {
        this.commentValidator = commentValidator;
        this.commentService = commentService;
    }

    private String retrieveUsernameFromToken() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }

    @PostMapping("add/{id}")
    public ResponseEntity<ResponseDto> addComment(@PathVariable long id, @RequestBody CommentDto commentDto) {
        try {
            String username = retrieveUsernameFromToken();
            commentValidator.addComment(commentDto, id, username);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponseDto(e.getReason()));
        }
        return new ResponseEntity<>(new OkResponseDto(201, "Comment was successfully added"), HttpStatus.CREATED);
    }

    @PutMapping("edit/{commentId}")
    public ResponseEntity<ResponseDto> editComment(@PathVariable long commentId, @RequestBody CommentDto commentDto) {
        try {
            String username = retrieveUsernameFromToken();
            commentValidator.editTheComment(commentDto, commentId, username);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponseDto(e.getReason()));
        }
        return new ResponseEntity<>(new OkResponseDto(200, "Comment was successfully changed"), HttpStatus.OK);
    }

    @PostMapping("upvote/{id}")
    public ResponseEntity<ResponseDto> upvoteComment(@PathVariable long id) {
        try {
            String username = retrieveUsernameFromToken();
            commentService.upvoteComment(id, username);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponseDto(e.getReason()));
        }
        return new ResponseEntity<>(new OkResponseDto(201, "Comment was successfully upvoted"), HttpStatus.CREATED);
    }

    @PostMapping("downvote/{id}")
    public ResponseEntity<ResponseDto> downvoteComment(@PathVariable long id) {
        try {
            String username = retrieveUsernameFromToken();
            commentService.downvoteComment(id, username);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponseDto(e.getReason()));
        }
        return new ResponseEntity<>(new OkResponseDto(201, "Comment was successfully downvoted"), HttpStatus.CREATED);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ResponseDto> deleteComment(@PathVariable long id) {
        try {
            String username = retrieveUsernameFromToken();
            commentService.deleteComment(id, username);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponseDto(e.getReason()));
        }
        return new ResponseEntity<>(new OkResponseDto(204, "Comment was successfully removed"), HttpStatus.NO_CONTENT);
    }
}
