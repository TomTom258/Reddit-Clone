package com.example.redditclone.dtos;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class LoginDtoTest {

    @Test
    public void testGetUsername() {
        String expectedUsername = "testUsername";
        LoginDto loginDto = new LoginDto(expectedUsername, "testPassword");

        String actualUsername = loginDto.getUsername();

        Assert.assertEquals(expectedUsername, actualUsername);
    }

    @Test
    public void testGetPassword() {
        String expectedPassword = "testPassword";
        LoginDto loginDto = new LoginDto("testUsername", expectedPassword);

        String actualPassword = loginDto.getPassword();

        Assert.assertEquals(expectedPassword, actualPassword);
    }
}
