package com.example.redditclone.users.controllers;

import com.example.redditclone.dtos.*;
import com.example.redditclone.emailService.EmailSenderService;
import com.example.redditclone.security.JWTGenerator;
import com.example.redditclone.users.repositories.UserRepository;
import com.example.redditclone.users.services.RegistrationValidator;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.example.redditclone.users.models.User;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class AuthController {
    private UserRepository userRepository;
    private RegistrationValidator registrationValidator;
    private AuthenticationManager authenticationManager;
    private JWTGenerator jwtGenerator;
    private EmailSenderService emailSenderService;

    @Autowired
    public AuthController(UserRepository userRepository, RegistrationValidator registrationValidator, AuthenticationManager authenticationManager,
                          JWTGenerator jwtGenerator, EmailSenderService emailSenderService) {
        this.userRepository = userRepository;
        this.registrationValidator = registrationValidator;
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
        this.emailSenderService = emailSenderService;
    }
    @PostMapping("register")
    public ResponseEntity<ResponseDto> register(@RequestBody RegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>(new ErrorResponseDto("Username is already taken!"), HttpStatus.CONFLICT);
        }
        User newUser = new User(
                registerDto.getUsername(),
                registerDto.getEmail(),
                registerDto.getPassword(),
                registerDto.isMfa());
        try {
            registrationValidator.registerUser(newUser);
            User findUser = userRepository.findByUsername(newUser.getUsername());
            emailSenderService.sendHtmlEmail("verificationEmail", findUser);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponseDto(e.getReason()));
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(new OkResponseDto(201, "Registration successful."), HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);

        Long id = registrationValidator.retrieveUserId(loginDto.getUsername());
        User user = userRepository.findByUsername(loginDto.getUsername());

        return new ResponseEntity<>(new AuthResponseDto(token, loginDto.getUsername(), id, user.isMfa()), HttpStatus.OK);
    }

    @GetMapping("/email/verify")
    public ResponseEntity<ResponseDto> verifyEmailAddress(@RequestParam String token) {
        try {
            emailSenderService.verifyEmailAddress(token);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponseDto(e.getReason()));
        }
        return ResponseEntity.ok(new OkResponseDto(200, "Email successfully verified."));
    }

    @PostMapping("/email/verify/resend/{id}")
    public ResponseEntity<ResponseDto> resendVerificationEmail(@PathVariable Long id) {
        try {
            emailSenderService.resendVerificationEmail(id);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponseDto(e.getReason()));
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(new OkResponseDto(201, "Email verification message resent."));
    }
}
