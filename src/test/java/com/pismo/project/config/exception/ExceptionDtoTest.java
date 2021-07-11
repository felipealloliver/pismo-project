package com.pismo.project.config.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ExceptionDtoTest {
    @Test
    @DisplayName("given a empty string should return a valid ExceptionDto with empty message")
    public void test01() {
        String emptyString = "";
        ExceptionDto exceptionDto = new ExceptionDto(emptyString);
        assertEquals(exceptionDto.getMessage(), emptyString);
    }

    @Test
    @DisplayName("given a null string should return a valid ExceptionDto with null message")
    public void test02() {
        String nullString = null;
        ExceptionDto exceptionDto = new ExceptionDto(nullString);
        assertNull(exceptionDto.getMessage());
    }

    @Test
    @DisplayName("given a string should return a valid ExceptionDto with the correspondenting message")
    public void test03() {
        String validString = "Error message";
        ExceptionDto exceptionDto = new ExceptionDto(validString);
        assertEquals(exceptionDto.getMessage(), validString);
    }
}