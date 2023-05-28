package com.example.redditclone.dtos;

public class CommentDto {
    private String content;
    private long userId;

    public CommentDto() {
    };

    public CommentDto(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public long getUserId() {
        return userId;
    }
}
