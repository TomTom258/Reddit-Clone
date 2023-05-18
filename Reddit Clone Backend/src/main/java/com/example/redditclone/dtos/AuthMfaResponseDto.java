package com.example.redditclone.dtos;

public class AuthMfaResponseDto {
    private String accessToken;
    private boolean mfa;
    private String uriForImage;
    private Long id;

    public AuthMfaResponseDto(String accessToken, Long id, boolean mfa, String uriForImage) {
        this.accessToken = accessToken;
        this.mfa = mfa;
        this.uriForImage = uriForImage;
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public boolean isMfa() {
        return mfa;
    }

    public void setMfa(boolean mfa) {
        this.mfa = mfa;
    }

    public String getUriForImage() {
        return uriForImage;
    }

    public void setUriForImage(String uriForImage) {
        this.uriForImage = uriForImage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}