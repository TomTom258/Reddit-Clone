package com.example.redditclone.emailService;

import com.example.redditclone.users.models.User;
import com.example.redditclone.users.repositories.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class EmailSenderServiceImp implements EmailSenderService {

    private JavaMailSender emailSender;
    private TemplateEngine templateEngine;
    private UserRepository userRepository;

    @Autowired
    public EmailSenderServiceImp(JavaMailSender emailSender, TemplateEngine templateEngine, UserRepository userRepository) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
        this.userRepository = userRepository;
    }

    public void sendHtmlEmail(String emailType, User user) throws MessagingException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime emailTokenExpirationTime = user.getVerificationTokenExpiresAt();

        Context ctx = new Context();
        ctx.setVariable("name", user.getUsername());
        ctx.setVariable("expirationEmailTime", emailTokenExpirationTime.format(formatter));
        ctx.setVariable("emailToken", user.getVerificationToken());

        jakarta.mail.internet.MimeMessage mimeMessage= this.emailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        message.setFrom("noreply@redditclone.com");
        message.setTo(user.getEmail());

        if (emailType.equals("verificationEmail")) {
            message.setSubject("Thank you for registering. Please verify your e-mail address");
            String htmlContent = this.templateEngine.process("VerificationEmailTemplate.html", ctx);
            message.setText(htmlContent, true);
        }

        if (emailType.equals("resendVerificationEmail")) {
            message.setSubject("Resend of the verification e-mail address");
            String htmlContent = this.templateEngine.process("ResendEmailTemplate.html", ctx);
            message.setText(htmlContent, true);
        }

        if (emailType.equals("resendPasswordEmail")) {
            message.setSubject("Change your password");
            ctx.setVariable("expirationPasswordTime", user.getForgottenPasswordExpiresAt().format(formatter));
            ctx.setVariable("passwordToken", user.getForgottenPasswordToken());
            String htmlContent = this.templateEngine.process("ResetPasswordEmailTemplate.html", ctx);
            message.setText(htmlContent, true);
        }
        this.emailSender.send(mimeMessage);
    }

    @Override
    public boolean verifyEmailAddress(String token) {
        User user = userRepository.findByVerificationToken(token);
        if (user == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid token"
            );
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tokenExpiresAt = user.getVerificationTokenExpiresAt();

        if (user.getVerifiedAt() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Email already verified"
            );
        }

        if (now.isAfter(tokenExpiresAt)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Token expired"
            );
        }
        user.setVerifiedAt(LocalDateTime.now());
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean resendVerificationEmail(Long id) throws MessagingException {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Invalid input!"));

        if (user.getVerifiedAt() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already verified");
        }

        user.setVerificationToken(UUID.randomUUID().toString());
        user.setVerificationTokenExpiresAt(LocalDateTime.now().plusHours(1));
        userRepository.save(user);
        sendHtmlEmail("resendVerificationEmail", user);
        return true;
    }

    @Override
    public boolean resetPasswordEmail(String email) throws MessagingException {
        User user = userRepository.findByEmail(email);
        sendHtmlEmail("resendPasswordEmail", user);
        return true;
    }
}