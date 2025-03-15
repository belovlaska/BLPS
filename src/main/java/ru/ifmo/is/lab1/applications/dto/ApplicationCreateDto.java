package ru.ifmo.is.lab1.applications.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import ru.ifmo.is.lab1.applications.Status;

@Data
public class ApplicationCreateDto {
  @NotNull
  @NotBlank
  private String name;

  @NotBlank
  private String description;

  @NotNull
  @NotBlank
  private String code;

  private Status status;

  @Min(0)
  private Integer cost;

}
