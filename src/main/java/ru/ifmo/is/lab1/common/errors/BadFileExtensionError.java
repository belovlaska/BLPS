package ru.ifmo.is.lab1.common.errors;

public class BadFileExtensionError extends RuntimeException {
  public BadFileExtensionError() {
    super();
  }

  public BadFileExtensionError(String message) {
    super(message);
  }
}
