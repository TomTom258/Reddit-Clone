package com.example.redditclone.dtos;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PostDtoTest {

    @Test
    public void testGetTitle() {
        String expectedTitle = "Test Title";
        PostDto postDto = new PostDto(expectedTitle, "Test Content", 1L);

        String actualTitle = postDto.getTitle();

        Assert.assertEquals(expectedTitle, actualTitle);
    }

    @Test
    public void testGetContent() {
        String expectedContent = "Test Content";
        PostDto postDto = new PostDto("Test Title", expectedContent, 1L);

        String actualContent = postDto.getContent();

        Assert.assertEquals(expectedContent, actualContent);
    }
}
