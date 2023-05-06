package com.example.redditclone.users.controllers;

import com.example.redditclone.dtos.ErrorResponseDto;
import com.example.redditclone.dtos.OkResponseDto;
import com.example.redditclone.dtos.PostDto;
import com.example.redditclone.dtos.ResponseDto;
import com.example.redditclone.users.models.Post;
import com.example.redditclone.users.services.PostValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/posts")
public class PostController {

    private PostValidator postValidator;
    @Autowired
    public PostController(PostValidator postValidator) {
        this.postValidator = postValidator;
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
}
