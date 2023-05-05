package com.example.redditclone.users.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "user")
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "karma")
    private Long karma;

    @Column(name = "verified_at", nullable = true)
    private LocalDateTime verifiedAt;

    @Column(name = "verification_token", nullable = false, unique = true)
    private String verificationToken;

    @Column(name = "verification_token_expires_at", nullable = false)
    private LocalDateTime verificationTokenExpiresAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "mfa")
    private boolean mfa;

    @Column(name  = "secret")
    private String secret;

    public User() {
        this.verificationToken = "";
        this.verificationTokenExpiresAt = LocalDateTime.now().plusHours(1);
        this.createdAt = LocalDateTime.now();
    }

    public User(String username, String email, String password, Boolean mfa) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.verifiedAt = null;
        this.verificationToken = UUID.randomUUID().toString();
        this.verificationTokenExpiresAt = LocalDateTime.now().plusHours(1);
        this.createdAt = LocalDateTime.now();
        this.mfa = mfa;
        this.secret = "";
        this.karma = 0L;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(LocalDateTime verifiedAt) {
        this.verifiedAt = verifiedAt;
    }

    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    public LocalDateTime getVerificationTokenExpiresAt() {
        return verificationTokenExpiresAt;
    }

    public void setVerificationTokenExpiresAt(LocalDateTime verificationTokenExpiresAt) {
        this.verificationTokenExpiresAt = verificationTokenExpiresAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isMfa() {
        return mfa;
    }

    public void setMfa(boolean mfa) {
        this.mfa = mfa;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
