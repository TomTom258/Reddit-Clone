package com.example.redditclone.posts.controllers;

import com.example.redditclone.dtos.*;
import com.example.redditclone.posts.models.Post;
import com.example.redditclone.posts.services.PostService;
import com.example.redditclone.posts.services.PostValidator;
import com.example.redditclone.users.models.User;
import com.example.redditclone.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private PostValidator postValidator;
    private PostService postService;
    private UserRepository userRepository;

    @Autowired
    public PostController(PostValidator postValidator, PostService postService, UserRepository userRepository) {
        this.postValidator = postValidator;
        this.postService = postService;
        this.userRepository = userRepository;
    }

    private String retrieveUsernameFromToken() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }

    @GetMapping("get")
    public ResponseEntity<List<Post>> getAllPosts() throws IOException {
        String username = retrieveUsernameFromToken();

        List<Post> storedPosts = postService.mapProfilePicturesAndReactions(username);
        storedPosts.sort(Comparator.comparing(Post::getReputation, Collections.reverseOrder()));

        return ResponseEntity.ok(storedPosts);
    }

    @PostMapping("add")
    public ResponseEntity<ResponseDto> post(@RequestBody PostDto postDto) {
        String username = retrieveUsernameFromToken();

        Post newPost = new Post(
                postDto.getTitle(),
                postDto.getContent(),
                username);
        try {
            postValidator.postThePost(newPost);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponseDto(e.getReason()));
        }
        return new ResponseEntity<>(new OkResponseDto(201, "Post was successfully added"), HttpStatus.CREATED);
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<ResponseDto> editPost(@PathVariable long id, @RequestBody PostDto postDto) {
        String username = retrieveUsernameFromToken();

        Post newPost = new Post(
                postDto.getTitle(),
                postDto.getContent(),
                username);
        try {
            postValidator.editThePost(newPost, id, username);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponseDto(e.getReason()));
        }
        return new ResponseEntity<>(new OkResponseDto(200, "Post was successfully changed"), HttpStatus.OK);
    }

    @PostMapping("upvote/{id}")
    public ResponseEntity<ResponseDto> upvotePost(@PathVariable long id) {
        try {
            String username = retrieveUsernameFromToken();
            postService.upvotePost(id, username);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponseDto(e.getReason()));
        }
        return new ResponseEntity<>(new OkResponseDto(201, "Post was successfully upvoted"), HttpStatus.CREATED);
    }

    @PostMapping("downvote/{id}")
    public ResponseEntity<ResponseDto> downvotePost(@PathVariable long id) {
        try {
            String username = retrieveUsernameFromToken();
            postService.downvotePost(id, username);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponseDto(e.getReason()));
        }
        return new ResponseEntity<>(new OkResponseDto(201, "Post was successfully downvoted"), HttpStatus.CREATED);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ResponseDto> deletePost(@PathVariable long id) {
        try {
            String username = retrieveUsernameFromToken();
            postService.deletePost(id, username);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponseDto(e.getReason()));
        }
        return new ResponseEntity<>(new OkResponseDto(204, "Post was successfully removed"), HttpStatus.NO_CONTENT);
    }
}
