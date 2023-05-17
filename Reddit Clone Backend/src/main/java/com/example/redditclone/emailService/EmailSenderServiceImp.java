package com.example.redditclone.emailService;

import com.example.redditclone.users.models.User;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EmailSenderServiceImp implements EmailSenderService {

    private JavaMailSender emailSender;
    private TemplateEngine templateEngine;

    @Autowired
    public EmailSenderServiceImp(JavaMailSender emailSender, TemplateEngine templateEngine) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }

    public void sendHtmlEmail(String emailType, User user) throws MessagingException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = user.getVerificationTokenExpiresAt();

        Context ctx = new Context();
        ctx.setVariable("name", user.getUsername());
        ctx.setVariable("expirationTime", dateTime.format(formatter));
        ctx.setVariable("verificationToken", user.getVerificationToken());

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
        this.emailSender.send(mimeMessage);
    }
}