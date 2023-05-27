package com.example.redditclone.posts.controllers;

import com.example.redditclone.dtos.PostDto;
import com.example.redditclone.posts.repositories.PostRepository;
import com.example.redditclone.posts.services.PostService;
import com.example.redditclone.posts.services.PostValidator;
import com.example.redditclone.users.models.User;
import com.example.redditclone.users.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = PostController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PostValidator postValidator;
    @MockBean
    private PostService postService;
    @Autowired
    private PostController postController;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private PostRepository postRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void PostController_Returns_Created_When_Adding_Post() throws Exception {
        User user = new User("testUsername", "test@email", "testPassword", false);
        PostDto postDto = new PostDto("This is test Title", "This is the first sentence.", 1L);

        Mockito.when(userRepository.getReferenceById(Mockito.any())).thenReturn(user);
        Mockito.when(postValidator.postThePost(Mockito.any())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/posts/add")
                        .content(objectMapper.writeValueAsString(postDto))
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(201)))
                .andExpect(jsonPath("$.message", is("Post was successfully added")));
    }

    @Test
    public void PostController_Returns_Ok_When_Editing_Post() throws Exception {
        long id = 1L;
        User user = new User("testUsername", "test@email", "testPassword", false);
        PostDto postDto = new PostDto("This is test Title", "This is the first sentence.", 1L);

        Mockito.when(userRepository.getReferenceById(Mockito.any())).thenReturn(user);
        Mockito.when(postValidator.editThePost(Mockito.any(), Mockito.any())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/posts/edit/{id}", id)
                        .content(objectMapper.writeValueAsString(postDto))
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(200)))
                .andExpect(jsonPath("$.message", is("Post was successfully changed")));
    }
}