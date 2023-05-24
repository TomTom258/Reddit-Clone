package com.example.redditclone.dtos;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterDtoTest {

    @Test
    public void testGetUsername() {
        String expectedUsername = "testUser";
        RegisterDto registerDto = new RegisterDto(expectedUsername, "test@example.com", "testPassword", true);

        String actualUsername = registerDto.getUsername();

        Assert.assertEquals(expectedUsername, actualUsername);
    }

    @Test
    public void testGetEmail() {
        String expectedEmail = "test@example.com";
        RegisterDto registerDto = new RegisterDto("testUser", expectedEmail, "testPassword", true);

        String actualEmail = registerDto.getEmail();

        Assert.assertEquals(expectedEmail, actualEmail);
    }

    @Test
    public void testGetPassword() {
        String expectedPassword = "testPassword";
        RegisterDto registerDto = new RegisterDto("testUser", "test@example.com", expectedPassword, true);

        String actualPassword = registerDto.getPassword();

        Assert.assertEquals(expectedPassword, actualPassword);
    }

    @Test
    public void testIsMfa() {
        boolean expectedMfa = true;
        RegisterDto registerDto = new RegisterDto("testUser", "test@example.com", "testPassword", expectedMfa);

        boolean actualMfa = registerDto.isMfa();

        Assert.assertEquals(expectedMfa, actualMfa);
    }
}
