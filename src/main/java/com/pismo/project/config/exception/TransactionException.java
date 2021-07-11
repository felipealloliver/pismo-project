package com.pismo.project.config.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TransactionException extends RuntimeException {
    private final List<ExceptionDto> errors;
}
