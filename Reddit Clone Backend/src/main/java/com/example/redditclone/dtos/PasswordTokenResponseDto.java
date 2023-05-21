package com.example.redditclone.dtos;

public class PasswordTokenResponseDto {
    private String passwordToken;
    private Integer status;
    private String message;
    public PasswordTokenResponseDto(String passwordToken, Integer status, String message) {
        this.passwordToken = passwordToken;
        this.status = status;
        this.message = message;
    }

    public String getPasswordToken() {
        return passwordToken;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
