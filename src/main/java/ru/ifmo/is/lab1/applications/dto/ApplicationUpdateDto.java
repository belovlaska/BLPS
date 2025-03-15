package ru.ifmo.is.lab1.applications.dto;

import lombok.Data;
import jakarta.validation.constraints.*;
import org.openapitools.jackson.nullable.JsonNullable;

@Data
public class ApplicationUpdateDto {
  @NotNull
  @NotBlank
  private JsonNullable<String> name;

  @NotBlank
  private JsonNullable<String> description;

  private JsonNullable<Float> monetization;

  @NotNull
  @NotBlank
  private JsonNullable<String> code;

  @Min(0)
  private JsonNullable<Integer> cost;

}
