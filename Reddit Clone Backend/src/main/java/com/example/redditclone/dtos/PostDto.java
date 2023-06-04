package com.example.redditclone.dtos;

public class PostDto {
    private String title;

    private String content;

    public PostDto() {
    };

    public PostDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
