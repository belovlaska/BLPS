package ru.ifmo.is.lab1.applications.dto;

import lombok.Data;
import jakarta.validation.constraints.*;
import org.openapitools.jackson.nullable.JsonNullable;

@Data
public class ApplicationUpdateDto {
  @NotNull
  @NotBlank
  private JsonNullable<String> name;

  @NotNull
  private JsonNullable<Integer> monetizationId;

  @NotBlank
  private JsonNullable<String> description;

  @Min(0)
  private JsonNullable<Integer> cost;

}
