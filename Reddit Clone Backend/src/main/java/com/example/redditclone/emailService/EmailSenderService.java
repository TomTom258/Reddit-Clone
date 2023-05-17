package com.example.redditclone.emailService;

import com.example.redditclone.users.models.User;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

@Service
public interface EmailSenderService {
    void sendHtmlEmail(String emailType, User user) throws MessagingException;
    boolean verifyEmailAddress(String token);
    boolean resendVerificationEmail(Long id) throws MessagingException;
}
