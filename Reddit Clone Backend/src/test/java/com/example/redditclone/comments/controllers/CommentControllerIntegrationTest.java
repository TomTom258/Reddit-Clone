package com.example.redditclone.comments.controllers;

import com.example.redditclone.comments.services.CommentService;
import com.example.redditclone.comments.services.CommentValidator;
import com.example.redditclone.dtos.CommentDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = CommentController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class CommentControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentValidator commentValidator;

    @MockBean
    private CommentService commentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addComment_ValidInput_ReturnsCreatedResponse() throws Exception {
        long id = 1L;
        CommentDto commentDto = new CommentDto("This is test content");

        when(commentValidator.addComment(commentDto, id)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/comments/add/{id}", id)
                        .content(objectMapper.writeValueAsString(commentDto))
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(201)))
                .andExpect(jsonPath("$.message", is("Comment was successfully added")));
    }

    @Test
    void addComment_IvalidInput_ReturnsCreatedResponse() throws Exception {
        long id = 1L;
        CommentDto commentDto = new CommentDto(".");


        Mockito.when(commentValidator.addComment(Mockito.any(CommentDto.class), Mockito.any(Long.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Comment must be at least 2 characters long!"));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/comments/add/{id}", id)
                        .content(objectMapper.writeValueAsString(commentDto))
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", is("Comment must be at least 2 characters long!")));
    }


}



