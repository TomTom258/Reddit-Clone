package com.example.redditclone.dtos;

public class MfaResponseDto extends ResponseDto {

    private Integer status;
    private String message;
    private Long id;
    private String username;

    public MfaResponseDto(Long id, String username, Integer status, String message) {
        this.status = status;
        this.message = message;
        this.id = id;
        this.username = username;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}