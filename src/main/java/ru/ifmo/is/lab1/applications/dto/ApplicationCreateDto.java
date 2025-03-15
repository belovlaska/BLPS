package ru.ifmo.is.lab1.applications.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

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

  @Min(0)
  private Integer cost;

}
