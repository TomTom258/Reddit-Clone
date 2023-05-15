package com.example.redditclone.dtos;

public class AuthResponseDto{
    private String accessToken;
    private String username;
    private Long id;
    private boolean mfa;

    public AuthResponseDto(String accessToken, String username, Long id, boolean mfa) {
        this.accessToken = accessToken;
        this.username = username;
        this.id = id;
        this.mfa = mfa;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isMfa() {
        return mfa;
    }

    public void setMfa(boolean mfa) {
        this.mfa = mfa;
    }
}
