package com.example.redditclone.users.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
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

    @Column(name = "verifiedAt", nullable = true)
    private LocalDateTime verifiedAt;

    @Column(name = "verificationToken", nullable = false, unique = true)
    private String verificationToken;

    @Column(name = "verificationTokenExpiresAt", nullable = false)
    private LocalDateTime verificationTokenExpiresAt;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "mfa")
    private boolean mfa;

    @Column(name  = "secret")
    private String secret;

    @Column(name = "profilePictureFilePath")
    private String profilePictureFilePath;

    @Column(name = "forgottenPasswordToken", nullable = true, unique = true)
    private String forgottenPasswordToken;

    @Column(name = "forgottenPasswordTokenExpiresAt", nullable = true)
    private LocalDateTime forgottenPasswordExpiresAt;

    @Column(name = "resetPasswordToken", nullable = true)
    private String resetPasswordToken;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_privileges", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "privileges_id")})
    @Column(name = "privileges", nullable = true)
    private Collection<Privilege> privileges;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "roles_id")})
    @Column(name = "roles", nullable = true)
    private Collection<Role> roles;

    public User() {
        this.verificationToken = UUID.randomUUID().toString();
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
        this.profilePictureFilePath = "";
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

    public Long getKarma() {
        return karma;
    }

    public void setKarma(Long karma) {
        this.karma = karma;
    }

    public Collection<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Collection<Privilege> privileges) {
        this.privileges = privileges;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public String getProfilePictureFilePath() {
        return profilePictureFilePath;
    }

    public void setProfilePictureFilePath(String profilePictureFilePath) {
        this.profilePictureFilePath = profilePictureFilePath;
    }

    public String getForgottenPasswordToken() {
        return forgottenPasswordToken;
    }

    public void setForgottenPasswordToken(String forgottenPasswordToken) {
        this.forgottenPasswordToken = forgottenPasswordToken;
    }

    public LocalDateTime getForgottenPasswordExpiresAt() {
        return forgottenPasswordExpiresAt;
    }

    public void setForgottenPasswordExpiresAt(LocalDateTime forgottenPasswordExpiresAt) {
        this.forgottenPasswordExpiresAt = forgottenPasswordExpiresAt;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }
}
