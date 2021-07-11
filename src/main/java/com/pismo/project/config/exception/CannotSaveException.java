package com.pismo.project.config.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CannotSaveException extends RuntimeException {
   private final String message;
}
