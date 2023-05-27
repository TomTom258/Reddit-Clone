package com.example.redditclone.dtos;

public class PostDto {
    private String title;

    private String content;

    private long userId;

    public PostDto() {
    };

    public PostDto(String title, String content, long userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public long getUserId() {
        return userId;
    }
}
