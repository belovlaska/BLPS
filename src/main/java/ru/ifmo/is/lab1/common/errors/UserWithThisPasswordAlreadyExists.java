package ru.ifmo.is.lab1.common.errors;

public class UserWithThisPasswordAlreadyExists extends RuntimeException {
  public UserWithThisPasswordAlreadyExists() {
    super();
  }

  public UserWithThisPasswordAlreadyExists(String message) {
    super(message);
  }
}
