package ru.ifmo.is.lab1.common.errors;

public class FileIsEmptyError extends RuntimeException {
  public FileIsEmptyError() {
    super();
  }

  public FileIsEmptyError(String message) {
    super(message);
  }
}
