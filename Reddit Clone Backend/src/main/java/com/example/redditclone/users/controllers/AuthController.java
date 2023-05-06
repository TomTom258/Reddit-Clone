package com.example.redditclone.users.controllers;

import com.example.redditclone.dtos.*;
import com.example.redditclone.users.models.Post;
import com.example.redditclone.users.repositories.PostRepository;
import com.example.redditclone.users.repositories.UserRepository;
import com.example.redditclone.users.services.PostValidator;
import com.example.redditclone.users.services.RegistrationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.redditclone.users.models.User;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class AuthController {
    private UserRepository userRepository;
    private RegistrationValidator registrationValidator;
    private PostValidator postValidator;

    @Autowired
    public AuthController(UserRepository userRepository, RegistrationValidator registrationValidator, PostValidator postValidator, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.registrationValidator = registrationValidator;
        this.postValidator = postValidator;
        this.postRepository = postRepository;
    }
    @PostMapping("register")
    public ResponseEntity<ResponseDto> register(@RequestBody RegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>(new ErrorResponseDto("Username is already taken"), HttpStatus.CONFLICT);
        }
        User newUser = new User(
                registerDto.getUsername(),
                registerDto.getEmail(),
                registerDto.getPassword(),
                registerDto.isMfa());
        try {
            registrationValidator.registerUser(newUser);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponseDto(e.getReason()));
        }
        return new ResponseEntity<>(new OkResponseDto(200, "Registration successful"), HttpStatus.OK);
    }

    @PostMapping("post")
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

    @PutMapping("posts/{id}")
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
