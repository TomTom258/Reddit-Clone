package com.example.redditclone.dtos;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthMfaResponseDtoTest {

    @Test
    public void testGetAccessToken() {
        String expectedToken = "testToken";
        AuthMfaResponseDto responseDto = new AuthMfaResponseDto(expectedToken, 1L, true, "testUri");

        String actualToken = responseDto.getAccessToken();

        Assert.assertEquals(expectedToken, actualToken);
    }

    @Test
    public void testSetAccessToken() {
        String expectedToken = "newToken";
        AuthMfaResponseDto responseDto = new AuthMfaResponseDto("testToken", 1L, true, "testUri");

        responseDto.setAccessToken(expectedToken);
        String actualToken = responseDto.getAccessToken();

        Assert.assertEquals(expectedToken, actualToken);
    }

    @Test
    public void testIsMfa() {
        boolean expectedMfa = true;
        AuthMfaResponseDto responseDto = new AuthMfaResponseDto("testToken", 1L, expectedMfa, "testUri");

        boolean actualMfa = responseDto.isMfa();

        Assert.assertEquals(expectedMfa, actualMfa);
    }

    @Test
    public void testSetMfa() {
        boolean expectedMfa = false;
        AuthMfaResponseDto responseDto = new AuthMfaResponseDto("testToken", 1L, true, "testUri");

        responseDto.setMfa(expectedMfa);
        boolean actualMfa = responseDto.isMfa();

        Assert.assertEquals(expectedMfa, actualMfa);
    }

    @Test
    public void testGetUriForImage() {
        String expectedUri = "testUri";
        AuthMfaResponseDto responseDto = new AuthMfaResponseDto("testToken", 1L, true, expectedUri);

        String actualUri = responseDto.getUriForImage();

        Assert.assertEquals(expectedUri, actualUri);
    }

    @Test
    public void testSetUriForImage() {
        String expectedUri = "newUri";
        AuthMfaResponseDto responseDto = new AuthMfaResponseDto("testToken", 1L, true, "testUri");

        responseDto.setUriForImage(expectedUri);
        String actualUri = responseDto.getUriForImage();

        Assert.assertEquals(expectedUri, actualUri);
    }

    @Test
    public void testGetId() {
        Long expectedId = 1L;
        AuthMfaResponseDto responseDto = new AuthMfaResponseDto("testToken", expectedId, true, "testUri");

        Long actualId = responseDto.getId();

        Assert.assertEquals(expectedId, actualId);
    }

    @Test
    public void testSetId() {
        Long expectedId = 2L;
        AuthMfaResponseDto responseDto = new AuthMfaResponseDto("testToken", 1L, true, "testUri");

        responseDto.setId(expectedId);
        Long actualId = responseDto.getId();

        Assert.assertEquals(expectedId, actualId);
    }
}
