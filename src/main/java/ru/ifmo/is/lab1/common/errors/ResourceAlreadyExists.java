package ru.ifmo.is.lab1.common.errors;

public class ResourceAlreadyExists extends RuntimeException {
  public ResourceAlreadyExists() {
    super();
  }

  public ResourceAlreadyExists(String message) {
    super(message);
  }
}
