package com.example.redditclone.posts.services;

import com.example.redditclone.posts.models.Post;
import com.example.redditclone.posts.repositories.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PostValidatorImpTest {
    @Mock
    private PostRepository fakeRepository;
    @InjectMocks
    private PostValidatorImp fakeValidator;

    @Test
    void validateTitle_returns_true_with_correct_input() {
        Post fakePost = new Post("Test Title", "Test Content", "TestUser");

        Boolean result = fakeValidator.validateTitle(fakePost);
        assertEquals(true, result);
    }

    @Test
    void validateTitle_returns_false_with_short_title() {
        Post fakePost = new Post("...", "Test Content", "TestUser");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> fakeValidator.validateTitle(fakePost));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("The title must be at least 8 characters long", exception.getReason());
    }

    @Test
    void validateContent_returns_true_with_correct_input() {
        Post fakePost = new Post("Test Title", "Test Content", "TestUser");

        Boolean result = fakeValidator.validateContent(fakePost);
        assertEquals(true, result);
    }

    @Test
    void validateContent_returns_false_with_short_content() {
        Post fakePost = new Post("This is the Title", "...", "TestUser");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> fakeValidator.validateContent(fakePost));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("The content must be at least 8 characters long", exception.getReason());
    }

    @Test
    void postTheTitle_returns_true_with_correct_input() {
        Post fakePost = new Post("Test Title", "Test Content", "TestUser");

        Boolean result = fakeValidator.postThePost(fakePost);
        assertEquals(true, result);
    }

    @Test
    void editTheTitle_returns_true_with_correct_input() {
        Post fakePost = new Post("Test Title", "Test Content", "TestUser");

        Mockito.when(fakeRepository.getReferenceById(Mockito.any())).thenReturn(fakePost);

        Boolean result = fakeValidator.editThePost(fakePost, 0L);
        assertEquals(true, result);
    }
}