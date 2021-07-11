package com.pismo.project.config.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CannotSaveException extends RuntimeException {
   private final String message;
}
