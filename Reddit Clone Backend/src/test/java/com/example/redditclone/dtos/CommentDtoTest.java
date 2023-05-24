package com.example.redditclone.dtos;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommentDtoTest {

    @Test
    public void testGetContent() {
        String expectedContent = "testContent";
        CommentDto commentDto = new CommentDto(expectedContent);

        String actualContent = commentDto.getContent();

        Assert.assertEquals(expectedContent, actualContent);
    }
}
