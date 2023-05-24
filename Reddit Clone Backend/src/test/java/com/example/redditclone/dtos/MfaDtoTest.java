package com.example.redditclone.dtos;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MfaDtoTest {

    @Test
    public void testGetId() {
        Long expectedId = 1L;
        MfaDto mfaDto = new MfaDto(expectedId, "testCode");

        Long actualId = mfaDto.getId();

        Assert.assertEquals(expectedId, actualId);
    }

    @Test
    public void testSetId() {
        Long expectedId = 2L;
        MfaDto mfaDto = new MfaDto(1L, "testCode");

        mfaDto.setId(expectedId);
        Long actualId = mfaDto.getId();

        Assert.assertEquals(expectedId, actualId);
    }

    @Test
    public void testGetCode() {
        String expectedCode = "testCode";
        MfaDto mfaDto = new MfaDto(1L, expectedCode);

        String actualCode = mfaDto.getCode();

        Assert.assertEquals(expectedCode, actualCode);
    }

    @Test
    public void testSetCode() {
        String expectedCode = "newCode";
        MfaDto mfaDto = new MfaDto(1L, "testCode");

        mfaDto.setCode(expectedCode);
        String actualCode = mfaDto.getCode();

        Assert.assertEquals(expectedCode, actualCode);
    }
}
