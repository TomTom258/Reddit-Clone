package com.example.redditclone.posts.controllers;

import com.example.redditclone.dtos.*;
import com.example.redditclone.posts.models.Post;
import com.example.redditclone.posts.repositories.PostRepository;
import com.example.redditclone.posts.services.PostService;
import com.example.redditclone.posts.services.PostValidator;
import com.example.redditclone.users.models.User;
import com.example.redditclone.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private PostRepository postRepository;
    private UserRepository userRepository;

    @Autowired
    public PostController(PostValidator postValidator, PostService postService, PostRepository postRepository, UserRepository userRepository) {
        this.postValidator = postValidator;
        this.postService = postService;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("get")
    public ResponseEntity<List<Post>> getAllPosts() throws IOException {
        List<Post> storedPosts = postService.assignProfilePictures();
        storedPosts.sort(Comparator.comparing(Post::getReputation, Collections.reverseOrder()));

        return ResponseEntity.ok(storedPosts);
    }

    @PostMapping("add")
    public ResponseEntity<ResponseDto> post(@RequestBody PostDto postDto) {
        User owner = userRepository.getReferenceById(postDto.getUserId());

        Post newPost = new Post(
                postDto.getTitle(),
                postDto.getContent(),
                owner.getUsername());
        try {
            postValidator.postThePost(newPost);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponseDto(e.getReason()));
        }
        return new ResponseEntity<>(new OkResponseDto(201, "Post was successfully added"), HttpStatus.CREATED);
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<ResponseDto> editPost(@PathVariable long id, @RequestBody PostDto postDto) {
        User owner = userRepository.getReferenceById(postDto.getUserId());

        Post newPost = new Post(
                postDto.getTitle(),
                postDto.getContent(),
                owner.getUsername());
        try {
            postValidator.editThePost(newPost, id);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponseDto(e.getReason()));
        }
        return new ResponseEntity<>(new OkResponseDto(200, "Post was successfully changed"), HttpStatus.OK);
    }

    @PostMapping("upvote/{id}")
    public ResponseEntity<ResponseDto> upvotePost(@PathVariable long id) {
        try {
            postService.upvotePost(id);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponseDto(e.getReason()));
        }
        return new ResponseEntity<>(new OkResponseDto(201, "Post was successfully upvoted"), HttpStatus.CREATED);
    }

    @PostMapping("downvote/{id}")
    public ResponseEntity<ResponseDto> downvotePost(@PathVariable long id) {
        try {
            postService.downvotePost(id);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponseDto(e.getReason()));
        }
        return new ResponseEntity<>(new OkResponseDto(201, "Post was successfully downvoted"), HttpStatus.CREATED);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ResponseDto> deletePost(@PathVariable long id) {
        try {
            postService.deletePost(id);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponseDto(e.getReason()));
        }
        return new ResponseEntity<>(new OkResponseDto(204, "Post was successfully removed"), HttpStatus.NO_CONTENT);
    }
}
