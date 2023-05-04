package com.example.redditclone.users.controllers;

import com.example.redditclone.dtos.OkResponseDto;
import com.example.redditclone.dtos.RegisterDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.redditclone.users.models.User;

@RestController
@RequestMapping("/api")
public class AuthController {
    @PostMapping("register")
    public ResponseEntity register(@RequestBody RegisterDto registerDto) {
        User user = new User(
                registerDto.getUsername(),
                registerDto.getEmail(),
                registerDto.getPassword(),
                registerDto.isMfa());
        return new ResponseEntity<>(new OkResponseDto(200, "Registration successful"), HttpStatus.OK);
    }
}
