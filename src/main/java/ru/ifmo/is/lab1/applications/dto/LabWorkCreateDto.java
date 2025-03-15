package ru.ifmo.is.lab1.applications.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import ru.ifmo.is.lab1.applications.Cost;

@Data
public class LabWorkCreateDto {
  @NotNull
  @NotBlank
  private String name;

  @NotNull
  private Integer coordinatesId;

  @NotBlank
  private String description;

  private Integer disciplineId;

  private Integer authorId;

  @NotNull
  private Cost difficulty;

  @Min(0)
  private Integer minimalPoint;

}
