package com.example.redditclone.comments.controllers;

import com.example.redditclone.comments.services.CommentService;
import com.example.redditclone.comments.services.CommentValidator;
import com.example.redditclone.dtos.CommentDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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

    @Test
    void addComment_ValidInput_ReturnsCreatedResponse() throws Exception {
        long id = 1L;
        CommentDto commentDto = new CommentDto("This is test content");

        when(commentValidator.addComment(commentDto, id)).thenReturn(true);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/comments/add/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(commentDto.toString()))
                .andReturn();

        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        assertEquals(201, (result.getResponse().getStatus()));
        assertEquals("Comment was successfully added", result.getResponse().getContentAsString());
        verify(commentValidator).addComment(commentDto, id);
    }
    // Rest of the test cases for other methods
}



