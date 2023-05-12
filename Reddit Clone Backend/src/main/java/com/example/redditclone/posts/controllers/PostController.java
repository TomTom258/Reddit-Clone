package com.example.redditclone.posts.controllers;

import com.example.redditclone.dtos.*;
import com.example.redditclone.posts.models.Post;
import com.example.redditclone.posts.repositories.PostRepository;
import com.example.redditclone.posts.services.PostService;
import com.example.redditclone.posts.services.PostValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private PostValidator postValidator;
    private PostService postService;
    private PostRepository postRepository;
    @Autowired
    public PostController(PostValidator postValidator, PostService postService, PostRepository postRepository) {
        this.postValidator = postValidator;
        this.postService = postService;
        this.postRepository = postRepository;
    }
    @GetMapping("get")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> storedPosts = postRepository.findAll();
        storedPosts.sort(Comparator.comparing(Post::getReputation, Collections.reverseOrder()));

        return ResponseEntity.ok(storedPosts);
    }

    @PostMapping("add")
    public ResponseEntity<ResponseDto> post(@RequestBody PostDto postDto) {
        Post newPost = new Post(
                postDto.getTitle(),
                postDto.getContent(),
                "test");
        try {
            postValidator.postThePost(newPost);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponseDto(e.getReason()));
        }
        return new ResponseEntity<>(new OkResponseDto(201, "Post was successfully added"), HttpStatus.CREATED);
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<ResponseDto> editPost(@PathVariable long id, @RequestBody PostDto postDto) {
        Post newPost = new Post(
                postDto.getTitle(),
                postDto.getContent(),
                "test");
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
