package com.example.redditclone.dtos;

public class PasswordTokenRequestDto {
    private String passwordToken;
    private String newPassword;
    private String newPasswordCheck;

    public PasswordTokenRequestDto() {
    }

    public String getPasswordToken() {
        return passwordToken;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getNewPasswordCheck() {
        return newPasswordCheck;
    }

    public void setPasswordToken(String passwordToken) {
        this.passwordToken = passwordToken;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setNewPasswordCheck(String newPasswordCheck) {
        this.newPasswordCheck = newPasswordCheck;
    }
}
