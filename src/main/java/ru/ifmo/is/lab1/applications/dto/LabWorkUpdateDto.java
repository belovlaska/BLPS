package ru.ifmo.is.lab1.applications.dto;

import lombok.Data;
import jakarta.validation.constraints.*;
import org.openapitools.jackson.nullable.JsonNullable;

@Data
public class LabWorkUpdateDto {
  @NotNull
  @NotBlank
  private JsonNullable<String> name;

  @NotNull
  private JsonNullable<Integer> coordinatesId;

  @NotBlank
  private JsonNullable<String> description;

  private JsonNullable<Integer> authorId;

  @NotNull
  private JsonNullable<Cost> difficulty;

  private JsonNullable<Integer> disciplineId;

  @Min(0)
  private JsonNullable<Integer> minimalPoint;

}
