package com.example.redditclone.users.services;

import com.example.redditclone.users.models.User;
import org.springframework.stereotype.Service;

@Service
public interface RegistrationValidator {
    public boolean validateUsername(User user);

    public boolean validatePassword(User user);

    public boolean validateEmail(User user);

    public boolean registerUser(User user);

    public Long retrieveUserId(String username);
}
