package com.example.redditclone.dtos;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthResponseDtoTest {

    @Test
    public void testGetAccessToken() {
        String expectedToken = "testToken";
        AuthResponseDto responseDto = new AuthResponseDto(expectedToken, "testUsername", 1L, true);

        String actualToken = responseDto.getAccessToken();

        Assert.assertEquals(expectedToken, actualToken);
    }

    @Test
    public void testSetAccessToken() {
        String expectedToken = "newToken";
        AuthResponseDto responseDto = new AuthResponseDto("testToken", "testUsername", 1L, true);

        responseDto.setAccessToken(expectedToken);
        String actualToken = responseDto.getAccessToken();

        Assert.assertEquals(expectedToken, actualToken);
    }

    @Test
    public void testGetUsername() {
        String expectedUsername = "testUsername";
        AuthResponseDto responseDto = new AuthResponseDto("testToken", expectedUsername, 1L, true);

        String actualUsername = responseDto.getUsername();

        Assert.assertEquals(expectedUsername, actualUsername);
    }

    @Test
    public void testSetUsername() {
        String expectedUsername = "newUsername";
        AuthResponseDto responseDto = new AuthResponseDto("testToken", "testUsername", 1L, true);

        responseDto.setUsername(expectedUsername);
        String actualUsername = responseDto.getUsername();

        Assert.assertEquals(expectedUsername, actualUsername);
    }

    @Test
    public void testGetId() {
        Long expectedId = 1L;
        AuthResponseDto responseDto = new AuthResponseDto("testToken", "testUsername", expectedId, true);

        Long actualId = responseDto.getId();

        Assert.assertEquals(expectedId, actualId);
    }

    @Test
    public void testSetId() {
        Long expectedId = 2L;
        AuthResponseDto responseDto = new AuthResponseDto("testToken", "testUsername", 1L, true);

        responseDto.setId(expectedId);
        Long actualId = responseDto.getId();

        Assert.assertEquals(expectedId, actualId);
    }

    @Test
    public void testIsMfa() {
        boolean expectedMfa = true;
        AuthResponseDto responseDto = new AuthResponseDto("testToken", "testUsername", 1L, expectedMfa);

        boolean actualMfa = responseDto.isMfa();

        Assert.assertEquals(expectedMfa, actualMfa);
    }

    @Test
    public void testSetMfa() {
        boolean expectedMfa = false;
        AuthResponseDto responseDto = new AuthResponseDto("testToken", "testUsername", 1L, true);

        responseDto.setMfa(expectedMfa);
        boolean actualMfa = responseDto.isMfa();

        Assert.assertEquals(expectedMfa, actualMfa);
    }
}
