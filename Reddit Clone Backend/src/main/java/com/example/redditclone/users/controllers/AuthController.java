package com.example.redditclone.users.controllers;

import com.example.redditclone.dtos.*;
import com.example.redditclone.emailService.EmailSenderService;
import com.example.redditclone.security.JWTGenerator;
import com.example.redditclone.security.TotpManager;
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
    private TotpManager totpManager;

    @Autowired
    public AuthController(UserRepository userRepository, RegistrationValidator registrationValidator, AuthenticationManager authenticationManager,
                          JWTGenerator jwtGenerator, EmailSenderService emailSenderService, TotpManager totpManager) {
        this.userRepository = userRepository;
        this.registrationValidator = registrationValidator;
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
        this.emailSenderService = emailSenderService;
        this.totpManager = totpManager;
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

        if (user.isMfa()) {
            return new ResponseEntity<>(new AuthMfaResponseDto(token, id, user.isMfa(), totpManager.getUriForImage(user.getSecret())), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new AuthResponseDto(token, loginDto.getUsername(), id, user.isMfa()), HttpStatus.OK);
        }
    }

    @PostMapping("verify")
    public ResponseEntity verifyMfa(@RequestBody MfaDto mfaDto) {
        User user = userRepository.findById(mfaDto.getId()).get();

        if (!totpManager.verifyCode(mfaDto.getCode(), user.getSecret())) {
            return new ResponseEntity<>(new ErrorResponseDto("Code is incorrect"), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(new MfaResponseDto(user.getId(), user.getUsername(), 200, "Verification successful"), HttpStatus.OK);
        }
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

    @PutMapping("/email/verify/resend/{id}")
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

    @PostMapping("/send-reset-password")
    public ResponseEntity sendPasswordRecoveryEmail(@RequestBody EmailRequestDto emailDto) {
        try {
            registrationValidator.setForgottenPasswordToken(emailDto.getEmail());
            emailSenderService.resetPasswordEmail(emailDto.getEmail());
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponseDto(e.getReason()));
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(new OkResponseDto(201, "Reset password email has been sent."));
    }

    @PostMapping("/reset-password-code")
    public ResponseEntity checkPasswordToken(@RequestBody CodeRequestDto codeDto) {
        try {
            registrationValidator.validateResetPasswordToken(codeDto.getCode(), codeDto.getEmail());
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponseDto(e.getReason()));
        }
        User user = userRepository.findByEmail(codeDto.getEmail());
        return ResponseEntity.ok(new PasswordTokenResponseDto(user.getResetPasswordToken(), 200, "Code is correct."));
    }

    @PostMapping("/reset-password")
    public ResponseEntity resetPassword(@RequestBody PasswordTokenRequestDto passwordTokenRequestDto) {
        try {
            registrationValidator.resetPassword(passwordTokenRequestDto);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponseDto(e.getReason()));
        }
        return ResponseEntity.ok(new OkResponseDto(201, "Password successfully changed."));
    }
}