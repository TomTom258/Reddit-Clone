package com.example.redditclone.dtos;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OkResponseDtoTest {

    @Test
    public void testGetStatus() {
        Integer expectedStatus = 200;
        OkResponseDto responseDto = new OkResponseDto(expectedStatus, "testMessage");

        Integer actualStatus = responseDto.getStatus();

        Assert.assertEquals(expectedStatus, actualStatus);
    }

    @Test
    public void testGetMessage() {
        String expectedMessage = "testMessage";
        OkResponseDto responseDto = new OkResponseDto(200, expectedMessage);

        String actualMessage = responseDto.getMessage();

        Assert.assertEquals(expectedMessage, actualMessage);
    }
}
