package com.example.redditclone.posts.controllers;

import com.example.redditclone.dtos.PostDto;
import com.example.redditclone.posts.models.Post;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = PostController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class PostControllerIntegrationTest {
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
    public void getAllPosts_ReturnsListOfPosts() throws Exception {
        Post post1 = new Post("Test Title 1", "Test Content 1", "testUser1");
        Post post2 = new Post("Test Title 2", "Test Content 2", "testUser2");
        List<Post> posts = Arrays.asList(post1, post2);

        Mockito.when(postRepository.findAll()).thenReturn(posts);

        mockMvc.perform(MockMvcRequestBuilders.get("/posts/get"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Test Title 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content").value("Test Content 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Test Title 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].content").value("Test Content 2"));
    }

    @Test
    public void addPost_ValidInput_ReturnsCreatedResponse() throws Exception {
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
    void addPost_IvalidInput_ReturnsBadRequestReponse() throws Exception {
        User user = new User("testUsername", "test@email", "testPassword", false);
        PostDto postDto = new PostDto("This is test Title", "..", 1L);

        Mockito.when(userRepository.getReferenceById(Mockito.any())).thenReturn(user);
        Mockito.when(postValidator.postThePost(Mockito.any()))
                .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "The content must be at least 8 characters long"));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/posts/add")
                        .content(objectMapper.writeValueAsString(postDto))
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", is("The content must be at least 8 characters long")));
    }

    @Test
    public void editPost_ValidInput_ReturnsOkResponse() throws Exception {
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

    @Test
    void editPost_IvalidInput_ReturnsBadRequestReponse() throws Exception {
        long id = 1L;
        User user = new User("testUsername", "test@email", "testPassword", false);
        PostDto postDto = new PostDto("This is test Title", "..", 1L);

        Mockito.when(userRepository.getReferenceById(Mockito.any())).thenReturn(user);
        Mockito.when(postValidator.editThePost(Mockito.any(), Mockito.any()))
                .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "The content must be at least 8 characters long"));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/posts/edit/{id}", id)
                        .content(objectMapper.writeValueAsString(postDto))
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", is("The content must be at least 8 characters long")));
    }

    @Test
    public void upvotePost_ValidInput_ReturnsCreatedResponse() throws Exception {
        long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.post("/posts/upvote/{id}", id))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(201))
                .andExpect(jsonPath("$.message").value("Post was successfully upvoted"));
    }

    @Test
    public void downvotePost_ValidInput_ReturnsCreatedResponse() throws Exception {
        long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.post("/posts/downvote/{id}", id))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(201))
                .andExpect(jsonPath("$.message").value("Post was successfully downvoted"));
    }

    @Test
    public void deletePost_ValidInput_ReturnsNoContentResponse() throws Exception {
        long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/posts/delete/{id}", id))
                .andExpect(status().isNoContent())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(204))
                .andExpect(jsonPath("$.message").value("Post was successfully removed"));
    }
}