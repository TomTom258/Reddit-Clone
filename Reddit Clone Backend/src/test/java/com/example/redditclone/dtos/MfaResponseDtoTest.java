package com.example.redditclone.dtos;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MfaResponseDtoTest {

    @Test
    public void testGetStatus() {
        Integer expectedStatus = 200;
        MfaResponseDto responseDto = new MfaResponseDto(1L, "testUsername", expectedStatus, "testMessage");

        Integer actualStatus = responseDto.getStatus();

        Assert.assertEquals(expectedStatus, actualStatus);
    }

    @Test
    public void testGetMessage() {
        String expectedMessage = "testMessage";
        MfaResponseDto responseDto = new MfaResponseDto(1L, "testUsername", 200, expectedMessage);

        String actualMessage = responseDto.getMessage();

        Assert.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testGetId() {
        Long expectedId = 1L;
        MfaResponseDto responseDto = new MfaResponseDto(expectedId, "testUsername", 200, "testMessage");

        Long actualId = responseDto.getId();

        Assert.assertEquals(expectedId, actualId);
    }

    @Test
    public void testGetUsername() {
        String expectedUsername = "testUsername";
        MfaResponseDto responseDto = new MfaResponseDto(1L, expectedUsername, 200, "testMessage");

        String actualUsername = responseDto.getUsername();

        Assert.assertEquals(expectedUsername, actualUsername);
    }
}
