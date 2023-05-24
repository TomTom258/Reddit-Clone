package com.example.redditclone.dtos;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CodeRequestDtoTest {

    @Test
    public void testGetCode() {
        String expectedCode = "testCode";
        CodeRequestDto requestDto = new CodeRequestDto(expectedCode, "testEmail");

        String actualCode = requestDto.getCode();

        Assert.assertEquals(expectedCode, actualCode);
    }

    @Test
    public void testGetEmail() {
        String expectedEmail = "testEmail";
        CodeRequestDto requestDto = new CodeRequestDto("testCode", expectedEmail);

        String actualEmail = requestDto.getEmail();

        Assert.assertEquals(expectedEmail, actualEmail);
    }
}
