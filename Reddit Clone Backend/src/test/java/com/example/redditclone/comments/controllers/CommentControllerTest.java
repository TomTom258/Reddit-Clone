package com.example.redditclone.comments.controllers;

import static org.junit.jupiter.api.Assertions.*;
import com.example.redditclone.comments.services.CommentService;
import com.example.redditclone.comments.services.CommentValidator;
import com.example.redditclone.dtos.CommentDto;
import com.example.redditclone.dtos.ErrorResponseDto;
import com.example.redditclone.dtos.ResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CommentControllerTest {
    private CommentController commentController;

    @Mock
    private CommentValidator commentValidator;

    @Mock
    private CommentService commentService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        commentController = new CommentController(commentValidator, commentService);
    }

    @Test
    void addComment_ValidInput_ReturnsCreatedResponse() {
        long id = 1L;
        CommentDto commentDto = new CommentDto();

        when(commentValidator.addComment(commentDto, id)).thenReturn(true);

        ResponseEntity<?> response = commentController.addComment(id, commentDto);

        verify(commentValidator).addComment(commentDto, id);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void addComment_InvalidInput_ReturnsErrorResponse() {
        long id = 1L;
        CommentDto commentDto = new CommentDto();
        String errorReason = "Invalid comment";

        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, errorReason))
                .when(commentValidator).addComment(commentDto, id);

        ResponseEntity<ResponseDto> response = commentController.addComment(id, commentDto);

        verify(commentValidator).addComment(commentDto, id);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(errorReason, ((ErrorResponseDto) response.getBody()).getError());
    }

    @Test
    void editComment_ValidInput_ReturnsOkResponse() {
        long commentId = 1L;
        CommentDto commentDto = new CommentDto();

        when(commentValidator.addComment(commentDto, commentId)).thenReturn(true);

        ResponseEntity<ResponseDto> response = commentController.editComment(commentId, commentDto);

        verify(commentValidator).editTheComment(commentDto, commentId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void editComment_InvalidInput_ReturnsErrorResponse() {
        long commentId = 1L;
        CommentDto commentDto = new CommentDto();
        String errorReason = "Invalid comment";

        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, errorReason))
                .when(commentValidator).editTheComment(commentDto, commentId);

        ResponseEntity<ResponseDto> response = commentController.editComment(commentId, commentDto);

        verify(commentValidator).editTheComment(commentDto, commentId);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(errorReason, ((ErrorResponseDto) response.getBody()).getError());
    }

    @Test
    void upvoteComment_ReturnsCreatedResponse() {
        long id = 1L;

        when(commentService.upvoteComment(id)).thenReturn(true);

        ResponseEntity<ResponseDto> response = commentController.upvoteComment(id);

        verify(commentService).upvoteComment(id);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void downvoteComment_ReturnsCreatedResponse() {
        long id = 1L;

        when(commentService.downvoteComment(id)).thenReturn(true);

        ResponseEntity<ResponseDto> response = commentController.downvoteComment(id);

        verify(commentService).downvoteComment(id);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void deleteComment_ReturnsNoContentResponse() {
        long id = 1L;

        when(commentService.downvoteComment(id)).thenReturn(true);

        ResponseEntity<ResponseDto> response = commentController.deleteComment(id);

        verify(commentService).deleteComment(id);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}