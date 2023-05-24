package com.example.redditclone.dtos;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordTokenResponseDtoTest {

    @Test
    public void testGetPasswordToken() {
        String expectedPasswordToken = "testToken";
        PasswordTokenResponseDto responseDto = new PasswordTokenResponseDto(expectedPasswordToken, 200, "testMessage");

        String actualPasswordToken = responseDto.getPasswordToken();

        Assert.assertEquals(expectedPasswordToken, actualPasswordToken);
    }

    @Test
    public void testGetStatus() {
        Integer expectedStatus = 200;
        PasswordTokenResponseDto responseDto = new PasswordTokenResponseDto("testToken", expectedStatus, "testMessage");

        Integer actualStatus = responseDto.getStatus();

        Assert.assertEquals(expectedStatus, actualStatus);
    }

    @Test
    public void testGetMessage() {
        String expectedMessage = "testMessage";
        PasswordTokenResponseDto responseDto = new PasswordTokenResponseDto("testToken", 200, expectedMessage);

        String actualMessage = responseDto.getMessage();

        Assert.assertEquals(expectedMessage, actualMessage);
    }
}
