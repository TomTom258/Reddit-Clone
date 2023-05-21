package com.example.redditclone.dtos;

public class EmailRequestDto {
    private String email;

    public EmailRequestDto() {
    }

    public EmailRequestDto(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
