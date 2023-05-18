package com.example.redditclone.users.services;

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
}
