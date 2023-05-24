package com.example.redditclone.dtos;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ErrorResponseDtoTest {

    @Test
    public void testGetError() {
        String expectedError = "testError";
        ErrorResponseDto responseDto = new ErrorResponseDto(expectedError);

        String actualError = responseDto.getError();

        Assert.assertEquals(expectedError, actualError);
    }

    @Test
    public void testSetError() {
        String expectedError = "newError";
        ErrorResponseDto responseDto = new ErrorResponseDto("testError");

        responseDto.setError(expectedError);
        String actualError = responseDto.getError();

        Assert.assertEquals(expectedError, actualError);
    }
}
