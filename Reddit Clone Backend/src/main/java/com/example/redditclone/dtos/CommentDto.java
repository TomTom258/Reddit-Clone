package com.example.redditclone.dtos;

public class CommentDto {
    private String content;

    public CommentDto() {
    };

    public CommentDto(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
