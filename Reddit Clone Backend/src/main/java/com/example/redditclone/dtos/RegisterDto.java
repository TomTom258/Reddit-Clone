package com.example.redditclone.dtos;

public class RegisterDto {
    private String username;
    private String email;
    private String password;
    private boolean mfa;

    public RegisterDto() {
    }

    public RegisterDto(String username, String email, String password, boolean mfa) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.mfa = mfa;
    }

    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public boolean isMfa() {
        return mfa;
    }
}
