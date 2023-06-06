package com.example.redditclone.users.services;

import com.example.redditclone.users.models.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public void increaseFailedAttempts(User user);
    public void resetFailedAttempts(String email);
    public void lock(User user);
    public boolean unlockWhenTimeExpired(User user);
}
