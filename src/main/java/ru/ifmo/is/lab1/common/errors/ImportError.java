package ru.ifmo.is.lab1.common.errors;

public class ImportError extends RuntimeException {
  public ImportError() {
    super();
  }

  public ImportError(String message) {
    super(message);
  }
}
