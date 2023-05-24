package com.example.redditclone.dtos;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class PasswordTokenRequestDtoTest {

    @Test
    public void testGetPasswordToken() {
        String expectedPasswordToken = "testToken";
        PasswordTokenRequestDto requestDto = new PasswordTokenRequestDto();

        requestDto.setPasswordToken(expectedPasswordToken);
        String actualPasswordToken = requestDto.getPasswordToken();

        Assert.assertEquals(expectedPasswordToken, actualPasswordToken);
    }

    @Test
    public void testGetNewPassword() {
        String expectedNewPassword = "testPassword";
        PasswordTokenRequestDto requestDto = new PasswordTokenRequestDto();

        requestDto.setNewPassword(expectedNewPassword);
        String actualNewPassword = requestDto.getNewPassword();

        Assert.assertEquals(expectedNewPassword, actualNewPassword);
    }

    @Test
    public void testGetNewPasswordCheck() {
        String expectedNewPasswordCheck = "testPasswordCheck";
        PasswordTokenRequestDto requestDto = new PasswordTokenRequestDto();

        requestDto.setNewPasswordCheck(expectedNewPasswordCheck);
        String actualNewPasswordCheck = requestDto.getNewPasswordCheck();

        Assert.assertEquals(expectedNewPasswordCheck, actualNewPasswordCheck);
    }
}


