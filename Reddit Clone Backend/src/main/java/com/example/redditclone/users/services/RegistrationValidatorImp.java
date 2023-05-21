package com.example.redditclone.users.services;

import com.example.redditclone.dtos.PasswordTokenRequestDto;
import com.example.redditclone.security.TotpManager;
import com.example.redditclone.users.models.User;
import com.example.redditclone.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RegistrationValidatorImp implements RegistrationValidator {
    private UserRepository userRepository;
    private TotpManager totpManager;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationValidatorImp(UserRepository userRepository, TotpManager totpManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.totpManager = totpManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean validateUsername(User user) {
        User newUser = userRepository.findByUsername(user.getUsername());

        if (newUser != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }
        if (user.getUsername().length() < 4) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username must be at least 4 characters long");
        }
        return true;
    }

    @Override
    public boolean validatePassword(User user) {
        String newPassword = user.getPassword();
        if (newPassword.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is required");
        }
        if (newPassword.length() < 8) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must be at least 8 characters long");
        }
        return true;
    }

    @Override
    public boolean validateEmail(User user) {
        User newUser = userRepository.findByEmail(user.getEmail());
        if (newUser != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
        String newEmail = user.getEmail();
        if (newEmail.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required");
        }

        try {
            InternetAddress internetAddress = new InternetAddress(newEmail);
            internetAddress.validate();
        } catch (AddressException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email");
        }
        return true;
    }

    @Override
    public boolean registerUser(User user) {
        boolean isEmailValid = validateEmail(user);
        boolean isUsernameValid = validateUsername(user);
        boolean isPasswordValid = validatePassword(user);
        if (isEmailValid && isUsernameValid && isPasswordValid) {
            User newUser = new User(user.getUsername(), user.getEmail(), passwordEncoder.encode(user.getPassword()), user.isMfa());
            if (newUser.isMfa()) {
                newUser.setSecret(totpManager.generateSecret());
            }
            userRepository.save(newUser);
            return true;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown error");
        }
    }

    @Override
    public Long retrieveUserId(String username) {
        return userRepository.findByUsername(username).getId();
    }

    @Override
    public boolean setForgottenPasswordToken(String email) {
        if (email.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required");
        }

        User user = userRepository.findByEmail(email);

        if (user != null) {
            if (user.getVerifiedAt() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unverified email");
            } else {
                Integer randomNumber = (int) (Math.random() * 100000);
                user.setForgottenPasswordToken(randomNumber.toString());
                user.setForgottenPasswordExpiresAt(LocalDateTime.now().plusHours(1));
                userRepository.save(user);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User doesn't exist !");
        }
        return true;
    }

    @Override
    public boolean validateResetPasswordToken(String code, String email) {
        User user = userRepository.findByEmail(email);

        LocalDateTime now = LocalDateTime.now();
        boolean isAfter = now.isAfter(user.getForgottenPasswordExpiresAt());

        if (isAfter) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token expired");
        }

        if (code.equals(user.getForgottenPasswordToken())) {
            user.setResetPasswordToken(UUID.randomUUID().toString());
            userRepository.save(user);
            return true;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong token!");
        }
    }

    @Override
    public boolean resetPassword(PasswordTokenRequestDto passwordTokenRequestDto) {
        User user = userRepository.findByResetPasswordToken(passwordTokenRequestDto.getPasswordToken());
        boolean passwordsMatch = passwordTokenRequestDto.getNewPassword().equals(passwordTokenRequestDto.getNewPasswordCheck());

        if (passwordsMatch) {
            user.setPassword(passwordEncoder.encode(passwordTokenRequestDto.getNewPassword()));
            userRepository.save(user);
            return true;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords dont match!");
        }
    }
}
