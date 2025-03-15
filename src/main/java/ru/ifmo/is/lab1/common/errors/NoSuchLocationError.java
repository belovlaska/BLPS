package ru.ifmo.is.lab1.common.errors;

public class NoSuchLocationError extends RuntimeException {
  public NoSuchLocationError() {
    super();
  }

  public NoSuchLocationError(String message) {
    super(message);
  }
}
