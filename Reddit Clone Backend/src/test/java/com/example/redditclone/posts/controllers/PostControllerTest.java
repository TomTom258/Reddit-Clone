package com.example.redditclone.posts.controllers;

import com.example.redditclone.dtos.*;
import com.example.redditclone.posts.models.Post;
import com.example.redditclone.posts.repositories.PostRepository;
import com.example.redditclone.posts.services.PostService;
import com.example.redditclone.posts.services.PostValidator;
import com.example.redditclone.users.models.User;
import com.example.redditclone.users.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PostControllerTest {
    @Mock
    private PostValidator postValidator;

    @Mock
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Mockito.reset(postValidator, postService, postRepository, userRepository);
    }

    @Test
    void getAllPosts_ReturnsSortedPosts() {
        List<Post> storedPosts = Arrays.asList(
                new Post("Title 1", "Content 1", "user1"),
                new Post("Title 2", "Content 2", "user2"),
                new Post("Title 3", "Content 3", "user3")
        );
        Mockito.when(postRepository.findAll()).thenReturn(storedPosts);

        ResponseEntity<List<Post>> responseEntity = postController.getAllPosts();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(storedPosts, responseEntity.getBody());
        Mockito.verify(postRepository).findAll();
    }

    @Test
    void post_ValidInput_ReturnsCreatedResponse() {
        User user = new User("testUsername", "test@email", "testPassword", false);
        PostDto postDto = new PostDto();

        when(userRepository.getReferenceById(Mockito.any())).thenReturn(user);
        when(postValidator.postThePost(Mockito.any())).thenReturn(true);

        ResponseEntity<?> response = postController.post(postDto);

        verify(postValidator).postThePost(Mockito.any());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void post_InvalidInput_ReturnsBadRequestResponse() {
        User user = new User("testUsername", "test@email", "testPassword", false);
        String errorReason = "The title must be at least 8 characters long";

        when(userRepository.getReferenceById(Mockito.any())).thenReturn(user);
        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, errorReason))
                .when(postValidator).postThePost(Mockito.any());

        ResponseEntity<ResponseDto> response = postController.post(new PostDto());

        verify(postValidator).postThePost(Mockito.any());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(errorReason, ((ErrorResponseDto) response.getBody()).getError());
    }

    @Test
    void edit_ValidInput_ReturnsOkResponse() {
        User user = new User("testUsername", "test@email", "testPassword", false);
        PostDto postDto = new PostDto();

        when(userRepository.getReferenceById(Mockito.any())).thenReturn(user);
        when(postValidator.editThePost(Mockito.any(), Mockito.any())).thenReturn(true);

        ResponseEntity<?> response = postController.editPost(1L, postDto);

        verify(postValidator).editThePost(Mockito.any(), Mockito.any());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void edit_InvalidInput_ReturnsBadRequest() {
        User user = new User("testUsername", "test@email", "testPassword", false);
        PostDto postDto = new PostDto();
        String errorReason = "The title must be at least 8 characters long";

        when(userRepository.getReferenceById(Mockito.any())).thenReturn(user);
        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, errorReason))
                .when(postValidator).editThePost(Mockito.any(), Mockito.any());

        ResponseEntity<?> response = postController.editPost(1L, postDto);

        verify(postValidator).editThePost(Mockito.any(), Mockito.any());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(errorReason, ((ErrorResponseDto) response.getBody()).getError());
    }

    @Test
    void upvotePost_ReturnsCreatedResponse() {
        long id = 1L;

        when(postService.upvotePost(id)).thenReturn(true);

        ResponseEntity<ResponseDto> response = postController.upvotePost(id);

        verify(postService).upvotePost(id);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void downvotePost_ReturnsCreatedResponse() {
        long id = 1L;

        when(postService.downvotePost(id)).thenReturn(true);

        ResponseEntity<ResponseDto> response = postController.downvotePost(id);

        verify(postService).downvotePost(id);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void deletePost_ReturnsNoContentResponse() {
        long id = 1L;

        when(postService.deletePost(id)).thenReturn(true);

        ResponseEntity<ResponseDto> response = postController.deletePost(id);

        verify(postService).deletePost(id);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}

