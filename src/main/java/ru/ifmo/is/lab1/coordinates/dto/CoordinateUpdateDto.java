package ru.ifmo.is.lab1.coordinates.dto;

import lombok.Data;
import jakarta.validation.constraints.*;
import org.openapitools.jackson.nullable.JsonNullable;

@Data
public class CoordinateUpdateDto {
  @NotNull
  @Max(903)
  private JsonNullable<Float> x;

  @NotNull
  @Min(-901)
  private JsonNullable<Float> y;
}
