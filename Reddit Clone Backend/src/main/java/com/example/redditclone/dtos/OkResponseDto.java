package com.example.redditclone.dtos;

public class OkResponseDto extends ResponseDto{
    private Integer status;
    private String message;

    public OkResponseDto() {
    }

    public OkResponseDto(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
