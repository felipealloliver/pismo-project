package com.pismo.project.config.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionDto {
    private String message;

    public static ExceptionDto of(String message) {
        return new ExceptionDto(message);
    }
}
