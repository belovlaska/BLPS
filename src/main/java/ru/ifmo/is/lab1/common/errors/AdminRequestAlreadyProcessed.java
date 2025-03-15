package ru.ifmo.is.lab1.common.errors;

public class AdminRequestAlreadyProcessed extends RuntimeException {
  public AdminRequestAlreadyProcessed() {
    super();
  }

  public AdminRequestAlreadyProcessed(String message) {
    super(message);
  }
}
