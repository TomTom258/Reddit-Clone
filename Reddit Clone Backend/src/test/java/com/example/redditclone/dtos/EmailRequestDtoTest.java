package com.example.redditclone.dtos;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmailRequestDtoTest {

    @Test
    public void testGetEmail() {
        String expectedEmail = "test@example.com";
        EmailRequestDto requestDto = new EmailRequestDto(expectedEmail);

        String actualEmail = requestDto.getEmail();

        Assert.assertEquals(expectedEmail, actualEmail);
    }
}
